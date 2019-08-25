package com.example.retrofit_android.Views.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.retrofit_android.Data.Api.Api;
import com.example.retrofit_android.Data.Model.User;
import com.example.retrofit_android.Data.Preferences.SessionPreferences;
import com.example.retrofit_android.R;

import java.util.regex.Pattern;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class EditarPerfilActivity extends AppCompatActivity {

    private EditText etNombre,etEmail,etPassword;
    private Button btnEditar;
    private SessionPreferences prefs;
    private User user;
    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editar_perfil);

        getSupportActionBar().setTitle("Editar datos");


        prefs = new SessionPreferences(getApplicationContext());

        etNombre = findViewById(R.id.etNombre);
        etEmail = findViewById(R.id.etEmail);
        etPassword = findViewById(R.id.etPassword);
        btnEditar = findViewById(R.id.btnEditar);

        getDatos();

        btnEditar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                progressDialog = ProgressDialog.show(EditarPerfilActivity.this,"Modificando cuenta","Comprobando credenciales...",true,false);//true es para decirle que el tiempo es indeterminado, false para que no sea cancelable, que no se pueda cerrar el dialogo
                if(validar()){
                    //De esta forma no se puede
                    //User objUser = new User(prefs.getUsuario().getId(),etNombre.getText().toString(),etEmail.getText().toString(),etPassword.getText().toString());
                    User objUser = new User();
                    objUser.setId(prefs.getUsuario().getId());
                    objUser.setName(etNombre.getText().toString());
                    objUser.setEmail(etEmail.getText().toString());
                    if(!TextUtils.isEmpty(etPassword.getText().toString().trim())){
                        objUser.setPassword(etPassword.getText().toString());
                    }
                    editar(objUser);
                }else{
                    progressDialog.dismiss();//Ocultar el dialogo
                }
            }
        });

    }

    private void getDatos(){
        Call<User> userCall = Api.getApi().getUser(prefs.getUsuario().getId());
        userCall.enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                    user = response.body();
                    etNombre.setText(user.getName());
                    etEmail.setText(user.getEmail());

               }

            @Override
            public void onFailure(Call<User> call, Throwable t) {
                progressDialog.dismiss();
                msjToast("Error al comunicar al servidor");
            }
        });
    }
    
    

    private void editar(User user) {

        Call<User> callEditar = Api.getApi().editarUser(user);
        callEditar.enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                progressDialog.dismiss(); //Se oculte cuando se ejecuta la petición
                if(response.isSuccessful()){ //Valida que la petición sea 200
                    msjToast("Usuario Editado "+response.body().getId());
                    prefs.guardarUsuario(response.body());//Ese reponse.body() es como si guardara el objeto User
                    Intent intent = new Intent(EditarPerfilActivity.this, PerfilActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(intent);
                }else{
                    msjToast("No se ha podido editar el usuario");
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

       /* if(password.isEmpty()){
            valido = false;
            etPassword.setError("La contraseña es requerida");
        }else{
            etPassword.setError(null);
        }*/

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
