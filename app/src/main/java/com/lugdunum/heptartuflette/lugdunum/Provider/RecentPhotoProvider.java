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
    //TODO : set the good request
    private String request = "/recentPhotos/";
    private MutableLiveData<Vector<RecentPhoto>> recentPhotos;

    public RecentPhotoProvider(int id) {
        request += id;

        JsonToModel();

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

                DateFormat dateFormat= new SimpleDateFormat("MMMM d, yyyy", Locale.FRENCH);
                Date date = dateFormat.parse(dateString);

                Double note = obj.getDouble("note");
                int noteNumber = obj.getInt("noteNumber");
                String format = obj.getString("format");
                String image = obj.getString("image");
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

}
