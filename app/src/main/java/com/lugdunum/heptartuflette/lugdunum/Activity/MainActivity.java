package com.lugdunum.heptartuflette.lugdunum.Activity;

import android.Manifest;
import android.arch.lifecycle.Observer;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.drawable.BitmapDrawable;
import android.location.Location;
import android.net.Uri;
import android.os.Bundle;
import android.os.Debug;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.maps.android.clustering.Cluster;
import com.lugdunum.heptartuflette.lugdunum.Model.Place;
import com.lugdunum.heptartuflette.lugdunum.Provider.PlaceProvider;
import com.google.maps.android.clustering.ClusterItem;
import com.google.maps.android.clustering.ClusterManager;
import com.lugdunum.heptartuflette.lugdunum.R;
import com.lugdunum.heptartuflette.lugdunum.Utils.Map.CustomClusterRenderer;
import com.lugdunum.heptartuflette.lugdunum.Utils.Map.ClusterItemPic;


import java.util.Vector;

public class MainActivity extends AppCompatActivity implements OnMapReadyCallback {

    private final int MY_PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION = 42;
    private static int RESULT_LOAD_IMAGE = 1;
//    private FusedLocationProviderClient mFusedLocationClient;
    private GoogleMap mMap;
    private Vector<Marker> markers;
    private PlaceProvider placeProvider;
    // Declare a variable for the cluster manager.
    private ClusterManager<ClusterItemPic> mClusterManager;


    private void setUpClusterer() {
        // Initialize the manager with the context and the map.
        // (Activity extends context, so we can pass 'this' in the constructor.)
        mClusterManager = new ClusterManager<>(this, mMap);
        // Set the renderer
        mClusterManager.setRenderer(new CustomClusterRenderer(this, mMap, mClusterManager));
        // Point the map's listeners at the listeners implemented by the cluster
        // manager.
        mMap.setOnCameraIdleListener(mClusterManager);
        mMap.setOnMarkerClickListener(mClusterManager);

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        placeProvider = new PlaceProvider();
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "TODO : Load activity to load a new image", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
//                Intent i = new Intent(
//                        Intent.ACTION_PICK,
//                        android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
//
//                startActivityForResult(i, RESULT_LOAD_IMAGE);
                addOldPhoto();
            }
        });
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        markers = new Vector<Marker>();
    }


    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        //Cluster
        setUpClusterer();

        // By observing the liveData, adding a new Place in the provider should draw the new marker
        placeProvider.getPlaces().observe(this, new Observer<Vector<Place>>() {
            @Override
            public void onChanged(@Nullable Vector<Place> places) {
                drawMarkers(places);
            }
        });

        // Add a marker in La Doua and move the camera
        LatLng laDoua = new LatLng(45.78216,4.87262);

        // Move the camera to the campus
        mMap.moveCamera(CameraUpdateFactory.newLatLng(laDoua));
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED) {
            mMap.setMyLocationEnabled(true);
        } else {
            if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                    Manifest.permission.ACCESS_FINE_LOCATION)) {

                // Show an explanation to the user *asynchronously* -- don't block
                // this thread waiting for the user's response! After the user
                // sees the explanation, try again to request the permission.


            } else {

                // No explanation needed; request the permission
                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                        MY_PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION);

            }

        }
    }


    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {
        switch (requestCode) {
            case MY_PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    // permission granted !
                    if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                            == PackageManager.PERMISSION_GRANTED) {
                        mMap.setMyLocationEnabled(true);
//                        mMap.moveCamera(CameraUpdateFactory.newLatLng());
                    }

                } else {
                    // permission denied
                    AlertDialog.Builder builder = new AlertDialog.Builder(this);
                    builder.setMessage(R.string.permission_message).setTitle(R.string.permission_message_title);
                    builder.setPositiveButton(R.string.ok, new DialogInterface.OnClickListener(){
                        @Override
                        public void onClick(DialogInterface dialog, int id) {
                            dialog.dismiss();
                        }
                    });

                    AlertDialog dialog = builder.create();
                    dialog.show();
                }
                return;
            }

            // other 'case' lines to check for other
            // permissions this app might request.
        }
    }

    private void drawMarkers(Vector<Place> places) {
        if(places != null){
            mClusterManager.clearItems();
            for(Place p : places){
                mClusterManager.addItem(new ClusterItemPic(p.getLocation().latitude,p.getLocation().longitude));
            }
        }
    }

    private void addOldPhoto() {
        Intent myIntent = new Intent(this, ShowOldPhoto.class);
        startActivity(myIntent);
    }

}
