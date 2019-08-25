package com.example.retrofit_android.Views.Activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;

import com.example.retrofit_android.Data.Api.Api;
import com.example.retrofit_android.Data.Model.Curso;
import com.example.retrofit_android.Data.Preferences.SessionPreferences;
import com.example.retrofit_android.R;
import com.example.retrofit_android.Views.Adapter.CursoAdapter;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import android.os.Handler;
import android.view.View;

import androidx.core.view.GravityCompat;
import androidx.appcompat.app.ActionBarDrawerToggle;

import android.view.MenuItem;

import com.google.android.material.navigation.NavigationView;

import androidx.drawerlayout.widget.DrawerLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.Menu;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PrincipalActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private SessionPreferences prefs;
    private ProgressDialog progressDialog;

    private RecyclerView cursoRecycler;
    private RecyclerView.Adapter adapter;
    private RecyclerView.LayoutManager layoutManager;
    private List<Curso> cursos = new ArrayList<>();

    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_principal);

        prefs = new SessionPreferences(this);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setTitle("Retrofit");

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        navigationView.setNavigationItemSelectedListener(this);

        View header = navigationView.getHeaderView(0);
        TextView tvNombre = (TextView)header.findViewById(R.id.textUser);
        TextView tvEmail = (TextView)header.findViewById(R.id.textEmail);
        cursoRecycler = findViewById(R.id.cursoRecycler);

        tvNombre.setText(prefs.getUsuario().getName());
        tvEmail.setText(prefs.getUsuario().getEmail());
        progressBar = findViewById(R.id.progressBar);

        getCursos();


    }

    private void getCursos(){
        Call<List<Curso>> callCursos = Api.getApi().getCursos();
        //Eejcutar la consulta al servidor
        callCursos.enqueue(new Callback<List<Curso>>() {
            @Override
            public void onResponse(Call<List<Curso>> call, Response<List<Curso>> response) {
               /* Log.d("Test",response.body().get(0).getNombre());
                Log.d("Test",response.body().get(0).getDesc());
                Log.d("Test",response.body().get(0).getProfesor().getNombre());*/

              /* cursos = response.body();
               adapter = new CursoAdapter(cursos);
               cursoRecycler.setAdapter(adapter);
               adapter.notifyDataSetChanged();//Para notificar que la data ha cambeado*/

                cursos = response.body();
                progressBar.setVisibility(View.GONE);
                adapter = new CursoAdapter(cursos, new CursoAdapter.OnItemClickListener() {
                    @Override
                    public void onItemClick(Curso curso, int position) {
                        //Toast.makeText(PrincipalActivity.this,"Imagen: "+curso.getIcono(), Toast.LENGTH_LONG).show();
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

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.principal, menu);
        return true;
    }



    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_home) {
            // Handle the camera action
        } else if (id == R.id.nav_Categorias) {
            Intent i = new Intent(PrincipalActivity.this, CategoriasActivity.class);
            startActivity(i);
        } else if (id == R.id.nav_Profesores) {
            Intent i = new Intent(PrincipalActivity.this, ProfesoresActivity.class);
            startActivity(i);
        } else if (id == R.id.nav_usuario) {
            Intent i = new Intent(PrincipalActivity.this, PerfilActivity.class);
            startActivity(i);
        }  else if (id == R.id.cerra_sesion) {
            progressDialog = ProgressDialog.show(PrincipalActivity.this,"Cerrando sesión","Borrando datos...",true,false);//true es para decirle que el tiempo es indeterminado, false para que no sea cancelable, que no se pueda cerrar el dialogo
            prefs.cerrarSesion();
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    progressDialog.dismiss();
                    Intent intent = new Intent(PrincipalActivity.this, MainActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);//Para cuando este logueado y la flecha atrás no pueda devolver al login. Crea una nueva tarea y borra todas las actividades que he pasado anteriormente
                    startActivity(intent);
                }
            },3000);

        }

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
