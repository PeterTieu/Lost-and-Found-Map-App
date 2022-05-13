package com.tieutech.lostandfoundmapapp.Model;

//ABOUT: Class that defines the blueprint of each Advert object
public class Advert {
    //Variables
    String isItemFound;
    String name;
    String phone;
    String description;
    String date;
    String location;
    double longitude;
    double latitude;

    //Constructor #1
    public Advert() {
    }

    //Constructor #2
    public Advert(String isItemFound, String name, String phone, String description, String date, String location) {
        this.isItemFound = isItemFound;
        this.name = name;
        this.phone = phone;
        this.description = description;
        this.date = date;
        this.location = location;
    }

    //Constructor #3
    public Advert(String isItemFound, String name, String phone, String description, String date, String location, double longitude, double latitude) {
        this.isItemFound = isItemFound;
        this.name = name;
        this.phone = phone;
        this.description = description;
        this.date = date;
        this.location = location;
        this.longitude = longitude;
        this.latitude = latitude;
    }

    //Getters
    public String isItemFound() { return isItemFound; }
    public String getName() { return name; }
    public String getPhone() { return phone; }
    public String getDescription() { return description; }
    public String getDate() { return date; }
    public String getLocation() { return location; }
    public double getLongitude() { return longitude; }
    public double getLatitude() { return latitude; }

    //Setters
    public void setItemFound(String itemFound) { isItemFound = itemFound; }
    public void setName(String name) { this.name = name; }
    public void setPhone(String phone) { this.phone = phone; }
    public void setDescription(String description) { this.description = description; }
    public void setDate(String date) { this.date = date; }
    public void setLocation(String location) { this.location = location; }
    public void setLongitude(double longitude) { this.longitude = longitude; }
    public void setLatitude(double latitude) { this.latitude = latitude; }
}
