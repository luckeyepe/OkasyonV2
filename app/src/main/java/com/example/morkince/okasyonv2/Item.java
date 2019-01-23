package com.example.morkince.okasyonv2;

public class Item {

    String itemName;
    double starRating;
    double itemPrice;
    String item_uid;

    public Item() {
    }

    public Item(String itemName, double starRating, double itemPrice, String item_uid) {
        this.itemName = itemName;
        this.starRating = starRating;
        this.itemPrice = itemPrice;
        this.item_uid = item_uid;
    }

    public double getItemPrice() {
        return itemPrice;
    }

    public void setItemPrice(double itemPrice) {
        this.itemPrice = itemPrice;
    }

    public String getItem_uid() {
        return item_uid;
    }

    public void setItem_uid(String item_uid) {
        this.item_uid = item_uid;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public double getStarRating() {
        return starRating;
    }

    public void setStarRating(double starRating) {
        this.starRating = starRating;
    }
}

