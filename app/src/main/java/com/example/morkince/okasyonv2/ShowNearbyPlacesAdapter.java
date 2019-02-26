package com.example.morkince.okasyonv2;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.morkince.okasyonv2.activities.adapter.EventsAdapter;
import com.example.morkince.okasyonv2.activities.client_activities.EventDetailsActivity;
import com.example.morkince.okasyonv2.activities.client_activities.FoundEventDetailsActivity;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.kd.dynamic.calendar.generator.ImageGenerator;
import com.squareup.picasso.Picasso;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;

public class ShowNearbyPlacesAdapter extends RecyclerView.Adapter<ShowNearbyPlacesAdapter.ViewHolder>{

    private ArrayList<NearbyPlacesModel> nearbyPlacesModel;
    private Context mContext;
    private FirebaseUser user;
    private StorageReference mStorageRef;
    private String eventName,eventLocation;

    //  Calendar currentDate;
    Bitmap generatedateIcon;
    ImageGenerator imageGenerator;

    public ShowNearbyPlacesAdapter(ArrayList<NearbyPlacesModel> nearbyPlacesModel, Context mContext) {
        this.nearbyPlacesModel = nearbyPlacesModel;
        this.mContext = mContext;
    }


    @NonNull
    @Override
    public ShowNearbyPlacesAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.row_getnearbyplaces, parent, false);
        ShowNearbyPlacesAdapter.ViewHolder holder = new ShowNearbyPlacesAdapter.ViewHolder(view,mContext,nearbyPlacesModel);

        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull final ShowNearbyPlacesAdapter.ViewHolder holder, int position) {

        holder.textView_getnearbyplacesNameofTheStore.setText(nearbyPlacesModel.get(position).getNameOfStore());
        holder.textView_getnearbyplacesPlaceofTheStore.setText(nearbyPlacesModel.get(position).getVicinityOfStore());
        holder.ratingBar_getnearbyplacesRatingBar.setRating((float)nearbyPlacesModel.get(position).getStarRatingOfStore());
        Picasso.get().load(nearbyPlacesModel.get(position).getImageUrlOfStore()).error(R.mipmap.ic_launcher).into(holder.imageView_getnearbyplacesPhoto);
        holder.textView_getnearbyplacesNumberofReviews.setText(nearbyPlacesModel.get(position).getTotalNumberOfRatings());
        if(nearbyPlacesModel.get(position).getOpeningHoursOfStore().equals("true"))
        {
            holder.textView_getnearbyplacesOpeningHours.setText("OPEN NOW");
        }
        else
        {
            holder.textView_getnearbyplacesOpeningHours.setText("CLOSED");
        }

    }

    @Override
    public int getItemCount() {
        return nearbyPlacesModel.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        TextView textView_getnearbyplacesNameofTheStore;
        TextView textView_getnearbyplacesPlaceofTheStore;
        TextView textView_getnearbyplacesOpeningHours;
        TextView textView_getnearbyplacesNumberofReviews;
        ImageView imageView_getnearbyplacesPhoto;
        LinearLayout getNearbyPlacesLayout;
        ArrayList<NearbyPlacesModel> nearbyPlacesModel;
        Context mContext;
        RatingBar ratingBar_getnearbyplacesRatingBar;

        public ViewHolder(View nearbyPlacesView, Context mContext,ArrayList<NearbyPlacesModel> nearbyPlacesModel){
            super(nearbyPlacesView);
            this.nearbyPlacesModel=nearbyPlacesModel;
            this.mContext=mContext;
            nearbyPlacesView.setOnClickListener(this);

            textView_getnearbyplacesNameofTheStore = itemView.findViewById(R.id.textView_getnearbyplacesNameofTheStore);
            ratingBar_getnearbyplacesRatingBar = itemView.findViewById(R.id.ratingBar_getnearbyplacesRatingBar);
            textView_getnearbyplacesPlaceofTheStore=itemView.findViewById(R.id.textView_getnearbyplacesPlaceofTheStore);
            textView_getnearbyplacesOpeningHours=itemView.findViewById(R.id.textView_getnearbyplacesOpeningHours);
            textView_getnearbyplacesNumberofReviews=itemView.findViewById(R.id.textView_getnearbyplacesNumberofReviews);
            imageView_getnearbyplacesPhoto=itemView.findViewById(R.id.imageView_getnearbyplacesPhoto);
            getNearbyPlacesLayout=itemView.findViewById(R.id.getNearbyPlacesLayout);
        }


        @Override
        public void onClick(View view) {

        }
    }
}
