package com.example.gallery;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.graphics.drawable.GradientDrawable;
import android.media.Image;
import android.os.Bundle;
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

        setContentView(R.layout.activity_gallery);

        bindList();
    }

    public ViewGroup fragment_gallery(LayoutInflater inflater, ViewGroup container,
                                Bundle savedInstanceState){
        ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.activity_gallery, container, false);

        recyclerView = (RecyclerView) rootView.findViewById(R.id.icon_gallery);

        bindList();

        return rootView;
    }

    private void bindList(){
        ArrayList<IVitem> itemList = new ArrayList<>();

        for(int i=1; i<=26; i++){
            itemList.add(new IVitem(recyclerView.getContext().getResources().getIdentifier("_" + i, "drawable", recyclerView.getContext().getPackageName())));
        }

        adapter = new IVAdapter(itemList);

        adapter.setOnImageClickListener(new IVAdapter.onImageClickListener() {
            @Override
            public void onImageClick(View v, int position) {
                LayoutInflater inflater = LayoutInflater.from(v.getContext());

            }
        });
        recyclerView.setAdapter(adapter);
        recyclerView.addItemDecoration(new IVDecoration(recyclerView.getContext()));
        recyclerView.setNestedScrollingEnabled(false);
    }
}