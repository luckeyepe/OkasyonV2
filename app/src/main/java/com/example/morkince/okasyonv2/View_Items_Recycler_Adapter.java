package com.example.morkince.okasyonv2;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import com.example.morkince.okasyonv2.activities.client_activities.ClientItemDetailActivity;
import com.example.morkince.okasyonv2.activities.homepage_supplier_activities.SupplierEditItemDetailsActivity;
import com.example.morkince.okasyonv2.activities.model.Item;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;



public class View_Items_Recycler_Adapter extends RecyclerView.Adapter<View_Items_Recycler_Adapter.ViewHolder>{
    private ArrayList<Item> items;
    private Context mContext;
    private String user_role;
    private StorageReference mStorageRef;
    String item_uid="",item_name="",star_rating="",item_price="",item_category="",item_description="",isForSale="",isPerSquareUnit="",price_description="",store_id="";


    public View_Items_Recycler_Adapter(ArrayList<Item> items, Context mContext, String user_role) {
        this.items = items;
        this.mContext = mContext;
        this.user_role = user_role;
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.cardview_item, parent, false);
        ViewHolder holder = new ViewHolder(view,mContext,items);
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


        item_name=items.get(position).getItem_name();
        item_price=items.get(position).getItem_price() + "";
        star_rating=items.get(position).getItem_average_rating()+"";


        holder.cardviewItem_itemName.setText(item_name);
        holder.cardview_itemPrice.setText(item_price);
        holder.cardviewItem_starRating.setText(star_rating);

    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        TextView cardviewItem_itemName;
        TextView cardview_itemPrice;
        TextView cardviewItem_starRating;
        ImageView cardviewItem_imageItem;
        CardView parentLayout_Item;
        ArrayList<Item> items;
        Context mContext;

        public ViewHolder(View itemView, Context mContext,ArrayList<Item> items){
            super(itemView);
            this.items=items;
            this.mContext=mContext;
            itemView.setOnClickListener(this);
            cardviewItem_itemName = itemView.findViewById(R.id.cardviewItem_itemName);
            cardview_itemPrice = itemView.findViewById(R.id.cardview_itemPrice);
            cardviewItem_starRating = itemView.findViewById(R.id.cardviewItem_starRating);
            cardviewItem_imageItem = itemView.findViewById(R.id.cardviewItem_imageItem);
            parentLayout_Item=itemView.findViewById(R.id.parentLayout_Item);
        }


        @Override
        public void onClick(View view) {
        int position = getAdapterPosition();
            Item item = this.items.get(position);
            item_uid = item.getItem_uid();
            item_name = item.getItem_name();
            item_price = item.getItem_price() + "";
            star_rating = item.getItem_average_rating() + "";
            item_category = item.getItem_category_id() + "";
            item_description = item.getItem_description() + "";
            isForSale = item.isItem_for_sale() + "";
            isPerSquareUnit = item.isItem_is_per_sqr_unit_of_measurement() + "";
            price_description = item.getItem_price_description() + "";
            store_id = item.getItem_store_id() + "";

        if(user_role.equals("Supplier")) {


            Intent intent = new Intent(mContext, SupplierEditItemDetailsActivity.class);
            intent.putExtra("item_uid", item_uid);
            intent.putExtra("item_name", item_name);
            intent.putExtra("item_price", item_price);
            intent.putExtra("star_rating", star_rating);
            intent.putExtra("item_category", item_category);
            intent.putExtra("item_description", item_description);
            intent.putExtra("isForSale", isForSale);
            intent.putExtra("isPerSquareUnit", isPerSquareUnit);
            intent.putExtra("price_description", price_description);
            intent.putExtra("store_id", store_id);
            this.mContext.startActivity(intent);
        }
        else
            {
                Intent intent = new Intent(mContext, ClientItemDetailActivity.class);
                intent.putExtra("user_role",user_role);
                intent.putExtra("item_uid", item_uid);
                this.mContext.startActivity(intent);
            }

        }
    }
}
