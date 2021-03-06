package com.example.morkince.okasyonv2.activities.client_activities;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.widget.*;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import com.example.morkince.okasyonv2.Custom_Progress_Dialog;
import com.example.morkince.okasyonv2.Events;
import com.example.morkince.okasyonv2.R;
import com.example.morkince.okasyonv2.RandomMessages;
import com.example.morkince.okasyonv2.activities.chat_activities.ChatLogActivitiy;
import com.example.morkince.okasyonv2.activities.homepage_supplier_activities.SupplierHomePage;
import com.example.morkince.okasyonv2.activities.model.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.kd.dynamic.calendar.generator.ImageGenerator;
import com.squareup.picasso.Picasso;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

public class FoundEventDetailsActivity extends AppCompatActivity {
    FirebaseUser user;
    FirebaseFirestore db;
    String event_id,event_category_id;
    Events event;

    Bitmap generatedateIcon;
    ImageGenerator imageGenerator;

    ImageView imageViewFoundEvents_eventdetailsImage,eventdetailsmessage,foundEventDetails_dateCalendar;
    TextView nameofevent,dateevent,addressevent,descriptionofevent,textView_foundeventdetailsDateoftheEvent;
    EditText eventdetailsdesc;
    Button sponsor,attend;
    private StorageReference mStorageRef;


        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_found_event_details);
        getSupportActionBar().setTitle("Event Details");
         user = FirebaseAuth.getInstance().getCurrentUser();
        Intent intent = getIntent();
        event_id= intent.getStringExtra("event_event_uid");
        event_category_id= intent.getStringExtra("event_category_id");
        refs();
        getEventDetails();

        sponsor.setOnClickListener(sponsorEvent);
        attend.setOnClickListener(attendEvent);
        eventdetailsmessage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
                final String currentUserUid = currentUser.getUid();

                final Custom_Progress_Dialog custom_progress_dialog = new Custom_Progress_Dialog(FoundEventDetailsActivity.this);
                RandomMessages randomMessages = new RandomMessages();
                custom_progress_dialog.showDialog("LOADING", randomMessages.getRandomMessage());

                FirebaseFirestore.getInstance()
                        .collection("Event")
                        .document(event_id)
                        .get()
                        .addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                            @Override
                            public void onSuccess(DocumentSnapshot documentSnapshot) {
                                final String event_creator_id = documentSnapshot.get("event_creator_id").toString();

                                FirebaseFirestore.getInstance()
                                        .collection("User")
                                        .document(currentUserUid)
                                        .get()
                                        .addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                                            @Override
                                            public void onSuccess(DocumentSnapshot documentSnapshot) {
                                                final User sendingUser = documentSnapshot.toObject(com.example.morkince.okasyonv2.activities.model.User.class);

                                                FirebaseFirestore.getInstance()
                                                        .collection("User")
                                                        .document(event_creator_id)
                                                        .get()
                                                        .addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                                                            @Override
                                                            public void onSuccess(DocumentSnapshot documentSnapshot) {
                                                                User receivingUser = documentSnapshot.toObject(com.example.morkince.okasyonv2.activities.model.User.class);

                                                                Intent intent = new Intent(FoundEventDetailsActivity.this, ChatLogActivitiy.class);
                                                                intent.putExtra("sendingUser", sendingUser);
                                                                intent.putExtra("receivingUser", receivingUser);
                                                                startActivity(intent);

                                                                custom_progress_dialog.dissmissDialog();
                                                                finish();
                                                            }
                                                        })
                                                        .addOnFailureListener(new OnFailureListener() {
                                                            @Override
                                                            public void onFailure(@NonNull Exception e) {
                                                                custom_progress_dialog.dissmissDialog();
                                                            }
                                                        });
                                            }
                                        })
                                        .addOnFailureListener(new OnFailureListener() {
                                            @Override
                                            public void onFailure(@NonNull Exception e) {
                                                custom_progress_dialog.dissmissDialog();
                                            }
                                        });
                            }
                        })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                custom_progress_dialog.dissmissDialog();
                                Toast.makeText(FoundEventDetailsActivity.this, "Coudn't Reach the Dude", Toast.LENGTH_LONG);
                            }
                        });
                //open chat log
            }
        });

    }



    public View.OnClickListener sponsorEvent = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            db = FirebaseFirestore.getInstance();
            db.collection("Sponsored_Events").document(user.getUid()).collection(event_category_id).document(event_id).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                @Override
                public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                    if(task.isSuccessful())
                    {
                        DocumentSnapshot document = task.getResult();
                        if (document.exists()) {
                            showAlert("You have already sponsored this Event!","Warning!");
                        }
                        else
                            showAlertSponsorEvent("Are you sure you want to sponsor?", "CONFIRM");
                    }
                }
            });
        }
    };

    public View.OnClickListener attendEvent = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            db = FirebaseFirestore.getInstance();
            db.collection("Attended_Events").document(user.getUid()).collection(event_category_id).document(event_id).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                @Override
                public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                    if(task.isSuccessful())
                    {
                        DocumentSnapshot document = task.getResult();
                        if (document.exists()) {
                            showAlert("You have already attended this Event!","Warning!");
                        }
                        else
                        showAlertAttendEvent("Are you sure you want to attend?", "CONFIRM");
                    }
                }
            });

        }
    };

    public void getEventDetails(){
        mStorageRef = FirebaseStorage.getInstance().getReference().child("event_images").child(event_id);
        mStorageRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {
                Picasso.get().load(uri.toString()).error(R.mipmap.ic_launcher).into(imageViewFoundEvents_eventdetailsImage);
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
                        nameofevent.setText(event.getEvent_name());
                        addressevent.setText(event.getEvent_location());
                        descriptionofevent.setText(event.getEvent_description());
                        eventdetailsdesc.setText(event.getEvent_tags());
                        textView_foundeventdetailsDateoftheEvent.setText(event.getEvent_date());
                        textView_foundeventdetailsDateoftheEvent.setText(event.getEvent_date());
                        Calendar currentDate = Calendar.getInstance();
                        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy", Locale.ENGLISH);

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

    public void showAlertSponsorEvent(String Message,String label)
    {
        //set alert for executing the task
        AlertDialog.Builder alert = new AlertDialog.Builder(FoundEventDetailsActivity.this);
        alert.setTitle(""+label);
        alert.setMessage(""+Message);

        alert.setPositiveButton("NO", new DialogInterface.OnClickListener() {
            public void onClick (DialogInterface dialog, int id){
                dialog.cancel();
            }
        });

        alert.setNegativeButton("YES", new DialogInterface.OnClickListener() {
            public void onClick (DialogInterface dialog, int id){
                dialog.cancel();
                Map<String, Object> sponsorEvent = new HashMap<>();
                sponsorEvent.put("sponsored_event_event_category_id",event_category_id);
                sponsorEvent.put("sponsored_event_event_uid",event_id);
                sponsorEvent.put("sponsored_event_user_uid", user.getUid());

                db = FirebaseFirestore.getInstance();
                db.collection("Sponsored_Events").document(user.getUid()).collection(event_category_id).document(event_id).set(sponsorEvent).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        showAlert("The Event Organizers have been notified of your generosity!", "SUCCESS!");
                    }
                });
            }
        });

        Dialog dialog = alert.create();
        //  dialog.getWindow().setType(WindowManager.LayoutParams.TYPE_SYSTEM_ALERT);
        dialog.show();
    }



    public void showAlertAttendEvent(String Message,String label)
    {
        //set alert for executing the task
        AlertDialog.Builder alert = new AlertDialog.Builder(FoundEventDetailsActivity.this);
        alert.setTitle(""+label);
        alert.setMessage(""+Message);

        alert.setPositiveButton("NO", new DialogInterface.OnClickListener() {
            public void onClick (DialogInterface dialog, int id){
                dialog.cancel();
            }
        });

        alert.setNegativeButton("YES", new DialogInterface.OnClickListener() {
            public void onClick (DialogInterface dialog, int id){
                dialog.cancel();

                Map<String, Object> attendedEvent = new HashMap<>();
                attendedEvent.put("attended_event_event_category_id",event_category_id);
                attendedEvent.put("attended_event_event_uid",event_id);
                attendedEvent.put("attended_event_user_uid", user.getUid());

                db = FirebaseFirestore.getInstance();
                db.collection("Attended_Events").document(user.getUid()).collection(event_category_id).document(event_id).set(attendedEvent).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        showAlert("The Event Organizers are excited to see you!", "SUCCESS!");
                    }
                });



            }
        });

        Dialog dialog = alert.create();
        //  dialog.getWindow().setType(WindowManager.LayoutParams.TYPE_SYSTEM_ALERT);
        dialog.show();
    }

    public void showAlert(String Message,String label)
    {
        //set alert for executing the task
        AlertDialog.Builder alert = new AlertDialog.Builder(FoundEventDetailsActivity.this);
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
        imageViewFoundEvents_eventdetailsImage=findViewById(R.id.imageViewFoundEvents_eventdetailsImage);
        eventdetailsmessage=findViewById(R.id.imageView_eventdetailsMessage);
        nameofevent=findViewById(R.id.textView_eventdetailsNameoftheEvent);
        dateevent=findViewById(R.id.textView_eventdetailsDateoftheEvent);
        addressevent=findViewById(R.id.textView_foundeventdetailsAddressofTheEvent);
        descriptionofevent=findViewById(R.id.textView_foundeventdetailsDescription);
        eventdetailsdesc=findViewById(R.id.editext_eventdetailsDetails);
        sponsor=findViewById(R.id.button_foundEventSponsorButton);
        attend=findViewById(R.id.button_foundEventAttendButton);
        foundEventDetails_dateCalendar=findViewById(R.id.foundEventDetails_dateCalendar);
        textView_foundeventdetailsDateoftheEvent=findViewById(R.id.textView_foundeventdetailsDateoftheEvent);
    }
}
