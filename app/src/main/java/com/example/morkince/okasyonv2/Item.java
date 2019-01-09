package com.example.morkince.okasyonv2;

public class Item {

    String itemName;
    double starRating;
    double itemPrice;

    public Item()
    {}

    public Item(String itemName,double starRating,double itemPrice)
    {
        this.itemName=itemName;
        this.starRating=starRating;
        this.itemPrice=itemPrice;
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

    public double getPrice() {
        return itemPrice;
    }

    public void setPrice(double itemPrice) {
        this.itemPrice = itemPrice;
    }
}
