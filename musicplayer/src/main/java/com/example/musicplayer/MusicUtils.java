package com.example.musicplayer;

import static androidx.core.content.ContextCompat.startActivity;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.io.File;
import java.util.ArrayList;

public class MusicUtils {
    private String[] items;
    private ListView listView;
    Context content;
    LayoutInflater inflater;

    public MusicUtils(Context content, ListView listView, LayoutInflater inflater){
        this.listView = listView;
        this.content = content;
        this.inflater = inflater;
    }


    public void displaySongs(){
        final ArrayList<File> mySongs = findSong();
        items = new String[mySongs.size()];
        for(int i=0;i<mySongs.size();i++){
            items[i] = mySongs.get(i).getName().toString().replace(".mp3", "").replace(".wav", "");

        }
        customAdapter customAdapter = new customAdapter(inflater);
        listView.setAdapter(customAdapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener(){

            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                String songName = (String) listView.getItemAtPosition(i);
                content.startActivity(new Intent(listView.getContext().getApplicationContext(), PlayerActivity.class)
                        .putExtra("songs", mySongs)
                        .putExtra("songName", songName)
                        .putExtra("pos", i));
            }
        });
    }

    class customAdapter extends BaseAdapter {
        private LayoutInflater inflater;

        public customAdapter(LayoutInflater content){
            this.inflater= content;
        }

        @Override
        public int getCount() {
            return items.length;
        }

        @Override
        public Object getItem(int i) {
            return null;
        }

        @Override
        public long getItemId(int i) {
            return 0;
        }

        @Override
        public View getView(int i, View view, ViewGroup viewGroup) {
            View myView = inflater.inflate(com.example.musicplayer.R.layout.music_item, null);
            TextView textSong = myView.findViewById(com.example.musicplayer.R.id.songName);
            textSong.setSelected(true);
            textSong.setText(items[i]);

            return myView;
        }
    }
    private ArrayList<File> findSong(){
        ArrayList <File> list = new ArrayList<>();
        Uri uri;
        Cursor cursor;
        int column_index_data, column_index_folder_name;
        String absolutePathofImage;
        uri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;

        String[] projection = {MediaStore.MediaColumns.DATA, MediaStore.Images.Media.BUCKET_DISPLAY_NAME, MediaStore.MediaColumns.DATE_TAKEN, MediaStore.MediaColumns._ID};
        cursor = listView.getContext().getContentResolver().query(uri, projection, null, null, MediaStore.MediaColumns.DATE_TAKEN + " DESC");

        column_index_data = cursor.getColumnIndexOrThrow(MediaStore.MediaColumns.DATA);

        while (cursor.moveToNext()){
            absolutePathofImage = cursor.getString(column_index_data);


            list.add(new File(absolutePathofImage));
        }

        return list;
    }
}
