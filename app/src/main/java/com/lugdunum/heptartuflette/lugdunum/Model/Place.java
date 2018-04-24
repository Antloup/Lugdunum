package com.lugdunum.heptartuflette.lugdunum.Model;

import com.google.android.gms.maps.model.LatLng;

import java.util.List;

public class Place {
    private int id;
    private LatLng location;
    private List<OldPhoto> oldPhotos;
    private List<RecentPhoto> recentPhotos;

    public Place(int id, LatLng location, List<OldPhoto> oldPhotos, List<RecentPhoto> recentPhotos) {
        this.id = id;
        this.location = location;
        this.oldPhotos = oldPhotos;
        this.recentPhotos = recentPhotos;
    }

    public LatLng getLocation() {
        return location;
    }

    public List<OldPhoto> getOldPhotos() {
        return oldPhotos;
    }

    public List<RecentPhoto> getRecentPhotos() {
        return recentPhotos;
    }
}
