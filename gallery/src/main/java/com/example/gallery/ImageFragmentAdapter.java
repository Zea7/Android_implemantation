package com.example.gallery;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import java.util.ArrayList;

public class ImageFragmentAdapter extends FragmentStateAdapter {
    private ArrayList<IVitem> images = new ArrayList<>();
    private int pos;


    public ImageFragmentAdapter(@NonNull FragmentActivity fragmentActivity, ArrayList<IVitem> images, int position) {
        super(fragmentActivity);
        this.images = images;
        this.pos = position;
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        return ImageFragment.newInstance(images, position);
    }

    @Override
    public int getItemCount() {
        return images.size();
    }
}
