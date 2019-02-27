package com.example.morkince.okasyonv2.activities.client_activities;

import android.app.Dialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.*;
import android.widget.*;
import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;
import com.example.morkince.okasyonv2.Events;
import com.example.morkince.okasyonv2.R;
import com.example.morkince.okasyonv2.activities.Homepage_organizer_activities.Activity_Vieworganizer;
import com.example.morkince.okasyonv2.activities.Homepage_organizer_activities.UserProfileActivity;
import com.example.morkince.okasyonv2.activities.adapter.EventsAdapter;
import com.example.morkince.okasyonv2.activities.chat_activities.LatestMessagesActivity;
import com.example.morkince.okasyonv2.activities.client_fragments.TopEvents_Fragment;
import com.example.morkince.okasyonv2.activities.client_fragments.YourEvents_Fragment;
import com.example.morkince.okasyonv2.activities.login_activities.MainActivity;
import com.example.morkince.okasyonv2.activities.model.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.InstanceIdResult;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class ClientHomePage extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    ActionBarDrawerToggle toggle;

    private BottomNavigationView.OnNavigationItemSelectedListener event_nav_listener =
            new BottomNavigationView.OnNavigationItemSelectedListener() {
                @Override
                public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                    Fragment selectedFragment = null;

                    switch (menuItem.getItemId())
                    {

                        case R.id.navigation_topevent:
                            selectedFragment = new TopEvents_Fragment();
                            break;
                        case R.id.navigation_yourevent:
                            selectedFragment = new YourEvents_Fragment();
                            break;

                    }
                    getSupportFragmentManager().beginTransaction().replace(R.id.framelayout_events, selectedFragment).commit();

                    return true;
                }
            };

    ImageView drawerImage;
    TextView drawerUsername;
    TextView drawerAccount;
    FirebaseUser user;
    FirebaseFirestore db;
    private StorageReference mStorageRef;
    RecyclerView recyclerView_clientTopEvents;
    EventsAdapter adapter;
    String typeOfEvent;
    private ArrayList<Events> events = new ArrayList<>();
    User userclient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_client_homepage);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Client");
        refs();
        user = FirebaseAuth.getInstance().getCurrentUser();
        updateProfileInfo();

        if (getIntent().hasExtra("isNewUser")){
            AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
            alertDialogBuilder.setMessage("Welcome to Okasyon");
            alertDialogBuilder.setCancelable(true);

            AlertDialog alert = alertDialogBuilder.create();
            alert.show();
        }

        FirebaseInstanceId.getInstance().getInstanceId()
                .addOnCompleteListener(new OnCompleteListener<InstanceIdResult>() {
                    @Override
                    public void onComplete(@NonNull Task<InstanceIdResult> task) {
                        if (!task.isSuccessful()) {
                            Log.w("Client HP", "getInstanceId failed", task.getException());
                            return;
                        }

                        // Get new Instance ID token
                        String token = task.getResult().getToken();

                        // Log and toast
                        DocumentReference db = FirebaseFirestore.getInstance().collection("User").document(user.getUid());
                        db.update("user_token", token);
                    }
                });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        View headerView = navigationView.getHeaderView(0);
        drawerImage =  headerView.findViewById(R.id.imageView_OrganizerImage);
        drawerImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ClientHomePage.this,UserProfileActivity.class);
                startActivity(intent);
             }
        });
        BottomNavigationView bottomNavigationView = findViewById(R.id.event_navigation);
        bottomNavigationView.setOnNavigationItemSelectedListener(event_nav_listener);

        getSupportFragmentManager().beginTransaction().replace(R.id.framelayout_events, new TopEvents_Fragment()).commit();
    }

    public void refs()
    {
        recyclerView_clientTopEvents=findViewById(R.id.recyclerView_clientTopEvents);
    }

