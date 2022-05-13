package com.tieutech.lostandfoundmapapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

//ABOUT: Main activity that links to other activities
public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

    }

    //Listener for "Create New Advert" Button
    public void createNewAdvertClick(View view){
        Intent createNewActivityIntent = new Intent(MainActivity.this, CreateANewAdvertActivity.class);
        startActivity(createNewActivityIntent);
    }

    //Listener for "Show All Lost And Found Items" Button
    public void showAllLostAndFoundItemsClick(View view) {
        Intent lostAndFoundItemsIntent = new Intent(MainActivity.this, LostAndFoundItemsActivity.class);
        startActivity(lostAndFoundItemsIntent);
    }

    //Listener for "Show On Map" Button
    public void showOnMapClick(View view) {
        Intent mapsIntent = new Intent(MainActivity.this, MapsActivity.class);
        startActivity(mapsIntent);
    }
}