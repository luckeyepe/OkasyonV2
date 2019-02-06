package com.example.morkince.okasyonv2.activities.HomePage_Client_activity;

import android.app.Dialog;
import android.app.SearchManager;
import android.app.SearchableInfo;
import android.content.Context;
import android.content.Intent;
import android.widget.SearchView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.core.app.NavUtils;
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
import com.example.morkince.okasyonv2.activities.login_activities.MainActivity;
import com.example.morkince.okasyonv2.activities.model.Store;
import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.Task;
import com.google.firebase.functions.FirebaseFunctions;
import com.google.firebase.functions.HttpsCallableResult;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_client__viewitems);
        getSupportActionBar().setTitle("Gowns");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        refs();
        mFunctions = FirebaseFunctions.getInstance();


        // add button listener


        // custom dialog


//                // set the custom dialog components - text, image and button
////                TextView text = (TextView) dialog.findViewById(R.id.text);
////                text.setText("Android custom dialog example!");
////                ImageView image = (ImageView) dialog.findViewById(R.id.image);
////                image.setImageResource(R.drawable.ic_launcher);


        Store item1 = new Store();
        item1.setStoreName("Marc's Gown");
        item1.setLocation("Hernan Cortes");
        item1.setPrice(100.00);

        Store item2 = new Store();
        item2.setStoreName("Japhet's SoundSystems");
        item2.setLocation("Nasipit Talamban");
        item2.setPrice(200.00);

        Store item3 = new Store();
        item3.setStoreName("Mikay's Eatery");
        item3.setLocation("629 Gov. Cuenco Ave Banilad Cebu City");
        item3.setPrice(300.00);


        StoreItem.add(item1);
        StoreItem.add(item2);
        StoreItem.add(item3);

        adapter = new ViewItemRecyclerAdapter(StoreItem, Client_Viewitems.this);
        recyclerView.setAdapter(adapter);


        recyclerView.addItemDecoration(new DividerItemDecoration(this, LinearLayoutManager.VERTICAL));
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater mi = getMenuInflater();
        mi.inflate(R.menu.menuclientviewitemsactionbar, menu);
//        mSearchView = (SearchView) menu.findItem(R.id.app_bar_search).getActionView();
////        setupSearchView();

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

                Toast.makeText(getApplicationContext(), query, Toast.LENGTH_SHORT).show();
                // Do your task here

                return false;
            }

        });

        return true;
    }

//    private void setupSearchView() {
//
//        mSearchView.setIconifiedByDefault(true);
//
//        SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
//        if (searchManager != null) {
//            List<SearchableInfo> searchables = searchManager.getSearchablesInGlobalSearch();
//
//            // Try to use the "applications" global search provider
//            SearchableInfo info = searchManager.getSearchableInfo(getComponentName());
//            for (SearchableInfo inf : searchables) {
//                if (inf.getSuggestAuthority() != null
//                        && inf.getSuggestAuthority().startsWith("applications")) {
//                    info = inf;
//                    Toast.makeText(getApplicationContext(), "This is my Toast message!" + info,
//                            Toast.LENGTH_LONG).show();
//                }
//            }
//            mSearchView.setSearchableInfo(info);
//            Toast.makeText(getApplicationContext(), "This is my Toast message!" + info,
//                    Toast.LENGTH_LONG).show();
//        }
//
//    }


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

    public void refs() {

        recyclerView = findViewById(R.id.RecyclerView_ClientViewItems);
    }

    private Task<Integer> addNumbers(int a, int b) {
        // Create the arguments to the callable function, which are two integers
        Map<String, Object> data = new HashMap<>();
        data.put("firstNumber", a);
        data.put("secondNumber", b);

        // Call the function and extract the operation from the result
        return mFunctions
                .getHttpsCallable("addNumbers")
                .call(data)
                .continueWith(new Continuation<HttpsCallableResult, Integer>() {
                    @Override
                    public Integer then(@NonNull Task<HttpsCallableResult> task) throws Exception {
                        // This continuation runs on either success or failure, but if the task
                        // has failed then getResult() will throw an Exception which will be
                        // propagated down.
                        Map<String, Object> result = (Map<String, Object>) task.getResult().getData();
                        return (Integer) result.get("operationResult");
                    }
                });
    }
}




