package com.example.morkince.okasyonv2;

import android.content.Intent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.recyclerview.widget.RecyclerView;
import com.example.morkince.okasyonv2.activities.client_activities.Client_Create_Event;
import com.squareup.picasso.Picasso;

public class HireOrganizerDetails extends AppCompatActivity {
    ImageView imageView_hireOrganizerPhoto;
    TextView textView_hireOrganizerName,textView_hireOrganizerLocation,textView_hireOrganizerDetails, textView_hireOrganizerDetailPrice;
    RecyclerView recyclerView_hireOrganizerReview;
    RatingBar ratingBar_hireOrganizerRate;
    Button button_hireOrganizerDetailsHire;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hire_organizer_details);
        refs();
        getSupportActionBar().setTitle("Organizer Details");


        final Intent intent= getIntent();
        String OrganizerName = intent.getStringExtra("OrganizerName");
        final String user_uid = intent.getStringExtra("user_uid");
        String image_url = intent.getStringExtra("image_url");
        String Location = intent.getStringExtra("Location");
        final String eventCategory = intent.getStringExtra("eventCategory");
        Double price = intent.getDoubleExtra("price", 0.0);
        String organizerDetails = intent.getStringExtra("organizerDetails");

        textView_hireOrganizerName.setText(OrganizerName);
        textView_hireOrganizerDetailPrice.setText("P"+price);
        textView_hireOrganizerLocation.setText(Location);
        textView_hireOrganizerDetails.setText(organizerDetails);
        Picasso.get().load(image_url).error(R.drawable.default_avata).into(imageView_hireOrganizerPhoto);


        button_hireOrganizerDetailsHire.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent1 = new Intent(HireOrganizerDetails.this, Client_Create_Event.class);
                intent1.putExtra("organizerUid", user_uid);
                intent1.putExtra("event_category", eventCategory);
                startActivity(intent1);
            }
        });
    }



    public void refs()
    {
        imageView_hireOrganizerPhoto = findViewById(R.id.imageView_hireOrganizerPhoto);
        textView_hireOrganizerName = findViewById(R.id.textView_hireOrganizerName);
        textView_hireOrganizerLocation = findViewById(R.id.textView_hireOrganizerLocation);
        textView_hireOrganizerDetails = findViewById(R.id.textView_hireOrganizerDetails);
        recyclerView_hireOrganizerReview = findViewById(R.id.recyclerView_hireOrganizerReview);
        ratingBar_hireOrganizerRate = findViewById(R.id.ratingBar_hireOrganizerRate);
        button_hireOrganizerDetailsHire = findViewById(R.id.button_hireOrganizerDetailsHire);
        textView_hireOrganizerDetailPrice = findViewById(R.id.textView_hireOrganizerDetailPrice);
    }
    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

}



