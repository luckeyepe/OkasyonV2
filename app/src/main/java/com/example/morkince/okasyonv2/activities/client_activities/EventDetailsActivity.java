package com.example.morkince.okasyonv2.activities.client_activities;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.net.Uri;
import android.provider.MediaStore;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.*;
import com.example.morkince.okasyonv2.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.kd.dynamic.calendar.generator.ImageGenerator;

import java.io.IOException;
import java.util.Calendar;

public class EventDetailsActivity extends AppCompatActivity {
    ImageView editDetails, calendarHandler,imageView_eventdetailsImage;
    TextView numberofInterestedAttendees,numberofInterestedSponsors;
    Button buttonSave;
    String event_id;
    EditText nameofEvent,dateofevent,addressofevent,descpitionofevent,detailsofevent;

    Calendar currentDate;
    Bitmap generatedateIcon;
    ImageGenerator imageGenerator;

    private static final int PICK_IMAGE = 100;
    private Uri filePath=null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_details);
        refs();
        enable(false);
        Intent intent = getIntent();
        event_id= intent.getStringExtra("event_id");

        calendarHandler.setEnabled(false);
        editDetails.setOnClickListener(edittheDetails);
        buttonSave.setOnClickListener(saveUpdatedData);
        calendarHandler.setOnClickListener(calendarHandlerDetails);
        imageView_eventdetailsImage.setOnClickListener(pickEventImage);

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

    public View.OnClickListener pickEventImage = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            openGallery();
        }
    };

    public void openGallery()
    {
        Intent gallery = new Intent(Intent.ACTION_PICK,MediaStore.Images.Media.INTERNAL_CONTENT_URI);
        startActivityForResult(gallery,PICK_IMAGE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == PICK_IMAGE && resultCode == RESULT_OK
                && data != null && data.getData() != null ) {
            filePath = data.getData();
            try {
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), filePath);
                imageView_eventdetailsImage.setImageBitmap(bitmap);
                uploadImage(event_id);
            }
            catch (IOException e)
            {
                e.printStackTrace();
            }
        }
    }

    public void uploadImage(String txtid){
        if(filePath != null)
        {
            Log.e("THIS IS THE USER UID", txtid);

            final ProgressDialog progressDialog = new ProgressDialog(EventDetailsActivity.this);
            progressDialog.setTitle("Uploading...");
            progressDialog.show();
            progressDialog.setCancelable(false);

            StorageReference ref = FirebaseStorage
                    .getInstance()
                    .getReference()
                    .child("images")
                    .child(txtid);

            ref.putFile(filePath)
                    .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                            progressDialog.dismiss();
                            showAlert("Successfully Updated Image!","Success");

                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            progressDialog.dismiss();
                            showAlert("An Error Occured","ERROR");


                        }
                    })
                    .addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {
                            double progress = (100.0*taskSnapshot.getBytesTransferred()/taskSnapshot
                                    .getTotalByteCount());
                            progressDialog.setMessage("Uploading : "+(int)progress+"%");
                        }
                    });
        }

    }

    public void showAlert(String Message,String label)
    {
        //set alert for executing the task
        AlertDialog.Builder alert = new AlertDialog.Builder(EventDetailsActivity.this);
        alert.setTitle(""+label);
        alert.setMessage(""+Message);

        alert.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            public void onClick (DialogInterface dialog, int id){
                dialog.cancel();
            }
        });

        Dialog dialog = alert.create();
        //  dialog.getWindow().setType(WindowManager.LayoutParams.TYPE_SYSTEM_ALERT);
        dialog.show();
    }


    public View.OnClickListener edittheDetails = new View.OnClickListener() {
        @Override
        public void onClick(View v) {

            enable(true);
            buttonSave.setVisibility(View.VISIBLE);




        }
    };

    public void enable(boolean status)
    {
        nameofEvent.setEnabled(status);
        calendarHandler.setEnabled(status);
        addressofevent.setEnabled(status);
        detailsofevent.setEnabled(status);
        descpitionofevent.setEnabled(status);
        imageView_eventdetailsImage.setEnabled(status);

    }

    public View.OnClickListener saveUpdatedData = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Toast.makeText(getApplicationContext(), "Save", Toast.LENGTH_SHORT).show();
            enable(false);
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
        imageView_eventdetailsImage = findViewById(R.id.imageView_eventdetailsImage);
    }
}