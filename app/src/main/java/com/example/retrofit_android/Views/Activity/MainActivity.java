package com.example.retrofit_android.Views.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.retrofit_android.Data.Api.Api;
import com.example.retrofit_android.Data.Model.LoginBody;
import com.example.retrofit_android.Data.Model.User;
import com.example.retrofit_android.Data.Preferences.SessionPreferences;
import com.example.retrofit_android.R;

import java.util.regex.Pattern;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    private EditText etEmail, etPassword;
    private Button btnLogin;
    private TextView tvRegistro;
    private ProgressDialog progressDialog;
    private SessionPreferences prefs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        prefs = new SessionPreferences(this);

        etEmail = (EditText)findViewById(R.id.etEmail);
        etPassword = (EditText)findViewById(R.id.etPassword);
        btnLogin = (Button)findViewById(R.id.btnIniciarS);
        tvRegistro = (TextView)findViewById(R.id.tvRegistro);

        tvRegistro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               /* Intent i = new Intent(getApplicationContext(),RegistroActivity.class);
                startActivity(i);*/
               startActivity(new Intent(getApplicationContext(),RegistroActivity.class));
            }
        });

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                progressDialog = ProgressDialog.show(MainActivity.this,"Inciando sesión","Comprobando credenciales...",true,false);//true es para decirle que el tiempo es indeterminado, false para que no sea cancelable, que no se pueda cerrar el dialogo
                if(validar()){
                    login(etEmail.getText().toString(),etPassword.getText().toString());
                }else{
                    progressDialog.dismiss();//Ocultar el dialogo
                }

            }
        });

    }

    private void login(String email, String password){

        LoginBody objLogin = new LoginBody(email,password);
        Call<User> callLogin = Api.getApi().login(objLogin);

        callLogin.enqueue(new Callback<User>() { //Ejecutar la llamada al servidor
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                progressDialog.dismiss(); //Se oculte cuando se ejecuta la petición
                if(response.isSuccessful()){ //Valida que la petición sea 200
                    msjToast("Usuario logueado ");
                   // User _objuser = response.body();
                    prefs.guardarUsuario(response.body());//Ese reponse.body() es como si guardara el objeto User
                    Intent intent = new Intent(MainActivity.this, PrincipalActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);//Para cuando este logueado y de flecha atrás no pueda devolver al login. Crea una nueva tarea y borra todas las actividades que he pasado anteriormente
                    startActivity(intent);
                }else{
                  msjToast("Usuario/Password incorrectos");
                  etPassword.setText("");
                  etPassword.setError("Password incorrecto");
              }
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {
                progressDialog.dismiss();
                msjToast("Error al comunicar al servidor");
            }
        });

    }

    private boolean validar(){
        boolean valido = true;
        String email = etEmail.getText().toString();
        String password = etPassword.getText().toString();

        if(email.isEmpty() || !validateEmail(email)){
            valido = false;
            etEmail.setError("El email es requerido");
        }else{
            etEmail.setError(null);
        }

        if(password.isEmpty()){
            valido = false;
            etPassword.setError("La contraseña es requerida");
        }else{
            etPassword.setError(null);
        }

        return  valido;

    }

    //Validar email
    private boolean validateEmail(String email){
        Pattern pattern = Patterns.EMAIL_ADDRESS;
        return pattern.matcher(email).matches();
    }

    private void msjToast(String mensaje){
        Toast.makeText(this,mensaje,Toast.LENGTH_LONG).show();
    }

}
