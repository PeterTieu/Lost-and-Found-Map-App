package com.tieutech.lostandfoundmapapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.content.Intent;
import android.os.Bundle;
import com.tieutech.lostandfoundmapapp.Adapter.AdvertRecyclerViewAdapter;
import com.tieutech.lostandfoundmapapp.Data.AdvertDatabaseHelper;
import com.tieutech.lostandfoundmapapp.Model.Advert;
import com.tieutech.lostandfoundmapapp.Util.Util;
import java.util.ArrayList;
import java.util.List;

//ABOUT: Activity that displays the list of all Adverts
public class LostAndFoundItemsActivity extends AppCompatActivity implements AdvertRecyclerViewAdapter.OnAdvertListener{

    //RecyclerView variables
    RecyclerView advertRecyclerView;
    AdvertRecyclerViewAdapter advertRecyclerViewAdapter;
    List<Advert> advertsList = new ArrayList<>(); //List of all Adverts

    //Database variable
    AdvertDatabaseHelper advertDatabaseHelper = new AdvertDatabaseHelper(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lost_and_found_items);

        advertRecyclerView = findViewById(R.id.advertRecyclerView); //Obtain RecyclerView

        advertsList = advertDatabaseHelper.fetchAllAdverts(); //Fetch all data from the Advert database and store it in the list of Adverts

        //RecyclerViewAdapter to link the RecyclerView for Adverts to the data
        advertRecyclerViewAdapter = new AdvertRecyclerViewAdapter(advertsList, this, this); //Instantiate the Recyclerview Adapter
        advertRecyclerView.setAdapter(advertRecyclerViewAdapter); //Set the Adapter to the RecyclerView

        //LinearLayoutManager to set the layout of the RecyclerView (and make it vertical)
        LinearLayoutManager layoutManager = new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.VERTICAL, false);
        advertRecyclerView.setLayoutManager(layoutManager); //Link the LayoutManager to the RecyclerView
    }

    //Listener for the selection of an Advert item
    @Override
    public void onAdvertClick(int position) {

        //Begin intent to open AdvertDetailsActivity
        Intent itemDetailsIntent = new Intent(LostAndFoundItemsActivity.this, ItemDetailsActivity.class);

        //Send data to the AdvertDetailsActivity
        itemDetailsIntent.putExtra(Util.DATA_ITEM_STATE, advertsList.get(position).isItemFound());
        itemDetailsIntent.putExtra(Util.ADVERT_DATABASE_NAME, advertsList.get(position).getName());
        itemDetailsIntent.putExtra(Util.ADVERT_PHONE, advertsList.get(position).getPhone());
        itemDetailsIntent.putExtra(Util.DATA_DESCRIPTION, advertsList.get(position).getDescription());
        itemDetailsIntent.putExtra(Util.DATA_DATE, advertsList.get(position).getDate());
        itemDetailsIntent.putExtra(Util.DATA_LOCATION, advertsList.get(position).getLocation());
        itemDetailsIntent.putExtra(Util.DATA_LONGITUDE, advertsList.get(position).getLongitude());
        itemDetailsIntent.putExtra(Util.DATA_LATITUDE, advertsList.get(position).getLatitude());

        //Open AdvertDetailsActivity
        startActivity(itemDetailsIntent);
    }
}