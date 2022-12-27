package com.example.ejemplobottomnavigation;

import android.content.ContentUris;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.database.Cursor;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.ejemplobottomnavigation.AudioMode;
import com.example.ejemplobottomnavigation.MusicListAdapter;
import com.example.ejemplobottomnavigation.R;
import com.example.ejemplobottomnavigation.databinding.FragmentHomeBinding;

import java.io.File;
import java.util.ArrayList;

public class ArtistList extends AppCompatActivity {

    RecyclerView recyclerView;
    TextView noMusicTV;
    ArrayList<AudioMode> songsList = new ArrayList<>();

    private static final String TAG = "MyActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_home);

        Bundle bundle = getIntent().getExtras();
        recyclerView = findViewById(R.id.recyclerView);
        noMusicTV = findViewById(R.id.Error_not_found);

        String[] projection = {
                MediaStore.Audio.Media.TITLE,
                MediaStore.Audio.Media.DATA,
                MediaStore.Audio.Media.DURATION,
                MediaStore.Audio.Media.ALBUM,
                MediaStore.Audio.Media.ARTIST,
                MediaStore.Audio.Media._ID
        };

        String selection = MediaStore.Audio.Media.ARTIST + " =?";

        String WhereVal[] = {bundle.getString("NAMAE")};

        Log.i(TAG,bundle.getString("NAMAE"));

        Cursor cursor = getContentResolver().query(MediaStore.Audio.Media.EXTERNAL_CONTENT_URI,projection,selection,WhereVal,null);

        int albumColumn = cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.ALBUM);

        while (cursor.moveToNext()) {

            long albumId = cursor.getLong(albumColumn);


            Uri albumArtworkUri = ContentUris.withAppendedId(Uri.parse("content://media/external/audio/albumart"), albumId);

            AudioMode songData = new AudioMode(cursor.getString(1), cursor.getString(0), cursor.getString(2), cursor.getString(3), cursor.getString(4));

            if (new File(songData.getPath()).exists()) {
                songsList.add(songData);
            }
        }

        if (songsList.size() == 0) {
            noMusicTV.setVisibility(View.VISIBLE);
        } else {
            //recycleview
            recyclerView.setLayoutManager(new LinearLayoutManager(this));
            recyclerView.setAdapter(new MusicListAdapter(songsList, getApplicationContext()));
        }
    }


}
