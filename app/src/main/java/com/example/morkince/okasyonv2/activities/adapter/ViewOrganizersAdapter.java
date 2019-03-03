package com.example.morkince.okasyonv2.activities.adapter;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.ImageView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RatingBar;
import android.widget.TextView;
import com.example.morkince.okasyonv2.Events;
import com.example.morkince.okasyonv2.HireOrganizerDetails;
import com.example.morkince.okasyonv2.R;
import com.example.morkince.okasyonv2.activities.client_activities.EventDetailsActivity;
import com.example.morkince.okasyonv2.activities.client_activities.FoundEventDetailsActivity;
import com.example.morkince.okasyonv2.activities.model.Organizer;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class ViewOrganizersAdapter extends RecyclerView.Adapter<ViewOrganizersAdapter.ViewHolder> {
    private ArrayList<Organizer> Organizer;
    private Context mContext;
    private FirebaseUser user;
    //  private StorageReference mStorageRef;

    public ViewOrganizersAdapter(ArrayList<Organizer> Organizer, Context mContext) {
        this.Organizer = Organizer;
        this.mContext = mContext;
    }

    @Override
    public ViewOrganizersAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_vieworganizercontent_organizer, parent, false);
        ViewOrganizersAdapter.ViewHolder holder = new ViewOrganizersAdapter.ViewHolder(view,mContext,Organizer);
        return holder;
    }

    @Override
    public void onBindViewHolder(final ViewOrganizersAdapter.ViewHolder holder, final int position) {

        holder.Organizername.setText(Organizer.get(position).getOrganizerName());
        holder.Location.setText(Organizer.get(position).getLocation());
        Picasso.get().load(Organizer.get(position).getImageuri()).error(R.drawable.default_avata).into( holder.imageView_OrganizerImage);


    }

    @Override
    public int getItemCount () {
        return Organizer.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        TextView Organizername;
        TextView Location;
        TextView Price;
        ImageView imageView_OrganizerImage;
        RatingBar Organizerrate;
        ConstraintLayout parentLayout;

        private ArrayList<Organizer> Organizer;
        private Context mContext;

        public ViewHolder(View itemView,Context mContext,ArrayList<Organizer> Organizer) {
            super(itemView);
            this.Organizer=Organizer;
            this.mContext=mContext;
            itemView.setOnClickListener(this);


            Organizername= itemView.findViewById(R.id.textView_OrganizerName);
            Location = itemView.findViewById(R.id.textView_Location);
            Price = itemView.findViewById(R.id.textView_Organizerprize);
            Organizerrate = itemView.findViewById(R.id.RatingBar_Organizerrate);
            imageView_OrganizerImage = itemView.findViewById(R.id.imageView_OrganizerImage);
            parentLayout = itemView.findViewById(R.id.ParentLayout);
        }


        @Override
        public void onClick(View view) {
            int position = getAdapterPosition();
            user = FirebaseAuth.getInstance().getCurrentUser();
            Intent intent = new Intent(mContext,HireOrganizerDetails.class);
            intent.putExtra("user_uid",Organizer.get(position).getUser_uid());
            intent.putExtra("OrganizerName",Organizer.get(position).getOrganizerName());
            intent.putExtra("Location",Organizer.get(position).getLocation());
            intent.putExtra("image_url",Organizer.get(position).getImageuri());
            intent.putExtra("eventCategory", Organizer.get(position).getEventCategory());
            intent.putExtra("price", Organizer.get(position).getPrice());
            intent.putExtra("organizerDetails", Organizer.get(position).getOrganizerDetails());
            mContext.startActivity(intent);


        }
    }
}