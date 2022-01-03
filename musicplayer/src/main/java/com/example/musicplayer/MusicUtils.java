package com.example.musicplayer;

import static androidx.core.content.ContextCompat.startActivity;

import android.Manifest;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.ContentUris;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.PorterDuff;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SeekBar;
import android.widget.TextView;

import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.gauravk.audiovisualizer.visualizer.BarVisualizer;
import com.google.android.material.snackbar.Snackbar;
import com.gun0912.tedpermission.TedPermission;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class MusicUtils {
    private ArrayList<Music> arrayList = new ArrayList<>();
    private Context content;
    private LayoutInflater inflater;
    private MusicAdapter adapter;
    private RecyclerView recyclerView;
    private Bundle savedInstanceState;
    private ViewGroup container;

    public MusicUtils(Context content, LayoutInflater inflater, Bundle savedInstanceState, ViewGroup container) {
        this.content = content;
        this.inflater = inflater;
        this.savedInstanceState = savedInstanceState;
        this.container = container;
    }

    public View adaptMusic() {
        ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.activity_music_player, container, false);

        recyclerView = rootView.findViewById(R.id.musicPlayerList);
        getAllAudio();
        adapter = new MusicAdapter(content, arrayList);
        recyclerView.setLayoutManager(new LinearLayoutManager(content));
        recyclerView.setAdapter(adapter);

        return rootView;
    }

    private void getAllAudio(){
        Uri uri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
        final String[] cursor = {MediaStore.Audio.Media._ID, MediaStore.Audio.Media.ALBUM,  MediaStore.Audio.Media.TITLE,
                MediaStore.Audio.Media.DATA, MediaStore.Audio.Media.ALBUM_ID, MediaStore.Audio.Media.DURATION };
        final String where = MediaStore.Audio.Media.IS_MUSIC + "=1";
        final Cursor cursor1 = content.getContentResolver().query(uri, cursor, where, null, MediaStore.Audio.Media.TITLE + " ASC");
        while(cursor1.moveToNext()){
            String album = cursor1.getString(cursor1.getColumnIndexOrThrow(MediaStore.Audio.Media.ALBUM));
            String title = cursor1.getString(cursor1.getColumnIndexOrThrow(MediaStore.Audio.Media.TITLE));
            String songUri = cursor1.getString(cursor1.getColumnIndexOrThrow(MediaStore.Audio.Media.DATA));
            long albumId = cursor1.getLong(cursor1.getColumnIndexOrThrow(MediaStore.Audio.Media.ALBUM_ID));
            int duration = cursor1.getInt(cursor1.getColumnIndexOrThrow(MediaStore.Audio.Media.DURATION));
            Uri imagePath = Uri.parse("content://media/external/audio/albumart");
            Uri imagePathUri = ContentUris.withAppendedId(imagePath, albumId);
            Music music = new Music(title, album,  String.valueOf(duration), songUri, String.valueOf(albumId), imagePathUri.toString());
            arrayList.add(music);
        }

    }
}
