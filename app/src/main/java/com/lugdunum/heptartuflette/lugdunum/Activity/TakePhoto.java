package com.lugdunum.heptartuflette.lugdunum.Activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.lugdunum.heptartuflette.lugdunum.Model.RecentPhoto;
import com.lugdunum.heptartuflette.lugdunum.Provider.RecentPhotoProvider;
import com.lugdunum.heptartuflette.lugdunum.R;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class TakePhoto extends AppCompatActivity {

    private TextView mTextMessage;
    private Bitmap oldPhotoBitmap;
    String mCurrentPhotoPath;
    static final int REQUEST_TAKE_PHOTO = 1;



    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_home:
                    mTextMessage.setText(R.string.title_home);
                    return true;
                case R.id.navigation_dashboard:
                    mTextMessage.setText(R.string.title_dashboard);
                    return true;
                case R.id.navigation_notifications:
                    mTextMessage.setText(R.string.title_notifications);
                    return true;
            }
            return false;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_take_photo);
        ImageView iv = (ImageView)findViewById(R.id.imageViewCamera);

        /* Fill up view */

        // Put camera picture
//        if(getIntent().hasExtra("imageByteArray")) {
//            Bitmap bitmap = BitmapFactory.decodeByteArray(
//                    getIntent().getByteArrayExtra("imageByteArray"),0,
//                    getIntent().getByteArrayExtra("imageByteArray").length
//            );
//            iv.setImageBitmap(bitmap);
//
//        }
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        String filename = getIntent().getStringExtra("imageName");
        try {
            FileInputStream is = this.openFileInput(filename);
            iv.setImageBitmap(BitmapFactory.decodeStream(is));
            is.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        filename = getIntent().getStringExtra("oldPhotoName");
        try {
            FileInputStream is = this.openFileInput(filename);
            oldPhotoBitmap = BitmapFactory.decodeStream(is);
            is.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        // Put OldPhoto picture
//        oldPhotoBitmap = (Bitmap) getIntent().getParcelableExtra("oldPhotoBitmap");
        ImageView oldImageView = (ImageView) findViewById(R.id.imageViewOld);
        oldImageView.setImageBitmap(oldPhotoBitmap);

        Button button= (Button) findViewById(R.id.save);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dispatchTakePictureIntent();
            }
        });

        button= (Button) findViewById(R.id.upload);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RecentPhotoProvider recentPhotoProvider = new RecentPhotoProvider();
                //TODO: Fill Photo
                recentPhotoProvider.postPhoto(new RecentPhoto("NAME","FORMAT",null,new Date()));
            }
        });

    }

    private void dispatchTakePictureIntent() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        // Ensure that there's a camera activity to handle the intent
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            // Create the File where the photo should go
            File photoFile = null;
            try {
                photoFile = createImageFile();
            } catch (IOException ex) {
                // Error occurred while creating the File
            }
            // Continue only if the File was successfully created
            if (photoFile != null) {
                Uri photoURI = FileProvider.getUriForFile(this,
                        "com.lugdunum.heptartuflette.lugdunum.fileprovider",
                        photoFile);
                takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
                startActivityForResult(takePictureIntent, REQUEST_TAKE_PHOTO);
            }
        }
    }


    private File createImageFile() throws IOException {
        // Create an image file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "JPEG_" + timeStamp + "_";
        File storageDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        File image = File.createTempFile(
                imageFileName,  /* prefix */
                ".jpg",         /* suffix */
                storageDir      /* directory */
        );
        return image;
    }

}
