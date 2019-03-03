package com.example.morkince.okasyonv2.activities.client_activities;

import android.content.Context;
import android.content.Intent;
import android.graphics.*;
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
import com.example.morkince.okasyonv2.Custom_Progress_Dialog;
import com.example.morkince.okasyonv2.PopUpDialogs;
import com.example.morkince.okasyonv2.R;
import com.example.morkince.okasyonv2.RandomMessages;
import com.example.morkince.okasyonv2.activities.adapter.CartEventAdapter;
import com.example.morkince.okasyonv2.activities.adapter.ViewCartItemAdapter;
import com.example.morkince.okasyonv2.activities.model.Cart_Item;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.*;
import com.google.firebase.storage.StorageReference;

import javax.annotation.Nullable;
import java.util.ArrayList;

public class ClientViewCartActivty extends AppCompatActivity {
    FirebaseFirestore db;
    private StorageReference mStorageRef;
   /* private ArrayList<Cart_Item> cartitem = new ArrayList<>();*/
    CartEventAdapter adapterEvent;
    ViewCartItemAdapter adapter;
    FirebaseUser Cartitems;
    RatingBar ratingBar;
    Button PlaceOrderbtn;
    CheckBox checkItem;
    TextView ItemName, ItemPrice;
    String user_id;
    TextView textView_CartViewItemsNoMesage;

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


        adapter = new ViewCartItemAdapter(getCartItems(),this);
      //  Log.e("NAKUHA NAKO", adapter.)
        PlaceOrderbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (adapter.checkedItem.size() >0) {
                    String buffer = "";
                    for (Cart_Item item : adapter.checkedItem) {
                        //                    buffer+=item.getCart_item_id() + " ";
                        //                    buffer+=item.getCart_item_group_uid() + " ";
                        FirebaseFirestore.getInstance()
                                .collection("Cart_Items")
                                .document(item.getCart_item_group_uid())
                                .collection("cart_items")
                                .document(item.getCart_item_id())
                                .update("cart_item_in_transaction", true);
                    }

                    new PopUpDialogs(ClientViewCartActivty.this).successDialog("You have ordered "+adapter.checkedItem.size()+" items", "SUCCESS");
                }else {
                    new PopUpDialogs(ClientViewCartActivty.this).infoDialog("Please check an atleast 1 item in your cart", "INFO");
                }

            }
        });

        ItemTouchHelper.SimpleCallback simpleCallback = new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT ) {
            @Override
            public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
                // Item swiped.
                int pos = viewHolder.getAdapterPosition();
                db = FirebaseFirestore.getInstance();
                db.collection("Cart_Items").document(cart_group_uid).collection("cart_items").document(adapter.cartitem.get(pos).getCart_item_id())
                        .delete()
                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {
                                Toast.makeText(getApplicationContext(), "successfully deleted!",
                                        Toast.LENGTH_SHORT).show();

                                Log.d("", "DocumentSnapshot successfully deleted!");
                            }
                        })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Log.w("", "Error deleting document", e);
                            }
                        });
