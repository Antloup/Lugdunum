package com.lugdunum.heptartuflette.lugdunum.Utils.Map;

import android.content.Context;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.maps.android.clustering.Cluster;
import com.google.maps.android.clustering.ClusterManager;
import com.google.maps.android.clustering.view.DefaultClusterRenderer;
import com.lugdunum.heptartuflette.lugdunum.R;

public class CustomClusterRenderer extends DefaultClusterRenderer<ClusterItemPic> {

    private Context mContext;

    public CustomClusterRenderer(Context context, GoogleMap map,
                                 ClusterManager<ClusterItemPic> clusterManager) {
        super(context, map, clusterManager);

        mContext = context;
    }

    @Override protected void onBeforeClusterItemRendered(ClusterItemPic item,
                                                         MarkerOptions markerOptions) {
        markerOptions.icon(BitmapDescriptorFactory.fromResource(R.drawable.ic_pic_perso))
        ;
    }

    @Override
    protected boolean shouldRenderAsCluster(Cluster<ClusterItemPic> cluster) {
        //start clustering if at least 2 items overlap
        return cluster.getSize() > 1;
    }
}