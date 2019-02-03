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
import com.example.morkince.okasyonv2.activities.model.Organizer;

import java.util.ArrayList;

public class ViewOrganizersAdapter extends RecyclerView.Adapter<ViewOrganizersAdapter.ViewHolder> {
    private ArrayList<Organizer> Organizer;
    private Context mContext;
    //  private StorageReference mStorageRef;

    public ViewOrganizersAdapter(ArrayList<Organizer> Organizer, Context mContext) {
        this.Organizer = Organizer;
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

        holder.Organizername.setText(Organizer.get(position).getStoreName());
        holder.Location.setText(Organizer.get(position).getLocation());
        holder.Price.setText(Organizer.get(position).getPrice() + "");
        holder.Organizerrate.setRating(Organizer.get(position).getRating());
    }
        @Override
        public int getItemCount () {
            return Organizer.size();
        }


        public class ViewHolder extends RecyclerView.ViewHolder {

            TextView Organizername;
            TextView Location;
            TextView Price;
            RatingBar Organizerrate;
            ConstraintLayout parentLayout;

            public ViewHolder(View itemView) {
                super(itemView);
                Organizername= itemView.findViewById(R.id.textView_OrganizerName);
                Location = itemView.findViewById(R.id.textView_Location);
                Price = itemView.findViewById(R.id.textView_Organizerprize);
               Organizerrate = itemView.findViewById(R.id.RatingBar_Organizerrate);
                parentLayout = itemView.findViewById(R.id.ParentLayout);
            }
        }
    }

