package com.example.ejemplobottomnavigation.ui.home;

import android.content.ContentUris;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
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

public class HomeFragment extends Fragment {

    RecyclerView recyclerView;
    TextView noMusicTV;
    ArrayList<AudioMode> songsList = new ArrayList<>();
    public static Context contextOfApplication;
    public static Context getContextOfApplication()
    {
        return contextOfApplication;
    }

private FragmentHomeBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
            ViewGroup container, Bundle savedInstanceState) {
        HomeViewModel homeViewModel =
                new ViewModelProvider(this).get(HomeViewModel.class);

    binding = FragmentHomeBinding.inflate(inflater, container, false);
    View root = binding.getRoot();

        final TextView textView = binding.TituloLista;
        homeViewModel.getText().observe(getViewLifecycleOwner(), textView::setText);


        recyclerView = root.findViewById(R.id.recyclerView);
        noMusicTV = root.findViewById(R.id.Error_not_found);

        String[] projection = {
                MediaStore.Audio.Media.TITLE,
                MediaStore.Audio.Media.DATA,
                MediaStore.Audio.Media.DURATION,
                MediaStore.Audio.Media.ALBUM,
                MediaStore.Audio.Media.ARTIST,
                MediaStore.Audio.Media._ID
        };

        String selection = MediaStore.Audio.Media.IS_MUSIC + " !=0";

        Cursor cursor = getActivity().getContentResolver().query(MediaStore.Audio.Media.EXTERNAL_CONTENT_URI, projection, selection, null, null);

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
            recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
            recyclerView.setAdapter(new MusicListAdapter(songsList, getContext()));
        }
        return root;


    }

@Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}