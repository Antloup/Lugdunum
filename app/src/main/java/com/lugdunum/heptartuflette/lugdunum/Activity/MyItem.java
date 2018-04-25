package com.lugdunum.heptartuflette.lugdunum.Activity;

import com.google.android.gms.maps.model.LatLng;
import com.google.maps.android.clustering.ClusterItem;

// Test à l'arrache A VIRER
public class MyItem implements ClusterItem {
    private final LatLng mPosition;

    public MyItem(double lat, double lng) {
        mPosition = new LatLng(lat, lng);
    }

    @Override
    public LatLng getPosition() {
        return mPosition;
    }
}
