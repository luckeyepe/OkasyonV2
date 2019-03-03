package com.example.morkince.okasyonv2;

import android.content.Intent;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;
import com.example.morkince.okasyonv2.activities.model.Item;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.*;
import com.google.firebase.storage.StorageReference;

import javax.annotation.Nullable;
import java.util.ArrayList;

public class View_Store_Items extends AppCompatActivity {

    private ArrayList<Item> items;
    View_Items_Recycler_Adapter adapter;
    Button viewStoreItems_addItemsButton;
    RecyclerView recyclerView;
    FirebaseUser user;
    FirebaseFirestore db;
    String user_role="Supplier";
  //  Task<QuerySnapshot>  taskUpdated=null;
    CollectionReference taskUpdated;
    private StorageReference mStorageRef;
    String  storeID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view__store__items);
        getSupportActionBar().setTitle("My Items");
        refs();
        viewStoreItems_addItemsButton.setOnClickListener(addItem);

        //GET STORE ID
        Intent intent = getIntent();
        storeID = intent.getStringExtra("storeID");
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
                        double numStars= (Double.parseDouble(document.get("item_average_rating") + " " ));
                        double itemPrice=Double.parseDouble(document.get("item_price").toString());
                        String item_uid=document.getString("item_uid");
                        String item_category=document.getString("item_category_id");
                        String item_description=document.getString("item_description");
                        Boolean isForSale = Boolean.valueOf(document.get("item_for_sale").toString());
                        Boolean isPerSquareUnit = Boolean.valueOf(document.get("item_is_per_sqr_unit_of_measurement").toString());
                        String price_description = document.getString("item_price_description");
                        String store_id = document.getString("item_store_id");
                        String item_tag=document.getString("item_tag");
                        String item_termsAndConditions=document.getString("item_termsAndConditions");


                       // Log.e("HERE IT IS ", itemName + numStars + " " + itemPrice );
                        items.add(new Item(itemName,numStars,itemPrice,item_uid,item_category,item_description,isForSale,isPerSquareUnit,price_description,store_id,item_tag,item_termsAndConditions));
                        adapter = new View_Items_Recycler_Adapter(items,View_Store_Items.this,user_role);
                        recyclerView.setLayoutManager(new GridLayoutManager(View_Store_Items.this,2));
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

              /*  for(DocumentChange dc : queryDocumentSnapshots.getDocumentChanges()){
                    QueryDocumentSnapshot document=dc.getDocument();
                    Log.e("THIS IS THE DATA", document.getId() + " => " + document.getData());
                    String itemName=document.getString("item_name");
                    double numStars=3.0;//(Long)document.get("storeRating_starRating");
                    double itemPrice=Double.parseDouble(document.get("item_price").toString());
                    String item_uid=document.getString("item_uid");
                    //  double priceOfItem = itemPrice.doubleValue();

                    Log.e("HERE IT IS ", itemName + numStars + " " + itemPrice );
                    itemsTOUpdate.add(new Item(itemName,numStars,itemPrice,item_uid));
                    adapter = new View_Items_Recycler_Adapter(itemsTOUpdate,View_Store_Items.this);

                    //   recyclerView.addItemDecoration(new DividerItemDecoration(View_Store_Items.this, LinearLayoutManager.HORIZONTAL));
                    recyclerView.setLayoutManager(new GridLayoutManager(View_Store_Items.this,2));
                    recyclerView.setAdapter(adapter);
                }*/
            showStoreItems();
            }
        });

    }


    public View.OnClickListener addItem = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            Intent intent = new Intent(View_Store_Items.this,SupplierAddItem.class);
            intent.putExtra("storeID",storeID);
            startActivity(intent);
        }
    };

    public void refs()
    {
        recyclerView=findViewById(R.id.viewStoreItems_recyclerViewItems);
        viewStoreItems_addItemsButton=findViewById(R.id.viewStoreItems_addItemsButton);

    }
}
