package com.example.morkince.okasyonv2;

import android.net.Uri;

public class Images {
    String name;
    Uri imageURI;

    public Images(){}

    public Images(String name, Uri imageURI) {
        this.name = name;
        this.imageURI = imageURI;
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Uri getImageURI() {
        return imageURI;
    }

    public void setImageURI(Uri imageURI) {
        this.imageURI = imageURI;
    }
}
