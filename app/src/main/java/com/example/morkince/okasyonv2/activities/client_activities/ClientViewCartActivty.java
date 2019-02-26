package com.example.morkince.okasyonv2.activities.client_activities;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.widget.*;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.morkince.okasyonv2.R;
import com.example.morkince.okasyonv2.activities.adapter.CartEventAdapter;
import com.example.morkince.okasyonv2.activities.adapter.ViewCartItemAdapter;
import com.example.morkince.okasyonv2.activities.model.CartItem;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;

public class ClientViewCartActivty extends AppCompatActivity {
    FirebaseFirestore db;
    private StorageReference mStorageRef;
   /* private ArrayList<CartItem> cartitem = new ArrayList<>();*/
    CartEventAdapter adapterEvent;
    ViewCartItemAdapter adapter;
    FirebaseUser Cartitems;
    RatingBar ratingBar;
    Button PlaceOrderbtn;
    CheckBox checkItem;
    TextView ItemName, ItemPrice;
    String user_id;

    RecyclerView recyclerView,recyclerView2;
    int size = 0;
    final Context context = this;
    String cart_group_uid, Cart_item_id;
    private RecyclerView.Adapter Adapter;
    private RecyclerView.LayoutManager layoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity__view_cart);
        getSupportActionBar().setTitle("Your Cart");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        refs();
        Intent intent = getIntent();
        cart_group_uid = intent.getStringExtra("event_cart_group_uid");
        Cart_item_id = intent.getStringExtra("cart_item_id");
        adapter= new ViewCartItemAdapter(getCartItems(),this);
      //  Log.e("NAKUHA NAKO", adapter.)
        PlaceOrderbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {



             String buffer="";
                for(CartItem item : adapter.checkedItem)
                {
                    buffer+=item.getCart_item_id() + " ";
                    buffer+=item.getCart_item_group_uid() + " ";
                }
                Toast.makeText(getApplicationContext(), buffer,
                        Toast.LENGTH_SHORT).show();
            }
        });
        ItemTouchHelper.SimpleCallback simpleCallback = new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
                // Item swiped.
                int pos = viewHolder.getAdapterPosition();
//                adapter.removeItem(pos);
//                datasets.remove(pos);
                adapter.notifyItemRemoved(pos);
            }
        };

        // Attach it to recyclerview
        new ItemTouchHelper(simpleCallback).attachToRecyclerView(recyclerView);

//        cart_item_id = intent.getStringExtra("cart_item_id");



//        db = FirebaseFirestore.getInstance();
//        db.collection("Cart_Items")
//                .document(cart_group_uid)
//                .collection("cart_items")
//                .add(newItem)
//                .addOnCompleteListener(new OnCompleteListener<DocumentReference>() {
//                    @Override
//                    public void onComplete(@NonNull Task<DocumentReference> task) {
//                        if (task.isSuccessful()) {
//                            progressDialog.dismiss();
//                            showAlert("Successfully Saved Item", "SUCCESS!");
//
//                        } else {
//                            Log.w("", "Error adding document " + task.getException().toString());
//                        }
//                    }
//                });

    }

    private ArrayList<CartItem> getCartItems() {
        final ArrayList<CartItem> cartitem = new ArrayList<>();
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
                                CartItem Item = document.toObject(CartItem.class);
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

        return  cartitem;
    }


//    private ArrayList<CartItem> getCartItem() {
//       final  ArrayList<CartItem> cartitem= new ArrayList<>();
//        Intent intent = getIntent();
//        cart_group_uid = intent.getStringExtra("event_cart_group_uid");
//        Cart_item_id = intent.getStringExtra("cart_Item_id");
////        cart_item_id = intent.getStringExtra("cart_item_id");
//
//        return  null;
//    }

    public void refs()
    {
        ratingBar= findViewById(R.id.ratingBar_storeRate);
        ItemName= findViewById(R.id.textView_ItemName3);
        ItemPrice = findViewById(R.id.textView_ItemName3);
        recyclerView = findViewById(R.id.RecyclerView_CartViewItems_Client);
        PlaceOrderbtn =findViewById(R.id.btn_PlaceOrder);
//        checkItem = findViewById(R.id.checkBox_itemtoelacetoorder);
    }
}
