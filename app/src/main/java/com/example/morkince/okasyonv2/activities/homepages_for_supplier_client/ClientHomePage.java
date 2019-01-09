package com.example.morkince.okasyonv2.activities.homepages_for_supplier_client;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.*;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;
import com.example.morkince.okasyonv2.R;
import com.example.morkince.okasyonv2.activities.client_activities.Client_Create_Event;
import com.example.morkince.okasyonv2.activities.client_activities_fragments.TopEvents_Fragment;
import com.example.morkince.okasyonv2.activities.client_activities_fragments.YourEvents_Fragment;
import com.example.morkince.okasyonv2.activities.login_activities.MainActivity;

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_client_homepage);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Client");


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        BottomNavigationView bottomNavigationView = findViewById(R.id.event_navigation);
        bottomNavigationView.setOnNavigationItemSelectedListener(event_nav_listener);

        getSupportFragmentManager().beginTransaction().replace(R.id.framelayout_events, new TopEvents_Fragment()).commit();
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
                            Intent intent = new Intent(ClientHomePage.this, Client_Create_Event.class);
                            startActivity(intent);
                        }
                    });

                    //hireOrganizer
                    hireOrganizer.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Intent intent = new Intent(ClientHomePage.this, Client_Create_Event.class);
                            startActivity(intent);
                        }
                    });



                    closeDialog.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            dialogWedding.dismiss();
                        }
                    });
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
                            Intent intent = new Intent(ClientHomePage.this, Client_Create_Event.class);
                            startActivity(intent);
                        }
                    });

                    //hireOrganizer
                    hireOrganizer.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Intent intent = new Intent(ClientHomePage.this, Client_Create_Event.class);
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
                            Intent intent = new Intent(ClientHomePage.this, Client_Create_Event.class);
                            startActivity(intent);
                        }
                    });

                    //hireOrganizer
                    hireOrganizer.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Intent intent = new Intent(ClientHomePage.this, Client_Create_Event.class);
                            startActivity(intent);
                        }
                    });


                    closeDialog.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            dialogBusiness.dismiss();
                        }
                    });
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
                            Intent intent = new Intent(ClientHomePage.this, Client_Create_Event.class);
                            startActivity(intent);
                        }
                    });

                    //hireOrganizer
                    hireOrganizer.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Intent intent = new Intent(ClientHomePage.this, Client_Create_Event.class);
                            startActivity(intent);
                        }
                    });



                    closeDialog.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            dialogSports.dismiss();
                        }
                    });
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
                            Intent intent = new Intent(ClientHomePage.this, Client_Create_Event.class);
                            startActivity(intent);
                        }
                    });

                    //hireOrganizer
                    hireOrganizer.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Intent intent = new Intent(ClientHomePage.this, Client_Create_Event.class);
                            startActivity(intent);
                        }
                    });


                    closeDialog.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            dialogCustomize.dismiss();
                        }
                    });

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
//        int id = item.getItemId();
//
//        if (id == R.id.nav_camera) {
//            // Handle the camera action
//        } else if (id == R.id.nav_gallery) {
//
//        } else if (id == R.id.nav_slideshow) {
//
//        } else if (id == R.id.nav_manage) {
//
//        } else if (id == R.id.nav_share) {
//
//        } else if (id == R.id.nav_send) {
//
//        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}