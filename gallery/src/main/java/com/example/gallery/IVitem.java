package com.example.gallery;

import android.net.Uri;

import java.io.Serializable;

public class IVitem implements Serializable {
    private String imageResId;
    private String uri;

    public IVitem(String redId, Uri id){
        imageResId = redId;
        this.uri = id.toString();
    }

    public String getPath(){
        return imageResId;
    }
    public Uri getUri(){
        return Uri.parse(uri);
    }
}
