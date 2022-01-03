package com.example.test;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.os.Environment;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.example.gallery.IVitem;
import com.example.musicplayer.Music;
import com.example.musicplayer.MusicPlayerActivity;
import com.example.musicplayer.MusicUtils;
import com.example.musicplayer.PlayerActivity;
import com.gun0912.tedpermission.TedPermission;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link Fragment3#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Fragment3 extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private ListView listView;
    private String[] items;


    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public Fragment3() {
        // Required empty public constructor
    }

    public static Fragment3 newInstance(int n) {
        Fragment3 fragment = new Fragment3();
        Bundle args = new Bundle();
        args.putInt("number", n);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return adaptMusicPlayer(inflater, container, savedInstanceState);
    }

    private View adaptMusicPlayer(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        ViewGroup rootView = (ViewGroup) inflater.inflate(com.example.musicplayer.R.layout.activity_music_player, container, false);
        listView = (ListView) rootView.findViewById(com.example.musicplayer.R.id.musicPlayerList);

        MusicUtils musicplayer = new MusicUtils(listView.getContext(), listView, inflater);
        musicplayer.displaySongs();
        return rootView;
    }


}