package com.example.retrofit_android.Data.Model;

public class User {

    private int id;
    private String name;
    private String email;
    private String password;

    public User(){

    }

    //Constructor para registrar
    public User(String name, String email, String pass){
        this.name = name;
        this.email = email;
        this.password = pass;
    }

    //Constructor para editar
    /*public User(int id,String name, String email, String pass){
        this.id = id;
        this.name = name;
        this.email = email;
        this.password = pass;
    }*/

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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
