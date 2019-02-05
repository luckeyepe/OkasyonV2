package com.example.morkince.okasyonv2.activities.client_activities;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import com.example.morkince.okasyonv2.R;
import com.kd.dynamic.calendar.generator.ImageGenerator;

import java.util.Calendar;

public class Client_Create_Event extends AppCompatActivity {

    EditText  textInputEditText_clientCreateEventDate;
    ImageView holder;
    Button button_CreateEvent;

    Calendar currentDate;
    Bitmap generatedateIcon;
    ImageGenerator imageGenerator;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_client_create_event);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Create Event");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        textInputEditText_clientCreateEventDate = findViewById(R.id.textInputEditText_clientCreateEventDate);
        textInputEditText_clientCreateEventDate.setEnabled(false);


        imageGenerator = new ImageGenerator(this);
        holder = findViewById(R.id.calendar_holder);

        imageGenerator.setIconSize(100, 100);
        imageGenerator.setDateSize(40);
        imageGenerator.setMonthSize(20);

        imageGenerator.setDatePosition(80);
        imageGenerator.setMonthPosition(24);

        imageGenerator.setDateColor(Color.parseColor("#D81B60"));
        imageGenerator.setMonthColor(Color.parseColor("#ffffff"));

        imageGenerator.setStorageToSDCard(true);

        holder.setOnClickListener(CalendarHolderView);

        //        button_CreateEvent.setOnClickListener(createEvent);
    }

//    public View.OnClickListener createEvent = new View.OnClickListener() {
//        @Override
//        public void onClick(View view) {
//          Intent intent = new Intent(Client_Create_Event.this , Client_Set_Preference.class);
//          startActivity(intent);
//        }
//    };

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater mi = getMenuInflater();
        mi.inflate(R.menu.menu_for_toolbar, menu);
        return super.onCreateOptionsMenu(menu);
    }



    public View.OnClickListener CalendarHolderView = new View.OnClickListener() {
        @Override
        public void onClick(View v) {

            currentDate = Calendar.getInstance();

            int yr= currentDate.get(Calendar.YEAR);
            int mon= currentDate.get(Calendar.MONTH);
            int day= currentDate.get(Calendar.DAY_OF_MONTH);

            DatePickerDialog datePickerDialog= new DatePickerDialog(Client_Create_Event.this, new DatePickerDialog.OnDateSetListener() {
                @Override
                public void onDateSet(DatePicker view, int  selectedYear, int selectedMonth, int selectedDay ) {
                   int monthToDisplay= selectedMonth + 1;
                    textInputEditText_clientCreateEventDate.setText(selectedDay+"-"+monthToDisplay+"-"+selectedYear);

                    currentDate.set(selectedYear,selectedMonth,selectedDay);
                    generatedateIcon= imageGenerator.generateDateImage(currentDate,R.drawable.calendar_icon);
                    holder.setImageBitmap(generatedateIcon);
                }
            }, yr, mon, day);
            datePickerDialog.show();

        }

    };

}
