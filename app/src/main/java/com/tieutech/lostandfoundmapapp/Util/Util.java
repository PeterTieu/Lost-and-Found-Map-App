package com.tieutech.lostandfoundmapapp.Util;

import android.content.Context;
import android.widget.Toast;

//ABOUT: Utility class for the project
public class Util {

    //Advert Database
    public static final int ADVERT_DATABASE_VERSION = 1;
    public static final String ADVERT_DATABASE_NAME = "advert_db";
    public static final String ADVERT_TABLE_NAME = "adverts_table";
    public static final String ADVERT_ID = "advert_id";
    public static final String ADVERT_IS_ITEM_FOUND = "advert_is_item_found";
    public static final String ADVERT_NAME = "advert_name";
    public static final String ADVERT_PHONE = "advert_phone";
    public static final String ADVERT_DESCRIPTION = "advert_description";
    public static final String ADVERT_DATE = "advert_date";
    public static final String ADVERT_LOCATION = "advert_location";
    public static final String ADVERT_LONGITUDE = "advert_longitude";
    public static final String ADVERT_LATITUDE = "advert_latitude";

    //Order data for putExtra (for data passed between activities)
    public static final String DATA_ITEM_STATE = "data_item_state";
    public static final String DATA_DESCRIPTION = "data_description";
    public static final String DATA_DATE = "data_date";
    public static final String DATA_LOCATION = "data_location";
    public static final String DATA_LONGITUDE = "data_longitude";
    public static final String DATA_LATITUDE = "data_latitude";

    //Make a Toast
    public static void makeToast(Context context, String message) {
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
    }
}
