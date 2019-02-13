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
import android.text.format.DateFormat;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.*;
import com.example.morkince.okasyonv2.Events;
import com.example.morkince.okasyonv2.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.kd.dynamic.calendar.generator.ImageGenerator;
import com.squareup.picasso.Picasso;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class EventDetailsActivity extends AppCompatActivity {
    ImageView editDetails, calendarHandler,imageView_eventdetailsImage;
    TextView eventDetails_setNumAttendeesTextView,numberofInterestedAttendees,numberofInterestedSponsors,textView_numberofEventsInterestedAttendees,textView_numberofEventsInterestedSponsor;
    Button buttonSave,foundEventDetails_browseItemsButton;
    String event_id;
    Events event;
    EditText nameofEvent,dateofevent,addressofevent,descpitionofevent,detailsofevent;


    Calendar currentDate;
    Bitmap generatedateIcon;
    ImageGenerator imageGenerator;

    FirebaseUser user;
    FirebaseFirestore db;
    private StorageReference mStorageRef;

    private static final int PICK_IMAGE = 100;
    private Uri filePath=null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_details);
        getSupportActionBar().setTitle("Event Details");
        refs();
        enable(false);

        Intent intent = getIntent();
        event_id= intent.getStringExtra("event_event_uid");


        getEventDetails();


        calendarHandler.setEnabled(false);
        editDetails.setOnClickListener(edittheDetails);
        buttonSave.setOnClickListener(saveUpdatedData);
        foundEventDetails_browseItemsButton.setOnClickListener(browseItems);
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

    public void getEventDetails(){

        mStorageRef = FirebaseStorage.getInstance().getReference().child("event_images").child(event_id);
        mStorageRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {
                Picasso.get().load(uri.toString()).error(R.mipmap.ic_launcher).into(imageView_eventdetailsImage);
            }
        });


        db = FirebaseFirestore.getInstance();
        db.collection("Event").document(event_id).get()
                .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {

                        if (task.isSuccessful()) {
                            DocumentSnapshot document = task.getResult();
                            if (document.exists()) {

                                event=document.toObject(Events.class);
                                nameofEvent.setText(event.getEvent_name());
                                addressofevent.setText(event.getEvent_location());
                                descpitionofevent.setText(event.getEvent_description());
                                detailsofevent.setText(event.getEvent_tags());
                                dateofevent.setText(event.getEvent_date());
                                eventDetails_setNumAttendeesTextView.setText(event.getEvent_num_of_attendees() + "");

                                Calendar currentDate = Calendar.getInstance();
                                SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy", Locale.ENGLISH);
                                try {
                                    currentDate.setTime(sdf.parse(event.getEvent_date()));// all done
                                } catch (ParseException e) {
                                    e.printStackTrace();
                                }

                                // .set(selectedYear,selectedMonth,selectedDay);
                                imageGenerator = new ImageGenerator(EventDetailsActivity.this);

                                imageGenerator.setIconSize(100, 100);
                                imageGenerator.setDateSize(40);
                                imageGenerator.setMonthSize(20);

                                imageGenerator.setDatePosition(80);
                                imageGenerator.setMonthPosition(24);

                                imageGenerator.setDateColor(Color.parseColor("#D81B60"));
                                imageGenerator.setMonthColor(Color.parseColor("#ffffff"));

                                imageGenerator.setStorageToSDCard(true);

                                generatedateIcon= imageGenerator.generateDateImage(currentDate,R.drawable.calendar_icon);
                                calendarHandler.setImageBitmap(generatedateIcon);

                            } else {
                                Log.d("", "No such document exist");
                            }
                        } else {
                            Log.d("", "Failed with ", task.getException());
                        }
                    }
                });


        //GET NUMBER OF ATTENDEES
        db.collection("Attendees_List").document(event_id).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if(task.isSuccessful())
                {
                    DocumentSnapshot document = task.getResult();
                    if (document.exists()) {
                        int num_attendees =Integer.parseInt(document.get("attendees_list_list_size") + "");
                        textView_numberofEventsInterestedAttendees.setText(num_attendees + "");
                    }
                }
            }
        });
       // GET NUMBER OF SPONSORS

        db.collection("Sponsors_List").document(event_id).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if(task.isSuccessful())
                {
                    DocumentSnapshot document = task.getResult();
                    if (document.exists()) {

                        //int num_sponsors =(Integer)(document.get("sponsors_list_list_size"));
                        int num_sponsors = Integer.parseInt(document.get("sponsors_list_list_size") + "");
                        textView_numberofEventsInterestedSponsor.setText(num_sponsors + "");
                    }
                }
            }
        });
    }



    public View.OnClickListener browseItems = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            Intent intent = new Intent(EventDetailsActivity.this,ClientViewItemsActivity.class);
            intent.putExtra("event_event_uid",event_id);
            startActivity(intent);
        }
    };

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
            Log.e("THIS IS THE client UID", txtid);

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
            editDetails.setVisibility(View.INVISIBLE);
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
            enable(false);
            buttonSave.setVisibility(View.INVISIBLE);
            editDetails.setVisibility(View.VISIBLE);
            String eventName = nameofEvent.getText().toString();
            String eventAddress = addressofevent.getText().toString();
            String eventDescription = descpitionofevent.getText().toString();
            String eventDetails = detailsofevent.getText().toString();
            String eventDate=dateofevent.getText().toString();

            if(eventName.isEmpty())
            {
                nameofEvent.setError("Please Enter Name of Event");
            }
            else if(eventAddress.isEmpty())
            {
                addressofevent.setError("Please Enter Address of Event");
            }
            else if(eventDescription.isEmpty())
            {
                descpitionofevent.setError("Please Enter Description of Event");
            }
            else if(eventDetails.isEmpty())
            {
                detailsofevent.setError("Please Enter Details of Event");
            }
            else if(eventDate.isEmpty())
            {
                dateofevent.setText("Please Enter Date of Event");
            }
            {
                Long tsLong = System.currentTimeMillis()/1000;
                Calendar cal = Calendar.getInstance(Locale.ENGLISH);
                cal.setTimeInMillis(tsLong);
                String date = DateFormat.format("dd-MM-yyyy", currentDate).toString();

                db = FirebaseFirestore.getInstance();
                DocumentReference userToUpdate = db.collection("Event").document("" + event_id);
                userToUpdate.update("event_name", eventName);
                userToUpdate.update("event_location", eventAddress);
                userToUpdate.update("event_description", eventDescription);
                userToUpdate.update("event_tags", eventDetails);
                userToUpdate.update("event_date", eventDate)
                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {
                                showAlert("Successfully Updated Data", "SUCCESS!");
                            }
                        });

            }


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
        calendarHandler = findViewById(R.id.eventDetails_imageView_calendar);
        buttonSave = findViewById(R.id.Button_saveEditEvent);
        numberofInterestedAttendees = findViewById(R.id.textView_numberofEventsInterestedAttendees);
        numberofInterestedSponsors = findViewById(R.id.textView_numberofEventsInterestedSponsor);
        imageView_eventdetailsImage = findViewById(R.id.imageView_eventdetailsImage);
        foundEventDetails_browseItemsButton = findViewById(R.id.foundEventDetails_browseItemsButton);
        textView_numberofEventsInterestedAttendees = findViewById(R.id.textView_numberofEventsInterestedAttendees);
        textView_numberofEventsInterestedSponsor = findViewById(R.id.textView_numberofEventsInterestedSponsor);
        eventDetails_setNumAttendeesTextView = findViewById(R.id.eventDetails_setNumAttendeesTextView);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater mi = getMenuInflater();
        mi.inflate(R.menu.delete_event, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();
        if(id == R.id.deleteEventButton)
        {
            showAlertConfirmDelete("Are you sure you want to Delete Event?","Confirm");
        }

        return super.onOptionsItemSelected(item);
    }

    public void showAlertConfirmDelete(String Message,String label)
    {
        //set alert for executing the task
        AlertDialog.Builder alert = new AlertDialog.Builder(EventDetailsActivity.this);
        alert.setTitle(""+label);
        alert.setMessage(""+Message);

        alert.setPositiveButton("NO", new DialogInterface.OnClickListener() {
            public void onClick (DialogInterface dialog, int id){
                dialog.cancel();

            }
        });

        alert.setNegativeButton("YES", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                DeleteItem();
            }
        });

        Dialog dialog = alert.create();
        //  dialog.getWindow().setType(WindowManager.LayoutParams.TYPE_SYSTEM_ALERT);
        dialog.show();
    }

    public void DeleteItem()
    {



        // Create a storage reference from our app
        StorageReference storageRef = FirebaseStorage.getInstance().getReference();


            // Create a reference to the file to delete
            StorageReference desertRef = storageRef.child("event_images").child(event_id);

            // Delete the file
            desertRef.delete().addOnSuccessListener(new OnSuccessListener<Void>() {
                @Override
                public void onSuccess(Void aVoid) {
                    // File deleted successfully
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception exception) {
                    // Uh-oh, an error occurred!

                }
            });


        db.collection("Event").document(event_id)
                .delete()
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        showAlertSuccessfulDelete("Sucessfully Deleted Event", "SUCCESS");
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        showAlert("Error : " + e, "ERROR");
                    }
                });
    }


    public void showAlertSuccessfulDelete(String Message,String label)
    {
        //set alert for executing the task
        AlertDialog.Builder alert = new AlertDialog.Builder(EventDetailsActivity.this);
        alert.setTitle(""+label);
        alert.setMessage(""+Message);

        alert.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            public void onClick (DialogInterface dialog, int id){
                Intent intent = new Intent(EventDetailsActivity.this,ClientHomePage.class);
                startActivity(intent);
                dialog.cancel();
                finish();
            }
        });

        Dialog dialog = alert.create();
        //  dialog.getWindow().setType(WindowManager.LayoutParams.TYPE_SYSTEM_ALERT);
        dialog.show();
    }



}