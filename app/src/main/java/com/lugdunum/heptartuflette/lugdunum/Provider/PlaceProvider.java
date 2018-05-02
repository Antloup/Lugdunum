package com.lugdunum.heptartuflette.lugdunum.Provider;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;

import com.google.android.gms.maps.model.LatLng;
import com.lugdunum.heptartuflette.lugdunum.Model.OldPhoto;
import com.lugdunum.heptartuflette.lugdunum.Model.Place;
import com.lugdunum.heptartuflette.lugdunum.Model.RecentPhoto;
import com.lugdunum.heptartuflette.lugdunum.Utils.JsonUtils;
import com.lugdunum.heptartuflette.lugdunum.Utils.RequestUtils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.Vector;
import java.util.concurrent.ExecutionException;

public class PlaceProvider {

    private String request = "Lugdunum/places/";
    private MutableLiveData<Vector<Place>> places;

    public PlaceProvider() {
        this.places = new MutableLiveData<Vector<Place>>() {};
        this.places.setValue(new Vector<Place>());
    }

    public void FetchData(int id) {
        request += id;

        // Connecting / get Json
        JsonToModel();

        // Mock provider for testing purposes
//        MockPlaces();

    }

    public void FetchData() {
        // Reset the vector
        places.setValue(new Vector<Place>());

        // Connecting / get Json
        JsonToModel();

        // Mock provider for testing purposes
//        MockPlaces();

    }

    public LiveData<Vector<Place>> getPlaces() {
        return places;
    }

    public void MockPlaces(){
        Vector<Place> vec = places.getValue();
        Place place1 = new Place(1,new LatLng(45.78218,4.86912),"Description1", new Vector<OldPhoto>(),new Vector<RecentPhoto>());
        Place place2 = new Place(2,new LatLng(45.78389,4.87412),"Description2", new Vector<OldPhoto>(),new Vector<RecentPhoto>());
        Place place3 = new Place(3,new LatLng(45.78538,4.88642),"Description3", new Vector<OldPhoto>(),new Vector<RecentPhoto>());
        this.places.getValue().add(place1);
        this.places.getValue().add(place2);
        this.places.getValue().add(place3);
        this.places.setValue(vec);
    }

    private void JsonToModel(){
        Vector<Place> vec = places.getValue();
        try {
//            JSONArray json = new JsonUtils()
//                    .execute(new URL(JsonUtils.protocol, JsonUtils.host, JsonUtils.port, request))
//                    .get();
            JSONArray json = new JsonUtils()
                    .execute(new RequestUtils(request))
                    .get();
            if(json != null){
                for (int i = 0; i < json.length(); i++) {
                    JSONObject obj = json.getJSONObject(i);
                    int id = obj.getInt("id");
                    double lat = obj.getDouble("latitude");
                    double lng = obj.getDouble("longitude");
                    String description = obj.getString("description");
                    Place place = new Place(id, new LatLng(lat, lng),description, new Vector<OldPhoto>(), new Vector<RecentPhoto>());
                    vec.add(place);
                }
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        this.places.setValue(vec);
    }


}
