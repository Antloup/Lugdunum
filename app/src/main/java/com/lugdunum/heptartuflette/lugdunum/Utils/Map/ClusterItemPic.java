package com.lugdunum.heptartuflette.lugdunum.Utils.Map;

import com.google.android.gms.maps.model.LatLng;
import com.google.maps.android.clustering.ClusterItem;

public class ClusterItemPic implements ClusterItem {
    private final LatLng mPosition;
    private final int id;

    public ClusterItemPic(double lat, double lng, int id) {
        mPosition = new LatLng(lat, lng);
        this.id=id;
    }

    @Override
    public LatLng getPosition() {
        return mPosition;
    }

    public int getId(){ return id;}

}
