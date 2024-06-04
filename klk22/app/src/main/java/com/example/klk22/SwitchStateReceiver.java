package com.example.klk22;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

public class SwitchStateReceiver  extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        boolean isSwitchOn = intent.getBooleanExtra("isSwitchOn", true);
        if (!isSwitchOn && context instanceof MainActivity) {
            MainActivity activity = (MainActivity) context;
            activity.getWindow().getDecorView().setBackgroundColor(activity.getResources().getColor(android.R.color.holo_orange_light));
        }
    }
}
