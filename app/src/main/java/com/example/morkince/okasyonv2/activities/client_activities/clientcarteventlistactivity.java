package com.example.morkince.okasyonv2.activities.client_activities;

import android.util.Log;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.morkince.okasyonv2.Events;
import com.example.morkince.okasyonv2.R;
import com.example.morkince.okasyonv2.activities.adapter.CartEventAdapter;
import com.example.morkince.okasyonv2.activities.adapter.EventsAdapter;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;

public class clientcarteventlistactivity extends AppCompatActivity {
    FirebaseUser user;
    FirebaseFirestore db;
    private StorageReference mStorageRef;
    RecyclerView RecyclerView_client_your_event;
    CartEventAdapter adapter;
    private ArrayList<Events> events = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_clientcarteventlistactivity);
        RecyclerView_client_your_event=findViewById(R.id.recycleview_clientcarteventlist);
        user = FirebaseAuth.getInstance().getCurrentUser();
        db = FirebaseFirestore.getInstance();
        db.collection("Event").whereEqualTo("event_creator_id", user.getUid())
                .get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {

                    if(task.getResult().size() !=0) {

                        for (QueryDocumentSnapshot document : task.getResult()) {

                            Events event = document.toObject(Events.class);
                            events.add(event);
                        }

                        adapter = new CartEventAdapter(events, clientcarteventlistactivity.this);
                        RecyclerView_client_your_event.setAdapter(adapter);
                        RecyclerView_client_your_event.addItemDecoration(new DividerItemDecoration(clientcarteventlistactivity.this, LinearLayoutManager.VERTICAL));
                        RecyclerView_client_your_event.setLayoutManager(new LinearLayoutManager(clientcarteventlistactivity.this));
                    }
                    else
                    {

                        db.collection("Event").whereEqualTo("event_event_organizer_uid",user.getUid()).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                            @Override
                            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                if (task.isSuccessful() ) {
                                    if(task.getResult().size() !=0)
                                    {

                                        for (QueryDocumentSnapshot document : task.getResult()) {

                                            Events event = document.toObject(Events.class);
                                            events.add(event);
                                            Log.e("THIS IS THE EVENT", event.getEvent_event_uid());
                                        }
                                        adapter = new CartEventAdapter(events, clientcarteventlistactivity.this);
                                        RecyclerView_client_your_event.setAdapter(adapter);
                                        RecyclerView_client_your_event.addItemDecoration(new DividerItemDecoration(clientcarteventlistactivity.this, LinearLayoutManager.VERTICAL));
                                        RecyclerView_client_your_event.setLayoutManager(new LinearLayoutManager(clientcarteventlistactivity.this));
                                    }
                                    else
                                    {
                                        if(events.isEmpty()) {
                                            Toast.makeText(clientcarteventlistactivity.this, "No Events to Show!",
                                                    Toast.LENGTH_SHORT).show();
                                        }
                                    }
                                } else {
                                    if (events.isEmpty()) {
                                        Toast.makeText(clientcarteventlistactivity.this, "Error in Retrieving Records!",
                                                Toast.LENGTH_SHORT).show();
                                    }
                                }
                            }
                        });
                    }
                } else {
                    if (events.isEmpty()) {
                        Toast.makeText(clientcarteventlistactivity.this, "Error in Retrieving Records!",
                                Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
    }

}
