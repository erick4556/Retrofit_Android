package com.example.retrofit_android.Views.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.retrofit_android.Data.Api.Api;
import com.example.retrofit_android.Data.Model.Categoria;
import com.example.retrofit_android.Data.Model.Profesor;
import com.example.retrofit_android.R;
import com.example.retrofit_android.Views.Adapter.ProfesorAdapter;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProfesoresActivity extends AppCompatActivity {

    
    private RecyclerView profRecycler;
    private ProfesorAdapter profesorAdapter;
    private RecyclerView.LayoutManager layoutManager;
    private ProgressBar pbCarga;
    private List<Profesor> profesores;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profesor);

        getSupportActionBar().setTitle("Profesores");

        pbCarga =  findViewById(R.id.profprogressBar);
        profRecycler =  findViewById(R.id.profRecycler);

        getProfesores();

    }

    public void getProfesores(){

        Call<List<Profesor>> callProfesores = Api.getApi().getProfesores();
        callProfesores.enqueue(new Callback<List<Profesor>>() {
            @Override
            public void onResponse(Call<List<Profesor>> call, Response<List<Profesor>> response) {
                profesores  = response.body();
                pbCarga.setVisibility(View.GONE);
                profesorAdapter = new ProfesorAdapter(profesores, new ProfesorAdapter.OnItemClickListener() {
                    @Override
                    public void onItemClick(Profesor profesor, int position) {
                        Toast.makeText(getApplicationContext(),"Nombre: "+profesor.getNombre(), Toast.LENGTH_LONG).show();
                        irProfesorDetalle(profesores.get(position));
                    }
                });
                profRecycler.setHasFixedSize(true);

                layoutManager =  new GridLayoutManager(getApplicationContext(), 2 );

                profRecycler.setLayoutManager(layoutManager);

                DividerItemDecoration divider = new DividerItemDecoration(getApplicationContext(), DividerItemDecoration.VERTICAL);
                divider.setDrawable(getResources().getDrawable(R.drawable.divider));

                profRecycler.addItemDecoration(divider);

                profRecycler.setAdapter(profesorAdapter);

                profesorAdapter.notifyDataSetChanged();
            }

            @Override
            public void onFailure(Call<List<Profesor>> call, Throwable t) {

            }
        });
        
    }

    public void irProfesorDetalle(Profesor profesor){
        Intent i = new Intent(getApplicationContext(), ProfesorDetalleActivity.class);
        i.putExtra("id",profesor.getId());
        i.putExtra("profesor", profesor.getNombre());
        startActivity(i);
    }

}
