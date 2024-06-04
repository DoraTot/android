package com.example.klk22;

import androidx.appcompat.app.AppCompatActivity;

import android.content.BroadcastReceiver;
import android.content.IntentFilter;
import android.os.Bundle;
import android.widget.Button;
import android.widget.Switch;
import android.content.Intent;
import android.os.Bundle;
import android.widget.CompoundButton;
import android.widget.Switch;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {
    private Button button1;
    private Button button2;
    private Switch switch1;
    public static MainActivity instance;
    private BroadcastReceiver switchStateReceiver;
    private static final int REQUEST_ADD_PRODUCT = 1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        button1 = findViewById(R.id.button1);
        button2 = findViewById(R.id.button2);
        switch1 = findViewById(R.id.switch1);

        instance = this;

        button1.setOnClickListener(v -> {
            Intent serviceIntent = new Intent(MainActivity.this, BackgroundService.class);
            startService(serviceIntent);
        });

        button2.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, AppProductActivity.class);
            startActivityForResult(intent, REQUEST_ADD_PRODUCT);
        });

        switchStateReceiver = new SwitchStateReceiver();
        IntentFilter filter = new IntentFilter("com.example.klk22.SWITCH_STATE");
        registerReceiver(switchStateReceiver, filter);


        switch1.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                Intent serviceIntent = new Intent(MainActivity.this, MyServiceMyForegroundService.class);
                if (isChecked) {
                    startService(serviceIntent);
                } else {
                    stopService(serviceIntent);
                }
            }
        });






    }

    public boolean isSwitchOn() {
        return switch1 .isChecked();
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(switchStateReceiver);
    }

}