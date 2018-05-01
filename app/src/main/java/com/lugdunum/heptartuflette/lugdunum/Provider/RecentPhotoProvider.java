package com.lugdunum.heptartuflette.lugdunum.Provider;

import android.arch.lifecycle.MutableLiveData;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;

import com.lugdunum.heptartuflette.lugdunum.Model.RecentPhoto;
import com.lugdunum.heptartuflette.lugdunum.Utils.JsonUtils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.net.MalformedURLException;
import java.net.URL;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.Vector;
import java.util.concurrent.ExecutionException;

public class RecentPhotoProvider {
    private String request = "Lugdunum/recentPhotoList/";
    private MutableLiveData<Vector<RecentPhoto>> recentPhotos;

    public RecentPhotoProvider(int id) {
        request += id;

        this.recentPhotos = new MutableLiveData<Vector<RecentPhoto>>() {};
        this.recentPhotos.setValue(new Vector<RecentPhoto>());

        // Connecting / get Json
        JsonToModel();

        // Mock provider for testing purposes
//        MockData();

    }

    public void JsonToModel(){
        Vector<RecentPhoto> vec = recentPhotos.getValue();
        try {
            JSONArray json = new JsonUtils()
                    .execute(new URL(JsonUtils.protocol,JsonUtils.host,JsonUtils.port,request))
                    .get();
            RecentPhoto photo = null;
            for (int i = 0 ; i < json.length(); i++) {
                JSONObject obj = json.getJSONObject(i);
                int id = obj.getInt("id");
                String name = obj.getString("name");
                String dateString = obj.getString("date");

                DateFormat dateFormat= new SimpleDateFormat("yyyy-MM-dd", Locale.FRENCH);
                dateString = dateString.substring(0,dateString.indexOf('T'));
                Date date = dateFormat.parse(dateString);

                Double note = obj.getDouble("note");
                int noteNumber = obj.getInt("noteNumber");
                String format = obj.getString("format");
                String image = obj.getString("file");
                byte[] decodedString = Base64.decode(image, Base64.DEFAULT);
                Bitmap decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
                photo = new RecentPhoto(id,name,format,decodedByte,date,note,noteNumber);
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
        } catch (ParseException e) {
            e.printStackTrace();
        }

        recentPhotos.setValue(vec);
    }

    public void MockData(){

        Vector<RecentPhoto> vec = recentPhotos.getValue();
        String image = "R0lGODlhIAAgAOYAAPLwAAAAABISEv//APb0APHvAPTyAPj2APn3AP37AP/9AP/+APPxAPv5APr4AAkJAPz6AP78ANXTAPHwAPf1AO/tAPDuAAICAFNSAAQEAPr5ALu6AOvpAJeWAIiHAHh3AOHgAPj3ALy6ANbUABoaEqqpAAwMABYWAM3MAHx7Eh4eAMvJALq5APbzADs7EmNiAE1NAC0sAFFRALu5ABoaADc3ElxbAHx7AIKBAH18AH59AIOCAA4OAJ6cAP36ANzbAEdGAPb1ADg3AM7MAAEBAPn4AN3cAFlYEubkAAoKAGloAN7cADc3ABUUACUlAFVUAMjGALSyAGZlAKSjAAsLANXUAGJhAEpJABkZEsrIACsqAJ2cAExLACEhAHNyANrYAAgIANHQAGhnAMPBAA0MAI+NAPn2ABwcANDPABAQAF1cAJGQAD8+AHt6EkNCAJ6dAAUFAM3LAHFxEqqoAPXzAPj1AF9eAKWkACopADo5AHBvEiwrAMTDADMyAFtaEpCPACH5BAAAAAAALAAAAAAgACAAAAf/gAIBg4QBND0hCwQAjI2OBj4DYVeEAoKFATBGAwaOnp8NDmtJAZaYHwgOn6ueBgMbXaaEHgN0rLeOAx6yAVIKnbi3DANzabJCDIu4DQMQn8NbcKWCD1AJwQgiOlEHjgwLf0SDpi8D2CsPARciDYyuZYWWYHGquAsdhDnmBBA3mJZcrgU7MEJFgCZZEBAoogTTtDcLggEoYOZLCQkhCCBQ47AUCQndJAKoE+HAgSB2OpZyAQCYyAIIJmBQWcqPwJcNOAChWUqOApETHSBhw7NUCnMiKfzQUrRUG6QSCYCI0VSAnp9ANSxxUlTAEWdAC0QYcoKngBoWXEossGAGFZoCibCgQQC00YA7F1Ra6hCxLqMBOPQKcNPOb8sEYjpaysCnsF8CB2Q4NPWkr+EDFZj8E5SBhYIChgE4ALEn3iU8HEL6LZCgyplKlwJg0NAidAEFY3iMix3AiwIKoQEMmJKO1yArFm7WJTDAxjSHeVAMUCaRQYQJH0w8d0hmR4UBCBjcIqAgwYY+lQIBADs=";
        byte[] decodedString = Base64.decode(image, Base64.DEFAULT);
        Bitmap decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
        Date date = new Date();
        RecentPhoto photo1 = new RecentPhoto(1,
                "Photo's Name",
                "png",
                decodedByte,
                date,
                2.8,
                8);
        RecentPhoto photo2 = new RecentPhoto(2,
                "Photo's Name",
                "png",
                decodedByte,
                date,
                4.2,
                8);
        RecentPhoto photo3 = new RecentPhoto(3,
                "Photo's Name",
                "png",
                decodedByte,
                date,
                1.5,
                8);
        RecentPhoto photo4 = new RecentPhoto(4,
                "Photo's Name",
                "png",
                decodedByte,
                date,
                1.5,
                8);
        RecentPhoto photo5 = new RecentPhoto(5,
                "Photo's Name",
                "png",
                decodedByte,
                date,
                1.5,
                8);
        RecentPhoto photo6 = new RecentPhoto(6,
                "Photo's Name",
                "png",
                decodedByte,
                date,
                1.5,
                8);
        RecentPhoto photo7 = new RecentPhoto(7,
                "Photo's Name",
                "png",
                decodedByte,
                date,
                1.5,
                8);
        this.recentPhotos.getValue().add(photo1);
        this.recentPhotos.getValue().add(photo2);
        this.recentPhotos.getValue().add(photo3);
        this.recentPhotos.getValue().add(photo4);
        this.recentPhotos.getValue().add(photo5);
        this.recentPhotos.getValue().add(photo6);
        this.recentPhotos.getValue().add(photo7);
        this.recentPhotos.setValue(vec);
    }

    public Vector<RecentPhoto> getRecentPhoto() {
        return recentPhotos.getValue();
    }

}
