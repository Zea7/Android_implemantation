package com.example.musicplayer;

import java.io.Serializable;

public class Music implements Serializable {
    private String name, artist, album,  duration, uriStr, albumId,imagePath;

    public Music(String name, String artist, String album, String duration, String uriStr, String albumId, String imagePath) {
        this.name = name;
        this.artist = artist;
        this.album = album;
        this.duration = duration;
        this.uriStr = uriStr;
        this.albumId = albumId;
        this.imagePath = imagePath;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getArtist() {
        return artist;
    }

    public void setArtist(String artist) {
        this.artist = artist;
    }

    public String getAlbum() {
        return album;
    }

    public void setAlbum(String album) {
        this.album = album;
    }


    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

    public String getUriStr() {
        return uriStr;
    }

    public void setUriStr(String uriStr) {
        this.uriStr = uriStr;
    }

    public String getAlbumId() {
        return albumId;
    }

    public void setAlbumId(String albumId) {
        this.albumId = albumId;
    }

    public String getImagePath() {
        return imagePath;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }
}

