package com.example.morkince.okasyonv2;

public class Item {

    String itemName;
    double starRating;
    double itemPrice;
    String item_uid;
    String item_category;
    String item_description;
    String isForSale;
    String isPerSquareUnit;
    String price_description;
    String store_id;

    public Item() {
    }

    public Item(String itemName, double starRating, double itemPrice, String item_uid, String item_category, String item_description, String isForSale, String isPerSquareUnit, String price_description, String store_id) {
        this.itemName = itemName;
        this.starRating = starRating;
        this.itemPrice = itemPrice;
        this.item_uid = item_uid;
        this.item_category = item_category;
        this.item_description = item_description;
        this.isForSale = isForSale;
        this.isPerSquareUnit = isPerSquareUnit;
        this.price_description = price_description;
        this.store_id = store_id;
    }


    public String getItem_category() {
        return item_category;
    }

    public void setItem_category(String item_category) {
        this.item_category = item_category;
    }

    public String getItem_description() {
        return item_description;
    }

    public void setItem_description(String item_description) {
        this.item_description = item_description;
    }

    public String isForSale() {
        return isForSale;
    }

    public void setForSale(String forSale) {
        isForSale = forSale;
    }

    public String isPerSquareUnit() {
        return isPerSquareUnit;
    }

    public void setPerSquareUnit(String perSquareUnit) {
        isPerSquareUnit = perSquareUnit;
    }

    public String getPrice_description() {
        return price_description;
    }

    public void setPrice_description(String price_description) {
        this.price_description = price_description;
    }

    public String getStore_id() {
        return store_id;
    }

    public void setStore_id(String store_id) {
        this.store_id = store_id;
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

