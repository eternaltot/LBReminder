package com.example.chart001.lbreminder.Alert;

/**
 * Created by Chart001 on 6/13/2015.
 */

import com.example.chart001.lbreminder.Alarm;
import com.example.chart001.lbreminder.service.AlarmSerReciever;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

public class AlarmAlertReciever extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        Intent lbAlarmServiceIntent = new Intent(
                context,
                AlarmSerReciever.class);
        context.sendBroadcast(lbAlarmServiceIntent, null);

        WakeLock.lockOn(context);
        Bundle bundle = intent.getExtras();
        final Alarm alarm = (Alarm) bundle.getSerializable("alarm");

        Intent lbAlarmAlertActivityIntent;

        lbAlarmAlertActivityIntent = new Intent(context, AlarmAlertActivity.class);

        lbAlarmAlertActivityIntent.putExtra("alarm", alarm);

        lbAlarmAlertActivityIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

        context.startActivity(lbAlarmAlertActivityIntent);
    }

}