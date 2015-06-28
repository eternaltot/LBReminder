package com.example.chart001.lbreminder.service;

/**
 * Created by Chart001 on 6/13/2015.
 */
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

public class AlarmSerReciever extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        //Log.d("AlarmSerReciever", "onReceive()");
        Intent serviceIntent = new Intent(context, AlarmSer.class);
        context.startService(serviceIntent);
    }

}
