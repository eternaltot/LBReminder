package com.example.chart001.lbreminder;

/**
 * Created by Chart001 on 6/13/2015.
 */
import java.lang.reflect.Field;

import com.example.chart001.lbreminder.preferences.AlarmPreActivity;
import com.example.chart001.lbreminder.service.AlarmSerReciever;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.ViewConfiguration;
import android.widget.Toast;

public abstract class BaseActivity  extends ActionBarActivity implements android.view.View.OnClickListener{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        try {
            ViewConfiguration config = ViewConfiguration.get(this);
            Field menuKeyField = ViewConfiguration.class.getDeclaredField("sHasPermanentMenuKey");
            if(menuKeyField != null) {
                menuKeyField.setAccessible(true);
                menuKeyField.setBoolean(config, false);
            }
        } catch (Exception ex) {
            // Ignore
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu items for use in the action bar
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_alarm, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        String url = null;
        Intent intent = null;
        switch (item.getItemId()) {
            case R.id.menu_item_new:
                Intent newAlarmIntent = new Intent(this, AlarmPreActivity.class);
                startActivity(newAlarmIntent);
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    protected void callMathAlarmScheduleService() {
        Intent mathAlarmServiceIntent = new Intent(this, AlarmSerReciever.class);
        sendBroadcast(mathAlarmServiceIntent, null);
    }
}
