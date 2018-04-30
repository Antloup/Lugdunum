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
    private String request = "/photoList/";
    private MutableLiveData<Vector<OldPhoto>> oldPhotos;

    public OldPhotoProvider(int id) {
        request += id;

        this.oldPhotos = new MutableLiveData<Vector<OldPhoto>>() {};
        this.oldPhotos.setValue(new Vector<OldPhoto>());

        // Connecting / get Json
//        JsonToModel();

        // Mock provider for testing purposes
        MockData();

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

    public void MockData(){

        Vector<OldPhoto> vec = oldPhotos.getValue();
        String image = "R0lGODlhIAAgAOYAAPLwAAAAABISEv//APb0APHvAPTyAPj2APn3AP37AP/9AP/+APPxAPv5APr4AAkJAPz6AP78ANXTAPHwAPf1AO/tAPDuAAICAFNSAAQEAPr5ALu6AOvpAJeWAIiHAHh3AOHgAPj3ALy6ANbUABoaEqqpAAwMABYWAM3MAHx7Eh4eAMvJALq5APbzADs7EmNiAE1NAC0sAFFRALu5ABoaADc3ElxbAHx7AIKBAH18AH59AIOCAA4OAJ6cAP36ANzbAEdGAPb1ADg3AM7MAAEBAPn4AN3cAFlYEubkAAoKAGloAN7cADc3ABUUACUlAFVUAMjGALSyAGZlAKSjAAsLANXUAGJhAEpJABkZEsrIACsqAJ2cAExLACEhAHNyANrYAAgIANHQAGhnAMPBAA0MAI+NAPn2ABwcANDPABAQAF1cAJGQAD8+AHt6EkNCAJ6dAAUFAM3LAHFxEqqoAPXzAPj1AF9eAKWkACopADo5AHBvEiwrAMTDADMyAFtaEpCPACH5BAAAAAAALAAAAAAgACAAAAf/gAIBg4QBND0hCwQAjI2OBj4DYVeEAoKFATBGAwaOnp8NDmtJAZaYHwgOn6ueBgMbXaaEHgN0rLeOAx6yAVIKnbi3DANzabJCDIu4DQMQn8NbcKWCD1AJwQgiOlEHjgwLf0SDpi8D2CsPARciDYyuZYWWYHGquAsdhDnmBBA3mJZcrgU7MEJFgCZZEBAoogTTtDcLggEoYOZLCQkhCCBQ47AUCQndJAKoE+HAgSB2OpZyAQCYyAIIJmBQWcqPwJcNOAChWUqOApETHSBhw7NUCnMiKfzQUrRUG6QSCYCI0VSAnp9ANSxxUlTAEWdAC0QYcoKngBoWXEossGAGFZoCibCgQQC00YA7F1Ra6hCxLqMBOPQKcNPOb8sEYjpaysCnsF8CB2Q4NPWkr+EDFZj8E5SBhYIChgE4ALEn3iU8HEL6LZCgyplKlwJg0NAidAEFY3iMix3AiwIKoQEMmJKO1yArFm7WJTDAxjSHeVAMUCaRQYQJH0w8d0hmR4UBCBjcIqAgwYY+lQIBADs=";
        byte[] decodedString = Base64.decode(image, Base64.DEFAULT);
        Bitmap decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
        OldPhoto photo = new OldPhoto(1,
                "Photo's Name",
                "png",
                decodedByte,
                "In the 70's",
                "This is the description",
                "http://www.google.com");
        this.oldPhotos.getValue().add(photo);
        this.oldPhotos.setValue(vec);
    }

    public Vector<OldPhoto> getOldPhotos() {
        return oldPhotos.getValue();
    }
}
