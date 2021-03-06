package com.example.morkince.okasyonv2.activities.client_activities;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.*;
import android.widget.*;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.morkince.okasyonv2.*;
import com.example.morkince.okasyonv2.R;
import com.example.morkince.okasyonv2.activities.ClientItemDetailsReviewActivity;
import com.example.morkince.okasyonv2.activities.chat_activities.ChatLogActivitiy;
import com.example.morkince.okasyonv2.activities.model.Cart_Item;
import com.example.morkince.okasyonv2.activities.model.Cartv1;
import com.example.morkince.okasyonv2.activities.model.Item;
import com.example.morkince.okasyonv2.activities.model.User;
import com.example.morkince.okasyonv2.testingphase.ItemImagesModel;
import com.example.morkince.okasyonv2.testingphase.ItemImagesRecyclerAdapter;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import androidx.appcompat.app.AppCompatActivity;
import android.view.MenuItem;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.*;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.kd.dynamic.calendar.generator.ImageGenerator;

import javax.annotation.Nullable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Locale;

public class ClientItemDetailActivity extends AppCompatActivity {
    public static final String currentUserUid = FirebaseAuth.getInstance().getCurrentUser().getUid();
    String cart_group_uid,user_role;
    String cartItem_item_id;
    BottomNavigationView bottomNavigationView;
    TextView textView_ActivityClientFindItemNameofTheItem,
            textView_ActivityClientFindItemPriceofTheItem,
            textView_ActivityClientFindItemDetails,
            ClientItemDetail_reviewsTextView,
            textView_clientItemDetailActivityStoreName,
            textView_ActivityClientFindItemTermsandConditions;
    RatingBar ratingBar_ActivityClientFindItemRating;
    RecyclerView recyclerView_ActivityClientFindItemRecyclerViewReviews,recyclerView_ActivityClientFindItemRecyclerViewImages;
    ToggleButton toggleButton_ActivityClientFindItemToggleForRentAndSale;

    String eventUID,store_uid;
    Calendar currentDate;
    Bitmap generatedateIcon;
    ImageGenerator imageGenerator;
    FirebaseUser user;
    FirebaseFirestore db;

    ItemImagesRecyclerAdapter adapterForItemImages;
    int inCounter=1;
    FirebaseStorage firebaseStorage;
    private StorageReference mStorageRef;

    private ArrayList<Reviews> reviews = new ArrayList<>();
    private ArrayList<ItemImagesModel> itemImages = new ArrayList<>();
    StoreReviewsAdapter adapter;


    String item_uid;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_client_item_detail);
        getSupportActionBar().hide();
        refs();
        mStorageRef = FirebaseStorage.getInstance().getReference();
        Intent intent = getIntent();
        item_uid = intent.getStringExtra("item_uid");
        eventUID = intent.getStringExtra("event_event_uid");
        cart_group_uid = intent.getStringExtra("event_cart_group_uid");
        user_role = intent.getStringExtra("user_role");
        bottomNavigationView = findViewById(R.id.bottomNavigationView_ActivityClientFindItemBottomNavigationforMessageandAddtoCart);
        bottomNavigationView.setOnNavigationItemSelectedListener(bottomNavigationViewListener);

        if(user_role.equals("Client"))
        {
            bottomNavigationView.setVisibility(View.GONE);
        }

        getValues();
       // loadReviews();
        loadItemImages();
        imageGenerator = new ImageGenerator(this);

        imageGenerator.setIconSize(50, 50);
        imageGenerator.setDateSize(30);
        imageGenerator.setMonthSize(15);

        imageGenerator.setDatePosition(42);
        imageGenerator.setMonthPosition(15);

        imageGenerator.setDateColor(Color.parseColor("#D81B60"));
        imageGenerator.setMonthColor(Color.parseColor("#ffffff"));

        imageGenerator.setStorageToSDCard(true);

