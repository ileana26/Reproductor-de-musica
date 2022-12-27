package com.example.ejemplobottomnavigation.ui.notifications;

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

import com.example.ejemplobottomnavigation.AlbumListAdapter;
import com.example.ejemplobottomnavigation.AlbumModel;
import com.example.ejemplobottomnavigation.ArtistListAdapter;
import com.example.ejemplobottomnavigation.R;
import com.example.ejemplobottomnavigation.databinding.FragmentNotificationsBinding;

import java.io.File;
import java.util.ArrayList;

public class NotificationsFragment extends Fragment {

    RecyclerView recyclerView;
    TextView noMusicTV;
    ArrayList<AlbumModel> albumList = new ArrayList<>();

private FragmentNotificationsBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
            ViewGroup container, Bundle savedInstanceState) {
        NotificationsViewModel notificationsViewModel =
                new ViewModelProvider(this).get(NotificationsViewModel.class);

    binding = FragmentNotificationsBinding.inflate(inflater, container, false);
    View root = binding.getRoot();

        final TextView textView = binding.textDashboard;
        notificationsViewModel.getText().observe(getViewLifecycleOwner(), textView::setText);
        recyclerView = root.findViewById(R.id.recyclerView);
        noMusicTV = root.findViewById(R.id.Error_not_found);

        String[] columns = { MediaStore.Audio.Artists.ARTIST_KEY,
                MediaStore.Audio.Artists.ARTIST };

        Cursor cursor = getActivity().getContentResolver().query(MediaStore.Audio.Albums.EXTERNAL_CONTENT_URI,
                columns, null, null, null);

        while (cursor.moveToNext()){
            AlbumModel songData = new AlbumModel(cursor.getString(0),cursor.getString(1));
            //if (new File(songData.getPath()).exists())
            albumList.add(songData);
        }

        if (albumList.size()==0){
            noMusicTV.setVisibility(View.VISIBLE);
        }else{
            recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
            recyclerView.setAdapter(new ArtistListAdapter(albumList, getContext()));
        }
        return root;
    }

@Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}