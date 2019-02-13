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
import android.os.Bundle;
import android.provider.MediaStore;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import androidx.appcompat.widget.Toolbar;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.View;
import android.widget.*;
import com.example.morkince.okasyonv2.Events;
import com.example.morkince.okasyonv2.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.kd.dynamic.calendar.generator.ImageGenerator;

import java.io.IOException;
import java.util.Calendar;
import java.util.Locale;

public class Client_Create_Event extends AppCompatActivity {

    EditText  textInputEditText_clientCreateEventDate;
    ImageView holder,clientCreateEvent_ImageView;
    Button button_CreateEventBtn;
    EditText textInputEditText_clientCreateEventNameofEvent, textInputEditText_clientCreateEventLocation, textInputEditText_clientCreateEventDescription, textInputEditText_clientCreateEventTags, textInputEditText_clientCreateEventNumberOfAttendees,  textInputEditText_clientCreateEventBudget;
    RadioGroup clientCreateEvent_radioGroup;
    String eventCategory;
    FirebaseUser user;
    FirebaseFirestore db;
    private Uri filePath=null;
    private static final int PICK_IMAGE = 100;


    Calendar currentDate;
    Bitmap generatedateIcon;
    ImageGenerator imageGenerator;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_client_create_event);
        refs();
        user = FirebaseAuth.getInstance().getCurrentUser();

        Intent intent = getIntent();
        eventCategory= intent.getStringExtra("event_category");


        Toolbar toolbar = findViewById(R.id.toolbar1);
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
        clientCreateEvent_ImageView.setOnClickListener(chooseImage);


        button_CreateEventBtn.setOnClickListener(createEvent);
    }

    private View.OnClickListener chooseImage = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
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
                clientCreateEvent_ImageView.setImageBitmap(bitmap);

            }
            catch (IOException e)
            {
                e.printStackTrace();
            }
        }
    }




    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

  /*  @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater mi = getMenuInflater();
        mi.inflate(R.menu.menu_for_toolbar, menu);
        return super.onCreateOptionsMenu(menu);
    }
*/
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

    public View.OnClickListener createEvent = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            Long tsLong = System.currentTimeMillis()/1000;
            int checkedRadioButtonID = clientCreateEvent_radioGroup.getCheckedRadioButtonId();

            Events event;
            String eventName,eventLocation,eventDescription,eventTags,eventBudget,eventNumberOfAttendees;
            boolean eventIsPrivate=true;
            eventName=textInputEditText_clientCreateEventNameofEvent.getText().toString();
            eventLocation=textInputEditText_clientCreateEventLocation.getText().toString();
            eventDescription=textInputEditText_clientCreateEventDescription.getText().toString();
            eventTags=textInputEditText_clientCreateEventTags.getText().toString();
            eventBudget=textInputEditText_clientCreateEventBudget.getText().toString();
            eventNumberOfAttendees=textInputEditText_clientCreateEventNumberOfAttendees.getText().toString();


            if (eventName.isEmpty()) {
                textInputEditText_clientCreateEventNameofEvent.setError("Event Name is Blank");
            } else if (eventLocation.isEmpty()) {
                textInputEditText_clientCreateEventLocation.setError("Event Location is Blank");
            }
            else if (eventDescription.isEmpty()) {
                textInputEditText_clientCreateEventDescription.setError("Event Description is Blank");
            }

            else if (eventTags.isEmpty()) {
                textInputEditText_clientCreateEventTags.setError("Event Tags is Blank");
            }

            else if(eventBudget.isEmpty()) {
                textInputEditText_clientCreateEventBudget.setError("Event Budget is Blank");
            }

            else if (eventNumberOfAttendees.isEmpty()) {
                textInputEditText_clientCreateEventNumberOfAttendees.setError("Number of Attendees is Blank");
            }
            else{

                if(checkedRadioButtonID==R.id.clientCreateEvent_radioButtonPrivate)
                {

                    eventIsPrivate=true;
                }
                else if(checkedRadioButtonID==R.id.clientCreateEvent_radioButtonPublic)
                {
                    eventIsPrivate=false;
                }

                if(filePath!=null) {
                    Calendar cal = Calendar.getInstance(Locale.ENGLISH);
                    cal.setTimeInMillis(tsLong);
                    String date = DateFormat.format("dd-MM-yyyy", currentDate).toString();

                    event = new Events(0.0, eventCategory, user.getUid() + "",date, eventDescription, "", eventIsPrivate, eventLocation, eventName, 0, Integer.parseInt(eventNumberOfAttendees), "", Double.parseDouble(eventBudget), tsLong,"s",eventTags);
                    db = FirebaseFirestore.getInstance();
                    db.collection("Event")
                            .add(event)
                            .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                                @Override
                                public void onSuccess(DocumentReference documentReference) {
                                    String eventID = documentReference.getId();
                                    FirebaseFirestore.getInstance().collection("Event").document(eventID).update("event_event_uid", eventID);
                                    uploadImage(eventID);
                                }
                            })
                            .addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    showAlert("ERROR", "Couldn't Create Event!");
                                }
                            });

                }
                else
                {
                    showAlert("Please Choose Image First", "Warning");
                }

            }





        }
    };


    public void uploadImage(String txtid){
        if(filePath != null)
        {
            Log.e("THIS IS THE client uid", txtid);

            final ProgressDialog progressDialog = new ProgressDialog(Client_Create_Event.this);
            progressDialog.setTitle("Uploading...");
            // THIS IS ALTERNATE //progressDialog.getWindow().setType(WindowManager.LayoutParams.TYPE_TOAST);
            //  progressDialog.getWindow().setType(WindowManager.LayoutParams.TYPE_SYSTEM_ALERT);
            progressDialog.show();
            progressDialog.setCancelable(false);
            StorageReference ref = FirebaseStorage.getInstance().getReference().child("event_images").child(txtid);
            ref.putFile(filePath)
                    .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                            progressDialog.dismiss();
                            showAlertSuccess("Successfully Created Event!","Success");
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
    public void showAlertSuccess(String Message,String label)
    {
        //set alert for executing the task
        AlertDialog.Builder alert = new AlertDialog.Builder(Client_Create_Event.this);
        alert.setTitle(""+label);
        alert.setMessage(""+Message);
        alert.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            public void onClick (DialogInterface dialog, int id){
                Intent intent = new Intent(Client_Create_Event.this,ClientHomePage.class);
                startActivity(intent);
                dialog.cancel();

            }
        });
        Dialog dialog = alert.create();
        //  dialog.getWindow().setType(WindowManager.LayoutParams.TYPE_SYSTEM_ALERT);
        dialog.show();
    }


    public void showAlert(String Message,String label)
    {
        //set alert for executing the task
        AlertDialog.Builder alert = new AlertDialog.Builder(Client_Create_Event.this);
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



    public void refs()
    {
        textInputEditText_clientCreateEventNameofEvent=findViewById(R.id.textInputEditText_clientCreateEventNameofEvent);
        textInputEditText_clientCreateEventLocation=findViewById(R.id.textInputEditText_clientCreateEventLocation);
        textInputEditText_clientCreateEventDescription=findViewById(R.id.textInputEditText_clientCreateEventDescription);
        textInputEditText_clientCreateEventTags=findViewById(R.id.textInputEditText_clientCreateEventTags);
        textInputEditText_clientCreateEventBudget=findViewById(R.id.textInputEditText_clientCreateEventBudget);
        textInputEditText_clientCreateEventNumberOfAttendees=findViewById(R.id.textInputEditText_clientCreateEventNumberOfAttendees);
        clientCreateEvent_radioGroup=findViewById(R.id.clientCreateEvent_radioGroup);
        clientCreateEvent_ImageView=findViewById(R.id.clientCreateEvent_ImageView);
        button_CreateEventBtn=findViewById(R.id.button_CreateEventBtn);

    }

}