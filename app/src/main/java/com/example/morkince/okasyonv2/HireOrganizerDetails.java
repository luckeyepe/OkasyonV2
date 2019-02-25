package com.example.morkince.okasyonv2;

import android.content.Intent;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.recyclerview.widget.RecyclerView;
import com.squareup.picasso.Picasso;

public class HireOrganizerDetails extends AppCompatActivity {
    ImageView imageView_hireOrganizerPhoto;
    TextView textView_hireOrganizerName,textView_hireOrganizerLocation,textView_hireOrganizerDetails;
    RecyclerView recyclerView_hireOrganizerReview;
    RatingBar ratingBar_hireOrganizerRate;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hire_organizer_details);
        refs();
        getSupportActionBar().setTitle("Organizer Details");


        Intent intent= getIntent();
        String OrganizerName = intent.getStringExtra("OrganizerName");
        String user_uid = intent.getStringExtra("user_uid");
        String image_url = intent.getStringExtra("image_url");
        String Location = intent.getStringExtra("Location");

        textView_hireOrganizerName.setText(OrganizerName);
        textView_hireOrganizerLocation.setText(Location);
        Picasso.get().load(image_url).error(R.drawable.default_avata).into(imageView_hireOrganizerPhoto);

    }

    public void refs()
    {
        imageView_hireOrganizerPhoto = findViewById(R.id.imageView_hireOrganizerPhoto);
        textView_hireOrganizerName = findViewById(R.id.textView_hireOrganizerName);
        textView_hireOrganizerLocation = findViewById(R.id.textView_hireOrganizerLocation);
        textView_hireOrganizerDetails = findViewById(R.id.textView_hireOrganizerDetails);
        recyclerView_hireOrganizerReview = findViewById(R.id.recyclerView_hireOrganizerReview);
        ratingBar_hireOrganizerRate = findViewById(R.id.ratingBar_hireOrganizerRate);
    }
    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

}



