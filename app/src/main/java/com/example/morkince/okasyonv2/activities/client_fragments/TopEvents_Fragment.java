package com.example.morkince.okasyonv2.activities.client_fragments;

import android.os.Bundle;
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
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;

public class TopEvents_Fragment extends Fragment {
    FirebaseUser user;
    FirebaseFirestore db;
    private StorageReference mStorageRef;
    RecyclerView recyclerView_clientTopEvents;
    EventsAdapter adapter;
    private ArrayList<Events> events = new ArrayList<>();
    SearchView searchView;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.client_top_events_fragment, container, false);
        displayEvents();

            recyclerView_clientTopEvents=view.findViewById(R.id.recyclerView_clientTopEvents);
            searchView = view.findViewById(R.id.searchView);
            searchView.onActionViewExpanded();
            searchView.setIconified(false);
            searchView.clearFocus();
            searchView.setQueryHint("Search Event");

        return view;
    }


    public void displayEvents()
    {
        db = FirebaseFirestore.getInstance();
        db.collection("Event").whereEqualTo("event_isPrivate", false).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {

                    if(task.getResult().size() !=0) {
                        for (QueryDocumentSnapshot document : task.getResult()) {

                            Events event = document.toObject(Events.class);
                            events.add(event);
                        }

                        adapter = new EventsAdapter(events, getActivity());
                        recyclerView_clientTopEvents.setAdapter(adapter);
                        recyclerView_clientTopEvents.addItemDecoration(new DividerItemDecoration(getActivity(), LinearLayoutManager.VERTICAL));
                        recyclerView_clientTopEvents.setLayoutManager(new LinearLayoutManager(getActivity()));
                    }
                    else
                    {
                        if(isAdded()) {
                            Toast.makeText(getActivity(), "No Events to Show!",
                                    Toast.LENGTH_SHORT).show();
                        }
                    }
                } else {
                    if(isAdded()) {
                        Toast.makeText(getActivity(), "No Events to Show!",
                                Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
    }
}
