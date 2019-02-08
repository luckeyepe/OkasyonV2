package com.example.morkince.okasyonv2;

import android.content.Context;
import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;

import java.util.ArrayList;

public class StoreReviewsAdapter  extends RecyclerView.Adapter<StoreReviewsAdapter.ViewHolder>{
    private ArrayList<Reviews> reviews;
    private Context mContext;

    public StoreReviewsAdapter(ArrayList<Reviews> reviews, Context mContext) {
        this.reviews = reviews;
        this.mContext = mContext;
    }


    @NonNull
    @Override
    public StoreReviewsAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.rows_content_reviews, parent, false);
        StoreReviewsAdapter.ViewHolder holder = new StoreReviewsAdapter.ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull StoreReviewsAdapter.ViewHolder holder, int position) {
        holder.reviews_reviewerName.setText(reviews.get(position).getEvaluatorName());
        holder.reviews_reviewComments.setText(reviews.get(position).getReviewComment() + "");
        holder.reviews_reviewStars.setRating((reviews.get(position).getNumStars()));
    }

    @Override
    public int getItemCount() {
        return reviews.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder{

        TextView reviews_reviewerName;
        EditText reviews_reviewComments;
        RatingBar reviews_reviewStars;
        ConstraintLayout parentLayoutStoreReviews;



        public ViewHolder(View itemView) {
            super(itemView);
            reviews_reviewerName = itemView.findViewById(R.id.reviews_reviewerName);
            reviews_reviewComments = itemView.findViewById(R.id.reviews_reviewComments);
            reviews_reviewStars = itemView.findViewById(R.id.reviews_reviewStars);
            parentLayoutStoreReviews=itemView.findViewById(R.id.parentLayoutStoreReviews);
        }
    }
}
