package com.example.morkince.okasyonv2.activities.client_activities;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.*;
import android.widget.*;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.morkince.okasyonv2.R;
import com.example.morkince.okasyonv2.activities.CallableFunctions;
import com.example.morkince.okasyonv2.activities.adapter.ViewItemRecyclerAdapter;
import com.example.morkince.okasyonv2.activities.model.Store;
import com.example.morkince.okasyonv2.activities.view_holders.BasicItemViewHolder;
import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.functions.FirebaseFunctions;
import com.google.firebase.functions.FirebaseFunctionsException;
import com.google.firebase.functions.HttpsCallableResult;
import com.xwray.groupie.GroupAdapter;
import com.xwray.groupie.ViewHolder;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class ClientViewItemsActivity extends AppCompatActivity {

    private ArrayList<Store> StoreItem = new ArrayList<>();
    ViewItemRecyclerAdapter adapter;
    RecyclerView recyclerView;
    int size = 0;
    final Context context = this;
    private Button button;
    Button saveFilterButton;
    private SearchView mSearchView;
    private FirebaseFunctions mFunctions;
    private String itemCategory = "Cake_and_Pastries";
    private GroupAdapter groupieAdapter = new GroupAdapter<ViewHolder>();
    private String event_event_uid;
   EditText txtBudget;
    EditText txtStorename;
    EditText txtLocation;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_client_viewitems);
        getSupportActionBar().setTitle("Cake and Pastries");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        refs();

        if (getIntent().hasExtra("event_event_uid")){
            event_event_uid = getIntent().getStringExtra("event_event_uid");
        }

        recyclerView.addItemDecoration(new DividerItemDecoration(this, LinearLayoutManager.VERTICAL));
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        mFunctions = FirebaseFunctions.getInstance();

        loadAllItemsInItemCategory(itemCategory);
    }

    private void loadAllItemsInItemCategory(String itemCategory) {
        FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();

        CallableFunctions callableFunctions = new CallableFunctions();

        callableFunctions.getRelatedItems(itemCategory, currentUser.getUid())
                .addOnCompleteListener(new OnCompleteListener<ArrayList<String>>() {
                    @Override
                    public void onComplete(Task<ArrayList<String>> task) {
                        if (task.isSuccessful()){
                            ArrayList<String> itemUid = task.getResult();

                            for (int i =0; i<itemUid.size(); i++) {
                                FirebaseFirestore.getInstance()
                                        .collection("Items")
                                        .document(itemUid.get(i))
                                        .get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                                    @Override
                                    public void onComplete(Task<DocumentSnapshot> task) {
                                        if (task.isSuccessful()) {
                                            com.example.morkince.okasyonv2.activities.model.Item item =
                                                    task.getResult().toObject(com.example.morkince.okasyonv2.activities.model.Item.class);

                                            BasicItemViewHolder basicItemViewHolder = new BasicItemViewHolder(item.getItem_uid(),
                                                    item.getItem_average_rating(),
                                                    item.getItem_price(),
                                                    item.getItem_name(),
                                                    getApplicationContext(),
                                                    item.getItem_display_picture_url(), event_event_uid);

                                            groupieAdapter.add(basicItemViewHolder);
                                        }
                                    }

                                });
                            }


                            recyclerView.setAdapter(groupieAdapter);
                        }else {
                            Exception e = task.getException();
                            if (e instanceof FirebaseFunctionsException) {
                                FirebaseFunctionsException ffe = (FirebaseFunctionsException) e;
                                FirebaseFunctionsException.Code code = ffe.getCode();
                                Object details = ffe.getDetails();
                            }
                        }
                    }
                });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater mi = getMenuInflater();
        mi.inflate(R.menu.menuclientviewitemsactionbar, menu);

        MenuItem searchItem = menu.findItem(R.id.app_bar_search);
        SearchView vs = (SearchView) searchItem.getActionView();
        vs.setQueryHint("Search View Hint");
        vs.setOnQueryTextListener(new SearchView.OnQueryTextListener() {

            @Override
            public boolean onQueryTextChange(String newText) {
                //Log.e("onQueryTextChange", "called");
                return false;
            }

            @Override
            public boolean onQueryTextSubmit(String query) {

//                Toast.makeText(getApplicationContext(), query, Toast.LENGTH_SHORT).show();
                // Do your task here
                groupieAdapter.clear();
                String itemQuery = (query.trim().toLowerCase());
                CallableFunctions callableFunctions = new CallableFunctions();
                callableFunctions.searchForItem(itemQuery, itemCategory)
                        .addOnCompleteListener(new OnCompleteListener<ArrayList<String>>() {
                    @Override
                    public void onComplete(Task<ArrayList<String>> task) {
                        if (task.isSuccessful()){
                            ArrayList<String> itemUid = task.getResult();


                            for (int i =0; i<itemUid.size(); i++) {
                                FirebaseFirestore.getInstance()
                                        .collection("Items")
                                        .document(itemUid.get(i))
                                        .get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                                            @Override
                                            public void onComplete(Task<DocumentSnapshot> task) {
                                                if (task.isSuccessful()) {
                                                    com.example.morkince.okasyonv2.activities.model.Item item =
                                                            task.getResult().toObject(com.example.morkince.okasyonv2.activities.model.Item.class);

                                                    BasicItemViewHolder basicItemViewHolder = new BasicItemViewHolder(item.getItem_uid(),
                                                            item.getItem_average_rating(),
                                                            item.getItem_price(),
                                                            item.getItem_name(),
                                                            getApplicationContext(),
                                                            item.getItem_display_picture_url(), event_event_uid);

                                                    groupieAdapter.add(basicItemViewHolder);
                                                }
                                    }

                                });
                            }

                            recyclerView.setAdapter(groupieAdapter);

                        }else {
                            Exception e = task.getException();
                            if (e instanceof FirebaseFunctionsException) {
                                FirebaseFunctionsException ffe = (FirebaseFunctionsException) e;
                                FirebaseFunctionsException.Code code = ffe.getCode();
                                Object details = ffe.getDetails();
                            }
                        }
                    }
                });

                return false;
            }

        });

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
//        Fragment selectedFragment = null;
        // Handle item selection
        switch (item.getItemId()) {

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
                window.setAttributes(wlp);

                saveFilterButton=view.findViewById(R.id.button_ApplyFilter);
                txtBudget =view.findViewById(R.id.edittext_Budget);
                txtStorename=view.findViewById(R.id.editText_Storename);
                txtLocation=view.findViewById(R.id.editText_Location);
                final RatingBar ratingBar = view.findViewById(R.id.ratingBar_filterrating);
                final CheckBox forRent = view.findViewById(R.id.checkBox_Rent);

                saveFilterButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String storeName = txtStorename.getText().toString().trim().isEmpty() ? "" : txtStorename.getText().toString().trim();
                        Double budget = txtBudget.getText().toString().trim().isEmpty() ? -1 : Double.valueOf(txtBudget.getText().toString().trim());
                        String location = txtLocation.toString().trim().isEmpty() ? "" :txtLocation.toString().trim();
                        Boolean isForRent = forRent.isChecked();
                        Integer itemScore = ratingBar.getNumStars();

//                        Toast.makeText(getApplicationContext(), txtBudget.getText().toString(), Toast.LENGTH_LONG).show();
//                        Toast.makeText(getApplicationContext(), txtStorname.getText().toString(), Toast.LENGTH_LONG).show();
//                        Toast.makeText(getApplicationContext(), txtlocation.getText().toString(), Toast.LENGTH_LONG).show();
                        CallableFunctions callableFunctions = new CallableFunctions();

                        callableFunctions.filterItems(itemCategory, storeName, budget, location, itemScore, isForRent)
                                .addOnCompleteListener(new OnCompleteListener<ArrayList<String>>() {
                            @Override
                            public void onComplete(@NonNull Task<ArrayList<String>> task) {

                            }
                        });
                    }
                });
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public void refs() {

        recyclerView = findViewById(R.id.RecyclerView_ClientViewItems);
    }

    private Task<ArrayList<String>> getRelatedItems(String itemCategory) {
        Map<String, Object> data = new HashMap<>();
        data.put("item_category", itemCategory);
        data.put("current_user_uid", FirebaseAuth.getInstance().getCurrentUser().getUid());

        return mFunctions
                .getHttpsCallable("getRelatedItems")
                .call(data)
                .continueWith(new Continuation<HttpsCallableResult, ArrayList<String>>() {
                    @Override
                    public ArrayList<String> then(@NonNull Task<HttpsCallableResult> task) throws Exception {
                        Map<String, Object> result = (Map<String, Object>) task.getResult().getData();
                        return (ArrayList<String>) result.get("itemUids");
                    }
                });
    }

    private Task<ArrayList<String>> searchForItem(String searchQuery, String itemCategory) {
        Map<String, Object> data = new HashMap<>();
        data.put("query", searchQuery);
        data.put("item_category", itemCategory);
        data.put("current_user_uid", FirebaseAuth.getInstance().getCurrentUser().getUid());

        return mFunctions
                .getHttpsCallable("searchForItem")
                .call(data)
                .continueWith(new Continuation<HttpsCallableResult, ArrayList<String>>() {
                    @Override
                    public ArrayList<String> then(@NonNull Task<HttpsCallableResult> task) throws Exception {
                        Map<String, Object> result = (Map<String, Object>) task.getResult().getData();
                        return (ArrayList<String>) result.get("itemUids");
                    }
                });
    }
}



