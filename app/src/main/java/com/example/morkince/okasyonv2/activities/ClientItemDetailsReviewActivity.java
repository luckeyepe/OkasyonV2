package com.example.morkince.okasyonv2.activities;

import android.content.Intent;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.morkince.okasyonv2.R;
import com.example.morkince.okasyonv2.Reviews;
import com.example.morkince.okasyonv2.StoreReviewsAdapter;
import com.example.morkince.okasyonv2.activities.client_activities.ClientItemDetailActivity;
import com.example.morkince.okasyonv2.testingphase.ItemImagesModel;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

public class ClientItemDetailsReviewActivity extends AppCompatActivity {
    RecyclerView recyclerView_ClientItemDetailsReviews;
    private ArrayList<Reviews> reviews = new ArrayList<>();
    StoreReviewsAdapter adapter;
    String item_uid;
    TextView clientItemDetails_viewReviews_txtView;

    FirebaseUser user;
    FirebaseFirestore db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        RecyclerView recyclerView;
        setContentView(R.layout.activity_client_item_details_review);
        getSupportActionBar().hide();
        refs();
        Intent intent = getIntent();
        item_uid=intent.getStringExtra("item_uid");
        loadReviews();


    }


    public void loadReviews()
    {
        db = FirebaseFirestore.getInstance();
        db.collection("Item_Rating").document("item_rating").collection(item_uid).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    for (QueryDocumentSnapshot document : task.getResult()) {
                        String evaluatorName=document.getString("itemRating_reviewerName");
                        Long numStars=(Long)document.get("itemRating_starRating");
                        String reviewComment=document.getString("itemRating_review");
                        reviews.add(new Reviews(evaluatorName,numStars,reviewComment));
                    }

                    adapter = new StoreReviewsAdapter(reviews, ClientItemDetailsReviewActivity.this);
                    recyclerView_ClientItemDetailsReviews.addItemDecoration(new DividerItemDecoration(ClientItemDetailsReviewActivity.this, LinearLayoutManager.VERTICAL));
                    recyclerView_ClientItemDetailsReviews.setLayoutManager(new LinearLayoutManager(ClientItemDetailsReviewActivity.this));
                    recyclerView_ClientItemDetailsReviews.setAdapter(adapter);

                    if(reviews.isEmpty())
                    {
                        Toast.makeText(getApplicationContext(), "No Reviews to Show",
                                Toast.LENGTH_SHORT).show();
                    }

                } else {
                    Toast.makeText(getApplicationContext(), "Error in Retrieving Records!!",
                            Toast.LENGTH_SHORT).show();
                }
                clientItemDetails_viewReviews_txtView.setText("VIEW REVIEWS(" + reviews.size() + ")");
            }
        });
    }

    public void refs()
    {
        recyclerView_ClientItemDetailsReviews=findViewById(R.id.recyclerView_ClientItemDetailsReviews);
        clientItemDetails_viewReviews_txtView=findViewById(R.id.clientItemDetails_viewReviews_txtView);
    }
}
