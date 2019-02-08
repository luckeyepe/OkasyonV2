package com.example.morkince.okasyonv2.activities.HomePage_Client_activity;

import android.app.Dialog;
import android.content.Context;
import android.widget.SearchView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.*;
import android.widget.Button;
import android.widget.ImageButton;
import com.example.morkince.okasyonv2.R;
import com.example.morkince.okasyonv2.activities.adapter.ViewItemRecyclerAdapter;
import com.example.morkince.okasyonv2.activities.model.Item;
import com.example.morkince.okasyonv2.activities.model.Store;
import com.example.morkince.okasyonv2.activities.view_holders.BasicItemViewHolder;
import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.functions.FirebaseFunctions;
import com.google.firebase.functions.FirebaseFunctionsException;
import com.google.firebase.functions.HttpsCallableResult;
import com.xwray.groupie.GroupAdapter;
import com.xwray.groupie.ViewHolder;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Client_Viewitems extends AppCompatActivity {
    private ArrayList<Store> StoreItem = new ArrayList<>();
    ViewItemRecyclerAdapter adapter;
    RecyclerView recyclerView;
    int size = 0;
    final Context context = this;
    private Button button;
    ImageButton Logout;
    private SearchView mSearchView;
    private FirebaseFunctions mFunctions;
    private String itemCategory = "Gowns";
    private GroupAdapter groupieAdapter = new GroupAdapter<ViewHolder>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_client__viewitems);
        getSupportActionBar().setTitle("Gowns");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        refs();

        recyclerView.addItemDecoration(new DividerItemDecoration(this, LinearLayoutManager.VERTICAL));
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        mFunctions = FirebaseFunctions.getInstance();

        loadAllItemsInItemCategory(itemCategory);
    }

    private void loadAllItemsInItemCategory(String itemCategory) {
        getRelatedItems(itemCategory).addOnCompleteListener(new OnCompleteListener<ArrayList<String>>() {
            @Override
            public void onComplete(Task<ArrayList<String>> task) {
                if (task.isSuccessful()){
                    ArrayList<String> itemUid = task.getResult();

                    for (String item: itemUid) {
                        FirebaseFirestore.getInstance()
                                .collection("Items")
                                .document(item)
                                .get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                            @Override
                            public void onComplete(Task<DocumentSnapshot> task) {
                                com.example.morkince.okasyonv2.activities.model.Item item = task.getResult().toObject(com.example.morkince.okasyonv2.activities.model.Item.class);
                                BasicItemViewHolder basicItemViewHolder = new BasicItemViewHolder(item.getItem_uid(),
                                        item.getItem_average_rating(),
                                        item.getItem_price(),
                                        item.getItem_name(),
                                        getApplicationContext(),
                                        item.getItem_display_picture_url());

                                groupieAdapter.add(basicItemViewHolder);
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
                searchForItem(itemQuery, itemCategory).addOnCompleteListener(new OnCompleteListener<ArrayList<String>>() {
                    @Override
                    public void onComplete(Task<ArrayList<String>> task) {
                        if (task.isSuccessful()){
                            ArrayList<String> itemUid = task.getResult();

                            for (String item: itemUid) {
                                FirebaseFirestore.getInstance()
                                        .collection("Items")
                                        .document(item)
                                        .get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                                    @Override
                                    public void onComplete(Task<DocumentSnapshot> task) {
                                        com.example.morkince.okasyonv2.activities.model.Item item = task.getResult().toObject(com.example.morkince.okasyonv2.activities.model.Item.class);
                                        BasicItemViewHolder basicItemViewHolder = new BasicItemViewHolder(item.getItem_uid(),
                                                item.getItem_average_rating(),
                                                item.getItem_price(),
                                                item.getItem_name(),
                                                getApplicationContext(),
                                                item.getItem_display_picture_url());

                                        groupieAdapter.add(basicItemViewHolder);
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
}