public void ClientCreateEvent()
    {
        Intent intent = new Intent(ClientHomePage.this, Client_Create_Event.class);
        intent.putExtra("event_category",typeOfEvent);
        startActivity(intent);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        int id = item.getItemId();

        if(toggle.onOptionsItemSelected(item)){
            return true;
        }

        else if(id == R.id.imageButton_clientCreateEvent)
        {
            final Dialog dialog = new Dialog(this);
            LayoutInflater layoutInflater = LayoutInflater.from(this);
            View dialogView = layoutInflater.inflate(R.layout.modal_createevent_client,null);

            ImageButton buttonClose = dialogView.findViewById(R.id.imageButton_modalCreateEventClose);

            Button chooseWedding= dialogView.findViewById(R.id.button_modalCreateEventWedding);

            Button chooseParty= dialogView.findViewById(R.id.button_modalCreateOwnEvent);
            Button chooseBusinessEvents =dialogView.findViewById(R.id.button_modalCreateEventBusinessEvents);
            Button chooseSports =dialogView.findViewById(R.id.button_modalCreateEventSports);
            Button chooseCustomize =dialogView.findViewById(R.id.button_modalCreateEventCustomEvents);

            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
            dialog.setContentView(dialogView);
            dialog.setCancelable(false);
            dialog.show();

            buttonClose.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dialog.dismiss();
                }
            });

            //WEDDING BUTTON
            chooseWedding.setOnClickListener(new View.OnClickListener()
            {
                @Override
                public void onClick(View v) {
                   final Dialog dialogWedding = new Dialog(ClientHomePage.this);
                    LayoutInflater layoutInflater = LayoutInflater.from( ClientHomePage.this);
                    View view = layoutInflater.inflate(R.layout.modal_hireororganize_client,null);

                    ImageButton closeDialog = view.findViewById(R.id.imageButton_modalHireOrganizerClose);
                    Button organizeOwn = view.findViewById(R.id.button_modalCreateOwnEvent);
                    Button hireOrganizer =view.findViewById(R.id.button_modalHireEventOrganizer);

                    dialogWedding.requestWindowFeature(Window.FEATURE_NO_TITLE);
                    dialogWedding.setContentView(view);
                    dialogWedding.setCancelable(false);
                    dialogWedding.show();

                    //organizeOwn

                    organizeOwn.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                           ClientCreateEvent();
                        }
                    });

                    //hireOrganizer
                    hireOrganizer.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Intent intent = new Intent(ClientHomePage.this, Activity_Vieworganizer.class);
                            intent.putExtra("event_category",typeOfEvent);
                            startActivity(intent);
                        }
                    });

                    closeDialog.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            dialogWedding.dismiss();
                        }
                    });
                    typeOfEvent="Wedding";
                    Toast.makeText(getApplicationContext(),"Wedding Event",Toast.LENGTH_SHORT).show();
                }
            });

            //PARTY BUTTON

            chooseParty.setOnClickListener(new View.OnClickListener()
            {
                @Override
                public void onClick(View v) {
                    final Dialog dialogParty = new Dialog(ClientHomePage.this);
                    LayoutInflater layoutInflater = LayoutInflater.from( ClientHomePage.this);
                    View view = layoutInflater.inflate(R.layout.modal_hireororganize_client,null);

                    ImageButton closeDialog = view.findViewById(R.id.imageButton_modalHireOrganizerClose);
                    Button organizeOwn = view.findViewById(R.id.button_modalCreateOwnEvent);
                    Button hireOrganizer =view.findViewById(R.id.button_modalHireEventOrganizer);

                    dialogParty.requestWindowFeature(Window.FEATURE_NO_TITLE);
                    dialogParty.setContentView(view);
                    dialogParty.setCancelable(false);
                    dialogParty.show();


                    //organizeOwn

                    organizeOwn.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            ClientCreateEvent();
                        }
                    });

                    //hireOrganizer
                    hireOrganizer.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Intent intent = new Intent(ClientHomePage.this, Activity_Vieworganizer.class);
                            intent.putExtra("event_category",typeOfEvent);
                            startActivity(intent);
                        }
                    });



                    //close dialog button
                    closeDialog.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            dialogParty.dismiss();
                        }
                    });
                    typeOfEvent="Party";
                    Toast.makeText(getApplicationContext(),"Party Events!!",Toast.LENGTH_SHORT).show();
                }
            });

            //BUSINESS BUTTON
            chooseBusinessEvents.setOnClickListener(new View.OnClickListener()
            {
                @Override
                public void onClick(View v) {
                    final Dialog dialogBusiness = new Dialog(ClientHomePage.this);
                    LayoutInflater layoutInflater = LayoutInflater.from( ClientHomePage.this);
                    View view = layoutInflater.inflate(R.layout.modal_hireororganize_client,null);

                    ImageButton closeDialog = view.findViewById(R.id.imageButton_modalHireOrganizerClose);
                    Button organizeOwn = view.findViewById(R.id.button_modalCreateOwnEvent);
                    Button hireOrganizer =view.findViewById(R.id.button_modalHireEventOrganizer);


                    dialogBusiness.requestWindowFeature(Window.FEATURE_NO_TITLE);
                    dialogBusiness.setContentView(view);
                    dialogBusiness.setCancelable(false);
                    dialogBusiness.show();


                    //organizeOwn

                    organizeOwn.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            ClientCreateEvent();
                        }
                    });

                    //hireOrganizer
                    hireOrganizer.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Intent intent = new Intent(ClientHomePage.this, Activity_Vieworganizer.class);
                            intent.putExtra("event_category",typeOfEvent);
                            startActivity(intent);
                        }
                    });


                    closeDialog.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            dialogBusiness.dismiss();
                        }
                    });
                    typeOfEvent="Business_Events";
                    Toast.makeText(getApplicationContext(),"Business Event!!",Toast.LENGTH_SHORT).show();
                }
            });
            //SPORTS BUTTON

            chooseSports.setOnClickListener(new View.OnClickListener()
            {
                @Override
                public void onClick(View v) {
                    final Dialog dialogSports = new Dialog(ClientHomePage.this);
                    LayoutInflater layoutInflater = LayoutInflater.from( ClientHomePage.this);
                    View view = layoutInflater.inflate(R.layout.modal_hireororganize_client,null);

                    ImageButton closeDialog = view.findViewById(R.id.imageButton_modalHireOrganizerClose);
                    Button organizeOwn = view.findViewById(R.id.button_modalCreateOwnEvent);
                    Button hireOrganizer =view.findViewById(R.id.button_modalHireEventOrganizer);


                    dialogSports.requestWindowFeature(Window.FEATURE_NO_TITLE);
                    dialogSports.setContentView(view);
                    dialogSports.setCancelable(false);
                    dialogSports.show();


                    //organizeOwn

                    organizeOwn.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            ClientCreateEvent();
                        }
                    });

                    //hireOrganizer
                    hireOrganizer.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Intent intent = new Intent(ClientHomePage.this, Activity_Vieworganizer.class);
                            intent.putExtra("event_category",typeOfEvent);
                            startActivity(intent);
                        }
                    });



                    closeDialog.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            dialogSports.dismiss();
                        }
                    });
                    typeOfEvent="Sports_Events";
                    Toast.makeText(getApplicationContext(),"Sports Events",Toast.LENGTH_SHORT).show();
                }
            });
            //CUSTOMIZE BUTTON
            chooseCustomize.setOnClickListener(new View.OnClickListener()
            {
                @Override
                public void onClick(View v) {
                    final Dialog dialogCustomize = new Dialog(ClientHomePage.this);
                    LayoutInflater layoutInflater = LayoutInflater.from( ClientHomePage.this);
                    View view = layoutInflater.inflate(R.layout.modal_hireororganize_client,null);

                    ImageButton closeDialog = view.findViewById(R.id.imageButton_modalHireOrganizerClose);
                    Button organizeOwn = view.findViewById(R.id.button_modalCreateOwnEvent);
                    Button hireOrganizer =view.findViewById(R.id.button_modalHireEventOrganizer);


                    dialogCustomize.requestWindowFeature(Window.FEATURE_NO_TITLE);
                    dialogCustomize.setContentView(view);
                    dialogCustomize.setCancelable(false);
                    dialogCustomize.show();

                    //organizeOwn

                    organizeOwn.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            ClientCreateEvent();
                        }
                    });

                    //hireOrganizer
                    hireOrganizer.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Intent intent = new Intent(ClientHomePage.this, Activity_Vieworganizer.class);
                            intent.putExtra("event_category",typeOfEvent);
                            startActivity(intent);
                        }
                    });


                    closeDialog.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            dialogCustomize.dismiss();
                        }
                    });
                    typeOfEvent="Customized_Events";
                    Toast.makeText(getApplicationContext(),"Customize Events",Toast.LENGTH_SHORT).show();

                }

            });



            Toast.makeText(getApplicationContext(),"Success!!",Toast.LENGTH_SHORT).show();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }






    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.client_create_event_button, menu);
        return true;
    }

