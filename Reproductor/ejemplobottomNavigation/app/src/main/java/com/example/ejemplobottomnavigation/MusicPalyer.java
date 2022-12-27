package com.example.ejemplobottomnavigation;

import android.content.ComponentName;
import android.content.ServiceConnection;
import android.media.MediaMetadataRetriever;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.example.ejemplobottomnavigation.ui.MyMediaPlayer;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;
import java.util.concurrent.TimeUnit;

public class MusicPalyer extends AppCompatActivity implements
         ActionPlaying, ServiceConnection {

    TextView titleTv, currentTimeTv, totalTimeTv, song_tv;
    SeekBar seekBar;
    ImageView pausePlay, nextBtn, previousBtn, musicIcon, shuffle, repeat;
    PlayerService musicService;

    public static ArrayList<AudioMode> songList = new ArrayList<>();

    AudioMode currentSong;
    MediaPlayer mediaPlayer = MyMediaPlayer.getInstance();
    static boolean repeatB = false, shuffleB = false;
    int position = -1;
    boolean isBound = false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_music_palyer);

        titleTv = findViewById(R.id.song_title);
        currentTimeTv = findViewById(R.id.current_time);
        totalTimeTv = findViewById(R.id.total_time);
        seekBar = findViewById(R.id.seekBar);
        pausePlay = findViewById(R.id.pause_play);
        nextBtn = findViewById(R.id.next);
        previousBtn = findViewById(R.id.previous);
        musicIcon = findViewById(R.id.musica);
        song_tv = findViewById(R.id.song_artist);
        shuffle = findViewById(R.id.shuffle);
        repeat = findViewById(R.id.id_repeat);
        titleTv.setSelected(true);



        songList = (ArrayList<AudioMode>) getIntent().getSerializableExtra("LIST");
        setResourcesWithMusic();

        MusicPalyer.this.runOnUiThread(new Runnable()
        {
            @Override
            public void run() {
                if(mediaPlayer!=null){
                    seekBar.setProgress(mediaPlayer.getCurrentPosition());
                    currentTimeTv.setText(convertToMMSS(mediaPlayer.getCurrentPosition()+""));
                    if(mediaPlayer.isPlaying()){
                        pausePlay.setImageResource(R.drawable.ic_baseline_pause_circle_outline_24);

                    }else{
                        pausePlay.setImageResource(R.drawable.ic_baseline_play_circle_outline_24);
                    }

                }
                new Handler().postDelayed(this,100);

            }


        });

        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                if(mediaPlayer!=null && fromUser){
                    mediaPlayer.seekTo(progress);
                }
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });


    }


    void setResourcesWithMusic(){
        currentSong = songList.get(MyMediaPlayer.currentIndex);
        titleTv.setText(currentSong.getTitle());
        song_tv.setText(currentSong.getArtist());
        totalTimeTv.setText(convertToMMSS(currentSong.getDuration()));
        pausePlay.setOnClickListener(v-> pausePlay());
        nextBtn.setOnClickListener(v-> playNextSong());
        previousBtn.setOnClickListener(v-> playPreviousSong());
        shuffle.setOnClickListener(v-> shufflePlayList());
        repeat.setOnClickListener(v-> repeatSong());


        playMusic();

    }


    private void repeatSong() {
        if(repeatB){
            repeatB = false;
            repeat.setImageResource(R.drawable.ic_baseline_repeat_on);
        }else{
            repeatB = true;
            repeat.setImageResource(R.drawable.ic_baseline_repeat_off);
        }
    }

    private void shufflePlayList() {
        if(shuffleB){
            shuffleB = false;
            shuffle.setImageResource(R.drawable.ic_baseline_shuffle_24);
        }else{
            shuffleB = true;
            shuffle.setImageResource(R.drawable.ic_baseline_shuffle_off);
        }
    }

    private void playMusic(){

        mediaPlayer.reset();
        try {
            mediaPlayer.setDataSource(currentSong.getPath());
            mediaPlayer.prepare();
            mediaPlayer.start();
            seekBar.setProgress(0);
            seekBar.setMax(mediaPlayer.getDuration());

        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public void playNextSong(){
        if(MyMediaPlayer.currentIndex == songList.size()-1){

            return;}
        MyMediaPlayer.currentIndex +=1;
        mediaPlayer.reset();

                setResourcesWithMusic();


                if (shuffleB && !repeatB) {
                    MyMediaPlayer.currentIndex = getRandom(songList.size()+ 1);
                } else if (!shuffleB && !repeatB) {
                    MyMediaPlayer.currentIndex = ((MyMediaPlayer.currentIndex + 1) % songList.size());
                }
            }

            public void playPreviousSong(){
        if(MyMediaPlayer.currentIndex == 0)
            return;
        MyMediaPlayer.currentIndex -=1;
        mediaPlayer.reset();
        setResourcesWithMusic();
                //

    }

    public void pausePlay(){

        if(mediaPlayer.isPlaying()) {
            mediaPlayer.pause();
        }
        else{
            mediaPlayer.start();
    }}


    public static String convertToMMSS(String duration){
        Long millis = Long.parseLong(duration);
        return String.format("%02d:%02d",
                TimeUnit.MILLISECONDS.toMinutes(millis) % TimeUnit.HOURS.toMinutes(1),
                TimeUnit.MILLISECONDS.toSeconds(millis) % TimeUnit.MINUTES.toSeconds(1));
    }

   //imagen del album
    private void metaData(Uri uri){
        MediaMetadataRetriever retriever = new MediaMetadataRetriever();
        retriever.setDataSource(uri.toString());
        byte[] art = retriever.getEmbeddedPicture();
        if(art != null){
            Glide.with(this).asBitmap().load(art).into(musicIcon);
        }else{
            Glide.with(this).asBitmap().load(R.drawable.iconomusica).into(musicIcon);
        }
    }



    private int getRandom(int i) {
        Random random = new Random();
        return random.nextInt(i+1);
    }
    

    @Override
    public void onServiceConnected(ComponentName componentName, IBinder service) {
        PlayerService.MyBinder myBinder = (PlayerService.MyBinder) service;
        musicService = myBinder.getService();
        musicService.setCallBack(this);
        Toast.makeText(this, "Reproduciendo", Toast.LENGTH_SHORT).show();
        //showNotification(R.drawable.ic_baseline_pause_24);
    }

    @Override
    public void onServiceDisconnected(ComponentName componentName) {
        musicService= null;
    }
}