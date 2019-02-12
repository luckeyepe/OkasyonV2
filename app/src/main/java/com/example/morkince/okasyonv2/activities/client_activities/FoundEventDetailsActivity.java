package com.example.morkince.okasyonv2.activities.client_activities;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import com.example.morkince.okasyonv2.Events;
import com.example.morkince.okasyonv2.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.kd.dynamic.calendar.generator.ImageGenerator;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class FoundEventDetailsActivity extends AppCompatActivity {
    FirebaseUser user;
    FirebaseFirestore db;
    String event_id;
    Events event;

    Bitmap generatedateIcon;
    ImageGenerator imageGenerator;

    ImageView eventdetailsimage,eventdetailsmessage,foundEventDetails_dateCalendar;
    TextView nameofevent,dateevent,addressevent,descriptionofevent;
    EditText eventdetailsdesc;
    Button sponsor,attend;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_found_event_details);
        Intent intent = getIntent();
        event_id= intent.getStringExtra("event_id");
        refs();
        getEventDetails();

        sponsor.setOnClickListener(sponsorEvent);
        attend.setOnClickListener(attendEvent);

    }



    public View.OnClickListener sponsorEvent = new View.OnClickListener() {
        @Override
        public void onClick(View view) {

        }
    };

    public View.OnClickListener attendEvent = new View.OnClickListener() {
        @Override
        public void onClick(View view) {

        }
    };

    public void getEventDetails(){
        db = FirebaseFirestore.getInstance();
        db.collection("Event").document(event_id).get()
                .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {

                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    if (document.exists()) {

                        event=document.toObject(Events.class);
                        nameofevent.setText(event.getEvent_name());
                        addressevent.setText(event.getEvent_location());
                        descriptionofevent.setText(event.getEvent_description());
                        eventdetailsdesc.setText(event.getEvent_tags());

                        Calendar currentDate = Calendar.getInstance();
                        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy hh:mm:ss", Locale.ENGLISH);
                        try {
                            currentDate.setTime(sdf.parse(event.getEvent_date()));// all done
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }

                        // .set(selectedYear,selectedMonth,selectedDay);
                        imageGenerator = new ImageGenerator(FoundEventDetailsActivity.this);

                        imageGenerator.setIconSize(100, 100);
                        imageGenerator.setDateSize(40);
                        imageGenerator.setMonthSize(20);

                        imageGenerator.setDatePosition(80);
                        imageGenerator.setMonthPosition(24);

                        imageGenerator.setDateColor(Color.parseColor("#D81B60"));
                        imageGenerator.setMonthColor(Color.parseColor("#ffffff"));

                        imageGenerator.setStorageToSDCard(true);

                        generatedateIcon= imageGenerator.generateDateImage(currentDate,R.drawable.calendar_icon);
                        foundEventDetails_dateCalendar.setImageBitmap(generatedateIcon);

                    } else {
                        Log.d("", "No such document exist");
                    }
                } else {
                    Log.d("", "Failed with ", task.getException());
                }
            }
        });

    }

    public void refs()
    {
        eventdetailsimage=findViewById(R.id.imageView_eventdetailsImage);
        eventdetailsmessage=findViewById(R.id.imageView_eventdetailsMessage);
        nameofevent=findViewById(R.id.textView_eventdetailsNameoftheEvent);
        dateevent=findViewById(R.id.textView_eventdetailsDateoftheEvent);
        addressevent=findViewById(R.id.textView_foundeventdetailsAddressofTheEvent);
        descriptionofevent=findViewById(R.id.textView_foundeventdetailsDescription);
        eventdetailsdesc=findViewById(R.id.editext_eventdetailsDetails);
        sponsor=findViewById(R.id.button_foundEventSponsorButton);
        attend=findViewById(R.id.button_foundEventAttendButton);
        foundEventDetails_dateCalendar=findViewById(R.id.foundEventDetails_dateCalendar);
    }
}
