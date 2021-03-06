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
import android.os.Environment;
import android.os.StrictMode;
import android.provider.MediaStore;
import android.view.*;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import androidx.appcompat.widget.Toolbar;
import android.text.format.DateFormat;
import android.util.Log;
import android.widget.*;
import com.example.morkince.okasyonv2.Events;
import com.example.morkince.okasyonv2.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.kd.dynamic.calendar.generator.ImageGenerator;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
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
    private static final int PICK_CAMERA = 0;
    private static final int PICK_IMAGE = 1;


    Calendar currentDate;
    Bitmap generatedateIcon;
    ImageGenerator imageGenerator;
    private String mCurrentPhotoPath;

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
            View layout = LayoutInflater.from(Client_Create_Event.this).inflate(R.layout.modal_camera_or_gallery, null);
            final Dialog dialog = new Dialog(Client_Create_Event.this);
            dialog.setContentView(layout);
            dialog.show();
            Window window = dialog.getWindow();
            WindowManager.LayoutParams wlp = window.getAttributes();
            wlp.gravity = Gravity.CENTER;
            window.setAttributes(wlp);
            window.setAttributes(wlp);
            Button button_cameroOrGalleryModalCamera = layout.findViewById(R.id.button_cameroOrGalleryModalCamera);
            Button button_cameroOrGalleryModalGallery = layout.findViewById(R.id.button_cameroOrGalleryModalGallery);

            button_cameroOrGalleryModalCamera.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    openCamera();
                    dialog.dismiss();
                }
            });

            button_cameroOrGalleryModalGallery.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    openGallery();
                    dialog.dismiss();
                }
            });


        }
    };

    public void openGallery()
    {
        Intent gallery = new Intent(Intent.ACTION_PICK,MediaStore.Images.Media.INTERNAL_CONTENT_URI);
        startActivityForResult(gallery,PICK_IMAGE);
    }

    public void openCamera()
    {
//        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
//        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
//            startActivityForResult(takePictureIntent, PICK_CAMERA);
//        }
        StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
        StrictMode.setVmPolicy(builder.build());

        Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (cameraIntent.resolveActivity(getPackageManager()) != null) {
            // Create the File where the photo should go
            File photoFile = null;
            try {
                photoFile = createImageFile();
            } catch (IOException ex) {
                // Error occurred while creating the File
                Log.i("", "IOException");
            }
            // Continue only if the File was successfully created
            if (photoFile != null) {
                cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(photoFile));
                startActivityForResult(cameraIntent, PICK_CAMERA);
            }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        switch (requestCode) {
            case PICK_CAMERA:
                if (resultCode == RESULT_OK) {
                    Bitmap imageBitmap;

                    try {
                        imageBitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), Uri.parse(mCurrentPhotoPath));
                        clientCreateEvent_ImageView.setImageBitmap(imageBitmap);
                        filePath = Uri.parse(mCurrentPhotoPath);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

//                    Bitmap bitmap;
//                    bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), filePath);

//                        Picasso.get().load(filePath).into(clientCreateEvent_ImageView);
//                    clientCreateEvent_ImageView.setImageBitmap(bitmap);
                }

                break;
            case PICK_IMAGE:
                if (resultCode == RESULT_OK) {
                    filePath = data.getData();
                    clientCreateEvent_ImageView.setImageURI(filePath);
                }
                break;
        }

