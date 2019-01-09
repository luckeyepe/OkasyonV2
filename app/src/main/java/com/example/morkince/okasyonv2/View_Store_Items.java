package com.example.morkince.okasyonv2;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import java.util.ArrayList;

public class View_Store_Items extends AppCompatActivity {

    private ArrayList<Item> items;
    View_Items_Recycler_Adapter adapter;
    RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view__store__items);
        refs();
        items= new ArrayList<>();
        Item item1 = new Item("Tarpaulin", 3 , 10);
        Item item2 = new Item("Sticker", 4 , 50);
        Item item3 = new Item("Sticker on Sintra", 5 , 110);
        items.add(item1);
        items.add(item2);
        items.add(item3);

        Log.e("ITEMS!",items.toString());
        adapter = new View_Items_Recycler_Adapter(items,View_Store_Items.this);
        recyclerView.addItemDecoration(new DividerItemDecoration(this, LinearLayoutManager.HORIZONTAL));
        recyclerView.setLayoutManager(new GridLayoutManager(this,2));
        recyclerView.setAdapter(adapter);
    }

    public void refs()
    {
        recyclerView=findViewById(R.id.viewStoreItems_recyclerViewItems);
    }
}
