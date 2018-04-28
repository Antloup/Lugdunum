package com.lugdunum.heptartuflette.lugdunum.Provider;

import android.arch.lifecycle.MutableLiveData;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;

import com.google.android.gms.maps.model.LatLng;
import com.lugdunum.heptartuflette.lugdunum.Model.OldPhoto;
import com.lugdunum.heptartuflette.lugdunum.Model.Place;
import com.lugdunum.heptartuflette.lugdunum.Model.RecentPhoto;
import com.lugdunum.heptartuflette.lugdunum.Utils.JsonUtils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.Vector;
import java.util.concurrent.ExecutionException;

public class OldPhotoProvider {
    //TODO : set the good request
    private String request = "/images/";
    private MutableLiveData<Vector<OldPhoto>> oldPhotos;

    public OldPhotoProvider(int id) {
        request += id;

        JsonToModel();

    }

    public void JsonToModel(){
        Vector<OldPhoto> vec = oldPhotos.getValue();
        try {
            JSONArray json = new JsonUtils()
                    .execute(new URL(JsonUtils.protocol,JsonUtils.host,JsonUtils.port,request))
                    .get();
            OldPhoto photo = null;
            for (int i = 0 ; i < json.length(); i++) {
                JSONObject obj = json.getJSONObject(i);
                int id = obj.getInt("id");
                String name = obj.getString("name");
                String date = obj.getString("date");
                String description = obj.getString("description");
                String infoLink = obj.getString("infoLink");
                String format = obj.getString("format");
                String image = obj.getString("image");
                byte[] decodedString = Base64.decode(image, Base64.DEFAULT);
                Bitmap decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
                photo = new OldPhoto(id,name,format,decodedByte,date,description,infoLink);
                vec.add(photo);
            }

        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        catch (MalformedURLException e) {
            e.printStackTrace();
        }

        oldPhotos.setValue(vec);
    }
}
