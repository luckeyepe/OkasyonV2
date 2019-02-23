package com.example.morkince.okasyonv2.activities.adapter;

import android.content.Context;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RatingBar;
import android.widget.TextView;
import com.example.morkince.okasyonv2.R;
import com.example.morkince.okasyonv2.activities.model.Cartv1;

import java.util.ArrayList;

public class TransactionRecyclerClientAdapter extends RecyclerView.Adapter< TransactionRecyclerClientAdapter.ViewHolder> {
    private ArrayList<Cartv1> cart;
    private Context mContext;
    //  private StorageReference mStorageRef;

    public TransactionRecyclerClientAdapter(ArrayList<Cartv1> cart, Context mContext) {
        this.cart = cart;
        this.mContext = mContext;
    }

    @Override
    public TransactionRecyclerClientAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_transaction_client, parent, false);
        TransactionRecyclerClientAdapter.ViewHolder holder = new  TransactionRecyclerClientAdapter.ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(final  TransactionRecyclerClientAdapter.ViewHolder holder, final int position) {

        holder.CartproductName.setText(cart.get(position).getItemName());
        holder.Price.setText(cart.get(position).getCart_item_order_cost() + "");
        holder.Productrate.setRating(cart.get(position).getCartItem_item_Rating());
    }
    @Override
    public int getItemCount () {
        return cart.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView CartproductName;
        TextView Price;
        RatingBar Productrate;
        ConstraintLayout parentLayout;

        public ViewHolder(View itemView) {
            super(itemView);
            CartproductName= itemView.findViewById(R.id.textView_transactionClientRowItemName);
            Price = itemView.findViewById(R.id.textView_transactionClientRowCost);
            Productrate = itemView.findViewById(R.id.ratingBar_transactionClientRow);
            parentLayout = itemView.findViewById(R.id.ParentLayout);
        }
    }
}