//        if(requestCode == PICK_IMAGE && resultCode == RESULT_OK
//                && data != null && data.getData() != null ) {
//            filePath = data.getData();
//            try {
//                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), filePath);
//                clientCreateEvent_ImageView.setImageBitmap(bitmap);
//
//            }
//            catch (IOException e)
//            {
//                e.printStackTrace();
//            }
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
            button_CreateEventBtn.setEnabled(false);

            String eventOrganizerUid = "";
            String eventCategoryID = "";

            if (getIntent().hasExtra("organizerUid")){
                eventOrganizerUid = getIntent().getStringExtra("organizerUid");
                eventCategoryID= getIntent().getStringExtra("event_category");
            }

            Long tsLong = System.currentTimeMillis()/1000;
            int checkedRadioButtonID = clientCreateEvent_radioGroup.getCheckedRadioButtonId();

            FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();

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
                button_CreateEventBtn.setEnabled(true);
                textInputEditText_clientCreateEventNameofEvent.setError("Event Name is Blank");
            } else if (eventLocation.isEmpty()) {
                button_CreateEventBtn.setEnabled(true);
                textInputEditText_clientCreateEventLocation.setError("Event Location is Blank");
            }
            else if (eventDescription.isEmpty()) {
                button_CreateEventBtn.setEnabled(true);
                textInputEditText_clientCreateEventDescription.setError("Event Description is Blank");
            }

            else if (eventTags.isEmpty()) {
                button_CreateEventBtn.setEnabled(true);
                textInputEditText_clientCreateEventTags.setError("Event Tags is Blank");
            }

            else if(eventBudget.isEmpty()) {
                button_CreateEventBtn.setEnabled(true);
                textInputEditText_clientCreateEventBudget.setError("Event Budget is Blank");
            }

            else if (eventNumberOfAttendees.isEmpty()) {
                button_CreateEventBtn.setEnabled(true);
                textInputEditText_clientCreateEventNumberOfAttendees.setError("Number of Attendees is Blank");
            }
            else if (textInputEditText_clientCreateEventDate.getText().toString().isEmpty()){
                button_CreateEventBtn.setEnabled(true);
                textInputEditText_clientCreateEventDate.setError("Set a date");
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

                    event = new Events(0.0,
                            eventCategoryID,
                            currentUser.getUid() + "",
                            date,
                            eventDescription,
                            "",
                            eventIsPrivate,
                            eventLocation,
                            eventName,
                            0,
                            Integer.parseInt(eventNumberOfAttendees),
                            "",
                            Double.parseDouble(eventBudget),
                            tsLong,
                            eventOrganizerUid,
                            eventTags);

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
                                    button_CreateEventBtn.setEnabled(true);

                                }
                            });

                }
                else
                {
                    showAlert("Please Choose Image First", "Warning");
                    button_CreateEventBtn.setEnabled(true);

                }

            }





        }
    };


    public void uploadImage(final String txtid){
        if(filePath != null)
        {
            Log.e("THIS IS THE client uid", txtid);

            final ProgressDialog progressDialog = new ProgressDialog(Client_Create_Event.this);
            progressDialog.setTitle("Uploading...");
            // THIS IS ALTERNATE //progressDialog.getWindow().setType(WindowManager.LayoutParams.TYPE_TOAST);
            //  progressDialog.getWindow().setType(WindowManager.LayoutParams.TYPE_SYSTEM_ALERT);
            progressDialog.show();
            progressDialog.setCancelable(false);
            final StorageReference ref = FirebaseStorage.getInstance().getReference().child("event_images").child(txtid);
            ref.putFile(filePath)
                    .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                            progressDialog.dismiss();
                            showAlertSuccess("Successfully Created Event!","Success", txtid);
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            progressDialog.dismiss();
                            showAlert("An Error Occured","ERROR");
                            button_CreateEventBtn.setEnabled(true);
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

    public void showAlertSuccess(String Message, String label, final String eventUid)
    {
        //set alert for executing the task
        AlertDialog.Builder alert = new AlertDialog.Builder(Client_Create_Event.this);
        alert.setTitle(""+label);
        alert.setMessage(""+Message);
        alert.setCancelable(false);
        alert.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            public void onClick (DialogInterface dialog, int id){
                Intent intent = new Intent(Client_Create_Event.this,EventDetailsActivity.class);
                intent.putExtra("event_event_uid", eventUid);
                startActivity(intent);
                finish();
                dialog.dismiss();


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


    private File createImageFile() throws IOException {
        // Create an image file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "JPEG_" + timeStamp + "_";
        File storageDir = Environment.getExternalStoragePublicDirectory(
                Environment.DIRECTORY_PICTURES);
        File image = File.createTempFile(
                imageFileName,  // prefix
                ".jpg",         // suffix
                storageDir      // directory
        );

        // Save a file: path for use with ACTION_VIEW intents
        mCurrentPhotoPath = "file:" + image.getAbsolutePath();
        return image;
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