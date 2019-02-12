package com.example.morkince.okasyonv2.activities.model

class Item_Images {
    var item_images_images_url: ArrayList<String>?=null
    var item_images_item_uid: String ?= null

    constructor()

    constructor(item_images_images_url: ArrayList<String>?, item_images_item_uid: String?) {
        this.item_images_images_url = item_images_images_url
        this.item_images_item_uid = item_images_item_uid
    }
}