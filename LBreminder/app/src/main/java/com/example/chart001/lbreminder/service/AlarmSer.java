package com.example.chart001.lbreminder.service;

/**
 * Created by Chart001 on 6/13/2015.
 */
import java.util.Comparator;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

import com.example.chart001.lbreminder.Alarm;
import com.example.chart001.lbreminder.Alert.AlarmAlertReciever;
import com.example.chart001.lbreminder.DatabaseSQLite.DatabaseSQLite;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;

public class AlarmSer extends Service {

    @Override
    public IBinder onBind(Intent intent) {

        return null;
    }

    @Override
    public void onCreate() {
        //Log.d(this.getClass().getSimpleName(),"onCreate()");
        super.onCreate();
    }

    private Alarm getNext(){
        Set<Alarm> alarmQueue = new TreeSet<Alarm>(new Comparator<Alarm>() {
            @Override
            public int compare(Alarm lhs, Alarm rhs) {
                int result = 0;
                long diff = lhs.getAlarmTime().getTimeInMillis() - rhs.getAlarmTime().getTimeInMillis();
                if(diff>0){
                    return 1;
                }else if (diff < 0){
                    return -1;
                }
                return result;
            }
        });

        DatabaseSQLite.init(getApplicationContext());
        List<Alarm> alarms = DatabaseSQLite.getAll();

        for(Alarm alarm : alarms){
            if(alarm.getAlarmActive())
                alarmQueue.add(alarm);
        }
        if(alarmQueue.iterator().hasNext()){
            return alarmQueue.iterator().next();
        }else{
            return null;
        }
    }

    @Override
    public void onDestroy() {
        DatabaseSQLite.deactivate();
        super.onDestroy();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        //Log.d(this.getClass().getSimpleName(),"onStartCommand()");
        Alarm alarm = getNext();
        if(null != alarm){
            alarm.schedule(getApplicationContext());
            Log.d(this.getClass().getSimpleName(),alarm.getTimeUntilNextAlarmMessage());

        }else{
            Intent myIntent = new Intent(getApplicationContext(), AlarmAlertReciever.class);
            myIntent.putExtra("alarm", new Alarm());

            PendingIntent pendingIntent = PendingIntent.getBroadcast(getApplicationContext(), 0, myIntent,PendingIntent.FLAG_CANCEL_CURRENT);
            AlarmManager alarmManager = (AlarmManager)getApplicationContext().getSystemService(Context.ALARM_SERVICE);

            alarmManager.cancel(pendingIntent);
        }
        return START_NOT_STICKY;
    }

}