//        clientItemDetails_visitStoreBtn.setOnClickListener(visitStore);
        textView_clientItemDetailActivityStoreName.setOnClickListener(visitStore);

        ClientItemDetail_reviewsTextView.setOnClickListener(goToReviews);

        recyclerView_ActivityClientFindItemRecyclerViewImages.setHasFixedSize(true);
    }

    public View.OnClickListener goToReviews = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent intent= new Intent(ClientItemDetailActivity.this, ClientItemDetailsReviewActivity.class);
            intent.putExtra("item_uid", item_uid);
            startActivity(intent);
            //start activity that show reviews
        }
    };


    private void getStoreName(String store_uid) {
        FirebaseFirestore.getInstance()
                .collection("Store")
                .document(store_uid)
                .addSnapshotListener(new EventListener<DocumentSnapshot>() {
                    @Override
                    public void onEvent(@Nullable DocumentSnapshot documentSnapshot, @Nullable FirebaseFirestoreException e) {
                        if (e!=null){
                            return;
                        }

                        textView_clientItemDetailActivityStoreName.setText(documentSnapshot.get("store_store_name").toString());
                    }
                });
    }


    public void loadItemImages()
    {
        for(int counter=1;counter<=5;counter++) {
            // Create a reference to the file to delete

            StorageReference desertRef = mStorageRef.child("item_images").child(item_uid).child(item_uid + counter);
            inCounter=counter;
            desertRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                @Override
                public void onSuccess(Uri uri) {
                    if(inCounter<5)
                        itemImages.add(new ItemImagesModel(uri));
                    else
                    {
                        itemImages.add(new ItemImagesModel(uri));
                        adapterForItemImages = new ItemImagesRecyclerAdapter(itemImages,ClientItemDetailActivity.this);
                        recyclerView_ActivityClientFindItemRecyclerViewImages.setLayoutManager(new LinearLayoutManager(ClientItemDetailActivity.this,LinearLayoutManager.HORIZONTAL,false));
                        //  recyclerView_itemImages.setLayoutManager(new GridLayoutManager(testingImageSlider.this,2));
                        recyclerView_ActivityClientFindItemRecyclerViewImages.setAdapter(adapterForItemImages);
                    }
                }
            });
        }
    }


    public View.OnClickListener visitStore = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Log.e("THIS IS STORE ID", store_uid);
            Intent intent = new Intent(ClientItemDetailActivity.this,ClientViewStoreDetails.class);
            intent.putExtra("store_uid",store_uid);
            intent.putExtra("user_role","Client");
            intent.putExtra("item_uid",item_uid);
            startActivity(intent);
        }
    };

    public void loadReviews()
    {
        db = FirebaseFirestore.getInstance();
        db.collection("Item_Rating").document("item_rating").collection(item_uid).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    for (QueryDocumentSnapshot document : task.getResult()) {
                        String evaluatorName=document.getString("itemRating_reviewerName");
                        Long numStars=(Long)document.get("itemRating_starRating");
                        String reviewComment=document.getString("itemRating_review");
                        reviews.add(new Reviews(evaluatorName,numStars,reviewComment));
                    }

                    adapter = new StoreReviewsAdapter(reviews, ClientItemDetailActivity.this);
                    recyclerView_ActivityClientFindItemRecyclerViewReviews.addItemDecoration(new DividerItemDecoration(ClientItemDetailActivity.this, LinearLayoutManager.VERTICAL));
                    recyclerView_ActivityClientFindItemRecyclerViewReviews.setLayoutManager(new LinearLayoutManager(ClientItemDetailActivity.this));
                    recyclerView_ActivityClientFindItemRecyclerViewReviews.setAdapter(adapter);

                } else {
                    Toast.makeText(getApplicationContext(), "Error in Retrieving Records!!",
                            Toast.LENGTH_SHORT).show();
                }

                //ClientItemDetail_reviewsTextView.setText("VIEW REVIEWS(" + reviews.size() + ")");
            }
        });
    }


    public void getValues()
    {
        final Custom_Progress_Dialog custom_progress_dialog = new Custom_Progress_Dialog(this);
        custom_progress_dialog.showDialog("LOADING", "Loading item data");

        db = FirebaseFirestore.getInstance();
        db.collection("Items").document(item_uid).get()
                .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {

                        if (task.isSuccessful()) {
                            DocumentSnapshot document = task.getResult();
                            if (document.exists()) {
                                Item itemDetails=document.toObject(Item.class);
                                textView_ActivityClientFindItemNameofTheItem.setText(itemDetails.getItem_name());
                                textView_ActivityClientFindItemPriceofTheItem.setText(itemDetails.getItem_price() + "");
                                textView_ActivityClientFindItemDetails.setText(itemDetails.getItem_description());

                                if(!itemDetails.isItem_for_sale()){
                                    toggleButton_ActivityClientFindItemToggleForRentAndSale.setChecked(!itemDetails.isItem_for_sale());
                                    toggleButton_ActivityClientFindItemToggleForRentAndSale.setVisibility(View.VISIBLE);
                                }else {
                                    toggleButton_ActivityClientFindItemToggleForRentAndSale.setVisibility(View.GONE);
                                }

                                ratingBar_ActivityClientFindItemRating.setRating(Float.parseFloat(itemDetails.getItem_average_rating()+""));
                                store_uid=itemDetails.getItem_store_id();
                                textView_ActivityClientFindItemTermsandConditions.setText(itemDetails.getItem_termsAndConditions());

                                getStoreName(store_uid);

                                custom_progress_dialog.dissmissDialog();

                            } else {
                                Log.d("", "No such document exist");
                            }
                        } else {
                            Log.d("", "Failed with ", task.getException());
                        }
                    }
                });

    }



    public void refs()
    {
        textView_ActivityClientFindItemNameofTheItem =findViewById(R.id.textView_ActivityClientFindItemNameofTheItem);
        textView_ActivityClientFindItemPriceofTheItem =findViewById(R.id.textView_ActivityClientFindItemPriceofTheItem);
        textView_ActivityClientFindItemDetails =findViewById(R.id.textView_ActivityClientFindItemDetails);
        ratingBar_ActivityClientFindItemRating =findViewById(R.id.ratingBar_ActivityClientFindItemRating);
        recyclerView_ActivityClientFindItemRecyclerViewReviews =findViewById(R.id.recyclerView_ActivityClientFindItemRecyclerViewReviews);
        toggleButton_ActivityClientFindItemToggleForRentAndSale =findViewById(R.id.toggleButton_ActivityClientFindItemToggleForRentAndSale);
        recyclerView_ActivityClientFindItemRecyclerViewImages =findViewById(R.id.recyclerView_ActivityClientFindItemRecyclerViewImages);
        ClientItemDetail_reviewsTextView =findViewById(R.id.ClientItemDetail_reviewsTextView);
        textView_clientItemDetailActivityStoreName = findViewById(R.id.textView_clientItemDetailActivityStoreName);
        textView_ActivityClientFindItemTermsandConditions =findViewById(R.id.textView_ActivityClientFindItemTermsandConditions);
    }


    private BottomNavigationView.OnNavigationItemSelectedListener bottomNavigationViewListener =
            new BottomNavigationView.OnNavigationItemSelectedListener() {
                @Override
                public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                    int id = menuItem.getItemId();

                    if (id == R.id.navigation_addtocart)
                    {
                        db = FirebaseFirestore.getInstance();
                        db.collection("Items").document(item_uid).get()
                                .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                                    @Override
                                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                                        if (task.isSuccessful()) {
                                            DocumentSnapshot document = task.getResult();
                                            if (document.exists()) {
                                                Item itemDetails = document.toObject(Item.class);
                                                if (itemDetails.isItem_for_sale() == false) {
                                                    View view = LayoutInflater.from(ClientItemDetailActivity.this).inflate(R.layout.modal_addtocart_for_rent, null);
                                                    final Dialog dialog = new Dialog(ClientItemDetailActivity.this);
                                                    dialog.setContentView(view);
                                                    dialog.show();
                                                    Window window = dialog.getWindow();
                                                    WindowManager.LayoutParams wlp = window.getAttributes();
                                                    wlp.gravity = Gravity.CENTER;
                                                    window.setAttributes(wlp);
                                                    window.setAttributes(wlp);
                                                    final RadioGroup Radiogroup_forDeliveryornot = view.findViewById(R.id.radiogroup_deliverornot);

                                                    final RadioButton forDelivered = view.findViewById(R.id.radioButton2_yes);
                                                    final RadioButton notDelivered = view.findViewById(R.id.radioButton_no);
                                                    final  Button addtocart=view.findViewById(R.id.button_modalAddtocart);
                                                    final EditText txtQuantity=view.findViewById(R.id.editText_itemQuantity2);
                                                    final EditText textxtBudget =view.findViewById(R.id.edittext_Budget);
                                                    final EditText txtStorename=view.findViewById(R.id.editText_Storename);
                                                    final EditText txtLocation=view.findViewById(R.id.editText_Location);
                                                    final TextView dateofitemrented=view.findViewById(R.id.textView_DateRented);
                                                    final TextView dateofitemreturned=view.findViewById(R.id.TextView_DateRented2);
                                                    final ImageView setDate=view.findViewById(R.id.imageView_modalcalendarforrent);
                                                    final ImageView setDate2=view.findViewById(R.id.imageView_modalcalendarforrent2);
                                                    final ImageView close=view.findViewById(R.id.imageButton_modalAddToCartForRentClose);
                                                    close.setOnClickListener(new View.OnClickListener() {
                                                        @Override
                                                        public void onClick(View v) {
                                                            dialog.dismiss();
                                                        }
                                                    });

                                                    setDate.setOnClickListener(new View.OnClickListener() {
                                                        @Override
                                                        public void onClick(View v) {
                                                            Toast.makeText(getApplicationContext(), "Success", Toast.LENGTH_SHORT).show();

                                                            currentDate = Calendar.getInstance();

                                                            int yr= currentDate.get(Calendar.YEAR);
                                                            int mon= currentDate.get(Calendar.MONTH);
                                                            int day= currentDate.get(Calendar.DAY_OF_MONTH);

                                                            DatePickerDialog datePickerDialog= new DatePickerDialog(ClientItemDetailActivity.this, new DatePickerDialog.OnDateSetListener() {
                                                                @Override
                                                                public void onDateSet(DatePicker view, int  selectedYear, int selectedMonth, int selectedDay ) {
                                                                    int monthToDisplay= selectedMonth + 1;
                                                                    dateofitemrented.setText(selectedDay+"-"+monthToDisplay+"-"+selectedYear);

                                                                    currentDate.set(selectedYear,selectedMonth,selectedDay);
                                                                    generatedateIcon= imageGenerator.generateDateImage(currentDate,R.drawable.calendar_icon);
                                                                    setDate.setImageBitmap(generatedateIcon);
                                                                }
                                                            }, yr, mon, day);
                                                            datePickerDialog.show();
                                                        }
                                                    });

                                                    setDate2.setOnClickListener(new View.OnClickListener() {
                                                        @Override
                                                        public void onClick(View v) {
                                                            Toast.makeText(getApplicationContext(), "Success", Toast.LENGTH_SHORT).show();

                                                            currentDate = Calendar.getInstance();

                                                            int yr= currentDate.get(Calendar.YEAR);
                                                            int mon= currentDate.get(Calendar.MONTH);
                                                            int day= currentDate.get(Calendar.DAY_OF_MONTH);

                                                            DatePickerDialog datePickerDialog= new DatePickerDialog(ClientItemDetailActivity.this, new DatePickerDialog.OnDateSetListener() {
                                                                @Override
                                                                public void onDateSet(DatePicker view, int  selectedYear, int selectedMonth, int selectedDay ) {
                                                                    int monthToDisplay= selectedMonth + 1;
                                                                    dateofitemreturned.setText(selectedDay+"-"+monthToDisplay+"-"+selectedYear);

                                                                    currentDate.set(selectedYear,selectedMonth,selectedDay);
                                                                    generatedateIcon= imageGenerator.generateDateImage(currentDate,R.drawable.calendar_icon);
                                                                    setDate2.setImageBitmap(generatedateIcon);
                                                                }
                                                            }, yr, mon, day);
                                                            datePickerDialog.show();
                                                        }


                                                    });

                                                    addtocart.setOnClickListener(new View.OnClickListener() {
                                                        @Override
                                                        public void onClick(View v) {
                                                            if (txtQuantity.getText().toString().trim().isEmpty()) {
                                                                txtQuantity.setError("Please Input Quantity");
                                                            }
                                                            else if (dateofitemrented.getText().toString().contains("start date"))
                                                            {
                                                                Toast.makeText(ClientItemDetailActivity.this, "Select Start Date", Toast.LENGTH_SHORT).show();
                                                            }
                                                            else if (dateofitemreturned.getText().toString().contains("end date"))
                                                            {
                                                                Toast.makeText(ClientItemDetailActivity.this, "Select Return Date", Toast.LENGTH_SHORT).show();                                                            }
                                                            else {
                                                                final ProgressDialog progressDialog = new ProgressDialog(ClientItemDetailActivity.this);
                                                                progressDialog.setTitle("Adding Item...");
                                                                // THIS IS ALTERNATE //progressDialog.getWindow().setType(WindowManager.LayoutParams.TYPE_TOAST);
                                                                //  progressDialog.getWindow().setType(WindowManager.LayoutParams.TYPE_SYSTEM_ALERT);
                                                                progressDialog.show();
                                                                progressDialog.setCancelable(false);
//                                                            String cartItem_delivery_location  = addItem_itemDescription.getText().toString();
                                                                String cart_item_rent_end_date = dateofitemreturned.getText().toString();
                                                                String cart_item_name = textView_ActivityClientFindItemNameofTheItem.getText().toString();
                                                                final int cart_item_item_count = Integer.parseInt(txtQuantity.getText().toString());
                                                                boolean isDeliver = false;
                                                                String cart_item_rent_start_date = dateofitemrented.getText().toString();
                                                                double cart_item_item_price = Double.parseDouble(textView_ActivityClientFindItemPriceofTheItem.getText().toString());
                                                                double cart_item_order_cost = cart_item_item_price * cart_item_item_count;
                                                                String cart_item_status = "Pending";

//                                                            String item_uid = "123";

                                                                int idOfChecked = Radiogroup_forDeliveryornot.getCheckedRadioButtonId();
                                                                if (idOfChecked == R.id.radioButton2_yes) {
                                                                    isDeliver = true;
//                                                                btnsave.setVisibility(View.VISIBLE);
//                                                                isDelivered=true;
                                                                } else {
                                                                    isDeliver = false;
//                                                                isDelivered=false;
                                                                }

                                                                Cart_Item newItem = new Cart_Item(cart_item_name,
                                                                        cart_item_order_cost,
                                                                        0,
                                                                        cart_item_item_count,
                                                                        isDeliver,
                                                                        cart_item_rent_end_date,
                                                                        cart_item_rent_start_date,
                                                                        eventUID,
                                                                        "",
                                                                        cart_item_status,
                                                                        item_uid,
                                                                        "",
                                                                        cart_item_item_price,
                                                                        cart_group_uid,
                                                                        currentUserUid);
                                                                db = FirebaseFirestore.getInstance();
                                                                db.collection("Cart_Items")
                                                                        .document(cart_group_uid)
                                                                        .collection("cart_items")
                                                                        .add(newItem)
                                                                        .addOnCompleteListener(new OnCompleteListener<DocumentReference>() {
                                                                            @Override
                                                                            public void onComplete(@NonNull Task<DocumentReference> task) {
                                                                                if (task.isSuccessful()) {
                                                                                    progressDialog.dismiss();
                                                                                    dialog.dismiss();
                                                                                    showAlertSuccessfulAddedToCart("Successfully Saved Item", "SUCCESS!");

                                                                                } else {
                                                                                    Log.w("", "Error adding document " + task.getException().toString());
                                                                                }
                                                                            }
                                                                        });

//                                                                    .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
//                                                                        @Override
//                                                                        public void onSuccess(DocumentReference documentReference) {
//                                                                            cart_group_uid = documentReference.getId();
//                                                                            FirebaseFirestore.getInstance().collection("Cart_Items")
//                                                                                    .document(cart_group_uid)
//                                                                                    .collection("cart_items")
//                                                                                    .document(item_uid)
//                                                                                    .update("cart_item_item_uid", documentReference.getId());
//
//
//                                                                        }
//                                                                    })
//
//                                                                    .addOnFailureListener(new OnFailureListener() {
//                                                                        @Override
//                                                                        public void onFailure(@NonNull Exception e) {
//
//                                                                        }
//                                                                    });
                                                                // showAlert("Add Item to Cartv1?", "Confirm");
                                                                HashMap<String, String> map = new HashMap<>();
                                                            }
                                                        }
                                                    });

                                                }else if (itemDetails.isItem_for_sale()){
                                                    View view = LayoutInflater.from(ClientItemDetailActivity.this).inflate(R.layout.modal_addtocart_for_sale, null);
                                                    final Dialog dialog2 = new Dialog(ClientItemDetailActivity.this);

                                                    dialog2.setContentView(view);
                                                    dialog2.show();
                                                    Window window = dialog2.getWindow();
                                                    WindowManager.LayoutParams wlp = window.getAttributes();
                                                    wlp.gravity = Gravity.CENTER;
                                                    window.setAttributes(wlp);
                                                    window.setAttributes(wlp);
                                                    final RadioGroup Radiogroup_forDeliveryornot2 = view.findViewById(R.id.radiogroup_forsale);
                                                    final RadioButton forDelivered = view.findViewById(R.id.radioButton_yesforsale);
                                                    final RadioButton notDelivered = view.findViewById(R.id.radioButton_noforsale);
                                                    final  Button addtocart2=view.findViewById(R.id.button_addtocartforsale);
                                                    final EditText txtQuantity2=view.findViewById(R.id.editText2_quantityforsale);
                                                    final ImageView close2=view.findViewById(R.id.imageButton_modalAddToCartForSaleClose);
                                                    final TextView note= view.findViewById(R.id.textView_notesignforsale);
                                                    close2.setOnClickListener(new View.OnClickListener() {
                                                        @Override
                                                        public void onClick(View v) {
                                                            dialog2.dismiss();
                                                        }
                                                    });
                                                    addtocart2.setOnClickListener(new View.OnClickListener() {
                                                        @Override
                                                        public void onClick(View v) {
                                                            if (txtQuantity2.getText().toString().isEmpty()) {
                                                                txtQuantity2.setError("Please Input Quantity");
                                                            } else {

                                                                final ProgressDialog progressDialog = new ProgressDialog(ClientItemDetailActivity.this);
                                                                progressDialog.setTitle("Adding Item...");
                                                                progressDialog.show();
                                                                progressDialog.setCancelable(false);
//                                                            String cartItem_delivery_location  = addItem_itemDescription.getText().toString();
                                                                String cart_item_name = textView_ActivityClientFindItemNameofTheItem.getText().toString();
                                                                final int cart_item_item_count = Integer.parseInt(txtQuantity2.getText().toString());
                                                                boolean isDeliver = false;
                                                                double cart_item_item_price = Double.parseDouble(textView_ActivityClientFindItemPriceofTheItem.getText().toString());
                                                                double cart_item_order_cost = cart_item_item_price * cart_item_item_count;
                                                                String cart_item_status2 = "Pending";

                                                                int idOfChecked = Radiogroup_forDeliveryornot2.getCheckedRadioButtonId();

                                                                if (idOfChecked == R.id.radioButton_yesforsale) {
                                                                    note.setVisibility(View.VISIBLE);
                                                                    isDeliver = true;
                                                                } else {
                                                                    isDeliver = false;

                                                                }

                                                                Cart_Item newItem = new Cart_Item(cart_item_name,
                                                                        cart_item_order_cost,
                                                                        0,
                                                                        cart_item_item_count,
                                                                        isDeliver,
                                                                        "",
                                                                        "",
                                                                        eventUID,
                                                                        "",
                                                                        cart_item_status2,
                                                                        item_uid,
                                                                        "",
                                                                        cart_item_item_price,
                                                                        cart_group_uid,
                                                                        currentUserUid);

                                                                db = FirebaseFirestore.getInstance();
                                                                db.collection("Cart_Items")
                                                                        .document(cart_group_uid)
                                                                        .collection("cart_items")
                                                                        .add(newItem)
                                                                        .addOnCompleteListener(new OnCompleteListener<DocumentReference>() {
                                                                            @Override
                                                                            public void onComplete(@NonNull Task<DocumentReference> task) {
                                                                                if (task.isSuccessful()) {
                                                                                    progressDialog.dismiss();
                                                                                    showAlertSuccessfulAddedToCart("Successfully Saved Item", "SUCCESS!");
                                                                                    dialog2.dismiss();
                                                                                } else {
                                                                                    Log.w("", "Error adding document " + task.getException().toString());
                                                                                }
                                                                                //   showAlert("Add Item to Cart1?", "Confirm");
                                                                                HashMap<String, String> map = new HashMap<>();
                                                                            }
                                                                        });

//                                                                    .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
//                                                                        @Override
//                                                                        public void onSuccess(DocumentReference documentReference) {
//                                                                            cart_group_uid = documentReference.getId();
//                                                                            FirebaseFirestore.getInstance().collection("Cart_Items")
//                                                                                    .document(cart_group_uid)
//                                                                                    .collection("cart_items")
//                                                                                    .document(item_uid)
//                                                                                    .update("cart_item_item_uid", documentReference.getId());
//
//
//                                                                        }
//                                                                    })
//
//                                                                    .addOnFailureListener(new OnFailureListener() {
//                                                                        @Override
//                                                                        public void onFailure(@NonNull Exception e) {
//
//                                                                        }
//                                                                    });

                                                            }
                                                        }
                                                    });
                                                }
                                                else {
                                                    Log.d("", "not available");
                                                }
                                            }
                                        }
                                    }

                                });
