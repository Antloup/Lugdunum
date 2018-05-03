package com.lugdunum.heptartuflette.lugdunum.Activity;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Environment;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.lugdunum.heptartuflette.lugdunum.Model.RecentPhoto;
import com.lugdunum.heptartuflette.lugdunum.Provider.RecentPhotoProvider;
import com.lugdunum.heptartuflette.lugdunum.R;
import com.lugdunum.heptartuflette.lugdunum.Utils.FileUtils;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Date;

import static android.os.Environment.DIRECTORY_PICTURES;
import static android.os.Environment.getExternalStoragePublicDirectory;

public class TakePhoto extends AppCompatActivity {

    private TextView mTextMessage;
    private Bitmap oldPhotoBitmap;
    private Bitmap recentPhotoBitmap;
    private String mCurrentPhotoPath;
    static final int REQUEST_TAKE_PHOTO = 1;


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

        mCurrentPhotoPath = getIntent().getStringExtra("filename");

        String filename = getIntent().getStringExtra("imageName");
        try {
            FileInputStream is = this.openFileInput(filename);
            recentPhotoBitmap = BitmapFactory.decodeStream(is);
            RequestOptions opt = new RequestOptions();
            opt.fitCenter();
            Glide.with(this).load(recentPhotoBitmap).apply(opt).into(iv);

            is.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        filename = getIntent().getStringExtra("oldPhotoName");
        ImageView oldImageView = (ImageView) findViewById(R.id.imageViewOld);

        try {
            FileInputStream is = this.openFileInput(filename);
            oldPhotoBitmap = BitmapFactory.decodeStream(is);
            RequestOptions opt = new RequestOptions();
            opt.fitCenter();
            Glide.with(this).load(oldPhotoBitmap).apply(opt).into(oldImageView);

            is.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        // Put OldPhoto picture
//        oldPhotoBitmap = (Bitmap) getIntent().getParcelableExtra("oldPhotoBitmap");
//        oldImageView.setImageBitmap(oldPhotoBitmap);

        Button saveButton= (Button) findViewById(R.id.save);
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //TODO move from Android/data/... to Pictures/Lugdunum
                Log.i("Path", mCurrentPhotoPath);
                File temp = new File(mCurrentPhotoPath);
                File dest = Environment.getExternalStoragePublicDirectory(DIRECTORY_PICTURES);
                Log.i("Path", dest.getPath());
                try {
                    FileUtils.copyFile(temp, dest);
                    FileUtils.deleteRecursive(temp);
                }catch(IOException e){
                    Log.e("TakePhoto","Error while moving the temp file !");
                    Log.e("TakePhoto", e.getMessage()+ mCurrentPhotoPath);
                }
                Snackbar snackbar = Snackbar
                        .make(v, "Photo sauvegardée !", Snackbar.LENGTH_LONG);
                snackbar.show();
            }
        });

        Button uploadButton= (Button) findViewById(R.id.upload);
        uploadButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RecentPhotoProvider recentPhotoProvider = new RecentPhotoProvider();
                //TODO: Fill Photo
                int id = getIntent().getIntExtra("idPlace",0);
                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                recentPhotoBitmap.compress(Bitmap.CompressFormat.JPEG,10,baos);
                recentPhotoBitmap = BitmapFactory.decodeByteArray(baos.toByteArray(),0, baos.toByteArray().length);
                recentPhotoProvider.postPhoto(new RecentPhoto("NAME","FORMAT",recentPhotoBitmap,new Date()),id);
            }
        });

    }

}
