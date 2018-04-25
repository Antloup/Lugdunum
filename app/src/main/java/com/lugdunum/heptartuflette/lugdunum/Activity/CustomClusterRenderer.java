package com.lugdunum.heptartuflette.lugdunum.Activity;

import android.content.Context;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.maps.android.clustering.ClusterManager;
import com.google.maps.android.clustering.view.DefaultClusterRenderer;
import com.lugdunum.heptartuflette.lugdunum.R;

public class CustomClusterRenderer extends DefaultClusterRenderer<MyItem> {

    private Context mContext;

    public CustomClusterRenderer(Context context, GoogleMap map,
                                 ClusterManager<MyItem> clusterManager) {
        super(context, map, clusterManager);

        mContext = context;
    }

    @Override protected void onBeforeClusterItemRendered(MyItem item,
                                                         MarkerOptions markerOptions) {
        markerOptions.icon(BitmapDescriptorFactory.fromResource(R.drawable.ic_pic_perso));
    }
}