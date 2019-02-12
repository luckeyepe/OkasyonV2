package com.example.morkince.okasyonv2.activities.model;

public class Item {

    String item_name = "";
    double item_average_rating = 0;
    double item_price = 0;
    String item_uid = "";
    String item_category_id = "";
    String item_description = "";
    boolean item_is_per_sqr_unit_of_measurement = false;
    boolean item_for_sale = false;
    String item_price_description = "";
    String item_store_id = "";
    String item_display_picture_url = "";
    String item_doc="";
    String item_tag="";

    public Item() {
    }

    public Item(String item_name, double item_average_rating, double item_price, String item_uid, String item_category_id, String item_description,  boolean item_for_sale, boolean item_is_per_sqr_unit_of_measurement,String item_price_description, String item_store_id, String item_tag) {
        this.item_name = item_name;
        this.item_average_rating = item_average_rating;
        this.item_price = item_price;
        this.item_uid = item_uid;
        this.item_category_id = item_category_id;
        this.item_description = item_description;
        this.item_is_per_sqr_unit_of_measurement = item_is_per_sqr_unit_of_measurement;
        this.item_for_sale = item_for_sale;
        this.item_price_description = item_price_description;
        this.item_store_id = item_store_id;
        this.item_tag=item_tag;
    }

    public Item(String item_name, double item_average_rating, double item_price, String item_uid, String item_category_id, String item_description, boolean item_is_per_sqr_unit_of_measurement, boolean item_for_sale, String item_price_description, String item_store_id, String item_display_picture_url, String item_doc) {
        this.item_name = item_name;
        this.item_average_rating = item_average_rating;
        this.item_price = item_price;
        this.item_uid = item_uid;
        this.item_category_id = item_category_id;
        this.item_description = item_description;
        this.item_is_per_sqr_unit_of_measurement = item_is_per_sqr_unit_of_measurement;
        this.item_for_sale = item_for_sale;
        this.item_price_description = item_price_description;
        this.item_store_id = item_store_id;
        this.item_display_picture_url = item_display_picture_url;
        this.item_doc = item_doc;
    }

    public String getItem_name() {
        return item_name;
    }

    public void setItem_name(String item_name) {
        this.item_name = item_name;
    }

    public double getItem_average_rating() {
        return item_average_rating;
    }

    public void setItem_average_rating(double item_average_rating) {
        this.item_average_rating = item_average_rating;
    }

    public double getItem_price() {
        return item_price;
    }

    public void setItem_price(double item_price) {
        this.item_price = item_price;
    }

    public String getItem_uid() {
        return item_uid;
    }

    public void setItem_uid(String item_uid) {
        this.item_uid = item_uid;
    }

    public String getItem_category_id() {
        return item_category_id;
    }

    public void setItem_category_id(String item_category_id) {
        this.item_category_id = item_category_id;
    }

    public String getItem_description() {
        return item_description;
    }

    public void setItem_description(String item_description) {
        this.item_description = item_description;
    }

    public boolean isItem_is_per_sqr_unit_of_measurement() {
        return item_is_per_sqr_unit_of_measurement;
    }

    public void setItem_is_per_sqr_unit_of_measurement(boolean item_is_per_sqr_unit_of_measurement) {
        this.item_is_per_sqr_unit_of_measurement = item_is_per_sqr_unit_of_measurement;
    }

    public boolean isItem_for_sale() {
        return item_for_sale;
    }

    public void setItem_for_sale(boolean item_for_sale) {
        this.item_for_sale = item_for_sale;
    }

    public String getItem_price_description() {
        return item_price_description;
    }

    public void setItem_price_description(String item_price_description) {
        this.item_price_description = item_price_description;
    }

    public String getItem_store_id() {
        return item_store_id;
    }

    public void setItem_store_id(String item_store_id) {
        this.item_store_id = item_store_id;
    }

    public String getItem_display_picture_url() {
        return item_display_picture_url;
    }

    public void setItem_display_picture_url(String item_display_picture_url) {
        this.item_display_picture_url = item_display_picture_url;
    }

    public String getItem_doc() {
        return item_doc;
    }

    public void setItem_doc(String item_doc) {
        this.item_doc = item_doc;
    }

    public String getItem_tag() {
        return item_tag;
    }

    public void setItem_tag(String item_tag) {
        this.item_tag = item_tag;
    }
}

