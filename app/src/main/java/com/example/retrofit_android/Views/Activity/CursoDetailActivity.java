package com.example.retrofit_android.Views.Activity;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;
import android.widget.TableLayout;

import com.example.retrofit_android.Data.Api.Api;
import com.example.retrofit_android.Data.Model.Curso;
import com.example.retrofit_android.R;
import com.example.retrofit_android.Views.Adapter.ViewPagerAdapter;
import com.example.retrofit_android.Views.Fragments.DetalleCurso;
import com.example.retrofit_android.Views.Fragments.DetalleVideo;
import com.google.android.material.tabs.TabLayout;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CursoDetailActivity extends AppCompatActivity {
    private TabLayout tabCurso;
    private ViewPager pagerCurso;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_curso_detail);

        //Boton de atrás
        ActionBar actionBar = getSupportActionBar();
        actionBar.setHomeButtonEnabled(true);
        actionBar.setDisplayHomeAsUpEnabled(true);

        getSupportActionBar().setTitle("Retrofit");

        //Eliminar la sombra
        actionBar.setElevation(0);

        pagerCurso = (ViewPager)findViewById(R.id.pagerCurso);
        setupViewPager(pagerCurso); //Ejecuto la funcion

        tabCurso = (TabLayout)findViewById(R.id.tabCurso);

        tabCurso.setupWithViewPager(pagerCurso); //Asignarle al tablayout el viewpager

    }

    private void setupViewPager(ViewPager viewPager){
        ViewPagerAdapter viewPagerAdapter = new ViewPagerAdapter(getSupportFragmentManager());
        viewPagerAdapter.addFragment(new DetalleCurso(),"DETALLE"); //Paso cada uno de los fragments que va tener cada una de las pantallas
        viewPagerAdapter.addFragment(new DetalleVideo(),"VIDEOS");
        //Agregar el adapter al viewpager
        viewPager.setAdapter(viewPagerAdapter);
    }



}
