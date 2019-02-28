package com.example.morkince.okasyonv2.activities.model

import android.os.Parcel
import android.os.Parcelable

class Transaction_Client() : Parcelable {
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

    constructor(parcel: Parcel) : this() {
        transaction_client_is_deliverable = parcel.readValue(Boolean::class.java.classLoader) as? Boolean
        transaction_client_item_rating = parcel.readValue(Double::class.java.classLoader) as? Double
        transaction_client_buyer_uid = parcel.readString()
        transaction_client_deliver_location = parcel.readString()
        transaction_client_event_uid = parcel.readString()
        transaction_client_item_uid = parcel.readString()
        transaction_client_item_count = parcel.readValue(Int::class.java.classLoader) as? Int
        transaction_client_item_price = parcel.readValue(Double::class.java.classLoader) as? Double
        transaction_client_item_name = parcel.readString()
        transaction_client_order_cost = parcel.readValue(Double::class.java.classLoader) as? Double
        transaction_client_rent_start_date = parcel.readString()
        transaction_client_rent_end_date = parcel.readString()
        transaction_client_uid = parcel.readString()
    }

//    constructor()
//
//    constructor(
//        transaction_client_is_deliverable: Boolean?,
//        transaction_client_item_rating: Double?,
//        transaction_client_buyer_uid: String?,
//        transaction_client_deliver_location: String?,
//        transaction_client_event_uid: String?,
//        transaction_client_item_uid: String?,
//        transaction_client_item_count: Int?,
//        transaction_client_item_price: Double?,
//        transaction_client_item_name: String?,
//        transaction_client_order_cost: Double?,
//        transaction_client_rent_start_date: String?,
//        transaction_client_rent_end_date: String?,
//        transaction_client_uid: String?
//    ) {
//        this.transaction_client_is_deliverable = transaction_client_is_deliverable
//        this.transaction_client_item_rating = transaction_client_item_rating
//        this.transaction_client_buyer_uid = transaction_client_buyer_uid
//        this.transaction_client_deliver_location = transaction_client_deliver_location
//        this.transaction_client_event_uid = transaction_client_event_uid
//        this.transaction_client_item_uid = transaction_client_item_uid
//        this.transaction_client_item_count = transaction_client_item_count
//        this.transaction_client_item_price = transaction_client_item_price
//        this.transaction_client_item_name = transaction_client_item_name
//        this.transaction_client_order_cost = transaction_client_order_cost
//        this.transaction_client_rent_start_date = transaction_client_rent_start_date
//        this.transaction_client_rent_end_date = transaction_client_rent_end_date
//        this.transaction_client_uid = transaction_client_uid
//    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeValue(transaction_client_is_deliverable)
        parcel.writeValue(transaction_client_item_rating)
        parcel.writeString(transaction_client_buyer_uid)
        parcel.writeString(transaction_client_deliver_location)
        parcel.writeString(transaction_client_event_uid)
        parcel.writeString(transaction_client_item_uid)
        parcel.writeValue(transaction_client_item_count)
        parcel.writeValue(transaction_client_item_price)
        parcel.writeString(transaction_client_item_name)
        parcel.writeValue(transaction_client_order_cost)
        parcel.writeString(transaction_client_rent_start_date)
        parcel.writeString(transaction_client_rent_end_date)
        parcel.writeString(transaction_client_uid)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Transaction_Client> {
        override fun createFromParcel(parcel: Parcel): Transaction_Client {
            return Transaction_Client(parcel)
        }

        override fun newArray(size: Int): Array<Transaction_Client?> {
            return arrayOfNulls(size)
        }
    }


}