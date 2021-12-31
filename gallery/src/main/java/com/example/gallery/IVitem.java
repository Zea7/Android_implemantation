package com.example.gallery;

import android.net.Uri;

public class IVitem {
    private String imageResId;

    public IVitem(String redId){
        imageResId = redId;
    }

    public String getPath(){
        return imageResId;
    }
}
