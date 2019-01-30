package com.example.morkince.okasyonv2.activities.HomePage_Client_activity;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import com.example.morkince.okasyonv2.R;
import com.example.morkince.okasyonv2.activities.adapter.ViewCartAdapter;
import com.example.morkince.okasyonv2.activities.adapter.ViewItemRecyclerAdapter;
import com.example.morkince.okasyonv2.activities.adapter.ViewOrganizersAdapter;
import com.example.morkince.okasyonv2.activities.model.Cart;
import com.example.morkince.okasyonv2.activities.model.Store;

import java.util.ArrayList;

public class activity_ViewCart extends AppCompatActivity {
    private ArrayList<Cart> CartItem = new ArrayList<>();
    ViewCartAdapter adapter;
    RecyclerView recyclerView;
    int size = 0;
    final Context context = this;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity__view_cart);
        getSupportActionBar().setTitle("Your Cart");
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

        adapter = new ViewCartAdapter(CartItem, activity_ViewCart.this);
        recyclerView.setAdapter(adapter);


        recyclerView.addItemDecoration(new DividerItemDecoration(this, LinearLayoutManager.VERTICAL));
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

    }
    public void refs()
    {

        recyclerView = findViewById(R.id.RecyclerView_CartViewItems_Client);
    }
}
