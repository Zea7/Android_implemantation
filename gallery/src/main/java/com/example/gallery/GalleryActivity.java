package com.example.gallery;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.database.Cursor;
import android.graphics.drawable.GradientDrawable;
import android.media.Image;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import java.util.ArrayList;
import java.util.List;

public class GalleryActivity extends AppCompatActivity {
    RecyclerView recyclerView;
    IVAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//
//        setContentView(R.layout.activity_gallery);
//
//        ArrayList<IVitem> itemList = new ArrayList<>();
//
//        adapter = new IVAdapter(itemList);
//
//        adapter.setOnImageClickListener(new IVAdapter.onImageClickListener() {
//            @Override
//            public void onImageClick(View v, int position) {
//                LayoutInflater inflater = LayoutInflater.from(v.getContext());
//
//            }
//        });
//        recyclerView.setAdapter(adapter);
//        recyclerView.addItemDecoration(new IVDecoration(recyclerView.getContext()));
//        recyclerView.setNestedScrollingEnabled(false);
    }

}