package com.example.morkince.okasyonv2.activities.client_activities;

import android.app.DatePickerDialog;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Trace;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.*;
import com.example.morkince.okasyonv2.R;
import com.kd.dynamic.calendar.generator.ImageGenerator;

import java.util.Calendar;

public class EventDetailsActivity extends AppCompatActivity {
    ImageView editDetails, calendarHandler;
    TextView numberofInterestedAttendees,numberofInterestedSponsors;
    Button buttonSave;
    EditText nameofEvent,dateofevent,addressofevent,descpitionofevent,detailsofevent;

    Calendar currentDate;
    Bitmap generatedateIcon;
    ImageGenerator imageGenerator;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_details);
         refs();

      calendarHandler.setEnabled(false);
      editDetails.setOnClickListener(edittheDetails);
      buttonSave.setOnClickListener(saveUpdatedData);
      calendarHandler.setOnClickListener(calendarHandlerDetails);

        imageGenerator = new ImageGenerator(this);


        imageGenerator.setIconSize(50, 50);
        imageGenerator.setDateSize(30);
        imageGenerator.setMonthSize(15);

        imageGenerator.setDatePosition(42);
        imageGenerator.setMonthPosition(15);

        imageGenerator.setDateColor(Color.parseColor("#D81B60"));
        imageGenerator.setMonthColor(Color.parseColor("#ffffff"));

        imageGenerator.setStorageToSDCard(true);





    }
    public View.OnClickListener edittheDetails = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            nameofEvent.setEnabled(true);
            calendarHandler.setEnabled(true);
            addressofevent.setEnabled(true);
            detailsofevent.setEnabled(true);
            descpitionofevent.setEnabled(true);
            buttonSave.setVisibility(View.VISIBLE);




        }
    };
    public View.OnClickListener saveUpdatedData = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Toast.makeText(getApplicationContext(), "Save", Toast.LENGTH_SHORT).show();
            nameofEvent.setEnabled(false);
            calendarHandler.setEnabled(false);
            addressofevent.setEnabled(false);
            detailsofevent.setEnabled(false);
            descpitionofevent.setEnabled(false);
            buttonSave.setVisibility(View.INVISIBLE);
        }
    };
    public View.OnClickListener calendarHandlerDetails = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Toast.makeText(getApplicationContext(), "hiCalendar", Toast.LENGTH_SHORT).show();

            currentDate = Calendar.getInstance();

            int yr= currentDate.get(Calendar.YEAR);
            int mon= currentDate.get(Calendar.MONTH);
            int day= currentDate.get(Calendar.DAY_OF_MONTH);

            DatePickerDialog datePickerDialog= new DatePickerDialog(EventDetailsActivity.this, new DatePickerDialog.OnDateSetListener() {
                @Override
                public void onDateSet(DatePicker view, int  selectedYear, int selectedMonth, int selectedDay ) {
                    int monthToDisplay= selectedMonth + 1;
                    dateofevent.setText(selectedDay+"-"+monthToDisplay+"-"+selectedYear);

                    currentDate.set(selectedYear,selectedMonth,selectedDay);
                    generatedateIcon= imageGenerator.generateDateImage(currentDate,R.drawable.calendar_icon);
                    calendarHandler.setImageBitmap(generatedateIcon);
                }
            }, yr, mon, day);
            datePickerDialog.show();
        }
    };
    public void refs()
    {
        nameofEvent =findViewById(R.id.editText_eventdetailsNameoftheEvent);
        dateofevent =findViewById(R.id.textView_eventdetailsDateoftheEvent);
        addressofevent =findViewById(R.id.textView_eventdetailsAddressofTheEvent);
        descpitionofevent =findViewById(R.id.textView_eventdetailsDescription);
        detailsofevent =findViewById(R.id.editext_eventdetailsDetails);
        editDetails = findViewById(R.id.imageView_eventdetailsEdit);
        calendarHandler = findViewById(R.id.imageView_calendar);
        buttonSave = findViewById(R.id.Button_saveEditEvent);
        numberofInterestedAttendees = findViewById(R.id.textView_numberofEventsInterestedAttendees);
        numberofInterestedSponsors = findViewById(R.id.textView_numberofEventsInterestedSponsor);
    }
}
