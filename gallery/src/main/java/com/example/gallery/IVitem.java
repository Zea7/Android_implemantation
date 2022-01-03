package com.example.gallery;

import android.net.Uri;

import java.io.Serializable;

public class IVitem implements Serializable {
    private String imageResId;
    private Uri uri;

    public IVitem(String redId, Uri id){
        imageResId = redId;
        this.uri = id;
    }

    public String getPath(){
        return imageResId;
    }
    public Uri getUri(){
        return uri;
    }
}
