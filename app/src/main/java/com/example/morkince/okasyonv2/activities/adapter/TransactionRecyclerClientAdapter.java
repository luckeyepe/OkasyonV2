package com.example.morkince.okasyonv2.activities.adapter;

import android.content.Context;
import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RatingBar;
import android.widget.TextView;
import com.example.morkince.okasyonv2.R;
import com.example.morkince.okasyonv2.activities.model.Cart;

import java.util.ArrayList;

public class TransactionRecyclerClientAdapter extends RecyclerView.Adapter< TransactionRecyclerClientAdapter.ViewHolder> {
    private ArrayList<Cart> cart;
    private Context mContext;
    //  private StorageReference mStorageRef;

    public TransactionRecyclerClientAdapter(ArrayList<Cart> cart, Context mContext) {
        this.cart = cart;
        this.mContext = mContext;
    }

    @Override
    public TransactionRecyclerClientAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_transactionclientviewcontent, parent, false);
        TransactionRecyclerClientAdapter.ViewHolder holder = new  TransactionRecyclerClientAdapter.ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(final  TransactionRecyclerClientAdapter.ViewHolder holder, final int position) {

        holder.CartproductName.setText(cart.get(position).getStoreName());
        holder.Price.setText(cart.get(position).getPrice() + "");
        holder.Productrate.setRating(cart.get(position).getRating());
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
            CartproductName= itemView.findViewById(R.id.textView_transaction_chat);
            Price = itemView.findViewById(R.id.textView_transaction_chat3);
            Productrate = itemView.findViewById(R.id.ratingBar_storeRate2);
            parentLayout = itemView.findViewById(R.id.ParentLayout);
        }
    }
}
