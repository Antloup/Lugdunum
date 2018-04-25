package com.lugdunum.heptartuflette.lugdunum.Provider;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.util.Log;

import com.google.android.gms.maps.model.LatLng;
import com.lugdunum.heptartuflette.lugdunum.Model.OldPhoto;
import com.lugdunum.heptartuflette.lugdunum.Model.Place;
import com.lugdunum.heptartuflette.lugdunum.Model.RecentPhoto;

import java.util.Vector;

public class PlaceProvider {
    private MutableLiveData<Vector<Place>> places;

    public PlaceProvider() {
        this.places = new MutableLiveData<Vector<Place>>() {};
        this.places.setValue(new Vector<Place>());
        fetchPlaces();
    }

    private void fetchPlaces() {
        // Mock provider for testing purposes
        Place place1 = new Place(1,new LatLng(45.78218,4.86912), new Vector<OldPhoto>(),new Vector<RecentPhoto>());
        Place place2 = new Place(2,new LatLng(45.78389,4.87412), new Vector<OldPhoto>(),new Vector<RecentPhoto>());
        Place place3 = new Place(3,new LatLng(45.78538,4.88642), new Vector<OldPhoto>(),new Vector<RecentPhoto>());
        this.places.getValue().add(place1);
        this.places.getValue().add(place2);
        this.places.getValue().add(place3);
    }

    public LiveData<Vector<Place>> getPlaces() {
        return places;
    }

}
