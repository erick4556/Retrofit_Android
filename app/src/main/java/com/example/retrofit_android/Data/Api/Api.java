package com.example.retrofit_android.Data.Api;

public class Api {

    private static final String BASE_URL = "ruta";


    public static ApiRoutes getApi(){
        return RetrofitClient.getClient(BASE_URL).create(ApiRoutes.class);
    }

}
