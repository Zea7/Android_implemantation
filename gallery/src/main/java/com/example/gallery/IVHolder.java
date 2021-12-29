package com.example.gallery;

import android.graphics.drawable.GradientDrawable;
import android.view.View;
import android.widget.ImageView;

import androidx.recyclerview.widget.RecyclerView;

public class IVHolder extends RecyclerView.ViewHolder {

    ImageView img;

    public IVHolder(View itemview){
        super(itemview);

        img = (ImageView) itemview.findViewById(R.id.img_icon);
        img.setClipToOutline(true);
    }
}
