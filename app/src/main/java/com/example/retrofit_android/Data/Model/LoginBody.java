package com.example.retrofit_android.Data.Model;

public class LoginBody {

    private String email;
    private String password;

    public LoginBody() { //Este constructor vacío es para poder tener la opción de crear el LoginBody de manera que pueda enviar los parámetros o no
    }

    public LoginBody(String email, String password) {
        this.email = email;
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
