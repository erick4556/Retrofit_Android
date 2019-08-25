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
import com.example.retrofit_android.Data.Model.User;
import com.example.retrofit_android.Data.Preferences.SessionPreferences;
import com.example.retrofit_android.R;

import java.util.regex.Pattern;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RegistroActivity extends AppCompatActivity {

    private EditText etNombre,etEmail, etPassword;
    private TextView tvIniciar;
    private Button btnRegistrar;
    private ProgressDialog progressDialog;
    private SessionPreferences prefs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro);
        prefs = new SessionPreferences(this);
        etNombre = (EditText)findViewById(R.id.etNombre);
        etEmail = (EditText)findViewById(R.id.etEmail);
        etPassword = (EditText)findViewById(R.id.etPassword);
        tvIniciar = (TextView)findViewById(R.id.tvIniciar);
        btnRegistrar = (Button)findViewById(R.id.btnRegistrarse);

        tvIniciar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                startActivity(new Intent(getApplicationContext(),MainActivity.class));
            }
        });

        btnRegistrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                progressDialog = ProgressDialog.show(RegistroActivity.this,"Creando cuenta","Comprobando credenciales...",true,false);//true es para decirle que el tiempo es indeterminado, false para que no sea cancelable, que no se pueda cerrar el dialogo
                if(validar()){
                    /*User objuser = new User();
                    objuser.setName(etNombre.getText().toString());
                    objuser.setEmail(etEmail.getText().toString());
                    objuser.setPassword(etPassword.getText().toString());*/
                    User objUser = new User(etNombre.getText().toString(),etEmail.getText().toString(),etPassword.getText().toString());
                    registrar(objUser);
                }else{
                    progressDialog.dismiss();//Ocultar el dialogo
                }
            }
        });


    }

    private void registrar(User user) {

        Call<User> callRegistro = Api.getApi().registar(user);
        callRegistro.enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                progressDialog.dismiss(); //Se oculte cuando se ejecuta la petición
                if(response.isSuccessful()){ //Valida que la petición sea 200
                    msjToast("Usuario Registrado "+response.body().getId());
                    /*etNombre.setText("");
                    etEmail.setText("");
                    etPassword.setText("");*/
                    prefs.guardarUsuario(response.body());//Ese reponse.body() es como si guardara el objeto User
                    Intent intent = new Intent(RegistroActivity.this, PrincipalActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(intent);
                }else{
                    msjToast("No se ha podido registrar tu cuenta");
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
        String nombre = etNombre.getText().toString();

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

        if(nombre.isEmpty()){
            valido = false;
            etNombre.setError("El nombre es requerido");
        }else{
            etNombre.setError(null);
        }

        return  valido;

    }

    private boolean validateEmail(String email){
        Pattern pattern = Patterns.EMAIL_ADDRESS;
        return pattern.matcher(email).matches();
    }

    private void msjToast(String mensaje){
        Toast.makeText(this,mensaje,Toast.LENGTH_LONG).show();
    }

}
