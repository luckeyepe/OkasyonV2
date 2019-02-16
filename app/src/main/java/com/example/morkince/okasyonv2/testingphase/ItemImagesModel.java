package com.example.morkince.okasyonv2.testingphase;

import android.net.Uri;

public class ItemImagesModel {

    Uri image;

    public ItemImagesModel(Uri image) {
        this.image = image;
    }

    public ItemImagesModel(){}

    public Uri getImage() {
        return image;
    }

    public void setImage(Uri image) {
        this.image = image;
    }
}
