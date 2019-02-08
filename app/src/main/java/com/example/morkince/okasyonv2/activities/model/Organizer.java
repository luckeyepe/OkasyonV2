package com.example.morkince.okasyonv2.activities.model;

import android.net.Uri;

public class Organizer {
    String  organizerName;
    Uri imageuri;
    String Location;
    Double Price;
    int Rating;


    public Organizer(){}
    public Organizer(String OrganizerName, Uri imageuri, String location, Double price, int rating) {
        organizerName = OrganizerName;
        this.imageuri = imageuri;
        Location = location;
        Price = price;
        Rating = rating;
    }

    public String getStoreName() {
        return organizerName;
    }

    public void setStoreName(String storeName) {
        organizerName = storeName;
    }

    public Uri getImageuri() {
        return imageuri;
    }

    public void setImageuri(Uri imageuri) {
        this.imageuri = imageuri;
    }

    public String getLocation() {
        return Location;
    }

    public void setLocation(String location) {
        Location = location;
    }

    public Double getPrice() {
        return Price;
    }

    public void setPrice(Double price) {
        Price = price;
    }

    public int getRating() {
        return Rating;
    }

    public void setRating(int rating) {
        Rating = rating;
    }
}