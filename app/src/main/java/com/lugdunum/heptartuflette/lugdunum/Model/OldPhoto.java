package com.lugdunum.heptartuflette.lugdunum.Model;

import java.io.File;
import java.util.Date;

public class OldPhoto extends Photo {
    private Date date;
    private float score;
    private int voteNumber;

    public OldPhoto(int id, String name, format photoFormat, File image, Date date, float score, int voteNumber) {
        super(id, name, photoFormat, image);
        this.date = date;
        this.score = score;
        this.voteNumber = voteNumber;
    }

    public Date getDate() {
        return date;
    }

    public float getScore() {
        return score;
    }

    public int getVoteNumber() {
        return voteNumber;
    }
}
