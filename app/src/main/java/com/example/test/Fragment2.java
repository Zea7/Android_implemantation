package com.example.test;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.media.Image;
import android.net.Uri;
import android.opengl.Matrix;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.core.content.FileProvider;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.os.Environment;
import android.os.Handler;
import android.provider.ContactsContract;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.LinearLayout;

import com.example.contact.ModelContacts;
import com.example.gallery.*;
import com.google.android.material.snackbar.Snackbar;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link Fragment2#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Fragment2 extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private RecyclerView recyclerView;
    private LinearLayout view;
    private IVAdapter adapter = new IVAdapter();
    private SwipeRefreshLayout refresh_layout;
    private File file = null;
    private String currentPhotoPath;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public Fragment2() {
        // Required empty public constructor
    }

    // TODO: Rename and change types and number of parameters
    public static Fragment2 newInstance(int n) {
        Fragment2 fragment = new Fragment2();
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
        try {
            return fragment_gallery(inflater, container, savedInstanceState);
        } catch (Exception e){
            ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.fragment_2, container, false);
            view = (LinearLayout) rootView.findViewById(R.id.temp_layer_2);
            refresh_layout = (SwipeRefreshLayout) rootView.findViewById(R.id.swipe_image);
            refresh_layout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
                @Override
                public void onRefresh() {
                    view.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            Snackbar.make(view, "Refresh Success", Snackbar.LENGTH_SHORT).show();
                            refresh_layout.setRefreshing(false);
                            Fragment2 fragment = Fragment2.newInstance(10);

                            FragmentManager fm = getParentFragmentManager();
                            FragmentTransaction ft = fm.beginTransaction();
                            ft.replace(R.id.fragment_second, fragment).commitAllowingStateLoss();
                        }
                    }, 500);
                }
            });

            return rootView;
        }
    }

    public ViewGroup fragment_gallery(LayoutInflater inflater, ViewGroup container,
                                      Bundle savedInstanceState) throws Exception {
        ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.activity_gallery, container, false);

        recyclerView = (RecyclerView)rootView.findViewById(R.id.icon_gallery);

        if(ContextCompat.checkSelfPermission(recyclerView.getContext(), Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED)
            throw new Exception("저장소 권한 없이 읽기 시도");

        List<IVitem> list = getConstract(recyclerView);

        refresh_layout = (SwipeRefreshLayout) rootView.findViewById(com.example.gallery.R.id.swipe_layout);
        refresh_layout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                recyclerView.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        Snackbar.make(recyclerView, "Refresh Success", Snackbar.LENGTH_SHORT).show();
                        refresh_layout.setRefreshing(false);

                        refresh();
                    }
                }, 500);
            }
        });

        adapter = new IVAdapter(recyclerView.getContext(), list);


        adapter.setOnImageClickListener(new IVAdapter.onImageClickListener() {
            @Override
            public void onImageClick(View v, int position) {
                LayoutInflater inflater = LayoutInflater.from(v.getContext());

            }
        });

        ImageButton button = (ImageButton) rootView.findViewById(R.id.camera_button);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Context content = view.getContext();

                Intent intent = new Intent(content, CameraActivity.class);
                startActivity(intent);
                refresh();
            }
        });

        recyclerView.setAdapter(adapter);
        recyclerView.addItemDecoration(new IVDecoration(recyclerView.getContext()));
        recyclerView.setNestedScrollingEnabled(false);

        return rootView;
    }

    private List<IVitem> getConstract(View view){
        List <IVitem> list = new ArrayList<>();
        Uri uri;
        Cursor cursor;
        int column_index_data, column_index_folder_name;
        String absolutePathofImage;
        uri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;

        String[] projection = {MediaStore.MediaColumns.DATA, MediaStore.Images.Media.BUCKET_DISPLAY_NAME, MediaStore.MediaColumns.DATE_TAKEN, MediaStore.MediaColumns._ID};
        cursor = view.getContext().getContentResolver().query(uri, projection, null, null, MediaStore.MediaColumns.DATE_TAKEN + " DESC");

        column_index_data = cursor.getColumnIndexOrThrow(MediaStore.MediaColumns.DATA);

        while (cursor.moveToNext()){
            absolutePathofImage = cursor.getString(column_index_data);


            list.add(new IVitem(absolutePathofImage));
        }

        return list;
    }

    private void refresh(){
        List<IVitem> newlist = getConstract(recyclerView);
        adapter = new IVAdapter(recyclerView.getContext(), newlist);
        recyclerView.swapAdapter(adapter, true);
    }


}