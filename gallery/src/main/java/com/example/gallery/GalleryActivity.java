package com.example.gallery;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.graphics.drawable.GradientDrawable;
import android.media.Image;
import android.os.Bundle;
import android.widget.ImageView;

import java.util.ArrayList;

public class GalleryActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_gallery);

        bindList();
    }

    private void bindList(){
        ArrayList<IVitem> itemList = new ArrayList<>();

        for(int i=1; i<=26; i++){
            itemList.add(new IVitem(getResources().getIdentifier("_" + i, "drawable", getPackageName())));
        }


        // Recycler view
        RecyclerView recyclerView = findViewById(R.id.icon_gallery);

        // Adapter
        IVAdapter adapter = new IVAdapter(itemList);
        recyclerView.setAdapter(adapter);
        recyclerView.addItemDecoration(new IVDecoration(this));
    }
}