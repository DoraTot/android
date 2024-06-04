package com.example.klk22;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Handler;
import android.os.IBinder;

import androidx.annotation.Nullable;
import androidx.core.app.NotificationCompat;


public class BackgroundService extends Service {
    private Handler handler;
    private Runnable runnable;

    private static final String CHANNEL_ID = "BackgroundServiceChannel";

    @Nullable
    @Override
    public
    IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        handler = new Handler();
        runnable = new Runnable() {
            @Override
            public void run() {
                checkSwitchState();
                handler.postDelayed(this, 6000); // Ponovno pokretanje svake minute
            }
        };
        handler.post(runnable);
    }


    private void checkSwitchState() {
        // Proverite stanje switch-a u glavnoj aktivnosti
        MainActivity mainActivity = MainActivity.instance;
        if (mainActivity != null) {
            boolean isSwitchOn = mainActivity.isSwitchOn();

            // Pošalji broadcast sa stanjem switch-a
            Intent broadcastIntent = new Intent("com.example.klk22.SWITCH_STATE");
            broadcastIntent.putExtra("isSwitchOn", isSwitchOn);
            sendBroadcast(broadcastIntent);

            if (!isSwitchOn) {
                showNotification();
            }
        }
    }

    private void showNotification() {
        createNotificationChannel();
        Notification notification = new NotificationCompat.Builder(this, CHANNEL_ID)
                .setContentTitle("Switch Off")
                .setContentText("Switch is currently off.")
                .setSmallIcon(R.drawable.ic_notification)
                .build();
        NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.notify(2, notification);
    }

    private void createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel serviceChannel = new NotificationChannel(
                    CHANNEL_ID,
                    "Background Service Channel",
                    NotificationManager.IMPORTANCE_DEFAULT
            );
            NotificationManager manager = getSystemService(NotificationManager.class);
            manager.createNotificationChannel(serviceChannel);
        }
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        handler.removeCallbacks(runnable);
    }


}
