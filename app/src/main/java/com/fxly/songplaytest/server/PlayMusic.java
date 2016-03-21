package com.fxly.songplaytest.server;

import android.app.Activity;
import android.app.Service;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.IBinder;

import android.app.Service;
import android.content.ContentResolver;
import android.content.Intent;
import android.database.Cursor;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Environment;
import android.os.IBinder;
import android.provider.MediaStore;



import java.io.IOException;

/**
 * Created by Lambert Liu on 2016-03-18.
 */
public class PlayMusic extends Service {

//    private static final String SONGFILENAME = "love.mp3\"";
    private MediaPlayer player;

    String path = Environment.getExternalStorageDirectory().toString() + "/";
    private MediaPlayer mediaPlayer;
    private final Uri musicTableForSD = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
    private final String musicTitle = MediaStore.Audio.AudioColumns.TITLE;
    private final String musicId = MediaStore.Audio.Media._ID;
    @Override
    public IBinder onBind(Intent intent) {
        // TODO Auto-generated method stub
        return null;
    }

    public void onStart(Intent intent, int startId) {
        super.onStart(intent, startId);

        playBackgroundMusic();


    }

    public void onDestroy() {
        super.onDestroy();
        mediaPlayer.stop();
        mediaPlayer.release();
    }




    private void playBackgroundMusic() {
        // Get a ContentResovler,
        // so that we can read the music information form SD card.
        SharedPreferences mysharedpreferences = getSharedPreferences("test_settings", Activity.MODE_PRIVATE);
        String  song_play_name = mysharedpreferences.getString("songname", "test");


        ContentResolver contentResolver = getContentResolver();
        String musicName = "%"+song_play_name;
        Cursor cursor = contentResolver.query(
                musicTableForSD,
                new String[]{musicId,musicTitle},
                musicTitle + " LIKE ?",
                new String[]{musicName},
                null);
        // If cursor has a recode, we support that we find the music.
        if (cursor.moveToNext()) {
            // Get the music id.
            int position = cursor.getInt(cursor.getColumnIndex(musicId));
            Uri uri = Uri.withAppendedPath(musicTableForSD, "/" + position);
            mediaPlayer = MediaPlayer.create(this, uri);
            if (null != mediaPlayer) {
                mediaPlayer.start();
                mediaPlayer.setLooping(true);
            }
        }
        // Finish our query work.
        cursor.close();
    }
}

