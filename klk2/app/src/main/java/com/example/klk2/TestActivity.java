package com.example.klk2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Color;
import android.os.Bundle;
import android.widget.TextView;

public class TestActivity extends AppCompatActivity {

    private TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);

        textView = findViewById(R.id.textView);

        BroadcastReceiver receiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                boolean changeColor = intent.getBooleanExtra("changeColor2", false);
                if (changeColor) {
                    textView.setTextColor(getResources().getColor(android.R.color.holo_red_dark));
                }
            }
        };
        IntentFilter filter = new IntentFilter("com.example.klk2.ACTION_COLOR_CHANGE");
        registerReceiver(receiver, filter);

        textView.setText("Oboji me!");
    }

//    @Override
//    protected void onNewIntent(Intent intent) {
//        super.onNewIntent(intent);
//        setIntent(intent);
//
//        if (getIntent().getBooleanExtra("changeColor", false)) {
//            changeTextColor();
//        }
//    }
//
//    private void changeTextColor() {
//        textView.setTextColor(Color.RED);
//    }



}