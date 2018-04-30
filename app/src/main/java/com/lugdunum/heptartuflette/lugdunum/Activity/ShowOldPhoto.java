package com.lugdunum.heptartuflette.lugdunum.Activity;

import android.Manifest;
import android.app.ActionBar;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Layout;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridLayout;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.lugdunum.heptartuflette.lugdunum.Model.RecentPhoto;
import com.lugdunum.heptartuflette.lugdunum.Provider.OldPhotoProvider;
import com.lugdunum.heptartuflette.lugdunum.Provider.PlaceProvider;
import com.lugdunum.heptartuflette.lugdunum.Provider.RecentPhotoProvider;
import com.lugdunum.heptartuflette.lugdunum.R;

import java.io.ByteArrayOutputStream;

public class ShowOldPhoto extends AppCompatActivity {
    static final int REQUEST_IMAGE_CAPTURE = 1;
    private int id;
    private OldPhotoProvider oldPhotoProvider;
    private RecentPhotoProvider recentPhotoProvider;
    private PlaceProvider placeProvider;
    private Bitmap oldPhotoBitmap;
    private Bitmap recentPhotoBitmap;
    RecentPhoto recentPhoto;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //Get id of the picture
        id = getIntent().getIntExtra("id",0);

        setContentView(R.layout.activity_show_old_photo);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.floatingActionButton);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                takePhoto();
            }
        });

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        //Create provider / fill content
        oldPhotoProvider = new OldPhotoProvider(id);
        recentPhotoProvider = new RecentPhotoProvider(id);
        placeProvider = new PlaceProvider(id);

        //Set OldPhoto Picture
        AppBarLayout appBarLayout = (AppBarLayout) findViewById(R.id.app_bar);
        oldPhotoBitmap = oldPhotoProvider.getOldPhotos().firstElement().getImage();
        Drawable drawable = new BitmapDrawable(getResources(), oldPhotoBitmap);
        appBarLayout.setBackground(drawable);

        //Set text
        TextView textView = (TextView) findViewById(R.id.TextDate);
        textView.setText(oldPhotoProvider.getOldPhotos().firstElement().getDate());
        textView = (TextView) findViewById(R.id.TextDescription);
        textView.setText(oldPhotoProvider.getOldPhotos().firstElement().getDescription());
        textView = (TextView) findViewById(R.id.TextLieu);
        textView.setText(placeProvider.getPlaces().getValue().get(0).getDescription());

        //Set RecentPhoto Picture
        GridLayout layout = (GridLayout)findViewById(R.id.Grid1);
        layout.removeAllViews();

        int total = recentPhotoProvider.getRecentPhoto().size();
        int column = 2;
        int row = ((total / column)+1)*2;
        layout.setColumnCount(column);
        layout.setRowCount(row);
        for (int i = 0, c = 0, r = 0; i < total; i++, c++) {
            recentPhoto = recentPhotoProvider.getRecentPhoto().get(i);
//            if (c == column) {
//                c = 0;
//                r+=2;
//            }
            ImageView image = new ImageView(this);
            image.setImageBitmap(recentPhoto.getImage());
            image.setLayoutParams(new ViewGroup.LayoutParams(100, 100));

            GridLayout.Spec rowSpan = GridLayout.spec(GridLayout.UNDEFINED, 1);
            GridLayout.Spec colSpan = GridLayout.spec(GridLayout.UNDEFINED, 1);
//            if (r == 0 && c == 0) {
//                colSpan = GridLayout.spec(GridLayout.UNDEFINED, 2);
//                rowSpan = GridLayout.spec(GridLayout.UNDEFINED, 2);
//            }
            GridLayout.LayoutParams gridParam = new GridLayout.LayoutParams(
                    rowSpan, colSpan
            );
            layout.addView(image,gridParam);

            image.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    compareOldPhoto(recentPhoto.getImage());
                }
            });

            gridParam = new GridLayout.LayoutParams(
                    rowSpan, colSpan
            );
            RatingBar ratingBar = new RatingBar(this, null, R.attr.ratingBarStyleSmall);
            ratingBar.setRating((float) recentPhoto.getScore());
            layout.addView(ratingBar,gridParam);

        }

    }

    private void compareOldPhoto(Bitmap recentPhoto){
        Intent myIntent = new Intent(this, CompareOldPhoto.class);
        myIntent.putExtra("oldPhotoBitmap",oldPhotoBitmap);
        myIntent.putExtra("recentPhotoBitmap",recentPhoto);
        startActivity(myIntent);
    }


    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {
        switch (requestCode) {
            case REQUEST_IMAGE_CAPTURE: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    // permission granted !
                    if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                            == PackageManager.PERMISSION_GRANTED) {
                        dispatchTakePictureIntent();
                    }

                } else {
                    // permission denied
                    AlertDialog.Builder builder = new AlertDialog.Builder(this);
                    builder.setMessage(R.string.camera_permission_message).setTitle(R.string.permission_message_title);
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
        }
    }

    private void takePhoto() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA)
                == PackageManager.PERMISSION_GRANTED) {
            // Already get permission
            dispatchTakePictureIntent();
        } else {
            // No permission yet, asking through onRequestPermissionsResult
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.CAMERA},
                    REQUEST_IMAGE_CAPTURE);
        }
    }

    private void dispatchTakePictureIntent() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
            Bundle extras = data.getExtras();
            Bitmap imageBitmap = (Bitmap) extras.get("data");
            Intent myIntent = new Intent(this, TakePhoto.class);

            //Converting bitmap to byteArray
            ByteArrayOutputStream _bs = new ByteArrayOutputStream();
            imageBitmap.compress(Bitmap.CompressFormat.PNG, 100, _bs);

            myIntent.putExtra("imageByteArray", _bs.toByteArray());
            myIntent.putExtra("oldPhotoBitmap",oldPhotoBitmap);
            startActivity(myIntent);
        }
    }

}
