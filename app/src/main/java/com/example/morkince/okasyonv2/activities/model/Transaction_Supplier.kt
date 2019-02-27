package com.example.morkince.okasyonv2.activities.model

class Transaction_Supplier {
    var transaction_supplier_is_deliverable: Boolean ?= null
    var transaction_supplier_item_rating: Double ?= null
    var transaction_supplier_buyer_uid: String ?= null
    var transaction_supplier_deliver_location: String ?= null
    var transaction_supplier_event_uid: String ?= null
    var transaction_supplier_item_uid: String ?= null
    var transaction_supplier_item_count: Int ?= null
    var transaction_supplier_item_price: Double ?= null
    var transaction_supplier_item_name: String  ?= null
    var transaction_supplier_order_cost: Double ?= null
    var transaction_supplier_rent_start_date: String ?= null
    var transaction_supplier_rent_end_date: String ?= null
    var transaction_supplier_uid: String ?= null

    constructor()

    constructor(
        transaction_supplier_is_deliverable: Boolean?,
        transaction_supplier_item_rating: Double?,
        transaction_supplier_buyer_uid: String?,
        transaction_supplier_deliver_location: String?,
        transaction_supplier_event_uid: String?,
        transaction_supplier_item_uid: String?,
        transaction_supplier_item_count: Int?,
        transaction_supplier_item_price: Double?,
        transaction_supplier_item_name: String?,
        transaction_supplier_order_cost: Double?,
        transaction_supplier_rent_start_date: String?,
        transaction_supplier_rent_end_date: String?,
        transaction_supplier_uid: String?
    ) {
        this.transaction_supplier_is_deliverable = transaction_supplier_is_deliverable
        this.transaction_supplier_item_rating = transaction_supplier_item_rating
        this.transaction_supplier_buyer_uid = transaction_supplier_buyer_uid
        this.transaction_supplier_deliver_location = transaction_supplier_deliver_location
        this.transaction_supplier_event_uid = transaction_supplier_event_uid
        this.transaction_supplier_item_uid = transaction_supplier_item_uid
        this.transaction_supplier_item_count = transaction_supplier_item_count
        this.transaction_supplier_item_price = transaction_supplier_item_price
        this.transaction_supplier_item_name = transaction_supplier_item_name
        this.transaction_supplier_order_cost = transaction_supplier_order_cost
        this.transaction_supplier_rent_start_date = transaction_supplier_rent_start_date
        this.transaction_supplier_rent_end_date = transaction_supplier_rent_end_date
        this.transaction_supplier_uid = transaction_supplier_uid
    }


}