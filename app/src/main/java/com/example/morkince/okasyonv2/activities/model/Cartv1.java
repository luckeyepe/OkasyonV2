package com.example.morkince.okasyonv2.activities.model;

import android.net.Uri;

public class Cartv1 {


    String cartItem_item_name;
    Uri imageuri;
    Double cart_item_order_cost;
    int cartItem_item_Rating;

    public Cartv1(){}
    public Cartv1(String OrganizerName, Uri imageuri, Double price, int cartItemitemRating) {
        cartItem_item_name = OrganizerName;
        this.imageuri = imageuri;
        cart_item_order_cost = price;
        cartItem_item_Rating = cartItemitemRating;
    }

    public String getItemName() {
        return cartItem_item_name;
    }

    public void setItemName(String storeName) {
        cartItem_item_name = storeName;
    }

    public Uri getImageuri() {
        return imageuri;
    }

    public void setImageuri(Uri imageuri) {
        this.imageuri = imageuri;
    }

    public Double getCart_item_order_cost() {
        return cart_item_order_cost;
    }

    public void setCart_item_order_cost(Double cart_item_order_cost) {
        this.cart_item_order_cost = cart_item_order_cost;
    }

    public int getCartItem_item_Rating() {
        return cartItem_item_Rating;
    }

    public void setCartItem_item_Rating(int cartItem_item_Rating) {
        this.cartItem_item_Rating = cartItem_item_Rating;
    }
}
