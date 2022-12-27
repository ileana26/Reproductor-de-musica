package com.example.ejemplobottomnavigation;

import android.net.Uri;

import java.io.Serializable;

public class AudioMode implements Serializable {
    String Path;
    String Title;
    String Duration;
    Uri ArtworkUri;
    String Artist;

    public String getId() {
        return Id;
    }

    public void setId(String id) {
        Id = id;
    }

    String Id;

    public Uri getArtworkUri() {
        return ArtworkUri;
    }

    public String getArtist() {
        return Artist;
    }

    public void setArtist(String artist) {
        Artist = artist;
    }

    public AudioMode(String path, String title, String duration, String artist, String id) {
        Path = path;
        Title = title;
        Duration = duration;
        Artist = artist;
        Id = id;
    }

    public String getPath() {
        return Path;
    }

    public void setPath(String path) {
        Path = path;
    }

    public String getTitle() {
        return Title;
    }

    public String getDuration() {
        return Duration;
    }

    public void setTitle(String title) {
        Title = title;
    }

    public void setDuration(String duration) {
        Duration = duration;
    }


}
