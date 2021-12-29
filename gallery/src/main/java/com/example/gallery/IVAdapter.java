package com.example.gallery;

import android.graphics.drawable.GradientDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.SimpleAdapter;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class IVAdapter extends RecyclerView.Adapter<IVHolder> {
    private List<IVitem> images;

    public IVAdapter(List<IVitem> a_list){
        images = a_list;
    }

    @Override
    public IVHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType){
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.gallery_item, viewGroup, false);

        return new IVHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull IVHolder viewHolder, int position){
        final IVitem item = images.get(position);

        viewHolder.img.setImageResource(item.getImageResId());
    }

    @Override
    public int getItemCount(){
        return images.size();
    }


}
