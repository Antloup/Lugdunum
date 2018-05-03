package com.lugdunum.heptartuflette.lugdunum.Activity;

import android.Manifest;
import android.app.ActionBar;
import android.content.ContentResolver;
import android.arch.lifecycle.Observer;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Layout;
import android.util.AttributeSet;
import android.util.Log;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.GridLayout;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CenterCrop;
import com.bumptech.glide.request.RequestOptions;
import com.lugdunum.heptartuflette.lugdunum.Model.OldPhoto;
import com.lugdunum.heptartuflette.lugdunum.Model.Place;
import com.lugdunum.heptartuflette.lugdunum.Model.RecentPhoto;
import com.lugdunum.heptartuflette.lugdunum.Provider.OldPhotoProvider;
import com.lugdunum.heptartuflette.lugdunum.Provider.PlaceProvider;
import com.lugdunum.heptartuflette.lugdunum.Provider.RecentPhotoProvider;
import com.lugdunum.heptartuflette.lugdunum.R;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Vector;

import static android.os.Environment.getExternalStoragePublicDirectory;

public class ShowOldPhoto extends AppCompatActivity {
    static final int REQUEST_IMAGE_CAPTURE = 1;
    public static final String PHOTO_FILE ="photoFile";
    private int id;
    private OldPhotoProvider oldPhotoProvider;
    private RecentPhotoProvider recentPhotoProvider;
    private PlaceProvider placeProvider;
    private Bitmap oldPhotoBitmap;
    private Uri userPicUri;
    private File userPicFile;
    private Bitmap recentPhotoBitmap;
    private GridLayout layout;
    private GridLayout gridPhotos;
    private Place place;
    private OldPhoto oldPhoto;
    private boolean init = false;
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
        oldPhotoProvider = new OldPhotoProvider();
        oldPhotoProvider.FetchData(id);
        recentPhotoProvider = new RecentPhotoProvider();
        recentPhotoProvider.FetchData(id);
        placeProvider = new PlaceProvider();
        placeProvider.FetchData(id);
        place = placeProvider.getPlaces().getValue().get(0);
        oldPhoto = oldPhotoProvider.getOldPhotos().getValue().get(0);

        //Set OldPhoto Picture
        AppBarLayout appBarLayout = (AppBarLayout) findViewById(R.id.app_bar);
        oldPhotoBitmap = oldPhoto.getImage();
        Drawable drawable = new BitmapDrawable(getResources(), oldPhotoBitmap);
        appBarLayout.setBackground(drawable);
        setTitle(oldPhoto.getName());

