package com.example.retrofit_android.Data.Api;

public class Api {

    private static final String BASE_URL = "route";


    public static ApiRoutes getApi(){
        return RetrofitClient.getClient(BASE_URL).create(ApiRoutes.class);
    }

}
