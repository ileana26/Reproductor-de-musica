package com.example.ejemplobottomnavigation;

import java.io.Serializable;

public class AlbumModel implements Serializable {
    String Path;
    String Title;

    public AlbumModel(String path, String title) {
        Path = path;
        Title = title;
    }

    public String getPath() {
        return Path;
    }

    public String getTitle() {
        return Title;
    }

    public void setPath(String path) {
        Path = path;
    }

    public void setTitle(String title) {
        Title = title;
    }

}
