package com.lugdunum.heptartuflette.lugdunum.Model;

import com.google.android.gms.maps.model.LatLng;

import java.util.Vector;

public class Place {
    private int id;
    private LatLng location;
    private Vector<OldPhoto> oldPhotos;
    private Vector<RecentPhoto> recentPhotos;

    public Place(int id, LatLng location, Vector<OldPhoto> oldPhotos, Vector<RecentPhoto> recentPhotos) {
        this.id = id;
        this.location = location;
        this.oldPhotos = oldPhotos;
        this.recentPhotos = recentPhotos;
    }

    public LatLng getLocation() {
        return location;
    }

    public Vector<OldPhoto> getOldPhotos() {
        return oldPhotos;
    }

    public Vector<RecentPhoto> getRecentPhotos() {
        return recentPhotos;
    }
}
