package com.example.morkince.okasyonv2;

import android.content.Context;
import android.media.Image;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class CustomAdapter extends BaseAdapter {
    Context c;
    ArrayList<Images> images;

    public CustomAdapter(Context c, ArrayList<Images> images) {
        this.c = c;
        this.images = images;
    }

    @Override
    public int getCount() {
        return images.size();
    }

    @Override
    public Object getItem(int i) {
        return images.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        if(view==null)
        {
            view = LayoutInflater.from(c).inflate(R.layout.model_selected_images,viewGroup,false);
        }

        final Images imageSelected = (Images) this.getItem(i);
        ImageView imageHolder = view.findViewById(R.id.selectMultipleImages_image);

        Picasso.get().load(imageSelected.getImageURI()).error(R.mipmap.ic_launcher).into(imageHolder);
        return view;
    }
}