        //Set RecentPhoto Picture
        layout = (GridLayout)findViewById(R.id.Grid);
        gridPhotos = (GridLayout)findViewById(R.id.GridPhotos);

    }

    @Override
    public void onResume(){
        super.onResume();
        gridPhotos.removeAllViews();
        recentPhotoProvider = new RecentPhotoProvider();
        recentPhotoProvider.FetchData(id);
        refreshGrid();
    }

    private void refreshGrid(){

        int total = recentPhotoProvider.getRecentPhoto().getValue().size();
        int column = 2;
//        int row = (((total / column)+1)*2)+3;
        int r = 0;
        int c = 0;
        layout.setColumnCount(column);
        gridPhotos.setColumnCount(column);
//        layout.setColumnCount(column);
//        layout.setRowCount(row);

        //Set text
        TextView textView = (TextView) findViewById(R.id.TextTitle);
        textView.setText(oldPhoto.getName());
        textView.setPadding(20,20,20,20);


        textView = (TextView) findViewById(R.id.TextDate);
        textView.setText(oldPhoto.getDate());
        textView.setPadding(20,20,20,20);

        textView = (TextView) findViewById(R.id.TextDescription);
        textView.setText(oldPhoto.getDescription());
        textView.setPadding(20,20,20,20);

        textView = (TextView) findViewById(R.id.TextLieu);
        textView.setText(place.getDescription());
        textView.setPadding(20,20,20,20);


        //Set image
        ImageView image = null;
        for (int i = 0; i < total; i++, c++) {
            recentPhoto = recentPhotoProvider.getRecentPhoto().getValue().get(i);
            final Bitmap bitmap = recentPhoto.getImage();

            if (c == column) {
                c = 0;
                r+= 2;
            }
            image = new ImageView(this);
            image.setId(recentPhoto.getId());
            RequestOptions opt = new RequestOptions();
            opt.centerCrop().override(500,400);
            Glide.with(this).load(bitmap).apply(opt).into(image);
            image.setPadding(20,20,20,20);

            GridLayout.Spec rowSpan = GridLayout.spec(r, 1);
            GridLayout.Spec colSpan = GridLayout.spec(c, 1);
            GridLayout.LayoutParams gridParam = new GridLayout.LayoutParams(
                    rowSpan, colSpan
            );

            gridParam.setGravity(Gravity.CENTER_HORIZONTAL);
            gridParam.columnSpec = GridLayout.spec(c, 1f);
            gridPhotos.addView(image,gridParam);

            image.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ImageView im = (ImageView) v;
                    String recentName = "recent"+recentPhoto.getId();
                    String oldName = "old"+oldPhoto.getId();

                    writeBitmap(bitmap,recentName);
                    writeBitmap(oldPhotoBitmap,oldName);
                    compareOldPhoto(recentName,oldName, im.getId());
                }
            });

            //Rating Bars
            rowSpan = GridLayout.spec(r+1, 1);
            gridParam = new GridLayout.LayoutParams(
                    rowSpan, colSpan
            );
            gridParam.setGravity(Gravity.CENTER_HORIZONTAL);
            RatingBar ratingBar = new RatingBar(this, null, R.attr.ratingBarStyleSmall);
            ratingBar.setId(recentPhoto.getId());
            ratingBar.setNumStars(5);
            ratingBar.setRating((float) recentPhoto.getScore());
            gridPhotos.addView(ratingBar,gridParam);

        }
    }

    private void compareOldPhoto(String recentPhotoName, String oldPhotoName, int id){
        Intent myIntent = new Intent(this, CompareOldPhoto.class);
        myIntent.putExtra("oldPhotoName",oldPhotoName);
        myIntent.putExtra("recentPhotoName",recentPhotoName);
        DateFormat df = DateFormat.getDateInstance();
        myIntent.putExtra("recentPhotoDate",  df.format(recentPhoto.getDate()));
        myIntent.putExtra("id",id);
        startActivity(myIntent);
    }


    private void writeBitmap (Bitmap bmp, String fileName) {
        try {
            //Write file
            FileOutputStream stream = this.openFileOutput(fileName, Context.MODE_PRIVATE);
            bmp.compress(Bitmap.CompressFormat.JPEG, 80, stream);

            //Cleanup
            stream.close();
//            bmp.recycle();

            //Pop intent
//            Intent in1 = new Intent(this, Activity2.class);
//            in1.putExtra("image", fileName);
//            startActivity(in1);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           @NonNull String permissions[], @NonNull int[] grantResults) {
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
        userPicFile = null;
        try {
            userPicFile = createImageFile();
        } catch (Exception ex) {
            Log.e("ShowOldPhoto","Error occurred while creating a recent photo File!");
        }
        if (userPicFile != null && takePictureIntent.resolveActivity(getPackageManager()) != null) {
            userPicUri = FileProvider.getUriForFile(this,
                    "com.lugdunum.heptartuflette.lugdunum.fileprovider",
                    userPicFile);
            takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, userPicUri);
            startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
        }
    }
    private File createImageFile() throws IOException, NullPointerException {
        // Create an image file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "JPEG_" + timeStamp + "_";
        File storageDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES);//getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES);
        return File.createTempFile(
                imageFileName,  /* prefix */
                ".jpg",         /* suffix */
                storageDir      /* directory */
        );
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
            Bitmap imageBitmap;
            String fileName = "userImg";
            String oldPhotoName = "old";
            getContentResolver().notifyChange(userPicUri, null);
            ContentResolver cr = getContentResolver();
            try {
                imageBitmap = android.provider.MediaStore.Images.Media.getBitmap(cr, userPicUri);
                writeBitmap(imageBitmap,fileName);
            } catch (Exception e){
                e.printStackTrace();
            }

            writeBitmap(oldPhotoBitmap,oldPhotoName);
            Intent myIntent = new Intent(this, TakePhoto.class);

            //Converting bitmap to byteArray
//            ByteArrayOutputStream _bs = new ByteArrayOutputStream();
//            imageBitmap.compress(Bitmap.CompressFormat.PNG, 100, _bs);
            myIntent.putExtra("filename", userPicFile.getAbsolutePath());
            myIntent.putExtra("imageName", fileName);
            myIntent.putExtra("oldPhotoName",oldPhotoName);
            myIntent.putExtra("idPlace",place.getId());
            startActivity(myIntent);
        }
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
