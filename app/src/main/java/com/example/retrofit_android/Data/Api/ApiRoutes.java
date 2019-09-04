package com.example.retrofit_android.Data.Api;

import com.example.retrofit_android.Data.Model.Categoria;
import com.example.retrofit_android.Data.Model.Curso;
import com.example.retrofit_android.Data.Model.LoginBody;
import com.example.retrofit_android.Data.Model.Profesor;
import com.example.retrofit_android.Data.Model.ServerResponse;
import com.example.retrofit_android.Data.Model.User;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface ApiRoutes {


    @POST("login")
    Call<User> login(@Body LoginBody loginBody);  //<User> Que retorne una clase User. @Body LoginBody la forma que envio los datos al servidor en forma de una clase

    @POST("register")
    Call<User> registar(@Body User user);

    @GET("cursos")
    Call<List<Curso>> getCursos();

    @GET("curso/{id}")
    Call<Curso> getCursos(@Path("id")int id); //int id el tipo de dato que estoy pasando

    @GET("categorias")
    Call<List<Categoria>> getCategorias();

    @GET("categoria/cursos/{id}")
    Call<List<Curso>> getCategoriaDetalle(@Path("id")int id);

    @GET("profesores")
    Call<List<Profesor>> getProfesores();

    @GET("profesor/cursos/{id}")
    Call<List<Curso>> getProfesorDetalle(@Path("id")int id);

    @GET("user/{id}")
    Call<User> getUser(@Path("id")int id);

    @POST("user/edit") //OJO la actualizacion la hago sin pasarle id por que se va los datos por parámetros, pero lo puedo hacer pasandole id
    Call<User> editarUser(@Body User user);

    @POST("user/curso/{user_id}/{curso_id}")
    Call<ServerResponse> agregarCurso(@Path("user_id") int user_id, @Path("curso_id") int curso_id);

    @GET("curso/user/{id}")
    Call<List<Curso>> getCursosUser(@Path("id")int id);

}
