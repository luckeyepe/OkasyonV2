package com.example.morkince.okasyonv2.activities.model;

import android.net.Uri;

public class Organizer {
    String organizerDetails;
    String  organizerName;
    String imageuri;
    String Location;
    Double Price;
    double Rating;
    String user_uid;
    String eventCategory;

    public Organizer(){}

    public String getOrganizerDetails() {
        return organizerDetails;
    }

    public void setOrganizerDetails(String organizerDetails) {
        this.organizerDetails = organizerDetails;
    }

    public Organizer(String organizerDetails, String organizerName, String imageuri, String location, Double price, double rating, String user_uid, String eventCategory) {
        this.organizerDetails = organizerDetails;
        this.organizerName = organizerName;
        this.imageuri = imageuri;
        Location = location;
        Price = price;
        Rating = rating;
        this.user_uid = user_uid;
        this.eventCategory = eventCategory;
    }

    public Organizer(String organizerName, String imageuri, String location, Double price, double rating, String user_uid, String eventCategory) {
        this.organizerName = organizerName;
        this.imageuri = imageuri;
        Location = location;
        Price = price;
        Rating = rating;
        this.user_uid = user_uid;
        this.eventCategory = eventCategory;
    }

    public String getEventCategory() {
        return eventCategory;
    }

    public void setEventCategory(String eventCategory) {
        this.eventCategory = eventCategory;
    }

    public String getImageuri() {
        return imageuri;
    }

    public void setImageuri(String imageuri) {
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

    public double getRating() {
        return Rating;
    }

    public void setRating(double rating) {
        Rating = rating;
    }

    public String getOrganizerName() {
        return organizerName;
    }

    public void setOrganizerName(String organizerName) {
        this.organizerName = organizerName;
    }

    public String getUser_uid() {
        return user_uid;
    }

    public void setUser_uid(String user_uid) {
        this.user_uid = user_uid;
    }
}