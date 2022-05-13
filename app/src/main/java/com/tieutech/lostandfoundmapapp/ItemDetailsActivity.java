package com.tieutech.lostandfoundmapapp;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import com.tieutech.lostandfoundmapapp.Data.AdvertDatabaseHelper;
import com.tieutech.lostandfoundmapapp.Util.Util;

//ABOUT: Activity that displays the details of the Advert clicked on from LostAndFoundItemsActivity
public class ItemDetailsActivity extends AppCompatActivity {

    //View variables
    TextView foundInfoTextView;
    TextView dateFoundTextView;
    TextView locationTextView;

    //Data variables
    String itemState;
    String name;
    String phone;
    String description;
    String date;
    String location;
    double longitude, latitude;

    //Database variables
    String[] entry;
    AdvertDatabaseHelper advertDatabaseHelper = new AdvertDatabaseHelper(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item_details);

        //Obtain views
        foundInfoTextView = (TextView) findViewById(R.id.foundInfoTextView);
        dateFoundTextView = (TextView) findViewById(R.id.dateFoundTextView);
        locationTextView = (TextView) findViewById(R.id.locationTextView);

        //Obtain data passed from the ListAndFoundItemsActivity for the Advert item that is clicked on
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            itemState = extras.getString(Util.DATA_ITEM_STATE);
            name = extras.getString(Util.ADVERT_DATABASE_NAME);
            phone = extras.getString(Util.ADVERT_PHONE);
            description = extras.getString(Util.DATA_DESCRIPTION);
            date = extras.getString(Util.DATA_DATE);
            location = extras.getString(Util.DATA_LOCATION);
            longitude = extras.getDouble(Util.DATA_LONGITUDE);
            latitude = extras.getDouble(Util.DATA_LATITUDE);
        }

        //Set texts for the views
        foundInfoTextView.setText(itemState + " " + description);
        dateFoundTextView.setText("On " + date);
        locationTextView.setText("At " + location);

        Log.i("retrieved", longitude + " " + latitude);
    }

    //Listener for the "Remove" button
    public void removeClick(View view)
    {
        entry = new String[]{itemState, name, phone, description, date, location}; //Entry to be removed
        advertDatabaseHelper.removeEntry(entry); //Remove the entry

        Util.makeToast(this, "Advert Removed!");

        //Pop the activity from the activity stack, revealing the LostAndFoundItemsActivity
        Intent intent = new Intent(this, LostAndFoundItemsActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
    }
}