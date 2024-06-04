package com.example.klk2;


import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Handler;
import android.os.IBinder;
import android.widget.Button;
import androidx.core.app.NotificationCompat;

import android.app.IntentService;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.graphics.Color;
import android.os.Handler;
import android.os.Looper;
import androidx.annotation.Nullable;
import androidx.core.app.NotificationCompat;

public class ColorChangeService extends Service{

    private Handler handler;
    private Runnable runnable;

    private Button button;
    private static final long INTERVAL = 3000; // 1 minut
    private static final String CHANNEL_ID = MainActivity.COLOR_CHANGE_CHANNEL_ID;

    public ColorChangeService() {
        super();
    }



    @Override
    public void onDestroy() {
        super.onDestroy();
        if (handler != null && runnable != null) {
            handler.removeCallbacks(runnable);
        }
    }
    @Override
    public void onCreate() {
        super.onCreate();
        handler = new Handler();
        button = MainActivity.buttonOboji; // Referenca na dugme

        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                changeButtonColor();
                handler.postDelayed(this, INTERVAL);
            }
        }, INTERVAL);

    }

    private void changeButtonColor() {
        handler = new Handler(Looper.getMainLooper());
        runnable = new Runnable() {
            @Override
            public void run() {
                int currentColor = MainActivity.buttonOboji.getCurrentTextColor();
                int newColor = (currentColor == Color.BLACK) ? Color.GREEN : Color.BLACK;
                MainActivity.buttonOboji.setTextColor(newColor);

                showNotification("Boja je promenjena!");

                handler.postDelayed(this, 3000); // Repeat every 3 seconds
            }
        };
        handler.post(runnable);

    }

    private void showNotification(String message) {
        NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        String channelId = CHANNEL_ID;

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel(channelId, "Color Change Notifications", NotificationManager.IMPORTANCE_DEFAULT);
            notificationManager.createNotificationChannel(channel);
        }

        Notification notification = new NotificationCompat.Builder(this, channelId)
                .setContentTitle("Notifikacija")
                .setContentText(message)
                .setSmallIcon(R.drawable.img)
                .build();

        notificationManager.notify(1, notification); // da bi radilo notification channeli moraju da rade na razlicitim channelima, ovde je 1, a za music je stavljeno 2
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}