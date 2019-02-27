package com.example.morkince.okasyonv2.activities.model

class Transaction_Client {
    var transaction_client_is_deliverable: Boolean ?= null
    var transaction_client_item_rating: Double ?= null
    var transaction_client_buyer_uid: String ?= null
    var transaction_client_deliver_location: String ?= null
    var transaction_client_event_uid: String ?= null
    var transaction_client_item_uid: String ?= null
    var transaction_client_item_count: Int ?= null
    var transaction_client_item_price: Double ?= null
    var transaction_client_item_name: String  ?= null
    var transaction_client_order_cost: Double ?= null
    var transaction_client_rent_start_date: String ?= null
    var transaction_client_rent_end_date: String ?= null
    var transaction_client_uid: String ?= null

    constructor()

    constructor(
        transaction_client_is_deliverable: Boolean?,
        transaction_client_item_rating: Double?,
        transaction_client_buyer_uid: String?,
        transaction_client_deliver_location: String?,
        transaction_client_event_uid: String?,
        transaction_client_item_uid: String?,
        transaction_client_item_count: Int?,
        transaction_client_item_price: Double?,
        transaction_client_item_name: String?,
        transaction_client_order_cost: Double?,
        transaction_client_rent_start_date: String?,
        transaction_client_rent_end_date: String?,
        transaction_client_uid: String?
    ) {
        this.transaction_client_is_deliverable = transaction_client_is_deliverable
        this.transaction_client_item_rating = transaction_client_item_rating
        this.transaction_client_buyer_uid = transaction_client_buyer_uid
        this.transaction_client_deliver_location = transaction_client_deliver_location
        this.transaction_client_event_uid = transaction_client_event_uid
        this.transaction_client_item_uid = transaction_client_item_uid
        this.transaction_client_item_count = transaction_client_item_count
        this.transaction_client_item_price = transaction_client_item_price
        this.transaction_client_item_name = transaction_client_item_name
        this.transaction_client_order_cost = transaction_client_order_cost
        this.transaction_client_rent_start_date = transaction_client_rent_start_date
        this.transaction_client_rent_end_date = transaction_client_rent_end_date
        this.transaction_client_uid = transaction_client_uid
    }


}