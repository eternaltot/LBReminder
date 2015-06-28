package com.example.chart001.lbreminder.phone;

/**
 * Created by Chart001 on 6/13/2015.
 */
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

public class PhoneStateChangeReciever extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        Log.d(getClass().getSimpleName(), "onReceive()");

    }

}