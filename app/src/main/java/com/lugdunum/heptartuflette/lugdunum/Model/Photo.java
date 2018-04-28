package com.lugdunum.heptartuflette.lugdunum.Model;

import android.graphics.Bitmap;

import java.io.File;

public class Photo {
    public enum format {PNG,JPG};

    private int id;
    private String name;
    private String photoFormat;
    private Bitmap image;

    public Photo(int id, String name, String photoFormat, Bitmap image) {
        this.id = id;
        this.name = name;
        this.photoFormat = photoFormat;
        this.image = image;
    }

    public String getName() {
        return name;
    }

    public String getPhotoFormat() {
        return photoFormat;
    }

    public Bitmap getImage() {
        return image;
    }
}
