package com.example.klk2;

import androidx.appcompat.app.AppCompatActivity;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    public static Button buttonOboji;
    private BroadcastReceiver colorChangeReceiver; // for buttonOboji

    public static final String COLOR_CHANGE_CHANNEL_ID = "color_change_channel"; // notification channels
    public static final String MUSIC_CHANNEL_ID = "music_channel";
    public static final String SECOND_MUSIC_CHANNEL_ID = "music_channel2";



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        createNotificationChannels();

        buttonOboji = findViewById(R.id.buttonOboji);
        Button buttonReprodukuj = findViewById(R.id.buttonReprodukuj);
        Button buttonDodaj = findViewById(R.id.buttonDodaj);
        Button buttonMusic2 = findViewById(R.id.buttonMusic);
        Button buttonTest = findViewById(R.id.buttonTest);

        buttonOboji.setTextColor(Color.GREEN);

        buttonOboji.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, ColorChangeService.class);
            startService(intent);
        });
        buttonReprodukuj.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, MusicService.class);
            startService(intent);
        });

        buttonDodaj.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, AddProductActivity.class);
            startActivity(intent);
        });

        buttonMusic2.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, MusicService2.class);
            startService(intent);
        });

        buttonTest.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, TestActivity.class);
            startActivity(intent);
        });

        colorChangeReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                int currentColor = buttonOboji.getCurrentTextColor();
                int newColor = (currentColor == Color.BLACK) ? Color.GREEN : Color.BLACK;
                buttonOboji.setTextColor(newColor);
            }
        };

        registerReceiver(colorChangeReceiver, new IntentFilter("com.example.klk2.COLOR_CHANGE"), Context.RECEIVER_NOT_EXPORTED);

    }



    private void createNotificationChannels() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel colorChangeChannel = new NotificationChannel(
                    COLOR_CHANGE_CHANNEL_ID,
                    "Color Change Notifications",
                    NotificationManager.IMPORTANCE_DEFAULT
            );
            colorChangeChannel.setDescription("Notifications for color change events");

            NotificationChannel musicChannel = new NotificationChannel(
                    MUSIC_CHANNEL_ID,
                    "Music Notifications",
                    NotificationManager.IMPORTANCE_DEFAULT
            );
            musicChannel.setDescription("Notifications for music playback");

            NotificationChannel musicChannel2 = new NotificationChannel(
                    SECOND_MUSIC_CHANNEL_ID,
                    "Music2 Notifications",
                    NotificationManager.IMPORTANCE_DEFAULT
            );
            musicChannel2.setDescription("Notifications for music2 playback");


            NotificationManager manager = getSystemService(NotificationManager.class);
            manager.createNotificationChannel(colorChangeChannel);
            manager.createNotificationChannel(musicChannel);
            manager.createNotificationChannel(musicChannel2);
        }
    }



    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(colorChangeReceiver);
    }


}