//

                    }
                    else if (id == R.id.navigation_message_now)
                    {
                        //get store id
                        //get supplier id
                        final Custom_Progress_Dialog custom_progress_dialog = new Custom_Progress_Dialog(ClientItemDetailActivity.this);
                        RandomMessages randomMessages = new RandomMessages();
                        custom_progress_dialog.showDialog("LOADING", randomMessages.getRandomMessage());

                        FirebaseFirestore.getInstance()
                                .collection("Store")
                                .document(store_uid)
                                .get()
                                .addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                                    @Override
                                    public void onSuccess(DocumentSnapshot documentSnapshot) {
                                        final String ownerUid = documentSnapshot.get("store_owner_id").toString();

                                        FirebaseFirestore.getInstance()
                                                .collection("User")
                                                .document(currentUserUid)
                                                .get()
                                                .addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                                                    @Override
                                                    public void onSuccess(DocumentSnapshot documentSnapshot) {
                                                        final User sendingUser = documentSnapshot.toObject(com.example.morkince.okasyonv2.activities.model.User.class);

                                                        FirebaseFirestore.getInstance()
                                                                .collection("User")
                                                                .document(ownerUid)
                                                                .get()
                                                                .addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                                                                    @Override
                                                                    public void onSuccess(DocumentSnapshot documentSnapshot) {
                                                                        User receivingUser = documentSnapshot.toObject(com.example.morkince.okasyonv2.activities.model.User.class);

                                                                        Intent intent = new Intent(ClientItemDetailActivity.this, ChatLogActivitiy.class);
                                                                        intent.putExtra("sendingUser", sendingUser);
                                                                        intent.putExtra("receivingUser", receivingUser);
                                                                        startActivity(intent);

                                                                        custom_progress_dialog.dissmissDialog();
                                                                        finish();
                                                                    }
                                                                })
                                                                .addOnFailureListener(new OnFailureListener() {
                                                                    @Override
                                                                    public void onFailure(@NonNull Exception e) {
                                                                        custom_progress_dialog.dissmissDialog();
                                                                    }
                                                                });
                                                    }
                                                })
                                                .addOnFailureListener(new OnFailureListener() {
                                                    @Override
                                                    public void onFailure(@NonNull Exception e) {
                                                        custom_progress_dialog.dissmissDialog();
                                                    }
                                                });
                                    }
                                })
                                .addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception e) {
                                        custom_progress_dialog.dissmissDialog();
                                        Toast.makeText(ClientItemDetailActivity.this, "Coudn't Reach the Dude", Toast.LENGTH_LONG);
                                    }
                                });
                        //open chat log
                    }
                    return true;
                }
            };

    public void showAlertSuccessfulAddedToCart(String Message,String label) {
        //set alert for executing the task
        AlertDialog.Builder alertSuccess = new AlertDialog.Builder(ClientItemDetailActivity.this);
        alertSuccess.setTitle("" + label);
        alertSuccess.setMessage("" + Message);
        alertSuccess.show();

        alertSuccess.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                dialog.cancel();

            }
        });


    }


