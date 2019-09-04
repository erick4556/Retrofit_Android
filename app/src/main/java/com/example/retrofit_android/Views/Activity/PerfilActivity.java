package com.example.retrofit_android.Views.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.retrofit_android.Data.Api.Api;
import com.example.retrofit_android.Data.Model.Curso;
import com.example.retrofit_android.Data.Preferences.SessionPreferences;
import com.example.retrofit_android.R;
import com.example.retrofit_android.Views.Adapter.CursoAdapter;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PerfilActivity extends AppCompatActivity {

    private SessionPreferences prefs;
    private TextView tvNombre, tvEmail;
    private ImageView imgEditar;

    private List<Curso> cursos;
    private RecyclerView cursoRecycler;
    private RecyclerView.Adapter adapter;
    private RecyclerView.LayoutManager layoutManager;
    private ProgressBar pbCarga;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_perfil);

        prefs = new SessionPreferences(getApplicationContext());

        getSupportActionBar().setTitle("Perfil");

        tvNombre = findViewById(R.id.tvNombre);
        tvEmail = findViewById(R.id.tvEmail);
        imgEditar = findViewById(R.id.imgEditar);

        cursoRecycler = findViewById(R.id.cursoRecyclerPerfil);
        pbCarga = findViewById(R.id.pbCarga);

        tvNombre.setText(prefs.getUsuario().getName());
        tvEmail.setText(prefs.getUsuario().getEmail());

        imgEditar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(PerfilActivity.this, EditarPerfilActivity.class);
                startActivity(intent);
            }
        });


        getCursos(prefs.getUsuario().getId());


    }

    private void getCursos(int id) {
        Call<List<Curso>> callCursos = Api.getApi().getCursosUser(id);
        //Ejecutar la consulta al servidor
        callCursos.enqueue(new Callback<List<Curso>>() {
            @Override
            public void onResponse(Call<List<Curso>> call, Response<List<Curso>> response) {

                cursos = response.body();
                pbCarga.setVisibility(View.GONE);
                adapter = new CursoAdapter(cursos, new CursoAdapter.OnItemClickListener() {
                    @Override
                    public void onItemClick(Curso curso, int position) {
                        Toast.makeText(PerfilActivity.this, "Imagen: " + curso.getIcono(), Toast.LENGTH_LONG).show();
                        irDetalle(cursos.get(position));
                    }
                });

                //Indicar que si la lista de elementos va estar dentro de un GridView o ListView
                layoutManager = new GridLayoutManager(getApplicationContext(), 2); //2 el tamaño de columnas por filas
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
