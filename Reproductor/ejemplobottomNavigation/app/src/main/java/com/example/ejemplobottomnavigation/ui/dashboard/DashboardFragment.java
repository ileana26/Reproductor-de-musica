package com.example.ejemplobottomnavigation.ui.dashboard;

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
import com.example.ejemplobottomnavigation.AudioMode;
import com.example.ejemplobottomnavigation.MusicListAdapter;
import com.example.ejemplobottomnavigation.R;
import com.example.ejemplobottomnavigation.databinding.FragmentDashboardBinding;

import java.io.File;
import java.util.ArrayList;

public class DashboardFragment extends Fragment {

    RecyclerView recyclerView;
    TextView noMusicTV;
    ArrayList<AlbumModel> albumList = new ArrayList<>();

private FragmentDashboardBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
            ViewGroup container, Bundle savedInstanceState) {
        DashboardViewModel dashboardViewModel =
                new ViewModelProvider(this).get(DashboardViewModel.class);

    binding = FragmentDashboardBinding.inflate(inflater, container, false);
    View root = binding.getRoot();

        final TextView textView = binding.textDashboard;
        dashboardViewModel.getText().observe(getViewLifecycleOwner(), textView::setText);
        recyclerView = root.findViewById(R.id.recyclerView);
        noMusicTV = root.findViewById(R.id.Error_not_found);

        String[] columns = { android.provider.MediaStore.Audio.Albums._ID,
                android.provider.MediaStore.Audio.Albums.ALBUM };

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
            recyclerView.setAdapter(new AlbumListAdapter(albumList, getContext()));
        }
        return root;

    }

@Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}