//                adapter.removeItem(pos);
//                datasets.remove(pos);
//                adapter.notifyItemRemoved(pos);
            }

            @Override
            public void onChildDraw(Canvas c, RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, float dX, float dY, int actionState, boolean isCurrentlyActive) {
                Paint p = new Paint();
                Bitmap icon;
                if (actionState == ItemTouchHelper.ACTION_STATE_SWIPE) {

                    View itemView = viewHolder.itemView;
                    float height = (float) itemView.getBottom() - (float) itemView.getTop();
                    float width = height / 3;

                    if (dX < 0) {
                        p.setColor(Color.parseColor("#D32F2F"));
                        RectF background = new RectF((float) itemView.getRight() + dX/4, (float) itemView.getTop(), (float) itemView.getRight(), (float) itemView.getBottom());
                        c.drawRect(background, p);
                        icon = BitmapFactory.decodeResource(getResources(), R.drawable.deletecartitem);
                        RectF icon_dest = new RectF((float) itemView.getRight() - 2 * width, (float) itemView.getTop() + width, (float) itemView.getRight() - width, (float) itemView.getBottom() - width);
                        c.drawBitmap(icon, null, icon_dest, p);

                    }
                }
                super.onChildDraw(c, recyclerView, viewHolder, dX/4, dY, actionState, isCurrentlyActive);
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

    private ArrayList<Cart_Item> getCartItems() {
        final ArrayList<Cart_Item> cartitem = new ArrayList<>();
        db = FirebaseFirestore.getInstance();

        RandomMessages randomMessages = new RandomMessages();
        final Custom_Progress_Dialog custom_progress_dialog = new Custom_Progress_Dialog(this);
        custom_progress_dialog.showDialog("LOADING", randomMessages.getRandomMessage());

        db.collection("Cart_Items")
                .document(cart_group_uid)
                .collection("cart_items")
                .whereEqualTo("cart_item_in_transaction", false)
                .addSnapshotListener(new EventListener<QuerySnapshot>() {
                    @Override
                    public void onEvent(@Nullable QuerySnapshot queryDocumentSnapshots, @Nullable FirebaseFirestoreException e) {
                        if (e != null) {
                            Log.e("View Cart", e.toString());
                            return;
                        }

                        if (queryDocumentSnapshots != null && queryDocumentSnapshots.size()>0) {
                            //clear the list
                            textView_CartViewItemsNoMesage.setVisibility(View.INVISIBLE);
                            cartitem.clear();
                            adapter = new ViewCartItemAdapter(cartitem, ClientViewCartActivty.this);
                            recyclerView.addItemDecoration(new DividerItemDecoration(ClientViewCartActivty.this, LinearLayoutManager.HORIZONTAL));
                            recyclerView.setLayoutManager(new LinearLayoutManager(ClientViewCartActivty.this));
                            recyclerView.setAdapter(adapter);

                            for (QueryDocumentSnapshot document : queryDocumentSnapshots) {
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
                                custom_progress_dialog.dissmissDialog();
                            }
                            else {
                                adapter = new ViewCartItemAdapter(cartitem, ClientViewCartActivty.this);
                                recyclerView.addItemDecoration(new DividerItemDecoration(ClientViewCartActivty.this, LinearLayoutManager.HORIZONTAL));
                                recyclerView.setLayoutManager(new LinearLayoutManager(ClientViewCartActivty.this));
                                recyclerView.setAdapter(adapter);
                                custom_progress_dialog.dissmissDialog();
                            }
                        } else {
                            //clear the list
                            cartitem.clear();
                            adapter = new ViewCartItemAdapter(cartitem, ClientViewCartActivty.this);
                            recyclerView.addItemDecoration(new DividerItemDecoration(ClientViewCartActivty.this, LinearLayoutManager.HORIZONTAL));
                            recyclerView.setLayoutManager(new LinearLayoutManager(ClientViewCartActivty.this));
                            recyclerView.setAdapter(adapter);
                            custom_progress_dialog.dissmissDialog();
                            textView_CartViewItemsNoMesage.setVisibility(View.VISIBLE);
                        }
                    }
                });

//                .get()
//                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
//                    @Override
//                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
//                        if (task.isSuccessful()) {
//                            for (QueryDocumentSnapshot document : task.getResult()) {
//                                Log.e("THIS IS THE DATA", document.getId() + " => " + document.getData());
//                                Cart_Item Item = document.toObject(Cart_Item.class);
//                                Log.e("Item THIS",  "" + Item.getcart_item_name()  + " lALAA");
//                                Log.e("Item THIS",  "" + Item.getcart_item_Rating() + " lALAA");
//                                Log.e("Item THIS",  "" + Item.getcart_item_order_cost() + " lALAA");
//                                cartitem.add(Item);
//                                //recipes =task.getDocuments().get(0).toObject(questionObject.class);
//                            }
//
//                            Log.d("RECIPES!", cartitem.toString());
//
//                            if(cartitem.isEmpty())
//                            {
//                                Toast.makeText(getApplicationContext(), "NO RECORDS TO SHOW",
//                                        Toast.LENGTH_SHORT).show();
//                            }
//                            else {
//                               adapter = new ViewCartItemAdapter(cartitem, ClientViewCartActivty.this);
//                                recyclerView.addItemDecoration(new DividerItemDecoration(ClientViewCartActivty.this, LinearLayoutManager.HORIZONTAL));
//                                recyclerView.setLayoutManager(new LinearLayoutManager(ClientViewCartActivty.this));
//                                recyclerView.setAdapter(adapter);
//                            }
//                        } else {
//                            Toast.makeText(getApplicationContext(), "Error in Retrieving Records!!",
//                                    Toast.LENGTH_SHORT).show();
//                        }
//
//                    }
//                });

        return  cartitem;
    }


//    private ArrayList<Cart_Item> getCartItem() {
//       final  ArrayList<Cart_Item> cartitem= new ArrayList<>();
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
        textView_CartViewItemsNoMesage = findViewById(R.id.textView_CartViewItemsNoMesage);
//        checkItem = findViewById(R.id.checkBox_itemtoelacetoorder);
    }
}
