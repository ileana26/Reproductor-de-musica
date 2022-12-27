package com.example.ejemplobottomnavigation;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.ejemplobottomnavigation.ui.MyMediaPlayer;

import java.util.ArrayList;

public class AlbumListAdapter extends RecyclerView.Adapter<AlbumListAdapter.ViewHolder>{

    ArrayList<AlbumModel> lista;
    Context context;

    public AlbumListAdapter(ArrayList<AlbumModel> lista, Context context) {
        this.lista = lista;
        this.context = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.recycler_album_item, parent, false);
        return new AlbumListAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, @SuppressLint("RecyclerView") int position) {
        AlbumModel albumData = lista.get(position);
        holder.titleTextView.setText(albumData.getTitle());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //A otra actividad(interfaz de reproducción)
                MyMediaPlayer.getInstance().reset();
                MyMediaPlayer.currentIndex = position;
                Intent intent = new Intent(context,AlbumList.class);
                intent.putExtra("LIST", lista);
                intent.putExtra("NAMAE",albumData.getTitle().toString());
                intent.setFlags(intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return lista.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        TextView titleTextView;
        ImageView iconImageView;

        public ViewHolder(View itemView) {
            super(itemView);
            titleTextView = itemView.findViewById(R.id.song);
            iconImageView = itemView.findViewById(R.id.music_img);
        }
    }
}
