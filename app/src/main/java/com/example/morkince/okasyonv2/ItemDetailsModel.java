package com.example.morkince.okasyonv2;

public class ItemDetailsModel {

    String item_category_id;
    String item_description;
    boolean item_for_sale;
    boolean item_is_per_sqr_unit_of_measurement;
    String item_name;
    double item_price;
    String item_price_description;
    String item_store_id;
    String item_uid;
    String item_tag;
    String item_termsAndConditions;

    public ItemDetailsModel(){}

    public ItemDetailsModel(String item_category_id, String item_description, boolean item_for_sale, boolean item_is_per_sqr_unit_of_measurement, String item_name, double item_price, String item_price_description, String item_store_id, String item_uid,  String item_tag,String item_termsAndConditions) {
        this.item_category_id = item_category_id;
        this.item_description = item_description;
        this.item_for_sale = item_for_sale;
        this.item_is_per_sqr_unit_of_measurement = item_is_per_sqr_unit_of_measurement;
        this.item_name = item_name;
        this.item_price = item_price;
        this.item_price_description = item_price_description;
        this.item_store_id = item_store_id;
        this.item_uid = item_uid;
        this.item_tag=item_tag;
        this.item_termsAndConditions = item_termsAndConditions;
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

    public boolean isItem_for_sale() {
        return item_for_sale;
    }

    public void setItem_for_sale(boolean item_for_sale) {
        this.item_for_sale = item_for_sale;
    }

    public boolean isItem_is_per_sqr_unit_of_measurement() {
        return item_is_per_sqr_unit_of_measurement;
    }

    public void setItem_is_per_sqr_unit_of_measurement(boolean item_is_per_sqr_unit_of_measurement) {
        this.item_is_per_sqr_unit_of_measurement = item_is_per_sqr_unit_of_measurement;
    }

    public String getItem_name() {
        return item_name;
    }

    public void setItem_name(String item_name) {
        this.item_name = item_name;
    }

    public double getItem_price() {
        return item_price;
    }

    public void setItem_price(double item_price) {
        this.item_price = item_price;
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

    public String getItem_uid() {
        return item_uid;
    }

    public void setItem_uid(String item_uid) {
        this.item_uid = item_uid;
    }

    public String getItem_tag() {
        return item_tag;
    }

    public void setItem_tag(String item_tag) {
        this.item_tag = item_tag;
    }

    public String getItem_termsAndConditions() {
        return item_termsAndConditions;
    }

    public void setItem_termsAndConditions(String item_termsAndConditions) {
        this.item_termsAndConditions = item_termsAndConditions;
    }
}
