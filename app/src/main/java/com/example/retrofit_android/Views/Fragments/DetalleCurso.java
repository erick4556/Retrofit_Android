package com.example.retrofit_android.Views.Fragments;


import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.retrofit_android.Data.Api.Api;
import com.example.retrofit_android.Data.Model.Curso;
import com.example.retrofit_android.Data.Model.ServerResponse;
import com.example.retrofit_android.Data.Preferences.SessionPreferences;
import com.example.retrofit_android.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.squareup.picasso.Picasso;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 */
public class DetalleCurso extends Fragment {

    ImageView imgPortada;
    TextView tvTiulo, tvDescripcion;
    private SessionPreferences prefs;

    public DetalleCurso() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        prefs = new SessionPreferences(getContext());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_detalle_curso, container, false);
        imgPortada = (ImageView)view.findViewById(R.id.imgPortada);
        tvTiulo = (TextView)view.findViewById(R.id.tvTitulo);
        tvDescripcion = (TextView)view.findViewById(R.id.tvDescripcion);

        FloatingActionButton fab = view.findViewById(R.id.btnFavaorito);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               agregarCurso(view);
            }
        });

        getCursoId();

        return view;
    }

    public void getCursoId(){
        Call<Curso> callCurso = Api.getApi().getCursos(getActivity().getIntent().getIntExtra("id",0));
        callCurso.enqueue(new Callback<Curso>() {
            @Override
            public void onResponse(Call<Curso> call, Response<Curso> response) {
                Log.d("TEST","Nombre del curso "+response.body().getNombre());
                Picasso.get().load(response.body().getPortada()).into(imgPortada);
                tvTiulo.setText(response.body().getNombre());
                tvDescripcion.setText(response.body().getDesc());
            }

            @Override
            public void onFailure(Call<Curso> call, Throwable t) {

            }
        });
    }

    private void agregarCurso(final View view){
        Call<ServerResponse> callUserCurso = Api.getApi().agregarCurso(prefs.getUsuario().getId(), getActivity().getIntent().getIntExtra("id",0));
        callUserCurso.enqueue(new Callback<ServerResponse>() {
            @Override
            public void onResponse(Call<ServerResponse> call, Response<ServerResponse> response) {
                    Snackbar.make(view, response.body().getMessage(), Snackbar.LENGTH_LONG).show();
            }

            @Override
            public void onFailure(Call<ServerResponse> call, Throwable t) {
                Snackbar.make(view, "Error al conectarse al servidor", Snackbar.LENGTH_LONG).show();
            }
        });
    }

}
