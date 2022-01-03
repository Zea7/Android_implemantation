package com.example.musicplayer;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.content.ContentUris;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.io.File;
import java.lang.reflect.Array;
import java.util.ArrayList;

public class MusicPlayerActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private ArrayList<Music> arrayList;
    private MusicAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_music_player);

        arrayList = new ArrayList<>();
        recyclerView = findViewById(R.id.musicPlayerList);
        adapter = new MusicAdapter(this, arrayList);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);
    }
    private ArrayList<Music> getAllAudio(){
        ArrayList<Music> tmpAudioList = new ArrayList<>();
        Uri uri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
        final String[] cursor = {MediaStore.Audio.Media._ID, MediaStore.Audio.Media.ALBUM,  MediaStore.Audio.Media.TITLE,
                MediaStore.Audio.Media.DATA, MediaStore.Audio.Media.ALBUM_ID, MediaStore.Audio.Media.DURATION };
        final String where = MediaStore.Audio.Media.IS_MUSIC + "=1";
        final Cursor cursor1 = recyclerView.getContext().getContentResolver().query(uri, cursor, where, null, null);
        while(cursor1.moveToNext()){
            String artist = cursor1.getString(cursor1.getColumnIndexOrThrow(MediaStore.Audio.Media.ARTIST));
            String album = cursor1.getString(cursor1.getColumnIndexOrThrow(MediaStore.Audio.Media.ALBUM));
            String title = cursor1.getString(cursor1.getColumnIndexOrThrow(MediaStore.Audio.Media.TITLE));
            String songUri = cursor1.getString(cursor1.getColumnIndexOrThrow(MediaStore.Audio.Media.DATA));
            long albumId = cursor1.getLong(cursor1.getColumnIndexOrThrow(MediaStore.Audio.Media.ALBUM_ID));
            int duration = cursor1.getInt(cursor1.getColumnIndexOrThrow(MediaStore.Audio.Media.DURATION));
            Uri imagePath = Uri.parse("content://media/external/audio/albumart");
            Uri imagePathUri = ContentUris.withAppendedId(imagePath, albumId);
            Music music = new Music(title, album, artist, String.valueOf(duration), uri.toString(), String.valueOf(albumId), imagePathUri.toString());
            arrayList.add(music);
        }
        return tmpAudioList;
    }

}