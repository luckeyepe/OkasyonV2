package com.example.morkince.okasyonv2.activities.model;

import android.net.Uri;

public class transactionclient {
    String  ProductName;
    Uri imageuri;
    Double Price;
    int Rating;


    public transactionclient(){}
    public transactionclient(String OrganizerName, Uri imageuri, Double price, int rating) {
        ProductName = OrganizerName;
        this.imageuri = imageuri;
        Price = price;
        Rating = rating;
    }

    public String getStoreName() {
        return ProductName;
    }

    public void setStoreName(String storeName) {
        ProductName = storeName;
    }

    public Uri getImageuri() {
        return imageuri;
    }

    public void setImageuri(Uri imageuri) {
        this.imageuri = imageuri;
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
