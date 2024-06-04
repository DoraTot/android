package com.example.klk22;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Build;
import android.os.IBinder;
import android.widget.RemoteViews;

import androidx.core.app.NotificationCompat;

public class MyServiceMyForegroundService extends Service {

    private static final String CHANNEL_ID = "ForegroundServiceChannel";
    private MediaPlayer mediaPlayer;

    public MyServiceMyForegroundService() {
    }


    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        if (intent != null) {
            String action = intent.getAction();
            if ("PLAY".equals(action)) {
                if (!mediaPlayer.isPlaying()) {
                    mediaPlayer.start();
                }
            } else if ("PAUSE".equals(action)) {
                if (mediaPlayer.isPlaying()) {
                    mediaPlayer.pause();
                }
            } else if ("STOP".equals(action)) {
                stopForeground(true);
                stopSelf();
            } else {
                startForegroundService();
            }
        }
        return START_STICKY;
    }

    private void startForegroundService() {
        createNotificationChannel();

        mediaPlayer = MediaPlayer.create(this, R.raw.sample_music);
        mediaPlayer.setLooping(true);
        mediaPlayer.start();


        Intent playIntent = new Intent(this, MyServiceMyForegroundService.class);
        playIntent.setAction("PLAY");
        PendingIntent playPendingIntent = PendingIntent.getService(this, 0, playIntent, PendingIntent.FLAG_UPDATE_CURRENT);

        Intent pauseIntent = new Intent(this, MyServiceMyForegroundService.class);
        pauseIntent.setAction("PAUSE");
        PendingIntent pausePendingIntent = PendingIntent.getService(this, 0, pauseIntent, PendingIntent.FLAG_UPDATE_CURRENT);

        Intent stopIntent = new Intent(this, MyServiceMyForegroundService.class);
        stopIntent.setAction("STOP");
        PendingIntent stopPendingIntent = PendingIntent.getService(this, 0, stopIntent, PendingIntent.FLAG_UPDATE_CURRENT);

        RemoteViews notificationLayout = new RemoteViews(getPackageName(), R.layout.notification);
        notificationLayout.setOnClickPendingIntent(R.id.button_play, playPendingIntent);
        notificationLayout.setOnClickPendingIntent(R.id.button_pause, pausePendingIntent);
        notificationLayout.setOnClickPendingIntent(R.id.button_stop, stopPendingIntent);

        Notification notification = new NotificationCompat.Builder(this, CHANNEL_ID)
                .setContentTitle("Foreground Service")
                .setContentText("Playing melody")
                .setSmallIcon(R.drawable.ic_notification)
                .setContent(notificationLayout)
                .setDeleteIntent(stopPendingIntent)
                .build();

        startForeground(1, notification);
    }


    private void createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel serviceChannel = new NotificationChannel(
                    CHANNEL_ID,
                    "Foreground Service Channel",
                    NotificationManager.IMPORTANCE_DEFAULT
            );
            NotificationManager manager = getSystemService(NotificationManager.class);
            manager.createNotificationChannel(serviceChannel);
        }
    }


    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }



    @Override
    public void onDestroy() {
        super.onDestroy();
        if (mediaPlayer != null) {
            mediaPlayer.release();
            mediaPlayer = null;
        }
    }



}