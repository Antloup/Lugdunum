package com.lugdunum.heptartuflette.lugdunum.Model;

import java.io.File;
import java.util.Date;

public class OldPhoto extends Photo {

    private String date;
    private String description;
    private String infoLink;

    public OldPhoto(int id, String name, format photoFormat, File image, String date, String description, String infoLink) {
        super(id, name, photoFormat, image);
        this.date = date;
        this.description = description;
        this.infoLink = infoLink;
    }

    public String getDate() {
        return date;
    }

    public String getDescription() {
        return description;
    }

    public String getInfoLink() {
        return infoLink;
    }
}
