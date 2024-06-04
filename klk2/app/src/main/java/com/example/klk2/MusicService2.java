package com.example.klk2;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Build;
import android.os.IBinder;
import android.util.Log;

import androidx.annotation.Nullable;
import androidx.core.app.NotificationCompat;

public class MusicService2 extends Service {

    private MediaPlayer mediaPlayer;
    private static final String CHANNEL_ID = "music_channel2";
    private static final String TAG = "MusicService2";



    @Override
    public void onCreate() {
        super.onCreate();
        createNotificationChannel();
    }

    protected void createNotificationChannel(){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel serviceChannel = new NotificationChannel(
                    CHANNEL_ID,
                    "Music2 Service Channel",
                    NotificationManager.IMPORTANCE_DEFAULT
            );
            NotificationManager manager = getSystemService(NotificationManager.class);
            manager.createNotificationChannel(serviceChannel);
        }
    }


    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        if (intent != null) {
            String action = intent.getAction();
            if ("STOP_MUSIC".equals(action)) {
                stopMusic();
            } else if ("LOG_MESSAGE".equals(action)) {
                Log.d(TAG, "Pozdrav iz servisa!");
            } else {
                if (mediaPlayer == null) {
                    mediaPlayer = MediaPlayer.create(this, R.raw.sample_music);
                    mediaPlayer.setLooping(true);
                    mediaPlayer.start();
                }
            }
        }
        startForeground(3, getNotification());
        return START_STICKY;
    }


    private Notification getNotification() {
        Intent stopIntent = new Intent(this, MusicService2.class);
        stopIntent.setAction("STOP_MUSIC");
        PendingIntent pendingStopIntent = PendingIntent.getService(this, 0, stopIntent, PendingIntent.FLAG_UPDATE_CURRENT | PendingIntent.FLAG_IMMUTABLE);

        Intent logIntent = new Intent(this, MusicService2.class);
        logIntent.setAction("LOG_MESSAGE");
        PendingIntent pendingLogIntent = PendingIntent.getService(this, 0, logIntent, PendingIntent.FLAG_UPDATE_CURRENT | PendingIntent.FLAG_IMMUTABLE);

        Intent broadcastIntent = new Intent(BroadCastReceiver.ACTION_COLOR_CHANGE);
        broadcastIntent.putExtra("changeColor2", true);
        PendingIntent pendingBroadcastIntent = PendingIntent.getBroadcast(this, 0, broadcastIntent, PendingIntent.FLAG_UPDATE_CURRENT | PendingIntent.FLAG_IMMUTABLE);


        return new NotificationCompat.Builder(this, CHANNEL_ID)
                .setContentTitle("Tuyo Player")
                .setContentText("Melody playing...")
                .setSmallIcon(R.drawable.img)
                .addAction(R.drawable.img, "Stop", pendingStopIntent)
                .addAction(R.drawable.img, "Log Message", pendingLogIntent)
                .addAction(R.drawable.img, "Change Color", pendingBroadcastIntent)
                .build();
    }

    private void stopMusic() {
        if (mediaPlayer != null) {
            if (mediaPlayer.isPlaying()) {
                mediaPlayer.stop();
            }
            mediaPlayer.release();
            mediaPlayer = null;
        }
        stopForeground(true);
        stopSelf();
    }


    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}
