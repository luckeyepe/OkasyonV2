package com.example.morkince.okasyonv2.activities.client_activities;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.ToggleButton;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.morkince.okasyonv2.*;
import com.example.morkince.okasyonv2.activities.homepage_supplier_activities.SupplierEditItemDetailsActivity;
import com.example.morkince.okasyonv2.activities.homepage_supplier_activities.SupplierHomePage;
import com.example.morkince.okasyonv2.activities.model.Item;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import androidx.appcompat.app.AppCompatActivity;
import android.view.MenuItem;
import android.widget.Toast;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.firestore.auth.User;
import com.kd.dynamic.calendar.generator.ImageGenerator;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Locale;

public class ClientItemDetailActivity extends AppCompatActivity {
    BottomNavigationView bottomNavigationView;
    TextView textView_ActivityClientFindItemNameofTheItem,textView_ActivityClientFindItemPriceofTheItem,textView_ActivityClientFindItemDetails,ClientItemDetail_reviewsTextView;
    RatingBar ratingBar_ActivityClientFindItemRating;
    RecyclerView recyclerView_ActivityClientFindItemRecyclerViewReviews,recyclerView_ActivityClientFindItemRecyclerViewImages;
    ToggleButton toggleButton_ActivityClientFindItemToggleForRentAndSale;
    String eventUID;
    FirebaseUser user;
    FirebaseFirestore db;

    private ArrayList<Reviews> reviews = new ArrayList<>();
    StoreReviewsAdapter adapter;


    String item_uid;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_client_item_detail);
        refs();
        Intent intent = getIntent();
        item_uid = intent.getStringExtra("item_uid");
        eventUID = intent.getStringExtra("event_event_uid");
        getValues();
        loadReviews();

        bottomNavigationView = findViewById(R.id.bottomNavigationView_ActivityClientFindItemBottomNavigationforMessageandAddtoCart);
        bottomNavigationView.setOnNavigationItemSelectedListener(bottomNavigationViewListener);
    }

    public void loadReviews()
    {
        db = FirebaseFirestore.getInstance();
        db.collection("Item_Rating").document("item_rating").collection(item_uid).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    for (QueryDocumentSnapshot document : task.getResult()) {
                        String evaluatorName=document.getString("itemRating_reviewerName");
                        Long numStars=(Long)document.get("itemRating_starRating");
                        String reviewComment=document.getString("itemRating_review");
                        reviews.add(new Reviews(evaluatorName,numStars,reviewComment));
                    }

                    adapter = new StoreReviewsAdapter(reviews, ClientItemDetailActivity.this);
                    recyclerView_ActivityClientFindItemRecyclerViewReviews.addItemDecoration(new DividerItemDecoration(ClientItemDetailActivity.this, LinearLayoutManager.VERTICAL));
                    recyclerView_ActivityClientFindItemRecyclerViewReviews.setLayoutManager(new LinearLayoutManager(ClientItemDetailActivity.this));
                    recyclerView_ActivityClientFindItemRecyclerViewReviews.setAdapter(adapter);

                } else {
                    Toast.makeText(getApplicationContext(), "Error in Retrieving Records!!",
                            Toast.LENGTH_SHORT).show();
                }

                ClientItemDetail_reviewsTextView.setText("REVIEWS(" + reviews.size() + ")");
            }
        });
    }


    public void getValues()
    {
        db = FirebaseFirestore.getInstance();
        db.collection("Items").document(item_uid).get()
                .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {

                        if (task.isSuccessful()) {
                            DocumentSnapshot document = task.getResult();
                            if (document.exists()) {
                                Item itemDetails=document.toObject(Item.class);
                                textView_ActivityClientFindItemNameofTheItem.setText(itemDetails.getItem_name());
                                textView_ActivityClientFindItemPriceofTheItem.setText(itemDetails.getItem_price() + "");
                                textView_ActivityClientFindItemDetails.setText(itemDetails.getItem_description());
                                ratingBar_ActivityClientFindItemRating.setRating(Float.parseFloat(itemDetails.getItem_average_rating()+""));

                            } else {
                                Log.d("", "No such document exist");
                            }
                        } else {
                            Log.d("", "Failed with ", task.getException());
                        }
                    }
                });

    }



    public void refs()
    {
        textView_ActivityClientFindItemNameofTheItem =findViewById(R.id.textView_ActivityClientFindItemNameofTheItem);
        textView_ActivityClientFindItemPriceofTheItem =findViewById(R.id.textView_ActivityClientFindItemPriceofTheItem);
        textView_ActivityClientFindItemDetails =findViewById(R.id.textView_ActivityClientFindItemDetails);
        ratingBar_ActivityClientFindItemRating =findViewById(R.id.ratingBar_ActivityClientFindItemRating);
        recyclerView_ActivityClientFindItemRecyclerViewReviews =findViewById(R.id.recyclerView_ActivityClientFindItemRecyclerViewReviews);
        toggleButton_ActivityClientFindItemToggleForRentAndSale =findViewById(R.id.toggleButton_ActivityClientFindItemToggleForRentAndSale);
        recyclerView_ActivityClientFindItemRecyclerViewImages =findViewById(R.id.recyclerView_ActivityClientFindItemRecyclerViewImages);
        ClientItemDetail_reviewsTextView =findViewById(R.id.ClientItemDetail_reviewsTextView);
    }


    private BottomNavigationView.OnNavigationItemSelectedListener bottomNavigationViewListener =
            new BottomNavigationView.OnNavigationItemSelectedListener() {
                @Override
                public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                    int id = menuItem.getItemId();
                    if (id == R.id.navigation_addtocart)
                    {
                        showAlert("Add Item to Cart?", "Confirm");

                    }
                    else if (id == R.id.navigation_message_now)
                    {
                        Toast.makeText(getApplicationContext(),"Message Now",Toast.LENGTH_SHORT).show();
                    }
                    return true;
                }
            };


    public void showAlert(String Message,String label)
    {
        //set alert for executing the task
        AlertDialog.Builder alert = new AlertDialog.Builder(ClientItemDetailActivity.this);
        alert.setTitle(""+label);
        alert.setMessage(""+Message);

        alert.setPositiveButton("YES", new DialogInterface.OnClickListener() {
            public void onClick (DialogInterface dialog, int id){
                //CODE GOES HERE TO ADD TO CART
                FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();

                HashMap<String, String> map = new HashMap<>();
                map.put("cart_item_item_uid", item_uid);
                map.put("cart_item_user_uid", currentUser.getUid());
                db = FirebaseFirestore.getInstance();
                db.collection("Cart_Items").document("cart_items").collection(eventUID).add(map);
                dialog.cancel();
            }
        });

        Dialog dialog = alert.create();
        //  dialog.getWindow().setType(WindowManager.LayoutParams.TYPE_SYSTEM_ALERT);
        dialog.show();
    }
}