//    @Override
//    public boolean onOptionsItemSelected(MenuItem item) {
//        // Handle action bar item clicks here. The action bar will
//        // automatically handle clicks on the Home/Up button, so long
//        // as you specify a parent activity in AndroidManifest.xml.
//        int id = item.getItemId();
//
//        //noinspection SimplifiableIfStatement
//        if (id == R.id.action_settings) {
//            return true;
//        }
//
//        return super.onOptionsItemSelected(item);
//    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
     int id = item.getItemId();

     switch (id){
         case R.id.nav_home:
             Intent homeIntent = new Intent (ClientHomePage.this, ClientHomePage.class);
             startActivity(homeIntent);
             break;

         case R.id.nav_profile:
             Intent profileIntent = new Intent (ClientHomePage.this, UserProfileActivity.class);
             startActivity(profileIntent);
             break;

         case R.id.nav_messages:
             Intent messagesIntent = new Intent(ClientHomePage.this,LatestMessagesActivity.class);
             startActivity(messagesIntent);
             break;

         case R.id.nav_notifications:
             break;

         case R.id.nav_cart:
             Intent cartIntent = new Intent (ClientHomePage.this, ClientCartEventListActivity.class);
             startActivity(cartIntent);
             break;

         case R.id.nav_transaction:
             Intent transactionIntent = new Intent(ClientHomePage.this, Transaction_ClientView.class);
             startActivity(transactionIntent);
             break;

         case R.id.nav_logout:
             FirebaseAuth.getInstance().signOut();
             Intent logoutIntent = new Intent(ClientHomePage.this,MainActivity.class);
             startActivity(logoutIntent);
             finish();
             break;

         default: break;
     }
