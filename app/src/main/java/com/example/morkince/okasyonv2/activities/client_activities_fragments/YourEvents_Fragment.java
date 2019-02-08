package com.example.morkince.okasyonv2.activities.client_activities_fragments;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;
import com.example.morkince.okasyonv2.Events;
import com.example.morkince.okasyonv2.EventsAdapter;
import com.example.morkince.okasyonv2.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;

public class YourEvents_Fragment extends Fragment {
    FirebaseUser user;
    FirebaseFirestore db;
    private StorageReference mStorageRef;
    RecyclerView RecyclerView_client_your_eventfragment;
    EventsAdapter adapter;
    private ArrayList<Events> events = new ArrayList<>();
    SearchView searchView;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.client_your_events_fragment, container, false);
        displayEvents();

        RecyclerView_client_your_eventfragment=view.findViewById(R.id.RecyclerView_client_your_eventfragment);
        return view;
    }

    public void displayEvents()
    {
        user = FirebaseAuth.getInstance().getCurrentUser();
        db = FirebaseFirestore.getInstance();
        db.collection("Event").whereEqualTo("event_creator_id", user.getUid()).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {

                    if(task.getResult().size() !=0) {

                        for (QueryDocumentSnapshot document : task.getResult()) {

                            Events event = document.toObject(Events.class);
                            events.add(event);
                        }

                        adapter = new EventsAdapter(events, getActivity());
                        RecyclerView_client_your_eventfragment.setAdapter(adapter);
                        RecyclerView_client_your_eventfragment.addItemDecoration(new DividerItemDecoration(getActivity(), LinearLayoutManager.VERTICAL));
                        RecyclerView_client_your_eventfragment.setLayoutManager(new LinearLayoutManager(getActivity()));
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
                                        }
                                        adapter = new EventsAdapter(events, getActivity());
                                        RecyclerView_client_your_eventfragment.setAdapter(adapter);
                                        RecyclerView_client_your_eventfragment.addItemDecoration(new DividerItemDecoration(getActivity(), LinearLayoutManager.VERTICAL));
                                        RecyclerView_client_your_eventfragment.setLayoutManager(new LinearLayoutManager(getActivity()));
                                    }
                                    else
                                    {

                                        Toast.makeText(getActivity().getApplicationContext(), "No Events to Show!",
                                                Toast.LENGTH_SHORT).show();
                                    }
                                } else {
                                    Toast.makeText(getActivity().getApplicationContext(), "Error in Retrieving Records!",
                                            Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
                    }
                } else {
                    Toast.makeText(getActivity().getApplicationContext(), "Error in Retrieving Records!",
                            Toast.LENGTH_SHORT).show();
                }
            }
        });
    }//END OF DISPLAY EVENTS METHOD



}//END OF CLASS
