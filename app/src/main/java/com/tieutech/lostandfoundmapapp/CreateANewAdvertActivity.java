package com.tieutech.lostandfoundmapapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import com.tieutech.lostandfoundmapapp.Data.AdvertDatabaseHelper;
import com.tieutech.lostandfoundmapapp.Model.Advert;
import com.tieutech.lostandfoundmapapp.Util.Util;
import java.io.IOException;
import java.util.List;
import java.util.Locale;

//ABOUT: Activity to create a new Advert
public class CreateANewAdvertActivity extends AppCompatActivity {

    //View variables
    RadioGroup lostAndFoundRadioGroup;
    RadioButton radioButton;
    int radioButtonID;
    EditText nameEdiText;
    EditText phoneEditText;
    EditText descriptionEditText;
    EditText dateEditText;
    EditText locationEditText;

    //Data variables
    String itemState;
    String name;
    String phone;
    String description;
    String date;
    String location;

    //Database variables
    AdvertDatabaseHelper advertDatabaseHelper;
    long rowID;

    //Location variables
    double longitude = 0;
    double latitude = 0;

    //Variable to listen for location changes and fetch location data
    private final LocationListener locationListener = new LocationListener() {

        //Define listener for location changes
        public void onLocationChanged(Location location) {

            //Obtain longitude and latitude values
            longitude = location.getLongitude();
            latitude = location.getLatitude();

            //-------------- Obtain the ADDRESS String based on the longitude and latitude values --------------
            Geocoder geocoder = new Geocoder(CreateANewAdvertActivity.this, Locale.getDefault()); //Variable for handling geological addresses

            List<Address> addresses; //List of addresses obtained from Geocoder
            try {
                addresses = geocoder.getFromLocation(latitude, longitude, 1); //Obtain list of addresses from the Geocoder
                String address = addresses.get(0).getAddressLine(0); //Obtain the ADDRESS String from the list of addresses

                locationEditText.setText(address); //Set location EditText view to obtained location
            }
            catch (IOException e) {
                e.printStackTrace();
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_a_new_advert);

        //Obtain the Adverts database
        advertDatabaseHelper = new AdvertDatabaseHelper(this);

        //Obtain the views
        lostAndFoundRadioGroup = (RadioGroup) findViewById(R.id.lostAndFoundRadioGroup);
        nameEdiText = (EditText) findViewById(R.id.nameEditText);
        phoneEditText = (EditText) findViewById(R.id.phoneEditText);
        descriptionEditText = (EditText) findViewById(R.id.descriptionEditText);
        dateEditText = (EditText) findViewById(R.id.dateEditText);
        locationEditText = (EditText) findViewById(R.id.locationEditText);
    }


    //Listener for "Get Current Location" Button
    public void getCurrentLocationClick(View view) {

        //-------------- heck for location permissions --------------
        //If permissions to access to "fine location" and "course location" are not granted by the device
        if (ContextCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED
                &&
                ActivityCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.ACCESS_COARSE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {

            //Request for permission access to "fine location" and "course location"
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION,
                            Manifest.permission.ACCESS_COARSE_LOCATION},
                    101);
        }

        //-------------- Obtain the current location --------------

        try {
            LocationManager locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE); //Access system location services
            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 2000, 5, locationListener); //Request location updates
        }
        catch(SecurityException e) {
            e.printStackTrace();
        }
    }

    //Listener for the "Save" Button - to save the Advert to the Advert database
    public void saveButton(View view) {

        //Obtain value selected for the Radio Button
        radioButtonID = lostAndFoundRadioGroup.getCheckedRadioButtonId();
        radioButton = findViewById(radioButtonID);

        if (radioButton.getText().equals("Lost")) {
            itemState = "Lost";
        }
        if (radioButton.getText().equals("Found")) {
            itemState = "Found";
        }

        //Obtain values entered in the EditText views
        name = nameEdiText.getText().toString();
        phone = phoneEditText.getText().toString();
        description = descriptionEditText.getText().toString();
        date = dateEditText.getText().toString();
        location = locationEditText.getText().toString();

        //Insert a new entry to the Order database using all the obtained data
        rowID = advertDatabaseHelper.insertAdvert(new Advert(
                itemState,
                name,
                phone,
                description,
                date,
                location,
                longitude,
                latitude));

        Util.makeToast(this, "Advert Saved!");

        //Pop the activity from the activity stack, revealing the MainActivity
        Intent intent = new Intent(this,MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
    }
}