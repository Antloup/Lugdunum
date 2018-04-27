package com.lugdunum.heptartuflette.lugdunum.Provider;

import android.arch.lifecycle.LiveData;
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

public class PlaceProvider {

    private String request = "/places";
    private MutableLiveData<Vector<Place>> places;

    public PlaceProvider() {
        this.places = new MutableLiveData<Vector<Place>>() {};
        this.places.setValue(new Vector<Place>());
        fetchPlaces();
    }

    private void fetchPlaces() {

        // Connecting / get Json
//        JsonToModel();

        // Mock provider for testing purposes
        MockPlaces();

    }

    public LiveData<Vector<Place>> getPlaces() {
        return places;
    }

    public void MockPlaces(){
        Vector<Place> vec = places.getValue();
        Place place1 = new Place(1,new LatLng(45.78218,4.86912), new Vector<OldPhoto>(),new Vector<RecentPhoto>());
        Place place2 = new Place(2,new LatLng(45.78389,4.87412), new Vector<OldPhoto>(),new Vector<RecentPhoto>());
        Place place3 = new Place(3,new LatLng(45.78538,4.88642), new Vector<OldPhoto>(),new Vector<RecentPhoto>());
        this.places.getValue().add(place1);
        this.places.getValue().add(place2);
        this.places.getValue().add(place3);
        this.places.setValue(vec);
    }

    public void JsonToModel(){
        Vector<Place> vec = places.getValue();
        try {
            JSONArray json = new JsonUtils()
                    .execute(new URL(JsonUtils.protocol,JsonUtils.host,request))
                    .get();
            for (int i = 0 ; i < json.length(); i++) {
                JSONObject obj = json.getJSONObject(i);
                double lat = obj.getDouble("latitude");
                double lng = obj.getDouble("longitude");
                int id = obj.getInt("id");
                Place place = new Place(id,new LatLng(lat,lng), new Vector<OldPhoto>(),new Vector<RecentPhoto>());
                vec.add(place);
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
