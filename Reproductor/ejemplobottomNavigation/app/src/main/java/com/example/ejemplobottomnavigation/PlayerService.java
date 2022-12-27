package com.example.ejemplobottomnavigation;

import static com.example.ejemplobottomnavigation.MusicPalyer.songList;

import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Binder;
import android.os.IBinder;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.Nullable;

import java.util.ArrayList;

public class PlayerService extends Service {

    IBinder mBinder= new MyBinder();
    MediaPlayer mediaPlayer;
    ArrayList<AudioMode> musicFiles = new ArrayList<>();
    Uri uri;
    int position = -1;
    private ActionPlaying actionPlaying;
    public static final String MUSIC_LAST_PLAYED = "LAST_PLAYED";


    @Override
    public void onCreate(){
        super.onCreate();
        musicFiles = songList;
    }


    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        Log.e("Bind", "Method");
        return mBinder;
    }

    public class MyBinder extends Binder{
        PlayerService getService(){
            return PlayerService.this;
        }
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startID){
        int myPosition = intent.getIntExtra("servicePosition", -1);
        String actionName = intent.getStringExtra("ActionName");

        if(actionName != null){
            switch (actionName){
                case "playPause":
                    Toast.makeText(this, "PlayPause", Toast.LENGTH_SHORT).show();
                    break;
                case "next":
                    Toast.makeText(this, "Next", Toast.LENGTH_SHORT).show();
                    break;
                case "previous":
                    Toast.makeText(this, "Previous", Toast.LENGTH_SHORT).show();
                    break;
            }
        }

        return START_STICKY;
    }



    void start(){
        mediaPlayer.start();
    }

    boolean isPlaying(){
        return mediaPlayer.isPlaying();
    }

    void stop(){
        mediaPlayer.stop();
    }

    void pause(){
        mediaPlayer.pause();
    }

    void release(){
        mediaPlayer.release();
    }

    int getDuration(){
       return mediaPlayer.getDuration();
    }

    void seekTo(int position){
        mediaPlayer.seekTo(position);
    }

    void createMediaPlayer(){
        mediaPlayer = MediaPlayer.create(getBaseContext(), uri);
    }

    void reset(){
        mediaPlayer.reset();
    }

    void setCallBack(ActionPlaying actionPlaying){
        this.actionPlaying = actionPlaying;


    }


    /*private final IBinder serviceBinder = new ServiceBinder();
    ExoPlayer player;
    PlayerNotificationManager notificationManager;


    public class ServiceBinder extends Binder{
        public PlayerService getPlayerService() {
            return PlayerService.this;
        }
    }

    @Override
    public IBinder onBind(Intent intent) {
        return serviceBinder;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        player = new ExoPlayer.Builder(getApplicationContext()).build();

        AudioAttributes audioAttributes = new AudioAttributes.Builder().setUsage(AudioAttributes.USAGE_MEDIA)
                .setContentType(AudioAttributes.CONTENT_TYPE_MUSIC).build();


        final String channelID =    getResources().getString(R.string.app_name) + "Music channel";
        final int notificationID = 1111111;
        notificationManager = new PlayerNotificationManager.Builder(this, notificationID, channelID)
                .setChannelImportance(IMPORTANCE_HIGH)
                .setNotificationListener(notificationListener)
                .setMediaDescriptionAdapter(descriptionAdapter)
                .setSmallIconResourceId(R.drawable.ic_baseline_warning_24)
                .setChannelDescriptionResourceId(R.string.app_name)
                .setChannelNameResourceId(R.drawable.ic_baseline_skip_next_24)
                .setChannelNameResourceId(R.drawable.ic_baseline_skip_previous_24)
                .setChannelNameResourceId(R.drawable.ic_baseline_pause_24)
                .setChannelNameResourceId(R.drawable.ic_baseline_play_arrow_24)
                .setChannelNameResourceId(R.string.app_name)
                .build();

        notificationManager.setPlayer(player);
        notificationManager.setPriority(NotificationCompat.PRIORITY_MAX);
        notificationManager.setUseRewindAction(false);
        notificationManager.setUseFastForwardAction(false);

    }

    @Override
            public void onDestroy(){

        if(player.isPlaying())player.stop();
        notificationManager.setPlayer(null);
        player.release();
        player = null;
        stopForeground(true);
        stopSelf();
        super.onDestroy();
    }

    PlayerNotificationManager.NotificationListener notificationListener = new PlayerNotificationManager.NotificationListener() {
        @Override
        public void onNotificationCancelled(int notificationId, boolean dismissedByUser) {
            PlayerNotificationManager.NotificationListener.super.onNotificationCancelled(notificationId, dismissedByUser);
            stopForeground(true);
            if (player.isPlaying()) {
                player.pause();
            }

        }

        @Override
        public void onNotificationPosted(int notificationId, Notification notification, boolean ongoing) {
            PlayerNotificationManager.NotificationListener.super.onNotificationPosted(notificationId, notification, ongoing);
            startForeground(notificationId, notification);
        }
    };


    PlayerNotificationManager.MediaDescriptionAdapter descriptionAdapter = new PlayerNotificationManager.MediaDescriptionAdapter() {
        @Override
        public CharSequence getCurrentContentTitle(Player player) {
            return Objects.requireNonNull(player.getCurrentMediaItem()).mediaMetadata.title;

        }

        @Nullable
        @Override
        public PendingIntent createCurrentContentIntent(Player player) {
            Intent openAppIntent = new Intent(getApplicationContext(), MainActivity.class);

            return PendingIntent.getActivity(getApplicationContext(), 0, openAppIntent, PendingIntent.FLAG_IMMUTABLE | PendingIntent.FLAG_UPDATE_CURRENT);
        }

        @Nullable
        @Override
        public CharSequence getCurrentContentText(Player player) {
            return null;
        }

        @Nullable
        @Override
        public Bitmap getCurrentLargeIcon(Player player, PlayerNotificationManager.BitmapCallback callback) {
            ImageView view = new ImageView(getApplicationContext());
            view.setImageURI(Objects.requireNonNull(player.getCurrentMediaItem()).mediaMetadata.artworkUri);

            BitmapDrawable bitmapDrawable = (BitmapDrawable) view.getDrawable();
            if (bitmapDrawable == null) {
                bitmapDrawable = (BitmapDrawable) ContextCompat.getDrawable(getApplicationContext(), R.drawable.iconomusica);
            }
            assert bitmapDrawable != null;

            return bitmapDrawable.getBitmap();
        }
    };
*/
}