package com.firatsahin.goalclock;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;

public class counterUpdateReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        if (intent.getAction() != null && intent.getAction().equals("com.firatsahin.goalclock.UPDATE_COUNTER")) {
            SharedPreferences sharedPreferences = context.getSharedPreferences("GoalClockPrefs", Context.MODE_PRIVATE);

        }
    }
}
