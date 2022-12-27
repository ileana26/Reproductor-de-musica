package com.example.ejemplobottomnavigation;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class NowPlaying extends Fragment {

    ImageView nextBtn, album;
    TextView artist, songname;
    FloatingActionButton playPause;
    View view;

    public NowPlaying() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view =inflater.inflate(R.layout.fragment_now_playing, container, false);

        artist= view.findViewById(R.id.song_artist);
        songname = view.findViewById(R.id.song_title);
        album = view.findViewById(R.id.imageView);
        playPause = view.findViewById(R.id.pause_play);


        return view;
    }
}