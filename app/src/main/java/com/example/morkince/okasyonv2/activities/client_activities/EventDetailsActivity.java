package com.example.morkince.okasyonv2.activities.client_activities;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import com.example.morkince.okasyonv2.R;

public class EventDetailsActivity extends AppCompatActivity {
    ImageView editDetails;
    EditText nameofEvent,dateofevent,addressofevent,descpitionofevent,detailsofevent;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_details);

        nameofEvent =findViewById(R.id.editText_eventdetailsNameoftheEvent);
        dateofevent =findViewById(R.id.textView_eventdetailsDateoftheEvent);
        addressofevent =findViewById(R.id.textView_eventdetailsAddressofTheEvent);
        descpitionofevent =findViewById(R.id.textView_eventdetailsDescription);
        detailsofevent =findViewById(R.id.editext_eventdetailsDetails);
        editDetails = findViewById(R.id.imageView_eventdetailsEdit);



        editDetails.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                nameofEvent.setEnabled(true);
                dateofevent.setEnabled(true);
                addressofevent.setEnabled(true);
                descpitionofevent.setEnabled(true);
                detailsofevent.setEnabled(true);

            }
        });


    }
}
