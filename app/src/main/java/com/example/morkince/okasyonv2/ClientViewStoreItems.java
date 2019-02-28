package com.example.morkince.okasyonv2;

import android.content.Intent;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.morkince.okasyonv2.activities.model.Item;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.*;

import javax.annotation.Nullable;
import java.util.ArrayList;

public class ClientViewStoreItems extends AppCompatActivity {

    String storeID,user_role;
    FirebaseFirestore db;

    RecyclerView recyclerView;
    View_Items_Recycler_Adapter adapter;

    private ArrayList<Item> items;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_client_view_store_items);
        refs();
        Intent intent = getIntent();
        storeID = intent.getStringExtra("storeID");
        user_role = intent.getStringExtra("user_role");
        showStoreItems();
    }


    public void showStoreItems()
    {
        db = FirebaseFirestore.getInstance();
        db.collection("Items").whereEqualTo("item_store_id",storeID).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    items= new ArrayList<>();
                    for (QueryDocumentSnapshot document : task.getResult()) {

                        String itemName=document.getString("item_name");
                        double numStars=3.0;//(Long)document.get("storeRating_starRating");
                        double itemPrice=Double.parseDouble(document.get("item_price").toString());
                        String item_uid=document.getString("item_uid");
                        String item_category=document.getString("item_category_id");
                        String item_description=document.getString("item_description");
                        Boolean isForSale = Boolean.valueOf(document.get("item_for_sale").toString());
                        Boolean isPerSquareUnit = Boolean.valueOf(document.get("item_is_per_sqr_unit_of_measurement").toString());
                        String price_description = document.getString("item_price_description");
                        String store_id = document.getString("item_store_id");
                        String item_tag=document.getString("item_tag");
                        String item_doc=document.getString("item_doc");
                        String item_termsAndConditions=document.getString("item_termsAndConditions");


                        // Log.e("HERE IT IS ", itemName + numStars + " " + itemPrice );
                        items.add(new Item(itemName,numStars,itemPrice,item_uid,item_category,item_description,isForSale,isPerSquareUnit,price_description,store_id,item_tag,item_termsAndConditions));
                        adapter = new View_Items_Recycler_Adapter(items,ClientViewStoreItems.this,user_role);
                        recyclerView.setLayoutManager(new GridLayoutManager(ClientViewStoreItems.this,2));
                        recyclerView.setAdapter(adapter);
                    }
                } else {
                    Toast.makeText(getApplicationContext(), "Error in Retrieving Records!!",
                            Toast.LENGTH_SHORT).show();
                }
                if(items.size()==0)
                {
                    Toast.makeText(getApplicationContext(), "No Items to Show!", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        db.collection("Items").whereEqualTo("item_store_id",storeID).addSnapshotListener(this, new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot queryDocumentSnapshots, @Nullable FirebaseFirestoreException e) {
                ArrayList<Item> itemsTOUpdate = new ArrayList<>();
                if(e!=null)
                {
                    return;
                }

                showStoreItems();
            }
        });

    }


    public void refs()
    {
        recyclerView=findViewById(R.id.clientViewStoreItems_RecyclerView);

    }
}
