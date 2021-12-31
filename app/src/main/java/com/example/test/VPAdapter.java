package com.example.test;

import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.Lifecycle;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.adapter.FragmentStateAdapter;
import androidx.viewpager2.widget.ViewPager2;

import com.example.gallery.IVAdapter;
import com.example.gallery.IVDecoration;
import com.example.gallery.IVitem;

import java.util.ArrayList;

public class VPAdapter extends FragmentStateAdapter {
    private ArrayList<Fragment> items;
    private int size = 0;
    private ArrayList<String> titles = new ArrayList<String>();



    public VPAdapter(@NonNull FragmentActivity fm) {
        super(fm);
        items = new ArrayList<Fragment>();
        size = 3;

        titles.add("연락처");
        titles.add("사진첩");
        titles.add("노트");
    }



    @NonNull
    @Override
    public Fragment createFragment(int position) {
        size++;
        if(position == 0) return Fragment1.newInstance(position);
        else if(position == 1) return Fragment2.newInstance(position);
        else return Fragment3.newInstance(position);

    }

    @Override
    public int getItemCount() {
        return size;
    }
}
