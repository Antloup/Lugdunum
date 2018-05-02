package com.lugdunum.heptartuflette.lugdunum.Model;

import android.graphics.Bitmap;

import java.io.File;
import java.util.Date;

public class RecentPhoto extends Photo {
    private Date date;
    private double score;
    private int voteNumber;

    public RecentPhoto(int id, String name, String photoFormat, Bitmap image, Date date, double score, int voteNumber) {
        super(id, name, photoFormat, image);
        this.date = date;
        this.score = score;
        this.voteNumber = voteNumber;
    }

    public RecentPhoto(String name, String photoFormat, Bitmap image, Date date) {
        super(name, photoFormat, image);
        this.date = date;
    }

    public Date getDate() {
        return date;
    }

    public double getScore() {
        return score;
    }

    public int getVoteNumber() {
        return voteNumber;
    }
}
