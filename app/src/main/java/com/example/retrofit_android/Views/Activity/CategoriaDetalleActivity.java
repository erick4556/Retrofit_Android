package com.example.retrofit_android.Views.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.retrofit_android.Data.Api.Api;
import com.example.retrofit_android.Data.Model.Curso;
import com.example.retrofit_android.R;
import com.example.retrofit_android.Views.Adapter.CursoAdapter;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CategoriaDetalleActivity extends AppCompatActivity {

    private RecyclerView cursoRecycler;
    private RecyclerView.Adapter adapter;
    private RecyclerView.LayoutManager layoutManager;
    private List<Curso> cursos = new ArrayList<>();

    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_categoria_detalle);

        String categoria = getIntent().getStringExtra("categoria");

        getSupportActionBar().setTitle(categoria);

        cursoRecycler = findViewById(R.id.categDetaRecycler);
        progressBar = findViewById(R.id.progressBarCategDeta);

        getCursos(getIntent().getIntExtra("id", 0));

    }

    private void getCursos(int id){
        Call<List<Curso>> callCursos = Api.getApi().getCategoriaDetalle(id);
        //Eejcutar la consulta al servidor
        callCursos.enqueue(new Callback<List<Curso>>() {
            @Override
            public void onResponse(Call<List<Curso>> call, Response<List<Curso>> response) {

                cursos = response.body();
                progressBar.setVisibility(View.GONE);
                adapter = new CursoAdapter(cursos, new CursoAdapter.OnItemClickListener() {
                    @Override
                    public void onItemClick(Curso curso, int position) {
                        Toast.makeText(CategoriaDetalleActivity.this,"Imagen: "+curso.getIcono(), Toast.LENGTH_LONG).show();
                        irDetalle(cursos.get(position));
                    }
                });
                //Indico que el recycler va tener un tamaño predefinido, no va poder moverse
                cursoRecycler.setHasFixedSize(true);
                //Indicar que si la lista de elementos va estar dentro de un GridView o ListView
                layoutManager = new GridLayoutManager(getApplicationContext(),2); //2 el tamaño de columnas por filas
                cursoRecycler.setLayoutManager(layoutManager);
                cursoRecycler.setAdapter(adapter);
                adapter.notifyDataSetChanged();

            }

            @Override
            public void onFailure(Call<List<Curso>> call, Throwable t) {

            }
        });

    }


    public void irDetalle(Curso curso){
        Intent i = new Intent(getApplicationContext(),CursoDetailActivity.class);
        i.putExtra("id",curso.getId());
        startActivity(i);
    }


}
