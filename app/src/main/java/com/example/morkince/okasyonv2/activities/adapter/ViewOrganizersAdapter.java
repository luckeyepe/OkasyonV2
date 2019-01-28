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
import com.example.morkince.okasyonv2.activities.model.Store;

import java.util.ArrayList;

public class ViewOrganizersAdapter extends RecyclerView.Adapter<ViewOrganizersAdapter.ViewHolder> {
    private ArrayList<Store> store;
    private Context mContext;
    //  private StorageReference mStorageRef;

    public ViewOrganizersAdapter(ArrayList<Store> store, Context mContext) {
        this.store = store;
        this.mContext = mContext;
    }

    @Override
    public ViewOrganizersAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_vieworganizercontent_organizer, parent, false);
        ViewOrganizersAdapter.ViewHolder holder = new ViewOrganizersAdapter.ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(final ViewOrganizersAdapter.ViewHolder holder, final int position) {

        holder.Storetitle.setText(store.get(position).getStoreName());
        holder.Location.setText(store.get(position).getLocation());
        holder.Price.setText(store.get(position).getPrice() + "");
        holder.storerate.setRating(store.get(position).getRating());
    }
        @Override
        public int getItemCount () {
            return store.size();
        }


        public class ViewHolder extends RecyclerView.ViewHolder {

            TextView Storetitle;
            TextView Location;
            TextView Price;
            RatingBar storerate;
            ConstraintLayout parentLayout;

            public ViewHolder(View itemView) {
                super(itemView);
                Storetitle = itemView.findViewById(R.id.textView_storeTitle);
                Location = itemView.findViewById(R.id.textView_storeLocation);
                Price = itemView.findViewById(R.id.textView_ItemPrice);
                storerate = itemView.findViewById(R.id.RatingBar_storeRate);
                parentLayout = itemView.findViewById(R.id.ParentLayout);
            }
        }
    }

