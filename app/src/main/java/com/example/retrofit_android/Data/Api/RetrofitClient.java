package com.example.retrofit_android.Data.Api;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.HttpUrl;
import okhttp3.Interceptor;


import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitClient {

    private static Retrofit retrofit = null;

    public static Retrofit getClient(String base_url) { //Le paso la url base
        if(retrofit == null) {
            retrofit = new Retrofit.Builder()
                    .baseUrl(base_url)
                    .client(getClient())
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return retrofit;
    }

    static private OkHttpClient getClient() {
        OkHttpClient client = new OkHttpClient.Builder()
                .connectTimeout(5, TimeUnit.MINUTES) //Tiempo que voy esperar para la conexión
                .readTimeout(5, TimeUnit.MINUTES)
                //Se intercepta la consulta que se esta haciendo a la url original y agregandole la api key al final procediendo a la siguiente consulta luego de haber agregado esto al final
                .addInterceptor(new Interceptor() {
                    @Override
                    public Response intercept(Chain chain) throws IOException {
                        Request origen = chain.request();
                        HttpUrl origenHttpUrl = origen.url(); //Para obtener la url del request o la consulta que se esta haciendo

                        HttpUrl url = origenHttpUrl.newBuilder()
                                .addQueryParameter("nombre", "apikey")
                                .build();

                        Request.Builder reqestBuilder = origen.newBuilder().url(url);
                        Request request = reqestBuilder.build();

                        return chain.proceed(request);
                    }
                })
                .build();

        return client;
    }
}
