package com.example.morkince.okasyonv2.activities.client_activities;

import android.content.Context;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.morkince.okasyonv2.R;
import com.example.morkince.okasyonv2.activities.adapter.ViewCartAdapter;
import com.example.morkince.okasyonv2.activities.model.Cart;

import java.util.ArrayList;

public class ClientViewCartActivty extends AppCompatActivity {
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

        adapter = new ViewCartAdapter(CartItem, ClientViewCartActivty.this);
        recyclerView.setAdapter(adapter);


        recyclerView.addItemDecoration(new DividerItemDecoration(this, LinearLayoutManager.VERTICAL));
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

    }
    public void refs()
    {

        recyclerView = findViewById(R.id.RecyclerView_CartViewItems_Client);
    }
}
