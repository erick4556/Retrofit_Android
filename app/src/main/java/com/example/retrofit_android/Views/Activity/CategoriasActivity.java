package com.example.retrofit_android.Views.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.retrofit_android.Data.Api.Api;
import com.example.retrofit_android.Data.Model.Categoria;
import com.example.retrofit_android.R;
import com.example.retrofit_android.Views.Adapter.CategoriaAdapter;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CategoriasActivity extends AppCompatActivity {

    private RecyclerView categoriaRecycler;
    private CategoriaAdapter categoriaAdapter;
    private RecyclerView.LayoutManager layoutManager;
    private ProgressBar pbCarga;
    private List<Categoria> categorias;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_categorias);

        getSupportActionBar().setTitle("Categorias");

        pbCarga =  findViewById(R.id.progressBarCateg);
        categoriaRecycler =  findViewById(R.id.categRecycler);

        getCategorias();

    }

    public void getCategorias(){
        Call<List<Categoria>> calLCategorias = Api.getApi().getCategorias();
        calLCategorias.enqueue(new Callback<List<Categoria>>() {
            @Override
            public void onResponse(final Call<List<Categoria>> call, Response<List<Categoria>> response) {
                categorias  = response.body();
                pbCarga.setVisibility(View.GONE);
                categoriaAdapter = new CategoriaAdapter(categorias, new CategoriaAdapter.OnItemClickListener() {
                    @Override
                    public void onItemClick(Categoria categoria, int id) {
                        Toast.makeText(CategoriasActivity.this,"Nombre: "+categoria.getNombre(), Toast.LENGTH_LONG).show();
                        irCategoriaDetalle(categorias.get(id));
                    }
                });
                categoriaRecycler.setHasFixedSize(true);

                layoutManager =  new LinearLayoutManager(getApplicationContext() );

                categoriaRecycler.setLayoutManager(layoutManager);

                DividerItemDecoration divider = new DividerItemDecoration(getApplicationContext(), DividerItemDecoration.VERTICAL);
                divider.setDrawable(getResources().getDrawable(R.drawable.divider));

                categoriaRecycler.addItemDecoration(divider);

                categoriaRecycler.setAdapter(categoriaAdapter);

                categoriaAdapter.notifyDataSetChanged();
            }

            @Override
            public void onFailure(Call<List<Categoria>> call, Throwable t) {
                pbCarga.setVisibility(View.GONE);
            }
        });
    }

    public void irCategoriaDetalle(Categoria categoria){
        Intent i = new Intent(getApplicationContext(), CategoriaDetalleActivity.class);
        i.putExtra("id",categoria.getId());
        i.putExtra("categoria", categoria.getNombre());
        startActivity(i);
    }


}
