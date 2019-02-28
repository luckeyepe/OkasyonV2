package com.example.morkince.okasyonv2.activities.model

import android.os.Parcel
import android.os.Parcelable

class Transaction_Supplier() :Parcelable {
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
    var transaction_supplier_status: String ?= null
    var transaction_supplier_review: String ?= null

    constructor(parcel: Parcel) : this() {
        transaction_supplier_is_deliverable = parcel.readValue(Boolean::class.java.classLoader) as? Boolean
        transaction_supplier_item_rating = parcel.readValue(Double::class.java.classLoader) as? Double
        transaction_supplier_buyer_uid = parcel.readString()
        transaction_supplier_deliver_location = parcel.readString()
        transaction_supplier_event_uid = parcel.readString()
        transaction_supplier_item_uid = parcel.readString()
        transaction_supplier_item_count = parcel.readValue(Int::class.java.classLoader) as? Int
        transaction_supplier_item_price = parcel.readValue(Double::class.java.classLoader) as? Double
        transaction_supplier_item_name = parcel.readString()
        transaction_supplier_order_cost = parcel.readValue(Double::class.java.classLoader) as? Double
        transaction_supplier_rent_start_date = parcel.readString()
        transaction_supplier_rent_end_date = parcel.readString()
        transaction_supplier_uid = parcel.readString()
        transaction_supplier_status = parcel.readString()
        transaction_supplier_review = parcel.readString()
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeValue(transaction_supplier_is_deliverable)
        parcel.writeValue(transaction_supplier_item_rating)
        parcel.writeString(transaction_supplier_buyer_uid)
        parcel.writeString(transaction_supplier_deliver_location)
        parcel.writeString(transaction_supplier_event_uid)
        parcel.writeString(transaction_supplier_item_uid)
        parcel.writeValue(transaction_supplier_item_count)
        parcel.writeValue(transaction_supplier_item_price)
        parcel.writeString(transaction_supplier_item_name)
        parcel.writeValue(transaction_supplier_order_cost)
        parcel.writeString(transaction_supplier_rent_start_date)
        parcel.writeString(transaction_supplier_rent_end_date)
        parcel.writeString(transaction_supplier_uid)
        parcel.writeString(transaction_supplier_status)
        parcel.writeString(transaction_supplier_review)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Transaction_Supplier> {
        override fun createFromParcel(parcel: Parcel): Transaction_Supplier {
            return Transaction_Supplier(parcel)
        }

        override fun newArray(size: Int): Array<Transaction_Supplier?> {
            return arrayOfNulls(size)
        }
    }


}