package com.example.retrofit_android.Views.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.retrofit_android.Data.Model.Curso;
import com.example.retrofit_android.Data.Preferences.SessionPreferences;
import com.example.retrofit_android.R;

import java.util.List;

public class PerfilActivity extends AppCompatActivity {

    private SessionPreferences prefs;
    private TextView tvNombre,tvEmail;
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
        imgEditar= findViewById(R.id.imgEditar);

        tvNombre.setText(prefs.getUsuario().getName());
        tvEmail.setText(prefs.getUsuario().getEmail());

        imgEditar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(PerfilActivity.this, EditarPerfilActivity.class);
                startActivity(intent);
            }
        });


    }
}