//    public void showAlert(String Message,String label)
//    {
//        //set alert for executing the task
//        AlertDialog.Builder alert = new AlertDialog.Builder(ClientItemDetailActivity.this);
//        alert.setTitle(""+label);
//        alert.setMessage(""+Message);
//
//        alert.setPositiveButton("YES", new DialogInterface.OnClickListener() {
//            public void onClick (DialogInterface dialog, int id){
//                //CODE GOES HERE TO ADD TO CART
//                FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
//
//                HashMap<String, String> map = new HashMap<>();
//                map.put("cart_item_item_uid", item_uid);
//                map.put("cart_item_user_uid", currentUser.getUid());
//                db = FirebaseFirestore.getInstance();
//                db.collection("Cart_Items").document("cart_items").collection(eventUID).add(map);
//                dialog.cancel();
//            }
//        });
//
//        alert.setNegativeButton("NO", new DialogInterface.OnClickListener()
//        {
//            public void onClick (DialogInterface dialog, int id)
//            {
//                dialog.cancel();
//            }
//        });
//
//
//        Dialog dialog = alert.create();
//        dialog.show();
//    }
}

//package com.example.morkince.okasyonv2.activities.client_activities;
//
//import android.app.AlertDialog;
//import android.app.DatePickerDialog;
//import android.app.Dialog;
//import android.app.ProgressDialog;
//import android.content.DialogInterface;
//import android.content.Intent;
//import android.graphics.Bitmap;
//import android.graphics.Color;
//import android.os.Bundle;
//import android.util.Log;
//import android.view.*;
//import android.widget.*;
//import androidx.annotation.NonNull;
//import androidx.recyclerview.widget.DividerItemDecoration;
//import androidx.recyclerview.widget.LinearLayoutManager;
//import androidx.recyclerview.widget.RecyclerView;
//import com.example.morkince.okasyonv2.*;
//import com.example.morkince.okasyonv2.R;
//import com.example.morkince.okasyonv2.activities.model.Cart_Item;
//import com.example.morkince.okasyonv2.activities.model.Item;
//import com.google.android.gms.tasks.OnCompleteListener;
//import com.google.android.gms.tasks.Task;
//import com.google.android.material.bottomnavigation.BottomNavigationView;
//import androidx.appcompat.app.AppCompatActivity;
//import com.google.firebase.auth.FirebaseAuth;
//import com.google.firebase.auth.FirebaseUser;
//import com.google.firebase.firestore.*;
//import com.kd.dynamic.calendar.generator.ImageGenerator;
//
//import java.util.ArrayList;
//import java.util.Calendar;
//import java.util.HashMap;
//
//public class ClientItemDetailActivity extends AppCompatActivity {
//    public static final String currentUserUid = FirebaseAuth.getInstance().getCurrentUser().getUid();
//    String cart_group_uid;
//    String cartItem_item_id;
//    BottomNavigationView bottomNavigationView;
//    TextView textView_ActivityClientFindItemNameofTheItem,textView_ActivityClientFindItemPriceofTheItem,textView_ActivityClientFindItemDetails,ClientItemDetail_reviewsTextView;
//    RatingBar ratingBar_ActivityClientFindItemRating;
//    RecyclerView recyclerView_ActivityClientFindItemRecyclerViewReviews,recyclerView_ActivityClientFindItemRecyclerViewImages;
//    ToggleButton toggleButton_ActivityClientFindItemToggleForRentAndSale;
//    String eventUID;
//     Calendar currentDate;
//    Bitmap generatedateIcon;
//     ImageGenerator imageGenerator;
//    FirebaseUser user;
//    FirebaseFirestore db;
////    boolean item_for_sale=true;
//    private ArrayList<Reviews> reviews = new ArrayList<>();
//    StoreReviewsAdapter adapter;
//
//
//    String item_uid;
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_client_item_detail);
//        refs();
//        Intent intent = getIntent();
//        item_uid = intent.getStringExtra("item_uid");
//        eventUID = intent.getStringExtra("event_event_uid");
//        cart_group_uid = intent.getStringExtra("event_cart_group_uid");
//        getValues();
//        loadReviews();
//        imageGenerator = new ImageGenerator(this);
//
//
//        imageGenerator.setIconSize(50, 50);
//        imageGenerator.setDateSize(30);
//        imageGenerator.setMonthSize(15);
//
//        imageGenerator.setDatePosition(42);
//        imageGenerator.setMonthPosition(15);
//
//        imageGenerator.setDateColor(Color.parseColor("#D81B60"));
//        imageGenerator.setMonthColor(Color.parseColor("#ffffff"));
//
//        imageGenerator.setStorageToSDCard(true);
//        bottomNavigationView = findViewById(R.id.bottomNavigationView_ActivityClientFindItemBottomNavigationforMessageandAddtoCart);
//        bottomNavigationView.setOnNavigationItemSelectedListener(bottomNavigationViewListener);
//    }
//
//    public void loadReviews()
//    {
//        db = FirebaseFirestore.getInstance();
//        db.collection("Item_Rating").document("item_rating").collection(item_uid).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
//            @Override
//            public void onComplete(@NonNull Task<QuerySnapshot> task) {
//                if (task.isSuccessful()) {
//                    for (QueryDocumentSnapshot document : task.getResult()) {
//                        String evaluatorName=document.getString("itemRating_reviewerName");
//                        Long numStars=(Long)document.get("itemRating_starRating");
//                        String reviewComment=document.getString("itemRating_review");
//                        reviews.add(new Reviews(evaluatorName,numStars,reviewComment));
//                    }
//
//                    adapter = new StoreReviewsAdapter(reviews, ClientItemDetailActivity.this);
//                    recyclerView_ActivityClientFindItemRecyclerViewReviews.addItemDecoration(new DividerItemDecoration(ClientItemDetailActivity.this, LinearLayoutManager.VERTICAL));
//                    recyclerView_ActivityClientFindItemRecyclerViewReviews.setLayoutManager(new LinearLayoutManager(ClientItemDetailActivity.this));
//                    recyclerView_ActivityClientFindItemRecyclerViewReviews.setAdapter(adapter);
//
//                } else {
//                    Toast.makeText(getApplicationContext(), "Error in Retrieving Records!!",
//                            Toast.LENGTH_SHORT).show();
//                }
//
//                ClientItemDetail_reviewsTextView.setText("REVIEWS(" + reviews.size() + ")");
//            }
//        });
//    }
//
//
//    public void getValues()
//    {
//        db = FirebaseFirestore.getInstance();
//        db.collection("Items").document(item_uid).get()
//                .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
//                    @Override
//                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
//
//                        if (task.isSuccessful()) {
//                            DocumentSnapshot document = task.getResult();
//                            if (document.exists()) {
//                                Item itemDetails=document.toObject(Item.class);
//                                textView_ActivityClientFindItemNameofTheItem.setText(itemDetails.getItem_name());
//                                textView_ActivityClientFindItemPriceofTheItem.setText(itemDetails.getItem_price() + "");
//                                textView_ActivityClientFindItemDetails.setText(itemDetails.getItem_description());
//                                toggleButton_ActivityClientFindItemToggleForRentAndSale.setChecked(itemDetails.isItem_for_sale());
//                                ratingBar_ActivityClientFindItemRating.setRating(Float.parseFloat(itemDetails.getItem_average_rating()+""));
//
//
//                            } else {
//                                Log.d("", "No such document exist");
//                            }
//                        } else {
//                            Log.d("", "Failed with ", task.getException());
//                        }
//                    }
//                });
//
//    }
//
//    public void refs()
//    {
//        textView_ActivityClientFindItemNameofTheItem =findViewById(R.id.textView_ActivityClientFindItemNameofTheItem);
//        textView_ActivityClientFindItemPriceofTheItem =findViewById(R.id.textView_ActivityClientFindItemPriceofTheItem);
//        textView_ActivityClientFindItemDetails =findViewById(R.id.textView_ActivityClientFindItemDetails);
//        ratingBar_ActivityClientFindItemRating =findViewById(R.id.ratingBar_ActivityClientFindItemRating);
//        recyclerView_ActivityClientFindItemRecyclerViewReviews =findViewById(R.id.recyclerView_ActivityClientFindItemRecyclerViewReviews);
//        toggleButton_ActivityClientFindItemToggleForRentAndSale =findViewById(R.id.toggleButton_ActivityClientFindItemToggleForRentAndSale);
//        recyclerView_ActivityClientFindItemRecyclerViewImages =findViewById(R.id.recyclerView_ActivityClientFindItemRecyclerViewImages);
//        ClientItemDetail_reviewsTextView =findViewById(R.id.ClientItemDetail_reviewsTextView);
//    }
//
//
//    private BottomNavigationView.OnNavigationItemSelectedListener bottomNavigationViewListener =
//            new BottomNavigationView.OnNavigationItemSelectedListener() {
//                @Override
//                public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
//                    int id = menuItem.getItemId();
//                    if (id == R.id.navigation_addtocart)
//                    {
//                        db = FirebaseFirestore.getInstance();
//                        db.collection("Items").document(item_uid).get()
//                                .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
//                                    @Override
//                                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
//                                        if (task.isSuccessful()) {
//                                            DocumentSnapshot document = task.getResult();
//                                            if (document.exists()) {
//                                                Item itemDetails = document.toObject(Item.class);
//                                                if (itemDetails.isItem_for_sale() == false) {
//                                                    View view = LayoutInflater.from(ClientItemDetailActivity.this).inflate(R.layout.modal_addtocart_for_rent, null);
//                                                    final Dialog dialog = new Dialog(ClientItemDetailActivity.this);
//                                                    dialog.setContentView(view);
//                                                    dialog.show();
//                                                    Window window = dialog.getWindow();
//                                                    WindowManager.LayoutParams wlp = window.getAttributes();
//                                                    wlp.gravity = Gravity.CENTER;
//                                                    window.setAttributes(wlp);
//                                                    window.setAttributes(wlp);
//                                                    final RadioGroup Radiogroup_forDeliveryornot = view.findViewById(R.id.radiogroup_deliverornot);
//
//                                                    final RadioButton forDelivered = view.findViewById(R.id.radioButton2_yes);
//                                                    final RadioButton notDelivered = view.findViewById(R.id.radioButton_no);
//                                                    final  Button addtocart=view.findViewById(R.id.button_modalAddtocart);
//                                                    final EditText txtQuantity=view.findViewById(R.id.editText_itemQuantity2);
//                                                    final EditText textxtBudget =view.findViewById(R.id.edittext_Budget);
//                                                    final EditText txtStorename=view.findViewById(R.id.editText_Storename);
//                                                    final EditText txtLocation=view.findViewById(R.id.editText_Location);
//                                                    final TextView dateofitemrented=view.findViewById(R.id.textView_DateRented);
//                                                    final TextView dateofitemreturned=view.findViewById(R.id.TextView_DateRented2);
//                                                    final ImageView setDate=view.findViewById(R.id.imageView_modalcalendarforrent);
//                                                    final ImageView setDate2=view.findViewById(R.id.imageView_modalcalendarforrent2);
//                                                    final ImageView close=view.findViewById(R.id.imageButton_modalAddToCartForRentClose);
//                                                    close.setOnClickListener(new View.OnClickListener() {
//                                                        @Override
//                                                        public void onClick(View v) {
//                                                            dialog.dismiss();
//                                                        }
//                                                    });
//                                                    setDate.setOnClickListener(new View.OnClickListener() {
//                                                        @Override
//                                                        public void onClick(View v) {
//                                                            Toast.makeText(getApplicationContext(), "hiCalendar", Toast.LENGTH_SHORT).show();
//
//                                                            currentDate = Calendar.getInstance();
//
//                                                            int yr= currentDate.get(Calendar.YEAR);
//                                                            int mon= currentDate.get(Calendar.MONTH);
//                                                            int day= currentDate.get(Calendar.DAY_OF_MONTH);
//
//                                                            DatePickerDialog datePickerDialog= new DatePickerDialog(ClientItemDetailActivity.this, new DatePickerDialog.OnDateSetListener() {
//                                                                @Override
//                                                                public void onDateSet(DatePicker view, int  selectedYear, int selectedMonth, int selectedDay ) {
//                                                                    int monthToDisplay= selectedMonth + 1;
//                                                                    dateofitemrented.setText(selectedDay+"-"+monthToDisplay+"-"+selectedYear);
//
//                                                                    currentDate.set(selectedYear,selectedMonth,selectedDay);
//                                                                    generatedateIcon= imageGenerator.generateDateImage(currentDate,R.drawable.calendar_icon);
//                                                                    setDate.setImageBitmap(generatedateIcon);
//                                                                }
//                                                            }, yr, mon, day);
//                                                            datePickerDialog.show();
//                                                        }
//                                                    });
//                                                    setDate2.setOnClickListener(new View.OnClickListener() {
//                                                        @Override
//                                                        public void onClick(View v) {
//                                                            Toast.makeText(getApplicationContext(), "hiCalendar", Toast.LENGTH_SHORT).show();
//
//                                                            currentDate = Calendar.getInstance();
//
//                                                            int yr= currentDate.get(Calendar.YEAR);
//                                                            int mon= currentDate.get(Calendar.MONTH);
//                                                            int day= currentDate.get(Calendar.DAY_OF_MONTH);
//
//                                                            DatePickerDialog datePickerDialog= new DatePickerDialog(ClientItemDetailActivity.this, new DatePickerDialog.OnDateSetListener() {
//                                                                @Override
//                                                                public void onDateSet(DatePicker view, int  selectedYear, int selectedMonth, int selectedDay ) {
//                                                                    int monthToDisplay= selectedMonth + 1;
//                                                                    dateofitemreturned.setText(selectedDay+"-"+monthToDisplay+"-"+selectedYear);
//
//                                                                    currentDate.set(selectedYear,selectedMonth,selectedDay);
//                                                                    generatedateIcon= imageGenerator.generateDateImage(currentDate,R.drawable.calendar_icon);
//                                                                    setDate2.setImageBitmap(generatedateIcon);
//                                                                }
//                                                            }, yr, mon, day);
//                                                            datePickerDialog.show();
//                                                        }
//
//
//                                                    });
//
//                                                    addtocart.setOnClickListener(new View.OnClickListener() {
//                                                        @Override
//                                                        public void onClick(View v) {
//                                                            final ProgressDialog progressDialog = new ProgressDialog(ClientItemDetailActivity.this);
//                                                            progressDialog.setTitle("Adding Item...");
//                                                            // THIS IS ALTERNATE //progressDialog.getWindow().setType(WindowManager.LayoutParams.TYPE_TOAST);
//                                                            //  progressDialog.getWindow().setType(WindowManager.LayoutParams.TYPE_SYSTEM_ALERT);
//                                                            progressDialog.show();
//                                                            progressDialog.setCancelable(false);
////                                                            String cartItem_delivery_location  = addItem_itemDescription.getText().toString();
//                                                            String cart_item_rent_end_date= dateofitemreturned.getText().toString();
//                                                            String cart_item_name=textView_ActivityClientFindItemNameofTheItem.getText().toString();
//                                                            final int cart_item_item_count= Integer.parseInt(txtQuantity.getText().toString());
//                                                            boolean isDeliver=false;
//                                                            String cart_item_rent_start_date = dateofitemrented.getText().toString();
//                                                            double cart_item_item_price= Double.parseDouble(textView_ActivityClientFindItemPriceofTheItem.getText().toString());
//                                                            double cart_item_order_cost = cart_item_item_price*cart_item_item_count;
//                                                            String cart_item_status = "Pending";
//
////                                                            String item_uid = "123";
//
//                                                            int idOfChecked = Radiogroup_forDeliveryornot.getCheckedRadioButtonId();
//                                                            if(idOfChecked== R.id.radioButton2_yes)
//                                                            {
//                                                               isDeliver=true;
////                                                                btnsave.setVisibility(View.VISIBLE);
////                                                                isDelivered=true;
//                                                            }
//                                                            else
//                                                            {
//                                                                isDeliver=false;
////                                                                isDelivered=false;
//                                                            }
//                                                            Cart_Item newItem = new Cart_Item(cart_item_name,
//                                                                    cart_item_order_cost,
//                                                                    0,
//                                                                    cart_item_item_count,
//                                                                    isDeliver,
//                                                                    cart_item_rent_end_date,
//                                                                    cart_item_rent_start_date,
//                                                                    eventUID,
//                                                                    "",
//                                                                    cart_item_status,
//                                                                    item_uid,
//                                                                    "",
//                                                                    cart_item_item_price,
//                                                                    cart_group_uid,
//                                                                    currentUserUid);
//                                                            db = FirebaseFirestore.getInstance();
//                                                            db.collection("Cart_Items")
//                                                                    .document(cart_group_uid)
//                                                                    .collection("cart_items")
//                                                                    .add(newItem)
//                                                                    .addOnCompleteListener(new OnCompleteListener<DocumentReference>() {
//                                                                        @Override
//                                                                        public void onComplete(@NonNull Task<DocumentReference> task) {
//                                                                            if(task.isSuccessful()){
//                                                                                progressDialog.dismiss();
//                                                                                showAlert("Successfully Saved Item", "SUCCESS!");
//                                                                            }else{
//                                                                                Log.w("", "Error adding document "+task.getException().toString());
//                                                                            }
//                                                                        }
//                                                                    });
//
////                                                                    .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
////                                                                        @Override
////                                                                        public void onSuccess(DocumentReference documentReference) {
////                                                                            cart_group_uid = documentReference.getId();
////                                                                            FirebaseFirestore.getInstance().collection("Cart_Items")
////                                                                                    .document(cart_group_uid)
////                                                                                    .collection("cart_items")
////                                                                                    .document(item_uid)
////                                                                                    .update("cart_item_item_uid", documentReference.getId());
////
////
////                                                                        }
////                                                                    })
////
////                                                                    .addOnFailureListener(new OnFailureListener() {
////                                                                        @Override
////                                                                        public void onFailure(@NonNull Exception e) {
////
////                                                                        }
////                                                                    });
//                                                            showAlert("Add Item to Cartv1?", "Confirm");
//                                                            HashMap<String, String> map = new HashMap<>();
//                                                        }
//
//                                                    });
//
//                                                }else if (itemDetails.isItem_for_sale() == true){
//                                                    View view = LayoutInflater.from(ClientItemDetailActivity.this).inflate(R.layout.modal_addtocart_for_sale, null);
//                                                    final Dialog dialog2 = new Dialog(ClientItemDetailActivity.this);
//                                                    dialog2.setContentView(view);
//                                                    dialog2.show();
//                                                    Window window = dialog2.getWindow();
//                                                    WindowManager.LayoutParams wlp = window.getAttributes();
//                                                    wlp.gravity = Gravity.CENTER;
//                                                    window.setAttributes(wlp);
//                                                    window.setAttributes(wlp);
//                                                    final RadioGroup Radiogroup_forDeliveryornot2 = view.findViewById(R.id.radiogroup_forsale);
//                                                    final RadioButton forDelivered = view.findViewById(R.id.radioButton_yesforsale);
//                                                    final RadioButton notDelivered = view.findViewById(R.id.radioButton_noforsale);
//                                                    final  Button addtocart2=view.findViewById(R.id.button_addtocartforsale);
//                                                    final EditText txtQuantity2=view.findViewById(R.id.editText2_quantityforsale);
//                                                    final ImageView close2=view.findViewById(R.id.imageButton_modalAddToCartForSaleClose);
//                                                    final TextView note= view.findViewById(R.id.textView_notesignforsale);
//                                                    close2.setOnClickListener(new View.OnClickListener() {
//                                                        @Override
//                                                        public void onClick(View v) {
//                                                            dialog2.dismiss();
//                                                        }
//                                                    });
//                                                    addtocart2.setOnClickListener(new View.OnClickListener() {
//                                                        @Override
//                                                        public void onClick(View v) {
//                                                            final ProgressDialog progressDialog = new ProgressDialog(ClientItemDetailActivity.this);
//                                                            progressDialog.setTitle("Adding Item...");
//                                                            // THIS IS ALTERNATE //progressDialog.getWindow().setType(WindowManager.LayoutParams.TYPE_TOAST);
//                                                            //  progressDialog.getWindow().setType(WindowManager.LayoutParams.TYPE_SYSTEM_ALERT);
//                                                            progressDialog.show();
//                                                            progressDialog.setCancelable(false);
////                                                            String cartItem_delivery_location  = addItem_itemDescription.getText().toString();
//                                                            String cart_item_name=textView_ActivityClientFindItemNameofTheItem.getText().toString();
//                                                            final int cart_item_item_count= Integer.parseInt(txtQuantity2.getText().toString());
//                                                            boolean isDeliver=false;
//                                                            double cart_item_item_price= Double.parseDouble(textView_ActivityClientFindItemPriceofTheItem.getText().toString());
//                                                            double cart_item_order_cost = cart_item_item_price*cart_item_item_count;
//                                                            String cart_item_status2 = "Pending";
////                                                            String item_uid = "123";
//
//                                                            int idOfChecked = Radiogroup_forDeliveryornot2.getCheckedRadioButtonId();
//                                                            if(idOfChecked== R.id.radioButton_yesforsale)
//                                                            {
//                                                                note.setVisibility(View.VISIBLE);
//                                                                isDeliver=true;
////                                                                isDelivered=true;
//
//                                                            }
//                                                            else
//                                                            {
//                                                                isDeliver=false;
//
////                                                                isDelivered=false;
//                                                            }
//
//                                                            Cart_Item newItem = new Cart_Item(cart_item_name,
//                                                                    cart_item_order_cost,
//                                                                    0,
//                                                                    cart_item_item_count,
//                                                                    isDeliver,
//                                                                  "",
//                                                                    "",
//                                                                    eventUID,
//                                                                    "",
//                                                                    cart_item_status2,
//                                                                    item_uid,
//                                                                    "",
//                                                                    cart_item_item_price,
//                                                                    cart_group_uid,
//                                                                    currentUserUid);
//                                                            db = FirebaseFirestore.getInstance();
//                                                            db.collection("Cart_Items")
//                                                                    .document(cart_group_uid)
//                                                                    .collection("cart_items")
//                                                                    .add(newItem)
//                                                                    .addOnCompleteListener(new OnCompleteListener<DocumentReference>() {
//                                                                        @Override
//                                                                        public void onComplete(@NonNull Task<DocumentReference> task) {
//                                                                            if(task.isSuccessful()){
//                                                                                progressDialog.dismiss();
//                                                                                showAlert("Successfully Saved Item", "SUCCESS!");
//                                                                            }else{
//                                                                                Log.w("", "Error adding document "+task.getException().toString());
//                                                                            }
//                                                                            showAlert("Add Item to Cartv1?", "Confirm");
//                                                                            HashMap<String, String> map = new HashMap<>();
//                                                                        }
//                                                                    });
//
////                                                                    .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
////                                                                        @Override
////                                                                        public void onSuccess(DocumentReference documentReference) {
////                                                                            cart_group_uid = documentReference.getId();
////                                                                            FirebaseFirestore.getInstance().collection("Cart_Items")
////                                                                                    .document(cart_group_uid)
////                                                                                    .collection("cart_items")
////                                                                                    .document(item_uid)
////                                                                                    .update("cart_item_item_uid", documentReference.getId());
////
////
////                                                                        }
////                                                                    })
////
////                                                                    .addOnFailureListener(new OnFailureListener() {
////                                                                        @Override
////                                                                        public void onFailure(@NonNull Exception e) {
////
////                                                                        }
////                                                                    });
//
//                                                        }
//
//                                                    });
//                                                }
//                                                else {
//                                                    Log.d("", "not available");
//                                                }
//                                            }
//                                        }
//                                    }
//                                });
//
//
//
//
////
////
//
//                    }
//                    else if (id == R.id.navigation_message_now)
//                    {
//                        Toast.makeText(getApplicationContext(),"Message Now",Toast.LENGTH_SHORT).show();
//                    }
//                    return true;
//                }
//            };
//
//
//    public void showAlert(String Message,String label)
//    {
//        //set alert for executing the task
//        AlertDialog.Builder alert = new AlertDialog.Builder(ClientItemDetailActivity.this);
//        alert.setTitle(""+label);
//        alert.setMessage(""+Message);
//
//        alert.setPositiveButton("YES", new DialogInterface.OnClickListener() {
//            public void onClick (DialogInterface dialog, int id){
//                //CODE GOES HERE TO ADD TO CART
//                FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
//
//                HashMap<String, String> map = new HashMap<>();
//                map.put("cart_item_item_uid", item_uid);
//                map.put("cart_item_user_uid", currentUser.getUid());
//                db = FirebaseFirestore.getInstance();
//                db.collection("Cart_Items").document("cart_items").collection(eventUID).add(map);
//                dialog.cancel();
//            }
//        });
//
//        Dialog dialog = alert.create();
//        //  dialog.getWindow().setType(WindowManager.LayoutParams.TYPE_SYSTEM_ALERT);
//        dialog.show();
//    }
//}