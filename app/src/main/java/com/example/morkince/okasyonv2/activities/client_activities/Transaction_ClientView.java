package com.example.morkince.okasyonv2.activities.client_activities;

import android.content.Context;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.morkince.okasyonv2.R;
import com.example.morkince.okasyonv2.activities.adapter.TransactionRecyclerClientAdapter;
import com.example.morkince.okasyonv2.activities.model.Cartv1;

import java.util.ArrayList;

public class Transaction_ClientView extends AppCompatActivity {
    private ArrayList<Cartv1> CartItem = new ArrayList<>();
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

        Cartv1 item1 = new Cartv1();
        item1.setItemName("Marc's Gown");
        item1.setCart_item_order_cost(100.00);

        Cartv1 item2 = new Cartv1();
        item2.setItemName("Japhet's SoundSystems");
        item2.setCart_item_order_cost(200.00);

        Cartv1 item3 = new Cartv1();
        item3.setItemName("Mikay's Eatery");
        item3.setCart_item_order_cost(300.00);


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

        recyclerView = findViewById(R.id.recyclerView_transactionViewActivtyRecyler);
    }
}
