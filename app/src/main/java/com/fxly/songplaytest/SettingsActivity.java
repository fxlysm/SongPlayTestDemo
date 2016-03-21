package com.fxly.songplaytest;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Timer;
import java.util.TimerTask;

public class SettingsActivity extends AppCompatActivity {
    SeekBar timeline_day, timeline_hor, timeline_min, timeline_second;
    TextView time_day, time_hor, time_min, time_second;

    EditText songname_edit;

    private int mCount = 0;

    private Timer mTimer = null;
    private TimerTask mTimerTask = null;

    LinearLayout about;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);


        songname_edit = (EditText) findViewById(R.id.songname_edit);

        time_day = (TextView) findViewById(R.id.time_day);
        time_hor = (TextView) findViewById(R.id.time_hor);
        time_min = (TextView) findViewById(R.id.time_min);


        timeline_day = (SeekBar) findViewById(R.id.timeline_day);
//        timeline_day.setMinimumWidth(-30);
        timeline_hor = (SeekBar) findViewById(R.id.timeline_hor);
        timeline_min = (SeekBar) findViewById(R.id.timeline_min);

        about=(LinearLayout) findViewById(R.id.about_layout);



        timeline_day.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar arg0, int arg1, boolean arg2) {
                time_day.setText(Integer.toString(arg1));
            }

            @Override
            public void onStartTrackingTouch(SeekBar arg0) {
            }

            @Override
            public void onStopTrackingTouch(SeekBar arg0) {
                // when tracking stoped, update preferences
                int interval_day = arg0.getProgress();
                SharedPreferences mysharedpreferences = getSharedPreferences("test_settings", Activity.MODE_PRIVATE);
                SharedPreferences.Editor editor = mysharedpreferences.edit();
                editor.putInt("control_day", interval_day);
                editor.commit();
//                preferences.edit().putInt(Settings.KEY_INTERVAL, interval).commit();
            }
        });

        timeline_hor.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar arg0, int arg1, boolean arg2) {
                time_hor.setText(Integer.toString(arg1));
            }

            @Override
            public void onStartTrackingTouch(SeekBar arg0) {
            }

            @Override
            public void onStopTrackingTouch(SeekBar arg0) {
                // when tracking stoped, update preferences
                int interval_day = arg0.getProgress();
                SharedPreferences mysharedpreferences = getSharedPreferences("test_settings", Activity.MODE_PRIVATE);
                SharedPreferences.Editor editor = mysharedpreferences.edit();
                editor.putInt("control_hor", interval_day);
                editor.commit();
//                preferences.edit().putInt(Settings.KEY_INTERVAL, interval).commit();
            }
        });

        timeline_min.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar arg0, int arg1, boolean arg2) {
                time_min.setText(Integer.toString(arg1));
            }

            @Override
            public void onStartTrackingTouch(SeekBar arg0) {
            }

            @Override
            public void onStopTrackingTouch(SeekBar arg0) {
                // when tracking stoped, update preferences
                int interval_day = arg0.getProgress();
                SharedPreferences mysharedpreferences = getSharedPreferences("test_settings", Activity.MODE_PRIVATE);
                SharedPreferences.Editor editor = mysharedpreferences.edit();
                editor.putInt("control_min", interval_day);
                editor.commit();
//                preferences.edit().putInt(Settings.KEY_INTERVAL, interval).commit();
            }
        });



        about.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent set=new Intent();
                set.setClass(SettingsActivity.this, AboutActivity.class);
                startActivity(set);
            }
        });

    }


    @Override
    protected void onStart() {

        SharedPreferences mysharedpreferences = getSharedPreferences("test_settings", Activity.MODE_PRIVATE);
//        SharedPreferences.Editor editor = mysharedpreferences.edit();
//        editor.putString("songname", songname_edit.getText().toString());
//        editor.commit();



        int getinterview_day = mysharedpreferences.getInt("control_day", 15);
        int getinterview_hor = mysharedpreferences.getInt("control_hor", 12);
        int getinterview_min = mysharedpreferences.getInt("control_min", 30);
        String song_name=mysharedpreferences.getString("songname","test");




        time_day.setText(String.valueOf(getinterview_day));
        time_hor.setText(String.valueOf(getinterview_hor));
        time_min.setText(String.valueOf(getinterview_min));

        timeline_day.setProgress(getinterview_day);
        timeline_hor.setProgress(getinterview_hor);
        timeline_min.setProgress(getinterview_min);
        songname_edit.setText(song_name);

        super.onStart();
    }

    @Override
    protected void onStop() {
        super.onStop();

        SharedPreferences mysharedpreferences = getSharedPreferences("test_settings", Activity.MODE_PRIVATE);
        SharedPreferences.Editor editor = mysharedpreferences.edit();
        editor.putString("songname", songname_edit.getText().toString());
        editor.commit();
        Toast.makeText(this, R.string.set_confi_save , Toast.LENGTH_LONG).show();
    }
}
