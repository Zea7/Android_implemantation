package com.example.test;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;
import androidx.viewpager2.widget.ViewPager2;

import com.example.gallery.IVAdapter;
import com.example.gallery.IVDecoration;
import com.example.gallery.IVitem;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class MainActivity extends AppCompatActivity {
    private final int[] images = {
            R.drawable.contact,
            R.drawable.gallery,
            R.drawable.note
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super .onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ViewPager2 vp = findViewById(R.id.viewpager);
        VPAdapter adapter = new VPAdapter(this);
        vp.setAdapter(adapter);

        TabLayout tab = findViewById(R.id.tab);

        List<String> names = new ArrayList<String>(){{add("연락처");add("사진첩");add("노트");}};

        new TabLayoutMediator(tab, vp, new TabLayoutMediator.TabConfigurationStrategy() {
            @Override
            public void onConfigureTab(@NonNull TabLayout.Tab tab, int position) {
                tab.setIcon(images[position]);
                tab.setText(names.get(position));
            }
        }).attach();

    }
}
