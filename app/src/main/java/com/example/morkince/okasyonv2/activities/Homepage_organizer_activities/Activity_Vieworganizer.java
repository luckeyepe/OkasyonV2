package com.example.morkince.okasyonv2.activities.Homepage_organizer_activities;

import android.app.Dialog;
import android.content.Context;
import android.net.Uri;
import android.util.Log;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.core.app.NavUtils;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.*;
import com.example.morkince.okasyonv2.R;
import com.example.morkince.okasyonv2.Reviews;
import com.example.morkince.okasyonv2.StoreReviewsAdapter;
import com.example.morkince.okasyonv2.activities.adapter.ViewOrganizersAdapter;
import com.example.morkince.okasyonv2.activities.homepage_supplier_activities.SupplierHomePage;
import com.example.morkince.okasyonv2.activities.model.Organizer;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

public class Activity_Vieworganizer extends AppCompatActivity {
    FirebaseUser user;
    FirebaseFirestore db;
    private ArrayList<Organizer> organizer = new ArrayList<>();
    ViewOrganizersAdapter adapter;
    RecyclerView recyclerView;
    int size = 0;
    final Context context = this;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity__vieworganizer);
        getSupportActionBar().setTitle("Choose Organizer");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        refs();

        String eventCategoryUid = "";

        if (getIntent().hasExtra("event_category")){
            eventCategoryUid = getIntent().getStringExtra("event_category");
        }



        db = FirebaseFirestore.getInstance();

        final String finalEventCategoryUid = eventCategoryUid;
        db.collection("User").whereEqualTo("user_role","organizer").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    for (DocumentSnapshot document : task.getResult()) {
                        String OrganizerName = document.getString("user_first_name") + document.getString("user_last_name");
                        String imageuri=document.getString("user_profilePictureURL");
                        String location=document.getString("user_address");
                        String user_uid=document.getString("user_uid");
                        Double price= document.getLong("user_price").doubleValue();
//                        double rating=3.5;

                        organizer.add(new Organizer(OrganizerName,imageuri,location,price,0, user_uid, finalEventCategoryUid));
                    }

                    adapter = new ViewOrganizersAdapter(organizer, Activity_Vieworganizer.this);
                    recyclerView.setAdapter(adapter);
                    recyclerView.addItemDecoration(new DividerItemDecoration(Activity_Vieworganizer.this, LinearLayoutManager.VERTICAL));
                    recyclerView.setLayoutManager(new LinearLayoutManager(Activity_Vieworganizer.this));

                }
            }
        });





    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater mi = getMenuInflater();
//        mi.inflate(R.menu.clientviewitemsactionbar, menu);
        mi.inflate(R.menu.menuclientviewitemsactionbar, menu);
        return super.onCreateOptionsMenu(menu);
    }

    //    @Override
//    public boolean onOptionsItemSelected(MenuItem item) {
//        int id = item.getItemId();
//        if(id == R.id.Filter)
//        {
//            final Dialog dialog = new Dialog(context);
//            dialog.setContentView(R.layout.activity_filter);
//            dialog.setTitle("Title...");
//            return true;
//        }
//        return super.onOptionsItemSelected(item);
//    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
//        Fragment selectedFragment = null;
        // Handle item selection
        switch (item.getItemId()) {
//            case android.R.id.home:
//                NavUtils.navigateUpFromSameTask(this);
//                return true;
            case R.id.Filter:
//                selectedFragment = new activity_filter();
//                break;
                View view = LayoutInflater.from(this).inflate(R.layout.activity_filter, null);
                final Dialog dialog = new Dialog(context);
                dialog.setContentView(view);
                dialog.setTitle("FILTER");
                dialog.show();
                Window window = dialog.getWindow();
                WindowManager.LayoutParams wlp = window.getAttributes();
                wlp.gravity = Gravity.BOTTOM | Gravity.RIGHT;
                window.setAttributes(wlp);
//                wlp.gravity = Gravity.RIGHT;
//                wlp.flags &= ~WindowManager.LayoutParams.FLAG_DIM_BEHIND;
                window.setAttributes(wlp);

                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
    public void refs()
    {

        recyclerView = findViewById(R.id.RecyclerView_viewOrganizer_activity);
    }
    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

}