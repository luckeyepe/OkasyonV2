package com.example.morkince.okasyonv2.activities;

import android.os.Bundle;
import android.os.PersistableBundle;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;
import com.example.morkince.okasyonv2.R;

public class HireOrganizerDetails extends AppCompatActivity {
    ImageView imageView_hireOrganizerPhoto;
    TextView textView_hireOrganizerName,textView_hireOrganizerLocation,textView_hireOrganizerDetails;
    RecyclerView recyclerView_hireOrganizerReview;
    RatingBar ratingBar_hireOrganizerRate;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hire_organizer_details);
        getSupportActionBar().setTitle("Organizer Details");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        refs();
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
