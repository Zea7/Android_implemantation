package com.example.gallery;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.PendingIntent;
import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.Context;
import android.content.Intent;
import android.content.IntentSender;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.GradientDrawable;
import android.media.Image;
import android.net.Uri;
import android.provider.BaseColumns;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.Serializable;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class IVAdapter extends RecyclerView.Adapter<IVAdapter.IVHolder> {
    private ArrayList<IVitem> images;
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


    public IVAdapter() {}

    public IVAdapter(Context _content, ArrayList<IVitem> a_list){
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
        int p = position;

        Glide.with(viewHolder.img.getContext()).load(item.getPath()).into(viewHolder.img);

        viewHolder.img.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                Log.d("GalleryAdapter", "Clicked" + item.getPath());

                Intent intent = new Intent(content, TouchActivity.class);

                intent.putExtra("images", images);
                intent.putExtra("image_path", item.getPath());
                intent.putExtra("image_uri", item.getUri());
                intent.putExtra("num", p);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                content.startActivity(intent);
            }
        });
    }


    @Override
    public int getItemCount(){
        return images.size();
    }
}
