package com.example.morkince.okasyonv2.activities.model;

public class CartItem {
        String cart_item_name;
        Double cart_item_order_cost;
        int cart_item_Rating;
        int cart_item_item_count;
        Boolean cart_item_Deliverable;
        String cart_item_rent_end_date;
        String cart_item_rent_start_date;
        String cart_item_event_uid;

        public String getCart_item_id() {
            return cart_item_id;
        }

        public void setCart_item_id(String cart_item_id) {
            this.cart_item_id = cart_item_id;
        }

        String cart_item_id;
        String cart_item_status;
        String cart_item_item_uid;
        String cart_item_delivery_location;
        Double cart_item_item_price;
        String cart_item_group_uid;
        String cart_item_buyer_uid;

        public void setCart_item_group_uid(String cart_item_group_uid) {
            this.cart_item_group_uid = cart_item_group_uid;
        }

        public String getCart_item_buyer_uid() {
            return cart_item_buyer_uid;
        }

        public void setCart_item_buyer_uid(String cart_item_buyer_uid) {
            this.cart_item_buyer_uid = cart_item_buyer_uid;
        }

        public String getCart_item_group_uid() {
            return cart_item_group_uid;
        }

        public CartItem(){}
        public CartItem(String cartItem_item_name, Double cart_item_order_cost, int cartItem_item_Rating, int cart_item_item_count, Boolean cartItem_item_Deliverable, String cart_item_rent_end_date, String cart_item_rent_start_date, String cart_item_event_uid, String cart_item_id, String cart_item_status, String cart_item_item_uid, String cart_item_delivery_location, Double cart_item_item_price,String cart_item_group_uid, String cart_item_buyer_uid) {
            this.cart_item_name = cartItem_item_name;
            this.cart_item_order_cost = cart_item_order_cost;
            this.cart_item_Rating = cartItem_item_Rating;
            this.cart_item_item_count = cart_item_item_count;
            this.cart_item_Deliverable = cartItem_item_Deliverable;
            this.cart_item_rent_end_date = cart_item_rent_end_date;
            this.cart_item_rent_start_date = cart_item_rent_start_date;
            this.cart_item_event_uid = cart_item_event_uid;
            this.cart_item_id = cart_item_id;
            this.cart_item_status = cart_item_status;
            this.cart_item_item_uid = cart_item_item_uid;
            this.cart_item_delivery_location = cart_item_delivery_location;
            this.cart_item_item_price = cart_item_item_price;
            this.cart_item_group_uid=cart_item_group_uid;
            this.cart_item_buyer_uid = cart_item_buyer_uid;
        }

        public String getcart_item_name() {
            return cart_item_name;
        }

        public void setCart_item_name(String cart_item_name) {
            this.cart_item_name = cart_item_name;
        }

        public Double getcart_item_order_cost() {
            return cart_item_order_cost;
        }

        public void setCart_item_order_cost(Double cart_item_order_cost) {
            this.cart_item_order_cost = cart_item_order_cost;
        }

        public int getcart_item_Rating() {
            return cart_item_Rating;
        }

        public void setCart_item_Rating(int cart_item_Rating) {
            this.cart_item_Rating = cart_item_Rating;
        }

        public int getCart_item_item_count() {
            return cart_item_item_count;
        }

        public void setCart_item_item_count(int cart_item_item_count) {
            this.cart_item_item_count = cart_item_item_count;
        }

        public Boolean getCart_item_Deliverable() {
            return cart_item_Deliverable;
        }

        public void setCart_item_Deliverable(Boolean cart_item_Deliverable) {
            this.cart_item_Deliverable = cart_item_Deliverable;
        }

        public String getCart_item_rent_end_date() {
            return cart_item_rent_end_date;
        }

        public void setCart_item_rent_end_date(String cart_item_rent_end_date) {
            this.cart_item_rent_end_date = cart_item_rent_end_date;
        }

        public String getCart_item_rent_start_date() {
            return cart_item_rent_start_date;
        }

        public void setCart_item_rent_start_date(String cart_item_rent_start_date) {
            this.cart_item_rent_start_date = cart_item_rent_start_date;
        }

        public String getCart_item_event_uid() {
            return cart_item_event_uid;
        }

        public void setCart_item_event_uid(String cart_item_event_uid) {
            this.cart_item_event_uid = cart_item_event_uid;
        }



        public String getCart_item_status() {
            return cart_item_status;
        }

        public void setCart_item_status(String cart_item_status) {
            this.cart_item_status = cart_item_status;
        }

        public String getCart_item_item_uid() {
            return cart_item_item_uid;
        }

        public void setCart_item_item_uid(String cart_item_item_uid) {
            this.cart_item_item_uid = cart_item_item_uid;
        }

        public String getCart_item_delivery_location() {
            return cart_item_delivery_location;
        }

        public void setCart_item_delivery_location(String cart_item_delivery_location) {
            this.cart_item_delivery_location = cart_item_delivery_location;
        }

        public Double getCart_item_item_price() {
            return cart_item_item_price;
        }

        public void setCart_item_item_price(Double cart_item_item_price) {
            this.cart_item_item_price = cart_item_item_price;
        }
    }


