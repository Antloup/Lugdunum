package com.lugdunum.heptartuflette.lugdunum.Model;

import java.io.File;

public class Photo {
    public enum format {PNG,JPG};

    private int id;
    private String name;
    private format photoFormat;
    private File image;

    public Photo(int id, String name, format photoFormat, File image) {
        this.id = id;
        this.name = name;
        this.photoFormat = photoFormat;
        this.image = image;
    }

    public String getName() {
        return name;
    }

    public format getPhotoFormat() {
        return photoFormat;
    }

    public File getImage() {
        return image;
    }
}
