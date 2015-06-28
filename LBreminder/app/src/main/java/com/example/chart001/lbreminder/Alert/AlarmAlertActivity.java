package com.example.chart001.lbreminder.Alert;

/**
 * Created by Chart001 on 6/13/2015.
 */
import com.example.chart001.lbreminder.Alarm;
import com.example.chart001.lbreminder.R;
import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.os.Vibrator;
import android.telephony.PhoneStateListener;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.HapticFeedbackConstants;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;

public class AlarmAlertActivity extends Activity implements OnClickListener {

    private Alarm alarm;
    private MediaPlayer mediaPlayer;

    private Vibrator vibrator;

    private boolean alarmActive;
    private TextView nameView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        final Window window = getWindow();
        window.addFlags(WindowManager.LayoutParams.FLAG_SHOW_WHEN_LOCKED
                | WindowManager.LayoutParams.FLAG_DISMISS_KEYGUARD);
        window.addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON
                | WindowManager.LayoutParams.FLAG_TURN_SCREEN_ON);

        //setContentView(R.layout.alarm_alert);

        Bundle bundle = this.getIntent().getExtras();
        alarm = (Alarm) bundle.getSerializable("alarm");

        //Setup layout
        this.setContentView(R.layout.alarm_alert);

        String name = getIntent().getStringExtra(alarm.getAlarmName());

        TextView tvTime = (TextView) findViewById(R.id.alarm_screen_time);
        tvTime.setText(alarm.getAlarmTimeString());

        nameView = (TextView) findViewById(R.id.alarm_screen_name);
        nameView.setText(alarm.getAlarmName());

        ((Button) findViewById(R.id.alarm_screen_button)).setOnClickListener(this);

        TelephonyManager telephonyManager = (TelephonyManager) this.getSystemService(Context.TELEPHONY_SERVICE);

        PhoneStateListener phoneStateListener = new PhoneStateListener() {
            @Override
            public void onCallStateChanged(int state, String incomingNumber) {
                switch (state) {
                    case TelephonyManager.CALL_STATE_RINGING:
                        //Log.d(getClass().getSimpleName(), "Incoming call: " + incomingNumber);
                        try {
                            mediaPlayer.pause();
                        } catch (IllegalStateException e) {

                        }
                        break;
                    case TelephonyManager.CALL_STATE_IDLE:
                        //Log.d(getClass().getSimpleName(), "Call State Idle");
                        try {
                            mediaPlayer.start();
                        } catch (IllegalStateException e) {

                        }
                        break;
                }
                super.onCallStateChanged(state, incomingNumber);
            }
        };

        telephonyManager.listen(phoneStateListener, PhoneStateListener.LISTEN_CALL_STATE);

        startAlarm();

    }

    @Override
    protected void onResume() {
        super.onResume();
        alarmActive = true;
    }

    private void startAlarm() {

        if (alarm.getAlarmTonePath() != "") {
            mediaPlayer = new MediaPlayer();
            if (alarm.getVibrate()) {
                vibrator = (Vibrator) getSystemService(VIBRATOR_SERVICE);
                long[] pattern = { 1000, 200, 200, 200 };
                vibrator.vibrate(pattern, 0);
            }
            try {
                mediaPlayer.setVolume(1.0f, 1.0f);
                mediaPlayer.setDataSource(this, Uri.parse(alarm.getAlarmTonePath()));
                mediaPlayer.setAudioStreamType(AudioManager.STREAM_ALARM);
                mediaPlayer.setLooping(true);
                mediaPlayer.prepare();
                mediaPlayer.start();

            } catch (Exception e) {
                mediaPlayer.release();
                alarmActive = false;
            }
        }
    }

    @Override
    public void onBackPressed() {
        if (!alarmActive)
            super.onBackPressed();
    }

    @Override
    protected void onPause() {
        super.onPause();
        WakeLock.lockOff(this);
    }

    @Override
    protected void onDestroy() {
        try {
            if (vibrator != null)
                vibrator.cancel();
        } catch (Exception e) {

        }
        try {
            mediaPlayer.stop();
        } catch (Exception e) {

        }
        try {
            mediaPlayer.release();
        } catch (Exception e) {

        }
        super.onDestroy();
    }

    @Override
    public void onClick(View v) {
        if (!alarmActive)
            return;
            alarmActive = false;
            if (vibrator != null)
                vibrator.cancel();
            try {
                mediaPlayer.stop();
            } catch (IllegalStateException ise) {

            }
            try {
                mediaPlayer.release();
            } catch (Exception e) {

            }
            this.finish();
    }
}
