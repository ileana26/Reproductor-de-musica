package com.example.ejemplobottomnavigation;

import android.annotation.SuppressLint;
import android.content.ContentUris;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import androidx.recyclerview.widget.RecyclerView;

import com.example.ejemplobottomnavigation.ui.MyMediaPlayer;
import com.google.android.material.snackbar.Snackbar;

import java.io.File;
import java.util.ArrayList;

public class MusicListAdapter extends RecyclerView.Adapter<MusicListAdapter.ViewHolder>{

    ArrayList<AudioMode> lista;
    Context context;


    public MusicListAdapter(ArrayList<AudioMode> lista, Context context) {
        this.lista = lista;
        this.context = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(context).inflate(R.layout.recycler_item, parent, false);
        return new ViewHolder(view);

    }

    @Override
    public void onBindViewHolder(ViewHolder holder, @SuppressLint("RecyclerView") int position) {

        AudioMode songData = lista.get(position);
        ViewHolder viewHolder = (ViewHolder) holder;

        holder.titleTextView.setText(songData.getTitle());


        //imagen?
        Uri artworkUri = songData.getArtworkUri();

        if (artworkUri != null) {
            viewHolder.iconImageView.setImageURI(artworkUri);

            if (viewHolder.iconImageView.getDrawable() == null) {
                viewHolder.iconImageView.setImageResource(R.drawable.iconomusica);
            }
        }

        if (MyMediaPlayer.currentIndex == position) {
            holder.titleTextView.setTextColor(Color.parseColor("#FFFFFFFF"));
        } else {
            holder.titleTextView.setTextColor(Color.parseColor("#FFFFFFFF"));
        }


        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //A otra actividad(interfaz de reproducción)
                MyMediaPlayer.getInstance().reset();
                MyMediaPlayer.currentIndex = position;
                Intent intent = new Intent(context, MusicPalyer.class);
                intent.putExtra("LIST", lista);
                intent.setFlags(intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(intent);
            }
        });

        holder.menuMore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PopupMenu popupMenu = new PopupMenu(context, view);
                popupMenu.getMenuInflater().inflate(R.menu.popup, popupMenu.getMenu());
                popupMenu.show();
                popupMenu.setOnMenuItemClickListener((menuItem) -> {
                    switch (menuItem.getItemId()) {
                        case R.id.delete:
                            Toast.makeText(context, "Delete Clicked", Toast.LENGTH_SHORT).show();
                            deleteFile(position, view);
                            break;
                    }
                    return true;
                });
            }
        });
    }

    private void deleteFile(int position, View view){
        Uri contenturi = ContentUris.withAppendedId(MediaStore.Audio.Media.EXTERNAL_CONTENT_URI,
                Long.parseLong(lista.get(position).getId()));
        File file = new File(lista.get(position).getPath());
        boolean deleted = file.delete();
        if(deleted) {
            context.getContentResolver().delete(contenturi, null, null);
            lista.remove(position);
            notifyItemRemoved(position);
            notifyItemRangeChanged(position, lista.size());
            Snackbar.make(view, "Borrado", Snackbar.LENGTH_LONG).show();
        }else{
            Snackbar.make(view, "No se pudo borrar", Snackbar.LENGTH_LONG).show();
        }

    }

    @Override
    public int getItemCount() {
        return lista.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        TextView titleTextView;
        ImageView iconImageView, menuMore, pausa;
        TextView artist,cancion;
        int a = 0;


        public ViewHolder(View itemView) {
            super(itemView);
            titleTextView = itemView.findViewById(R.id.song);
            iconImageView = itemView.findViewById(R.id.music_img);
            artist = itemView.findViewById(R.id.song_artist);
            menuMore = itemView.findViewById(R.id.menu);
            cancion = itemView.findViewById(R.id.song_title);
        }
    }

}
