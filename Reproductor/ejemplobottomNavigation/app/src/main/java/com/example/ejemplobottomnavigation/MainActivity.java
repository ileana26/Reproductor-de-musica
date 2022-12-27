package com.example.ejemplobottomnavigation;

import android.Manifest;
import android.content.pm.PackageManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.example.ejemplobottomnavigation.databinding.ActivityMainBinding;
import com.example.ejemplobottomnavigation.ui.MyMediaPlayer;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity {

private ActivityMainBinding binding;

    ImageView pausePlay;
    MediaPlayer mediaPlayer = MyMediaPlayer.getInstance();
    int a =0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

     binding = ActivityMainBinding.inflate(getLayoutInflater());
     setContentView(binding.getRoot());

        BottomNavigationView navView = findViewById(R.id.nav_view);
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(
                R.id.navigation_home, R.id.navigation_dashboard, R.id.navigation_notifications)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_activity_main);
        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);
        NavigationUI.setupWithNavController(binding.navView, navController);

        pausePlay = findViewById(R.id.pause_play);


        if (checkPermission()==false){
            requestPermission();
            return;
        }

        if(a != 0){
            pausePlay.setImageResource(R.drawable.ic_baseline_pause_circle_outline_24);
            a = 1;}

    }


    boolean checkPermission(){
        int result = ContextCompat.checkSelfPermission(MainActivity.this, Manifest.permission.READ_EXTERNAL_STORAGE);
        if (result == PackageManager.PERMISSION_GRANTED){
            return true;
        }else{
            return false;
        }
    }

    void requestPermission(){
        if (ActivityCompat.shouldShowRequestPermissionRationale(MainActivity.this, Manifest.permission.READ_EXTERNAL_STORAGE)){
            Toast.makeText(MainActivity.this, "ES REQUERIDO EL PERMISO DE LECTURA, POR FAVOR PERMITA LA LECTURA DE ARCIVOS DESDE LOS AJUSTES DE LA APLICACION", Toast.LENGTH_SHORT).show();
        }else
        ActivityCompat.requestPermissions(MainActivity.this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},123);
    }

    private void pausePlay(){

        if(mediaPlayer.isPlaying())
            mediaPlayer.pause();
        else
            mediaPlayer.start();

    }

}