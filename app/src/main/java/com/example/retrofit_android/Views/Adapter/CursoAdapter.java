package com.example.retrofit_android.Views.Adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.retrofit_android.Data.Model.Curso;
import com.example.retrofit_android.R;
import com.squareup.picasso.Picasso;

import java.util.List;

public class CursoAdapter extends RecyclerView.Adapter<CursoAdapter.ViewHolder> {

    private List<Curso> cursos;
    private OnItemClickListener onItemClickListener;

    public CursoAdapter(List<Curso> cursos, OnItemClickListener onItemClickListener) {
        this.cursos = cursos;
        this.onItemClickListener = onItemClickListener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemview = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_curso,parent,false);
        return new ViewHolder(itemview);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
       /* final Curso objCursos = cursos.get(position); //constante

        holder.tvTitulo.setText(objCursos.getNombre());
        holder.tvProfesor.setText(objCursos.getProfesor().getNombre());
        holder.tvDescripcion.setText(objCursos.getDesc());

           Picasso.get().load(objCursos.getIcono()).into(holder.imgIcono);
        Picasso.get().load(objCursos.getProfesor().getFoto()).into(holder.imgProfesor);*/
        holder.bind(cursos.get(position),onItemClickListener);
    }

    @Override
    public int getItemCount() {
        return cursos.size();
       /* int i = 0;
        if(cursos.size() > 0 ){
            i = cursos.size();
        }

        return i;*/
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        ImageView imgIcono,imgProfesor;
        TextView tvTitulo,tvDescripcion,tvProfesor;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imgIcono = (ImageView)itemView.findViewById(R.id.imgIcono);
            imgProfesor = (ImageView)itemView.findViewById(R.id.profile_image);
            tvTitulo = (TextView)itemView.findViewById(R.id.tvTitulo);
            tvDescripcion = (TextView)itemView.findViewById(R.id.tvDescrip);
            tvProfesor = (TextView)itemView.findViewById(R.id.tvProfesor);

        }

        public void bind(final Curso curso, final OnItemClickListener listener){

            tvTitulo.setText(curso.getNombre());
            tvProfesor.setText(curso.getProfesor().getNombre());
            tvDescripcion.setText(curso.getDesc());

            Picasso.get().load(curso.getIcono()).fit().centerCrop().into(imgIcono);
            Picasso.get().load(curso.getProfesor().getFoto()).into(imgProfesor);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    listener.onItemClick(curso,getAdapterPosition());

                }
            });

        }


    }

    public interface OnItemClickListener{
        void onItemClick (Curso curso, int position);
    }

}
