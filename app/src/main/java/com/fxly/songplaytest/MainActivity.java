package com.fxly.songplaytest;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends AppCompatActivity {

    long count;
    private Button mBtnStartTime;
    private Button mBtnStopTime;
    private TextView show_min;
    private TextView mTVTime;
    private TextView show_se;

    private int mCount = 0;

    private Timer mTimer = null;
    private TimerTask mTimerTask = null;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mTVTime = (TextView) findViewById(R.id.showtime);
//        show_min = (TextView) findViewById(R.id.showtime1);
//        show_se = (TextView) findViewById(R.id.showtime1);

        mBtnStartTime = (Button) findViewById(R.id.btn_start_time);
        mBtnStopTime = (Button) findViewById(R.id.btn_stop_time);


        mBtnStartTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                startTime();

                SharedPreferences mysharedpreferences = getSharedPreferences("test_settings", Activity.MODE_PRIVATE);
                int getinterview_day = mysharedpreferences.getInt("control_day", 15);
                int getinterview_hor = mysharedpreferences.getInt("control_hor", 23);
                int getinterview_min = mysharedpreferences.getInt("control_min", 59);

                count = getinterview_day * 24 * 3600 + getinterview_hor * 3600 + getinterview_min * 60;

//                new Thread(new Runnable(){
//                    public void run(){
//                        while(threadDisable){
//
//
//                        }
//                    }
//                }).start();

                if (count > 0) {
                    start_play_music();

                } else {
                    stop_play_music();
                }

            }
        });

        mBtnStopTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                stop_play_music();
            }
        });

    }


    public void start_play_music() {

//      new Thread(new Runnable(){
//          public void run(){
//              for(int mm=10;;mm++){
//
//              }
//
//          }
//      }).start();
        startService(new Intent(
                "com.kazam.PlaysongService.START_AUDIO_SERVICE"));

        is_stop();

    }

    public void stop_play_music() {
        stopService(new Intent(
                "com.kazam.PlaysongService.START_AUDIO_SERVICE"));

//        mTVTime.setText("00:00:00" );
    }

    public void is_stop() {

        new Thread(new MyThread()).start();

    }

    final Handler handler = new Handler() {          // handle
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 1:

                    if (count>0){
                        count--;
                        long hor= count/3600;
                        long min= (count-(hor*3600))/60;
                        long se= count%60;
//                        if(hor>=10){
//                            mTVTime.setText(""+ hor );
//                        }else {
//                            mTVTime.setText("0" + hor);
//                        }
//                        if(min>=10){
//                            show_min.setText(""+ hor );
//                        }else {
//                            show_min.setText("0" + min);
//                        }
//                        if(se>=10){
//                            show_se.setText(""+ hor );
//                        }else {
//                            show_se.setText("0" + se);
//                        }
                        mTVTime.setText("0" + hor+":"+min+":"+se);

                        break;
                    }else {
                        stop_play_music();
                        mTVTime.setText("00:00:00" );
//                        show_min.setText("00" );
//                        show_se.setText("00" );
                    }
            }
            super.handleMessage(msg);
        }
    };

    public class MyThread implements Runnable {      // thread
        @Override
        public void run() {
            while (true) {
                try {
                    Thread.sleep(1000);     // sleep 1000ms
                    Message message = new Message();
                    message.what = 1;
                    handler.sendMessage(message);
                } catch (Exception e) {
                }
            }

        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            Intent set=new Intent();
            set.setClass(MainActivity.this, SettingsActivity.class);
            startActivity(set);

            return true;
        }

        return super.onOptionsItemSelected(item);
    }

}
