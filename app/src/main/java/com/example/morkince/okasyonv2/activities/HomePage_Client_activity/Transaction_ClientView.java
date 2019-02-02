package com.example.morkince.okasyonv2.activities.HomePage_Client_activity;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import com.example.morkince.okasyonv2.R;
import com.example.morkince.okasyonv2.activities.adapter.TransactionRecyclerClientAdapter;
import com.example.morkince.okasyonv2.activities.adapter.ViewCartAdapter;
import com.example.morkince.okasyonv2.activities.model.Cart;

import java.util.ArrayList;

public class Transaction_ClientView extends AppCompatActivity {
    private ArrayList<Cart> CartItem = new ArrayList<>();
    TransactionRecyclerClientAdapter adapter;
    RecyclerView recyclerView;
    int size = 0;
    final Context context = this;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transaction__client_view);
        getSupportActionBar().setTitle("Transaction00123456789012345");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        refs();
        Cart item1 = new Cart();
        item1.setStoreName("Marc's Gown");
        item1.setPrice(100.00);

        Cart item2 = new Cart();
        item2.setStoreName("Japhet's SoundSystems");
        item2.setPrice(200.00);

        Cart item3 = new Cart();
        item3.setStoreName("Mikay's Eatery");
        item3.setPrice(300.00);


        CartItem.add(item1);
        CartItem.add(item2);
        CartItem.add(item3);

        adapter = new TransactionRecyclerClientAdapter(CartItem, Transaction_ClientView.this);
        recyclerView.setAdapter(adapter);


        recyclerView.addItemDecoration(new DividerItemDecoration(this, LinearLayoutManager.VERTICAL));
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

    }
    public void refs()
    {

        recyclerView = findViewById(R.id.Recycler_TransactionView_Client);
    }
}
