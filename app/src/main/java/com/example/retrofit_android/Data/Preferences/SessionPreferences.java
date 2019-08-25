package com.example.retrofit_android.Data.Preferences;

import android.content.Context;
import android.content.SharedPreferences;

import com.example.retrofit_android.Data.Model.User;

public class SessionPreferences {

    private static final String PREFS_NAME = "SESSION";
    private final SharedPreferences prefs;
    //Constantes sesión
    private static final String PREF_ID = "PREF_ID";
    private static final String PREF_NOMBRE = "PREF_NAME";
    private static final String PREF_EMAIL = "PREF_EMAIL";
    private static final String PREF_SESSION = "PREF_SESSION";

    public SessionPreferences(Context context) {
        prefs = context.getApplicationContext().getSharedPreferences(PREFS_NAME,Context.MODE_PRIVATE); //Para obtener el contexto actual de la aplicación y despues le paso el getSharedPreferences(Nombre_Sesion, modo);
    }

    //Para ver si el usuario está logueado o no
    public boolean estaLogueado(){
        return prefs.getBoolean(PREF_SESSION,false); //Valor por defecto es false
    }

    public void guardarUsuario(User user){
        SharedPreferences.Editor editor = prefs.edit(); //Para comenzar la edición del sharedpreferences
        editor.putInt(PREF_ID,user.getId());
        editor.putString(PREF_NOMBRE,user.getName()); //Le paso como quiero que se guarde en el xml y el dato a guardar
        editor.putString(PREF_EMAIL,user.getEmail());
        //Validar si el usuario esta logueado o no
        editor.putBoolean(PREF_SESSION,true); //Cuando el usuario se guarde por primera vez, el usuario esta logueado y se guarda en true hasta que no se cierre sesión no cambia a false

        editor.apply(); //Aplicar los cambios al editor de preferencia
    }

    public void cerrarSesion(){
        SharedPreferences.Editor editor = prefs.edit(); //Para comenzar la edición del sharedpreferences
        editor.putInt(PREF_ID,0);
        editor.putString(PREF_NOMBRE,null);
        editor.putString(PREF_EMAIL,null);

        editor.putBoolean(PREF_SESSION,false);

        editor.apply();
    }

    public User getUsuario(){
        User objUser = new User();
        objUser.setId(prefs.getInt(PREF_ID,0));
        objUser.setName(prefs.getString(PREF_NOMBRE,""));
        objUser.setEmail(prefs.getString(PREF_EMAIL,""));

        return objUser;
    }

}
