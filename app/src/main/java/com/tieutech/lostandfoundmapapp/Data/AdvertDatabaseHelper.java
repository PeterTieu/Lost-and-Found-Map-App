package com.tieutech.lostandfoundmapapp.Data;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import androidx.annotation.Nullable;
import com.tieutech.lostandfoundmapapp.Model.Advert;
import com.tieutech.lostandfoundmapapp.Util.Util;
import java.util.ArrayList;
import java.util.List;

public class AdvertDatabaseHelper extends SQLiteOpenHelper {
    public AdvertDatabaseHelper(@Nullable Context context) {
        super(context, Util.ADVERT_DATABASE_NAME, null, Util.ADVERT_DATABASE_VERSION);
    }

    //Create teh database with SQL commands
    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String CREATE_ADVERT_TABLE = "CREATE TABLE "
                + Util.ADVERT_TABLE_NAME + " ("
                + Util.ADVERT_ID + " INTEGER PRIMARY KEY AUTOINCREMENT , "
                + Util.ADVERT_IS_ITEM_FOUND + " TEXT , "
                + Util.ADVERT_NAME + " TEXT , "
                + Util.ADVERT_PHONE + " TEXT , "
                + Util.ADVERT_DESCRIPTION + " TEXT , "
                + Util.ADVERT_DATE + " TEXT , "
                + Util.ADVERT_LOCATION + " TEXT , "
                + Util.ADVERT_LONGITUDE + " REAL , "
                + Util.ADVERT_LATITUDE + " REAL)";

        sqLiteDatabase.execSQL(CREATE_ADVERT_TABLE);
    }

    //2nd param: old version
    //3rd param: new version
    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int oldVersion, int newVersion) {

        String DROP_USER_TABLE = "DROP TABLE IF EXISTS ";
        sqLiteDatabase.execSQL(DROP_USER_TABLE, new String[]{Util.ADVERT_TABLE_NAME});

        onCreate(sqLiteDatabase); //Call onCreate(..) as defined above
    }

    //Function: Inserts an advert to the SQLiteDatabase, then returns the rowID
    public long insertAdvert(Advert advert) {

        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase(); //Get the readable database

        //Gather all data to be inserted as a single row entry in the database
        ContentValues contentValues = new ContentValues();
        contentValues.put(Util.ADVERT_IS_ITEM_FOUND, advert.isItemFound());
        contentValues.put(Util.ADVERT_NAME, advert.getName());
        contentValues.put(Util.ADVERT_PHONE, advert.getPhone());
        contentValues.put(Util.ADVERT_DESCRIPTION, advert.getDescription());
        contentValues.put(Util.ADVERT_DATE, advert.getDate());
        contentValues.put(Util.ADVERT_LOCATION, advert.getLocation());
        contentValues.put(Util.ADVERT_LONGITUDE, advert.getLongitude());
        contentValues.put(Util.ADVERT_LATITUDE, advert.getLatitude());

        //Insert the data to the database and return the rowID if it is successful
        long rowId = sqLiteDatabase.insert(Util.ADVERT_TABLE_NAME, null, contentValues);

        //Close the database
        sqLiteDatabase.close();

        //Return the row ID if insert was successful, and -1 if it failed
        return rowId;
    }


    // Checks to see if the advert exists in the SQLiteDatabase - and returns a boolean
    public boolean fetchAdvert(String name, String phone, String description, String date, String location) {

        SQLiteDatabase sqLiteDatabase = this.getReadableDatabase(); //Get the readable database

        Cursor cursor = sqLiteDatabase.query(
                Util.ADVERT_TABLE_NAME, //Table name
                new String[]{Util.ADVERT_ID}, //Primary key column
                Util.ADVERT_NAME + "=? and "
                        + Util.ADVERT_PHONE + "=? and "
                        + Util.ADVERT_DESCRIPTION + "=? and "
                        + Util.ADVERT_DATE + "=? and "
                        + Util.ADVERT_LOCATION + "=? and "
                        + Util.ADVERT_LONGITUDE + "=? and "
                        + Util.ADVERT_LATITUDE + "=? ",
                new String[]{name, phone, description, date, location}, //Values of the columns to match
                null, //Grouping of the rows
                null, //Filtering of the grows
                null); //Sorting of the order

        int numberOfRows = cursor.getCount(); //Get number of rows with the SAME values as username and password (most likely return just 1)

        sqLiteDatabase.close();

        if (numberOfRows > 0) {
            return true;
        }
        else {
            return false;
        }
    }

    //Obtain the Cursor for all data in the database
    public Cursor fetchAdvertList() {

        //Obtain the column names
        String [] columns = new String[] {
                Util.ADVERT_ID,
                Util.ADVERT_IS_ITEM_FOUND,
                Util.ADVERT_NAME,
                Util.ADVERT_PHONE,
                Util.ADVERT_DESCRIPTION,
                Util.ADVERT_DATE,
                Util.ADVERT_LOCATION,
                Util.ADVERT_LONGITUDE,
                Util.ADVERT_LATITUDE};

        //NOTE: get-READABLE-Database
        SQLiteDatabase sqLiteDatabase = this.getReadableDatabase();

        Cursor cursor = sqLiteDatabase.query(
                Util.ADVERT_TABLE_NAME,
                columns, // The array of columns to return (pass null to get all)
                null, // The columns for the WHERE clause
                null, // The values for the WHERE clause
                null, // don't group the rows
                null, // don't filter by row groups
                null // The sort order
        );

        if (cursor != null) {
            cursor.moveToFirst();
        }
        return cursor;
    }

    //Fetch all Adverts and place them into a list
    public List<Advert> fetchAllAdverts() {
        List<Advert> userList = new ArrayList<>();

        SQLiteDatabase db = this.getReadableDatabase();

        String selectAll = " SELECT * FROM " + Util.ADVERT_TABLE_NAME;
        Cursor cursor = db.rawQuery(selectAll, null);

        if (cursor.moveToFirst()) {
            do {
                Advert advert = new Advert();
                advert.setItemFound(cursor.getString(1));
                advert.setName(cursor.getString(2));
                advert.setPhone(cursor.getString(3));
                advert.setDescription(cursor.getString(4));
                advert.setDate(cursor.getString(5));
                advert.setLocation(cursor.getString(6));
                advert.setLongitude(cursor.getDouble(7));
                advert.setLatitude(cursor.getDouble(8));

                userList.add(advert);
            } while(cursor.moveToNext());
        }

        return userList;
    }

    //Remove an entry from the database
    public void removeEntry(String[] entry)
    {
        SQLiteDatabase db = this.getWritableDatabase(); //Get the readable database

        //Delete the entry
        db.delete(Util.ADVERT_TABLE_NAME, Util.ADVERT_IS_ITEM_FOUND + "=? and " + Util.ADVERT_NAME + "=? and " + Util.ADVERT_PHONE + "=? and " + Util.ADVERT_DESCRIPTION + "=? and " + Util.ADVERT_DATE + "=? and " + Util.ADVERT_LOCATION + "=?", entry);

        db.close();
    }
}