//        if (id == R.id.nav_logout) {
//            // Handle the camera action
//            FirebaseAuth.getInstance().signOut();
//            Intent intent = new Intent(ClientHomePage.this,MainActivity.class);
//            startActivity(intent);
//            finish();
//        }
//        else if(id == R.id.nav_messages)
//        {
//            Intent intent = new Intent(ClientHomePage.this,LatestMessagesActivity.class);
//            startActivity(intent);
//        }
//        else if(id == R.id.nav_profile)
//        {
//            Intent intent = new Intent (ClientHomePage.this, UserProfileActivity.class);
//            startActivity(intent);
//        }
//        else if(id == R.id.nav_cart){
//            Intent intent = new Intent (ClientHomePage.this, ClientCartEventListActivity.class);
//            startActivity(intent);
//        }
//        //else if (id == R.id.nav_gallery) {
////
////        } else if (id == R.id.nav_slideshow) {
////
////        } else if (id == R.id.nav_manage) {
////
////        } else if (id == R.id.nav_share) {
////
////        } else if (id == R.id.nav_send) {
////
////        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
    public void updateProfileInfo()
    {
        mStorageRef = FirebaseStorage.getInstance().getReference().child("user_profPic").child(user.getUid());

        mStorageRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {
                Picasso.get().load(uri.toString()).error(R.mipmap.ic_launcher_round).into(drawerImage);
//                Picasso.get().load(uri.toString()).into(drawerImage);
            }
        });
        db = FirebaseFirestore.getInstance();
        db.collection("User").document(user.getUid()).get()
                .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {

                        if (task.isSuccessful()) {
                            DocumentSnapshot document = task.getResult();
                            if (document.exists()) {
                                userclient =document.toObject(User.class);
                                drawerUsername.setText(userclient.getUser_first_name()+ userclient.getUser_last_name());
                                drawerAccount.setText(userclient.getUser_email());
                                Log.e("NAA DISPLAY", userclient.getUser_first_name());
                            } else {
                                Log.d("", "No such document exist");
                            }
                        } else {
                            Log.d("", "Failed with ", task.getException());
                        }
                    }
                });

        //     Intent intent = new Intent(MainActivity.this, homepage.class);
            //    intent.putExtra("name", personName+"");     }

        NavigationView drawer =  findViewById(R.id.nav_view);
        View headerView = drawer.getHeaderView(0);
       drawerImage =  headerView.findViewById(R.id.imageView_OrganizerImage);
        drawerUsername =  headerView.findViewById(R.id.textview_OrganizerName);
         drawerAccount =headerView.findViewById(R.id.textView_OrganizerEmail);


    }
}
