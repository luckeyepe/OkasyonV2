package com.example.morkince.okasyonv2.activities.client_activities;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.morkince.okasyonv2.R;
import com.example.morkince.okasyonv2.activities.adapter.CartEventAdapter;
import com.example.morkince.okasyonv2.activities.adapter.ViewCartItemAdapter;
import com.example.morkince.okasyonv2.activities.model.Cart_Item;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;

public class ClientViewCartActivty extends AppCompatActivity {
    FirebaseFirestore db;
    private StorageReference mStorageRef;
    private ArrayList<Cart_Item> cartitem = new ArrayList<>();
    CartEventAdapter adapterEvent;
    ViewCartItemAdapter adapter;
    FirebaseUser Cartitems;
    RatingBar ratingBar;
    TextView ItemName, ItemPrice;
    String user_id;
    RecyclerView recyclerView;
    int size = 0;
    final Context context = this;
    String cart_group_uid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity__view_cart);
        getSupportActionBar().setTitle("Your Cart");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        refs();
        Intent intent = getIntent();
        cart_group_uid = intent.getStringExtra("event_cart_group_uid");

        db = FirebaseFirestore.getInstance();

        db.collection("Cart_Items")
                .document(cart_group_uid)
                .collection("cart_items").get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                Log.e("THIS IS THE DATA", document.getId() + " => " + document.getData());
                                Cart_Item Item = document.toObject(Cart_Item.class);
                                Log.e("Item THIS",  "" + Item.getcart_item_name()  + " lALAA");
                                Log.e("Item THIS",  "" + Item.getcart_item_Rating() + " lALAA");
                                Log.e("Item THIS",  "" + Item.getcart_item_order_cost() + " lALAA");
                                cartitem.add(Item);
                                //recipes =task.getDocuments().get(0).toObject(questionObject.class);
                            }

                            Log.d("RECIPES!", cartitem.toString());

                            if(cartitem.isEmpty())
                            {
                                Toast.makeText(getApplicationContext(), "NO RECORDS TO SHOW",
                                        Toast.LENGTH_SHORT).show();
                            }
                            else {
                                adapter = new ViewCartItemAdapter(cartitem, ClientViewCartActivty.this);
                                recyclerView.addItemDecoration(new DividerItemDecoration(ClientViewCartActivty.this, LinearLayoutManager.HORIZONTAL));
                                recyclerView.setLayoutManager(new LinearLayoutManager(ClientViewCartActivty.this));
                                recyclerView.setAdapter(adapter);
                            }
                        } else {
                            Toast.makeText(getApplicationContext(), "Error in Retrieving Records!!",
                                    Toast.LENGTH_SHORT).show();
                        }

                    }
        });
//        recyclerView.addItemDecoration(new DividerItemDecoration(this, LinearLayoutManager.VERTICAL));
//        recyclerView.setLayoutManager(new LinearLayoutManager(this));

    }
    public void refs()
    {
        ratingBar= findViewById(R.id.ratingBar_storeRate);
        ItemName= findViewById(R.id.textView_ItemName3);
        ItemPrice = findViewById(R.id.textView_ItemName3);
        recyclerView = findViewById(R.id.RecyclerView_CartViewItems_Client);
    }
}
