package com.lugdunum.heptartuflette.lugdunum.Provider;

import android.arch.lifecycle.MutableLiveData;

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
    private String request = "/oldPhotos/";
    private MutableLiveData<OldPhoto> oldPhoto;

    public OldPhotoProvider(int id) {
        request += id;

        //JsonToModel();

    }

    public void JsonToModel(){
        try {
            JSONArray json = new JsonUtils()
                    .execute(new URL(JsonUtils.protocol,JsonUtils.host,request))
                    .get();
            for (int i = 0 ; i < json.length(); i++) {
                JSONObject obj = json.getJSONObject(i);
                int id = obj.getInt("id");
                String name = obj.getString("name");
                String date = obj.getString("date");
                String description = obj.getString("desc");
                String infoLink = obj.getString("link");
                //TODO : complete request
                OldPhoto photo = new OldPhoto(id,name,null,null,date,description,infoLink);
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
    }
}
