package com.example.gallery;

import android.net.Uri;

public class IVitem {
    private int imageResId;

    public IVitem(int redId){
        imageResId = redId;
    }

    public int getImageResId(){
        return imageResId;
    }
}
