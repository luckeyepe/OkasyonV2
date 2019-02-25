package com.example.morkince.okasyonv2.activities.client_fragments;

import android.os.Bundle;
import android.util.Log;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.appcompat.widget.SearchView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;
import com.example.morkince.okasyonv2.Events;
import com.example.morkince.okasyonv2.activities.adapter.EventsAdapter;
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
        db.collection("Event").whereEqualTo("event_creator_id", user.getUid())
                .get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {

                    if(isAdded()) {
                        if (task.getResult().size() != 0) {

                            for (QueryDocumentSnapshot document : task.getResult()) {

                                Events event = document.toObject(Events.class);
                                events.add(event);
                            }

                            adapter = new EventsAdapter(events, getActivity());
                            RecyclerView_client_your_eventfragment.setAdapter(adapter);
                            RecyclerView_client_your_eventfragment.addItemDecoration(new DividerItemDecoration(getActivity(), LinearLayoutManager.VERTICAL));
                            RecyclerView_client_your_eventfragment.setLayoutManager(new LinearLayoutManager(getActivity()));
                        } else {

                            db.collection("Event").whereEqualTo("event_event_organizer_uid", user.getUid()).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                @Override
                                public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                    if (task.isSuccessful()) {
                                        if (task.getResult().size() != 0) {

                                            for (QueryDocumentSnapshot document : task.getResult()) {

                                                Events event = document.toObject(Events.class);
                                                events.add(event);
                                                Log.e("THIS IS THE EVENT", event.getEvent_event_uid());
                                            }
                                            adapter = new EventsAdapter(events, getActivity());
                                            RecyclerView_client_your_eventfragment.setAdapter(adapter);
                                            RecyclerView_client_your_eventfragment.addItemDecoration(new DividerItemDecoration(getActivity(), LinearLayoutManager.VERTICAL));
                                            RecyclerView_client_your_eventfragment.setLayoutManager(new LinearLayoutManager(getActivity()));
                                        } else {
                                            if (isAdded()) {
                                                Toast.makeText(getActivity(), "No Events to Show!",
                                                        Toast.LENGTH_SHORT).show();
                                            }
                                        }
                                    } else {
                                        if (isAdded()) {
                                            Toast.makeText(getActivity(), "Error in Retrieving Records!",
                                                    Toast.LENGTH_SHORT).show();
                                        }
                                    }
                                }
                            });
                        }
                    }
                } else {
                    if (isAdded()) {
                        Toast.makeText(getActivity(), "Error in Retrieving Records!",
                                Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
    }//END OF DISPLAY EVENTS METHOD



}//END OF CLASS
