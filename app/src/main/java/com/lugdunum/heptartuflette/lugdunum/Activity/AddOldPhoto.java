package com.lugdunum.heptartuflette.lugdunum.Activity;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.lugdunum.heptartuflette.lugdunum.Model.OldPhoto;
import com.lugdunum.heptartuflette.lugdunum.Model.Place;
import com.lugdunum.heptartuflette.lugdunum.Provider.OldPhotoProvider;
import com.lugdunum.heptartuflette.lugdunum.Provider.PlaceProvider;
import com.lugdunum.heptartuflette.lugdunum.R;

public class AddOldPhoto extends AppCompatActivity implements OnMapReadyCallback {
    private GoogleMap mMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_old_photo);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ImageView img = (ImageView) findViewById(R.id.oldImg);
        Glide.with(this).load(getIntent().getStringExtra("picturePath")).into(img);
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        Button contributeButton = (Button) findViewById(R.id.contributeButton);
        contributeButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                addPhoto();
            }
        });
        // TODO: pop the current activity and don't reload main activity
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    private void addPhoto() {
        //Upload Place
        PlaceProvider placeProvider = new PlaceProvider();
        //TODO : fill place
        Place place = new Place(new LatLng(10,10),"DESC",null,null);
        placeProvider.postPlace(place);

        //Upload OldPhoto
        OldPhotoProvider oldPhotoProvider = new OldPhotoProvider();
        ImageView imageView = (ImageView) findViewById(R.id.oldImg);
        Bitmap bitmap = ((BitmapDrawable)imageView.getDrawable()).getBitmap();
        //TODO : fill oldPhoto
        OldPhoto oldPhoto = new OldPhoto("NAME","FORMAT",bitmap,"DATE","DESC","INFOLINK");
        oldPhotoProvider.postPhoto(oldPhoto);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        mMap.setOnMapClickListener(new GoogleMap.OnMapClickListener() {

            @Override
            public void onMapClick(LatLng point) {
                mMap.clear();
                MarkerOptions marker = new MarkerOptions().position(
                        new LatLng(point.latitude, point.longitude)).title("New Marker");
                mMap.addMarker(marker);
            }
        });

        // We center the map on the campus for now
        LatLng laDoua = new LatLng(45.78216,4.87262);

        // Move the camera to the campus and set appropriate zoom
        mMap.moveCamera(CameraUpdateFactory.zoomTo(15));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(laDoua));
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                this.finish();
                return true;

            default:
                // If we got here, the user's action was not recognized.
                // Invoke the superclass to handle it.
                return super.onOptionsItemSelected(item);

        }
    }
}
