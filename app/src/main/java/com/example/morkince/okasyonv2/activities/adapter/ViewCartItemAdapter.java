package com.example.morkince.okasyonv2.activities.adapter;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;
import com.example.morkince.okasyonv2.R;
import com.example.morkince.okasyonv2.activities.Homepage_organizer_activities.CartItemDetailsActivity;
import com.example.morkince.okasyonv2.activities.model.CartItem;

import java.util.ArrayList;

public class ViewCartItemAdapter extends RecyclerView.Adapter<ViewCartItemAdapter.ViewHolder>{
    private ArrayList<CartItem> cartitem;
    private Context mContext;
    //  private StorageReference mStorageRef;

    public ViewCartItemAdapter(ArrayList<CartItem> cartitem, Context mContext) {
        this.cartitem = cartitem;
        this.mContext = mContext;
        Log.e("CART"," LOLAA" );
    }

    @Override
    public ViewCartItemAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_viewcartcontent_client, parent, false);
        ViewCartItemAdapter.ViewHolder holder = new ViewCartItemAdapter.ViewHolder(view, mContext,cartitem);
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


    public class ViewHolder extends RecyclerView.ViewHolder implements  View.OnClickListener {

        TextView CartproductName2;
        TextView Price2;
        RatingBar Productrate2;
        ConstraintLayout parentLayout2;
        private ArrayList<CartItem> cartitem;
        private Context mContext;

        public ViewHolder(View itemView,Context mContext, ArrayList<CartItem> cartitem) {
            super(itemView);
            this.cartitem = cartitem;
            this.mContext = mContext;
            itemView.setOnClickListener(this);
            CartproductName2= itemView.findViewById(R.id.textView_ItemName3);
            Price2 = itemView.findViewById(R.id.textView_ItemPrice2);
            Productrate2 = itemView.findViewById(R.id.ratingBar_storeRate);
            parentLayout2 = itemView.findViewById(R.id.ParentLayout);
        }
        @Override
        public void onClick(View view) {
            int position = getAdapterPosition();
            Intent intent = new Intent(mContext, CartItemDetailsActivity.class);
            intent.putExtra("Cart_item_id",cartitem.get(position).getCart_item_id());
            intent.putExtra("Cart_item_group_id",cartitem.get(position).getCart_item_group_uid());
            intent.putExtra("Cart_item_item_id",cartitem.get(position).getCart_item_item_uid());
            this.mContext.startActivity(intent);
        }
    }
}
