package com.example.morkince.okasyonv2;

import android.content.Context;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class View_Items_Recycler_Adapter extends RecyclerView.Adapter<View_Items_Recycler_Adapter.ViewHolder>{
    private ArrayList<Item> items;
    private Context mContext;
    private StorageReference mStorageRef;

    public View_Items_Recycler_Adapter(ArrayList<Item> items, Context mContext) {
        this.items = items;
        this.mContext = mContext;
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.cardview_item, parent, false);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull final View_Items_Recycler_Adapter.ViewHolder holder, int position) {

        mStorageRef = FirebaseStorage.getInstance().getReference().child("item_images").child(items.get(position).getItem_uid()).child(items.get(position).getItem_uid()+"1");

        mStorageRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {
                Picasso.get().load(uri.toString()).error(R.mipmap.ic_launcher).into(holder.cardviewItem_imageItem);
            }
        });


        holder.cardviewItem_itemName.setText(items.get(position).getItemName());
        holder.cardview_itemPrice.setText("P"+items.get(position).getItemPrice() + "");
        holder.cardviewItem_starRating.setText(items.get(position).getStarRating() + " Stars");
    }

    @Override
    public int getItemCount() {
        return items.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder{

        TextView cardviewItem_itemName;
        TextView cardview_itemPrice;
        TextView cardviewItem_starRating;
        ImageView cardviewItem_imageItem;
        CardView parentLayout;



        public ViewHolder(View itemView) {
            super(itemView);
            cardviewItem_itemName = itemView.findViewById(R.id.cardviewItem_itemName);
            cardview_itemPrice = itemView.findViewById(R.id.cardview_itemPrice);
            cardviewItem_starRating = itemView.findViewById(R.id.cardviewItem_starRating);
            cardviewItem_imageItem = itemView.findViewById(R.id.cardviewItem_imageItem);
            parentLayout=itemView.findViewById(R.id.parentLayout);
        }
    }

}
