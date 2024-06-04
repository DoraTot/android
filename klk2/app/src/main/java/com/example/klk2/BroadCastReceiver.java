package com.example.klk2;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

public class BroadCastReceiver extends BroadcastReceiver {
    public static final String ACTION_COLOR_CHANGE = "com.example.klk2.ACTION_COLOR_CHANGE";

    @Override
    public void onReceive(Context context, Intent intent) {
        if (ACTION_COLOR_CHANGE.equals(intent.getAction())) {
            Intent colorIntent = new Intent(context, TestActivity.class);
            colorIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            colorIntent.putExtra("changeColor2", true);
            context.startActivity(colorIntent);
            Toast.makeText(context, "Text color changed to red!", Toast.LENGTH_SHORT).show();
        }
    }

}
