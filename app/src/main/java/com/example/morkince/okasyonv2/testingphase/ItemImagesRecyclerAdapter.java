package com.example.morkince.okasyonv2.testingphase;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;
import com.example.morkince.okasyonv2.R;
import com.example.morkince.okasyonv2.View_Items_Recycler_Adapter;
import com.example.morkince.okasyonv2.activities.chat_activities.MaxImageActivity;
import com.example.morkince.okasyonv2.activities.model.Item;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class ItemImagesRecyclerAdapter extends RecyclerView.Adapter<ItemImagesRecyclerAdapter.ViewHolder>{
    private ArrayList<ItemImagesModel> itemImages;
    private Context mContext;
    private StorageReference mStorageRef;


    public ItemImagesRecyclerAdapter(ArrayList<ItemImagesModel> itemImages, Context mContext) {
        this.itemImages = itemImages;
        this.mContext = mContext;
    }


    @NonNull
    @Override
    public ItemImagesRecyclerAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_testing_image_slider_imagehandlers, parent, false);
        ItemImagesRecyclerAdapter.ViewHolder holder = new ItemImagesRecyclerAdapter.ViewHolder(view,mContext,itemImages);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull final ItemImagesRecyclerAdapter.ViewHolder holder, final int position) {

       Picasso.get().load(itemImages.get(position).getImage().toString()).into(holder.imageViewforImages);

        holder.imageViewforImages.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, MaxImageActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                intent.putExtra("sourceUrl", itemImages.get(position).getImage().toString());
                mContext.startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return itemImages.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        ImageView imageViewforImages;
        ArrayList<ItemImagesModel> itemImages;
        Context mContext;

        public ViewHolder(View itemView, Context mContext,ArrayList<ItemImagesModel> itemImages){
            super(itemView);
            this.itemImages=itemImages;
            this.mContext=mContext;
            itemView.setOnClickListener(this);
            imageViewforImages = itemView.findViewById(R.id.imageViewforImages);

        }


        @Override
        public void onClick(View view) {



        }
    }

}


