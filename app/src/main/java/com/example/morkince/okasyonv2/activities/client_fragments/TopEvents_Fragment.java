package com.example.morkince.okasyonv2.activities.client_fragments;

import android.content.Intent;
import android.inputmethodservice.Keyboard;
import android.os.Bundle;
import android.widget.TextView;
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
import com.example.morkince.okasyonv2.*;
import com.example.morkince.okasyonv2.R;
import com.example.morkince.okasyonv2.activities.CallableFunctions;
import com.example.morkince.okasyonv2.activities.client_activities.EventDetailsActivity;
import com.example.morkince.okasyonv2.activities.client_activities.FoundEventDetailsActivity;
import com.example.morkince.okasyonv2.activities.view_holders.EventViewHolder;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.*;
import com.google.firebase.storage.StorageReference;
import com.xwray.groupie.GroupAdapter;
import com.xwray.groupie.Item;
import com.xwray.groupie.OnItemClickListener;
import com.xwray.groupie.ViewHolder;

import java.util.ArrayList;

public class TopEvents_Fragment extends Fragment {
    FirebaseUser user;
    FirebaseFirestore db;
    private StorageReference mStorageRef;
    RecyclerView recyclerView_clientTopEvents;
    TextView noMessage;
    private ArrayList<Events> events = new ArrayList<>();
    SearchView searchView;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable final ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.client_top_events_fragment, container, false);
        displayEvents();

        recyclerView_clientTopEvents=view.findViewById(R.id.recyclerView_clientTopEvents);
        noMessage = view.findViewById(R.id.textView_clientTopEventsFragementNoEvents);
        searchView = view.findViewById(R.id.searchView);
        searchView.onActionViewExpanded();
        searchView.setIconified(false);
        searchView.clearFocus();
        searchView.setQueryHint("Search Event");

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
              @Override
              public boolean onQueryTextSubmit(String query) {
                  final GroupAdapter<ViewHolder> groupAdapter = new GroupAdapter<>();

                  RandomMessages randomMessages = new RandomMessages();
                  final Custom_Progress_Dialog custom_progress_dialog = new Custom_Progress_Dialog(getActivity());
                  custom_progress_dialog.showDialog("LOADING", randomMessages.getRandomMessage());

                  String eventQuery = searchView.getQuery().toString();
                  CallableFunctions callableFunctions = new CallableFunctions();
                  callableFunctions.searchForEvent(eventQuery)
                          .addOnCompleteListener(new OnCompleteListener<ArrayList<String>>() {
                              @Override
                              public void onComplete(@NonNull Task<ArrayList<String>> task) {
                                  if (task.isSuccessful()){
                                      ArrayList<String> result = task.getResult();
                                        groupAdapter.clear();
//                                      events.clear();
//                                      adapter = new EventsAdapter(events, getActivity());
//                                      recyclerView_clientTopEvents.setAdapter(adapter);
//                                      recyclerView_clientTopEvents.addItemDecoration(new DividerItemDecoration(getActivity(), LinearLayoutManager.VERTICAL));
//                                      recyclerView_clientTopEvents.setLayoutManager(new LinearLayoutManager(getActivity()));

                                      if (result.size()>0){
                                          //show events
                                          noMessage.setVisibility(View.INVISIBLE);

                                          for (int x =0; x<result.size(); x++){

                                              String eventUid = result.get(x);

                                              db = FirebaseFirestore.getInstance();
                                              db.collection("Event").document(eventUid)
                                                      .get()
                                                      .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                                                          @Override
                                                          public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                                                              //event_is_private
                                                              if (task.isSuccessful()) {
                                                                  if(isAdded()) {
                                                                      Events event = task.getResult().toObject(Events.class);
                                                                      if (!event.isEvent_is_private()) {
                                                                          groupAdapter.add(new EventViewHolder(event.getEvent_event_uid(), getContext()));
//                                                                          events.add(event);
//                                                                          adapter = new EventsAdapter(events, getActivity());
                                                                      }
                                                                  }
                                                              } else {
                                                                  if(isAdded()) {
                                                                      custom_progress_dialog.dissmissDialog();
                                                                      noMessage.setVisibility(View.VISIBLE);
                                                                      Toast.makeText(getActivity(), "No Events to Show!",
                                                                              Toast.LENGTH_SHORT).show();
                                                                  }
                                                              }
                                                          }
                                                      });

                                          }


                                          recyclerView_clientTopEvents.setAdapter(groupAdapter);
                                          recyclerView_clientTopEvents.addItemDecoration(new DividerItemDecoration(getActivity(), LinearLayoutManager.VERTICAL));
                                          recyclerView_clientTopEvents.setLayoutManager(new LinearLayoutManager(getActivity()));
                                          custom_progress_dialog.dissmissDialog();

                                      }else {
                                          //show no events
                                          noMessage.setVisibility(View.VISIBLE);
                                          custom_progress_dialog.dissmissDialog();
                                      }
                                  }
                              }
                          });

                  groupAdapter.setOnItemClickListener(new OnItemClickListener() {
                      @Override
                      public void onItemClick(@NonNull Item item, @NonNull View view) {
                          final FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();

                          EventViewHolder eventViewHolder = (EventViewHolder) item;
//                          Intent intent = new Intent(view.getContext(), EventDetailsActivity.class);
//                          intent.putExtra("event_event_uid", eventViewHolder.getEventUid());
//                          startActivity(intent);
                          FirebaseFirestore.getInstance()
                                  .collection("Event")
                                  .document(eventViewHolder.getEventUid())
                                  .get()
                                  .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                                      @Override
                                      public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                                          if (task.isSuccessful()){
                                              Events events = task.getResult().toObject(Events.class);

                                              if (events.getEvent_creator_id().equalsIgnoreCase(currentUser.getUid()) ||
                                                      events.getEvent_event_organizer_uid().equalsIgnoreCase(currentUser.getUid()))
                                              {
                                                  Intent intent = new Intent(getContext(),EventDetailsActivity.class);
                                                  intent.putExtra("event_event_uid",events.getEvent_event_uid());
                                                  intent.putExtra("event_category_id",events.getEvent_category_id());
                                                  intent.putExtra("event_cart_group_uid", events.getEvent_budget_spent());

                                                  startActivity(intent);
                                              }
                                              else
                                              {
                                                  Intent intent = new Intent(getContext(), FoundEventDetailsActivity.class);
                                                  intent.putExtra("event_event_uid",events.getEvent_event_uid());
                                                  intent.putExtra("event_category_id",events.getEvent_category_id());

                                                  startActivity(intent);
                                              }
                                          }else {
                                              //error
                                              new PopUpDialogs(getContext()).errorDialog("Something went wrong", "ERROR");
                                          }
                                      }
                                  });
                          /////////////////////////
                      }
                  });

                  return false;
              }

              @Override
              public boolean onQueryTextChange(String newText) {

                  return false;
              }
          });

        return view;
    }


    public void displayEvents() {
        RandomMessages randomMessages = new RandomMessages();
        final Custom_Progress_Dialog custom_progress_dialog = new Custom_Progress_Dialog(getActivity());
        custom_progress_dialog.showDialog("LOADING", randomMessages.getRandomMessage());

        final GroupAdapter<ViewHolder> groupAdapter = new GroupAdapter<>();

        db = FirebaseFirestore.getInstance();
        db.collection("Event").whereEqualTo("event_is_private", false)
                .addSnapshotListener(new EventListener<QuerySnapshot>() {
                    @Override
                    public void onEvent(@javax.annotation.Nullable QuerySnapshot queryDocumentSnapshots, @javax.annotation.Nullable FirebaseFirestoreException e) {
                        if (e != null) {
                            if (isAdded()) {
                                noMessage.setVisibility(View.VISIBLE);
                                Toast.makeText(getActivity(), "No Events to Show!",
                                        Toast.LENGTH_SHORT).show();
                                custom_progress_dialog.dissmissDialog();
                                return;
                            }

                            custom_progress_dialog.dissmissDialog();
                        }

                        if (!queryDocumentSnapshots.isEmpty() && queryDocumentSnapshots != null) {
                            if (isAdded()) {
                                groupAdapter.clear();

                                noMessage.setVisibility(View.INVISIBLE);
                                for (QueryDocumentSnapshot document : queryDocumentSnapshots) {
                                    Events event = document.toObject(Events.class);
                                    groupAdapter.add(new EventViewHolder(event.getEvent_event_uid(), getContext()));
                                }

                                recyclerView_clientTopEvents.setAdapter(groupAdapter);
                                recyclerView_clientTopEvents.addItemDecoration(new DividerItemDecoration(getActivity(), LinearLayoutManager.VERTICAL));
                                recyclerView_clientTopEvents.setLayoutManager(new LinearLayoutManager(getActivity()));
                                custom_progress_dialog.dissmissDialog();
                            }

                            custom_progress_dialog.dissmissDialog();
                        } else {
                            if (isAdded()) {
                                noMessage.setVisibility(View.VISIBLE);
                                Toast.makeText(getActivity(), "No Events to Show!",
                                        Toast.LENGTH_SHORT).show();
                                custom_progress_dialog.dissmissDialog();
                            }

                            custom_progress_dialog.dissmissDialog();
                        }

                        custom_progress_dialog.dissmissDialog();
                    }

//                .get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
//            @Override
//            public void onComplete(@NonNull Task<QuerySnapshot> task) {
//                if (task.isSuccessful()) {
//
//                    if(task.getResult().size() !=0) {
//                        if(isAdded()) {
//                            noMessage.setVisibility(View.INVISIBLE);
//                            for (QueryDocumentSnapshot document : task.getResult()) {
//
//                                Events event = document.toObject(Events.class);
//                                events.add(event);
//                            }
//
//
//                            adapter = new EventsAdapter(events, getActivity());
//                            recyclerView_clientTopEvents.setAdapter(adapter);
//                            recyclerView_clientTopEvents.addItemDecoration(new DividerItemDecoration(getActivity(), LinearLayoutManager.VERTICAL));
//                            recyclerView_clientTopEvents.setLayoutManager(new LinearLayoutManager(getActivity()));
//                            custom_progress_dialog.dissmissDialog();
//                        }
//                    }
//                    else
//                    {
//                        if(isAdded()) {
//                            noMessage.setVisibility(View.VISIBLE);
//                            Toast.makeText(getActivity(), "No Events to Show!",
//                                    Toast.LENGTH_SHORT).show();
//                            custom_progress_dialog.dissmissDialog();
//                        }
//                    }
//                } else {
//                    if(isAdded()) {
//                        noMessage.setVisibility(View.VISIBLE);
//                        Toast.makeText(getActivity(), "No Events to Show!",
//                                Toast.LENGTH_SHORT).show();
//                        custom_progress_dialog.dissmissDialog();
//                    }
//                }
//            }
//        });
                });

        groupAdapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(@NonNull Item item, @NonNull View view) {
                final FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();

                EventViewHolder eventViewHolder = (EventViewHolder) item;
//                          Intent intent = new Intent(view.getContext(), EventDetailsActivity.class);
//                          intent.putExtra("event_event_uid", eventViewHolder.getEventUid());
//                          startActivity(intent);
                FirebaseFirestore.getInstance()
                        .collection("Event")
                        .document(eventViewHolder.getEventUid())
                        .get()
                        .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                            @Override
                            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                                if (task.isSuccessful()){
                                    Events events = task.getResult().toObject(Events.class);

                                    if (events.getEvent_creator_id().equalsIgnoreCase(currentUser.getUid()) ||
                                            events.getEvent_event_organizer_uid().equalsIgnoreCase(currentUser.getUid()))
                                    {
                                        Intent intent = new Intent(getContext(),EventDetailsActivity.class);
                                        intent.putExtra("event_event_uid",events.getEvent_event_uid());
                                        intent.putExtra("event_category_id",events.getEvent_category_id());
                                        intent.putExtra("event_cart_group_uid", events.getEvent_budget_spent());

                                        startActivity(intent);
                                    }
                                    else
                                    {
                                        Intent intent = new Intent(getContext(), FoundEventDetailsActivity.class);
                                        intent.putExtra("event_event_uid",events.getEvent_event_uid());
                                        intent.putExtra("event_category_id",events.getEvent_category_id());

                                        startActivity(intent);
                                    }
                                }else {
                                    //error
                                    new PopUpDialogs(getContext()).errorDialog("Something went wrong", "ERROR");
                                }
                            }
                        });
                /////////////////////////
            }
        });
    }
}
