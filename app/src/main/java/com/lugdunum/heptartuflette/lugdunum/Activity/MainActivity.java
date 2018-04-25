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

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnSuccessListener;
import com.lugdunum.heptartuflette.lugdunum.Model.Place;
import com.lugdunum.heptartuflette.lugdunum.Provider.PlaceProvider;
import com.lugdunum.heptartuflette.lugdunum.R;

import java.util.Vector;

public class MainActivity extends AppCompatActivity implements OnMapReadyCallback {

    private final int MY_PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION = 42;
    private static int RESULT_LOAD_IMAGE = 1;
//    private FusedLocationProviderClient mFusedLocationClient;
    private GoogleMap mMap;
    private Vector<Marker> markers;
    private PlaceProvider placeProvider;

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
                Intent i = new Intent(
                        Intent.ACTION_PICK,
                        android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);

                startActivityForResult(i, RESULT_LOAD_IMAGE);
//                addOldPhoto();
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
        // By observing the liveData, adding a new Place in the provider should draw the new marker
        placeProvider.getPlaces().observe(this, new Observer<Vector<Place>>() {
            @Override
            public void onChanged(@Nullable Vector<Place> places) {
                drawMarkers(places);
            }
        });
        // Move the camera to the campus
        LatLng laDoua = new LatLng(45.78216,4.87262);
        mMap.addMarker(new MarkerOptions().position(laDoua).title("Marker in La Doua").icon(BitmapDescriptorFactory.fromResource(R.drawable.ic_pic_perso)));
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
            for(Marker m : markers){
                m.remove();
            }
            for(Place p : places){
                markers.add(mMap.addMarker(new MarkerOptions().position(p.getLocation()).title("Marqueur")));
            }
        }
    }

    private void addOldPhoto() {
        Intent myIntent = new Intent(this, AddOldPhoto.class);
        startActivity(myIntent);
    }

//    private LatLng getUserPosition() {
//
//
//        mFusedLocationClient.getLastLocation()
//                .addOnSuccessListener(this, new OnSuccessListener<Location>() {
//                    @Override
//                    public void onSuccess(Location location) {
//                        // Got last known location. In some rare situations this can be null.
//                        if (location != null) {
//                            // Logic to handle location object
//                        }
//                    }
//                });
//
//
//    }
}
