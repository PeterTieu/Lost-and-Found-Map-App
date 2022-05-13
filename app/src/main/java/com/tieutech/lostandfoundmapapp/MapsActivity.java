package com.tieutech.lostandfoundmapapp;

import androidx.fragment.app.FragmentActivity;

import android.os.Bundle;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.tieutech.lostandfoundmapapp.Data.AdvertDatabaseHelper;
import com.tieutech.lostandfoundmapapp.Model.Advert;
import com.tieutech.lostandfoundmapapp.databinding.ActivityMapsBinding;
import java.util.ArrayList;
import java.util.List;

//ABOUT: Map activity that displays markers of the locations of all Adverts
public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mGoogleMap;
    private ActivityMapsBinding binding;

    //List variables
    List<Advert> advertsList = new ArrayList<>(); //List of all Adverts
    AdvertDatabaseHelper advertDatabaseHelper = new AdvertDatabaseHelper(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMapsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }

    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {

        mGoogleMap = googleMap; //Obtain Google Map

        advertsList = advertDatabaseHelper.fetchAllAdverts(); //Fetch all data from the Advert database and store it in the list of Adverts

        //Loop through each of the Advert objects in the list of Adverts
        for (int i=0; i<advertsList.size(); i++) {

            //Extract variables from the current Advert
            double latitude = advertsList.get(i).getLatitude(); //Latitude of the current Advert
            double longitude = advertsList.get(i).getLongitude(); //Longitude of the current Advert
            String markerLabel = advertsList.get(i).getName(); //Name of the current Advert

            LatLng latLng = new LatLng(latitude, longitude); //Obtain the LatLng variable reflecting the latitude and longitude

            //Add a marker on the Google Map, based on the extracted latitude, longitude, and marker label
            mGoogleMap.addMarker(new MarkerOptions().position(latLng).title(markerLabel).icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED)));
        }

        //Move the camera to the location of the LAST added Advert in the database of Adverts
        LatLng lastAddress = new LatLng(advertsList.get(0).getLatitude(), advertsList.get(advertsList.size()-1).getLongitude()); //LatLng of last added Advert
        mGoogleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(lastAddress, 13)); //"zoom" parameter: Determines the level of zoom, between 1 and 20
    }
}