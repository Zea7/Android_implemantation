package com.example.gallery;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.GradientDrawable;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.SimpleAdapter;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.List;

public class IVAdapter extends RecyclerView.Adapter<IVAdapter.IVHolder> {
    private List<IVitem> images;
    private Context content;

    public interface onImageClickListener {
        void onImageClick(View v, int position);
    }

    private onImageClickListener mListener = null;


    public void setOnImageClickListener(onImageClickListener listener){
        this.mListener = listener;
    }

    public class IVHolder extends RecyclerView.ViewHolder{
        ImageView img;
        public IVHolder(View itemview) {
            super(itemview);

            img = (ImageButton) itemview.findViewById(R.id.img_icon);
            img.setClipToOutline(true);

        }
    }



    public IVAdapter(Context _content, List<IVitem> a_list){
        images = a_list;
        this.content = _content;
    }

    @Override
    public IVHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType){
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.gallery_item, viewGroup, false);

        return new IVHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull IVHolder viewHolder, int position){
        IVitem item = images.get(position);

<<<<<<< HEAD
        Glide.with(viewHolder.img.getContext()).load(item.getImageResId()).into(viewHolder.img);
=======
        Glide.with(viewHolder.img.getContext()).load(item.getPath()).into(viewHolder.img);

        viewHolder.img.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                String name = item.getPath().split("/")[item.getPath().split("/").length - 1];
                System.out.println(name.split("."));

                Log.d("GalleryAdapter", "Clicked" + item.getPath());

                Intent intent = new Intent(content, TouchActivity.class);
                intent.putExtra("image_path", item.getPath());
                intent.putExtra("image_name", name);
                content.startActivity(intent);
            }
        });
>>>>>>> 6b3832d (Gallery Prototype(12.31))
    }

    @Override
    public int getItemCount(){
        return images.size();
    }



}
