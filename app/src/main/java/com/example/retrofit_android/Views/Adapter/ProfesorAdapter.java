package com.example.retrofit_android.Views.Adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.retrofit_android.Data.Model.Categoria;
import com.example.retrofit_android.Data.Model.Profesor;
import com.example.retrofit_android.R;
import com.squareup.picasso.Picasso;

import java.util.List;

public class ProfesorAdapter extends RecyclerView.Adapter<ProfesorAdapter.ViewHolder> {

    private List<Profesor> profesores;

    private OnItemClickListener onItemClickListener;

    public ProfesorAdapter(List<Profesor> profesores, OnItemClickListener onItemClickListener){
        this.profesores = profesores;
        this.onItemClickListener = onItemClickListener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemview = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_profesor,parent,false);
        return new ViewHolder(itemview);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.bind(profesores.get(position), onItemClickListener);
    }

    @Override
    public int getItemCount() {
        return profesores.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        ImageView imagenProfesor;
        TextView  nombreProfesor;
        TextView  correoProfesor;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imagenProfesor = itemView.findViewById(R.id.imgProfesor);
            nombreProfesor = itemView.findViewById(R.id.tvNombre);
            correoProfesor = itemView.findViewById(R.id.tvEmail);

        }

        public void bind(final Profesor profesor, final OnItemClickListener listener){

            Picasso.get().load(profesor.getFoto()).into(imagenProfesor);
            nombreProfesor.setText(profesor.getNombre());
            correoProfesor.setText(profesor.getEmail());

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    listener.onItemClick(profesor,getAdapterPosition());
                }
            });
        }
    }

    public interface OnItemClickListener{
        void onItemClick(Profesor profesor, int position);
    }

}


