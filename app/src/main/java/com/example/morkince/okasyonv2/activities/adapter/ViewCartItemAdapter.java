package com.example.morkince.okasyonv2.activities.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RatingBar;
import android.widget.TextView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;
import com.example.morkince.okasyonv2.R;
import com.example.morkince.okasyonv2.activities.model.Cart_Item;

import java.util.ArrayList;

public class ViewCartItemAdapter extends RecyclerView.Adapter<ViewCartItemAdapter.ViewHolder>{
    private ArrayList<Cart_Item> cartitem;
    private Context mContext;
    //  private StorageReference mStorageRef;

    public ViewCartItemAdapter(ArrayList<Cart_Item> cartitem, Context mContext) {
        this.cartitem = cartitem;
        this.mContext = mContext;
    }

    @Override
    public ViewCartItemAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_viewcartcontent_client, parent, false);
        ViewCartItemAdapter.ViewHolder holder = new ViewCartItemAdapter.ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(final ViewCartItemAdapter.ViewHolder holder, final int position) {

        holder.CartproductName2.setText(cartitem.get(position).getcart_item_name());
        holder.Price2.setText(cartitem.get(position).getcart_item_order_cost() + "");
        holder.Productrate2.setRating(cartitem.get(position).getcart_item_Rating());
    }
    @Override
    public int getItemCount () {
        return cartitem.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView CartproductName2;
        TextView Price2;
        RatingBar Productrate2;
        ConstraintLayout parentLayout2;

        public ViewHolder(View itemView) {
            super(itemView);
            CartproductName2= itemView.findViewById(R.id.textView_ItemName3);
            Price2 = itemView.findViewById(R.id.textView_ItemPrice2);
            Productrate2 = itemView.findViewById(R.id.ratingBar_storeRate);
            parentLayout2 = itemView.findViewById(R.id.ParentLayout);
        }
    }
}
