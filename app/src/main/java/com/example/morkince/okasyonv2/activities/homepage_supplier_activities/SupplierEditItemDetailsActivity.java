package com.example.morkince.okasyonv2.activities.homepage_supplier_activities;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.*;
import com.example.morkince.okasyonv2.R;
import com.example.morkince.okasyonv2.Reviews;
import com.example.morkince.okasyonv2.StoreReviewsAdapter;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.*;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;

public class SupplierEditItemDetailsActivity extends AppCompatActivity {
    EditText editText_supplierEditItemDetailsNameofItem,editText_supplierEditItemDetailsPriceofItem,editText_supplierEditItemDetails,editText_supplierEditItemDetailsPriceDescriptionofItem;
    ImageView edit;
    TextView editItemDetails_reviewsTxtView;
    RatingBar itemDetails_starRating;
    ToggleButton supplierEditItemDetailsToggleButtonForRentAndSale;
    Spinner editItem_itemCategorySpinner;
    Button editItemDetails_saveBtn;
    ArrayList<String> listOfItemCategories = new ArrayList<>();
    String item_uid,item_name,star_rating,item_price,item_category,item_description,isForSale,isPerSquareUnit,price_description,store_id;
    boolean isItemforSale,checkIfThereStillExistsAnImage=false;
    FirebaseUser user;
    FirebaseFirestore db;
    private StorageReference mStorageRef;

    RecyclerView editItemDetails_recyclerView;
    StoreReviewsAdapter adapter;
    private ArrayList<Reviews> reviews = new ArrayList<>();



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_supplier_edit_item_details);
        getSupportActionBar().setTitle("Item Details");
        getValues();
        refs();
        fillSpinner();
        IsEnabled(false);
        setValues();
        editItemDetails_saveBtn.setVisibility(View.GONE);


        edit.setOnClickListener(editItem);
        editItemDetails_saveBtn.setOnClickListener(saveItemInfo);

        supplierEditItemDetailsToggleButtonForRentAndSale.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    isItemforSale=false;
                    Toast.makeText(getApplicationContext(), "FOR RENT",
                            Toast.LENGTH_SHORT).show();

                } else {
                    // The toggle is disabled

                    isItemforSale = true;
                    Toast.makeText(getApplicationContext(), "FOR SALE",
                            Toast.LENGTH_SHORT).show();

                }
            }
        });



        //ADD REVIEWS
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
                                        Log.e("HERE IT IS ", evaluatorName + numStars + " " + reviewComment );
                                    }

                                    adapter = new StoreReviewsAdapter(reviews,SupplierEditItemDetailsActivity.this);
                                    editItemDetails_recyclerView.addItemDecoration(new DividerItemDecoration(SupplierEditItemDetailsActivity.this, LinearLayoutManager.VERTICAL));
                                    editItemDetails_recyclerView.setLayoutManager(new LinearLayoutManager(SupplierEditItemDetailsActivity.this));
                                    editItemDetails_recyclerView.setAdapter(adapter);

                                } else {
                                    Toast.makeText(getApplicationContext(), "Error in Retrieving Records!!",
                                            Toast.LENGTH_SHORT).show();
                                }

                                editItemDetails_reviewsTxtView.setText("REVIEWS(" + reviews.size() + ")");
                            }
                        });

    }



    public void setValues()
    {
        editText_supplierEditItemDetailsNameofItem.setText(item_name);
        editText_supplierEditItemDetailsPriceofItem.setText(item_price);
        editText_supplierEditItemDetailsPriceDescriptionofItem.setText(price_description);
        editText_supplierEditItemDetails.setText(item_description);
        itemDetails_starRating.setRating(5);
    }


    public void getValues()
    {
        Intent intent = getIntent();
        item_uid = intent.getStringExtra("item_uid");
        item_name = intent.getStringExtra("item_name");
        star_rating = intent.getStringExtra("star_rating");
        item_price = intent.getStringExtra("item_price");
        item_category = intent.getStringExtra("item_category");
        item_description = intent.getStringExtra("item_description");
        isForSale = intent.getStringExtra("isForSale");
        isPerSquareUnit = intent.getStringExtra("isPerSquareUnit");
        price_description = intent.getStringExtra("price_description");
        store_id = intent.getStringExtra("store_id");
    }

    private View.OnClickListener saveItemInfo = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            IsEnabled(false);
            editItemDetails_saveBtn.setVisibility(View.GONE);

            if (editText_supplierEditItemDetailsNameofItem.getText().toString().isEmpty()) {
                editText_supplierEditItemDetailsNameofItem.setError("Item Name is Blank");
            } else if (editText_supplierEditItemDetailsPriceofItem.getText().toString().isEmpty()) {
                editText_supplierEditItemDetailsPriceofItem.setError("Price  is Blank");
            } else if (editText_supplierEditItemDetails.getText().toString().isEmpty()) {
                editText_supplierEditItemDetails.setError("Item Details is Blank");
            } else if (editText_supplierEditItemDetailsPriceDescriptionofItem.getText().toString().isEmpty()) {
                editText_supplierEditItemDetailsPriceDescriptionofItem.setError("Price Description is Blank");
            }

            else {
                final ProgressDialog progressDialog = new ProgressDialog(SupplierEditItemDetailsActivity.this);
                progressDialog.setTitle("Updating...");
                // THIS IS ALTERNATE //progressDialog.getWindow().setType(WindowManager.LayoutParams.TYPE_TOAST);
                //  progressDialog.getWindow().setType(WindowManager.LayoutParams.TYPE_SYSTEM_ALERT);
                progressDialog.show();
                progressDialog.setCancelable(false);

                db = FirebaseFirestore.getInstance();
                DocumentReference userToUpdate = db.collection("Items").document("" + item_uid);
                userToUpdate.update("item_name",editText_supplierEditItemDetailsNameofItem.getText().toString());
                userToUpdate.update("item_price_description",editText_supplierEditItemDetailsPriceDescriptionofItem.getText().toString());
                userToUpdate.update("item_for_sale",isItemforSale);
                userToUpdate.update("item_price",editText_supplierEditItemDetailsPriceofItem.getText().toString());
                userToUpdate.update("item_description", editText_supplierEditItemDetails.getText().toString())
                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {
                                progressDialog.dismiss();
                                showAlert("Successfully Updated Data", "SUCCESS!");
                            }
                        });
            }
        }
    };


    private View.OnClickListener editItem= new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            IsEnabled(true);
            editItemDetails_saveBtn.setVisibility(View.VISIBLE);
        }
    };

    public void IsEnabled(boolean state)
    {
        editText_supplierEditItemDetailsNameofItem.setEnabled(state);
        editText_supplierEditItemDetailsPriceofItem.setEnabled(state);
        editText_supplierEditItemDetails.setEnabled(state);
        editItem_itemCategorySpinner.setEnabled(state);
        editText_supplierEditItemDetailsPriceDescriptionofItem.setEnabled(state);
        supplierEditItemDetailsToggleButtonForRentAndSale.setEnabled(state);
    }

    public void DeleteItem()
    {



        // Create a storage reference from our app
        StorageReference storageRef = FirebaseStorage.getInstance().getReference();

        for(int counter=0;counter<=5;counter++) {
            // Create a reference to the file to delete
            StorageReference desertRef = storageRef.child("item_images").child(item_uid).child(item_uid + counter);

            // Delete the file
            desertRef.delete().addOnSuccessListener(new OnSuccessListener<Void>() {
                @Override
                public void onSuccess(Void aVoid) {
                    // File deleted successfully
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception exception) {
                    // Uh-oh, an error occurred!
                    checkIfThereStillExistsAnImage = true;
                }
            });

            if (checkIfThereStillExistsAnImage)
                break;
        }

        db.collection("Items").document(item_uid)
                .delete()
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        showAlertSuccessfulDelete("Sucessfully Deleted Item", "SUCCESS");
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        showAlert("Error : " + e, "ERROR");
                    }
                });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        int id = item.getItemId();
        if(id == R.id.delete_item)
        {
        showAlertConfirmDelete("Are you sure you want to Delete Item?","Confirm");

        }

        return super.onOptionsItemSelected(item);
    }
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_activity_supplier_edit_item_details, menu);
        return true;
    }


    public void fillSpinner()
    {
        db = FirebaseFirestore.getInstance();
        db.collection("Item_Category").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    for (DocumentSnapshot document : task.getResult()) {
                        String itemCategory = document.getString("itemCategory_name");
                        listOfItemCategories.add(itemCategory);
                    }
                    ArrayAdapter<String> adapter = new ArrayAdapter<String>(SupplierEditItemDetailsActivity.this,android.R.layout.simple_spinner_dropdown_item,listOfItemCategories);
                    editItem_itemCategorySpinner.setAdapter(adapter);

                    setValues();
                    for (int i=0;i<editItem_itemCategorySpinner.getCount();i++){
                        if (editItem_itemCategorySpinner.getItemAtPosition(i).toString().equalsIgnoreCase(item_category)){
                            editItem_itemCategorySpinner.setSelection(i);
                        }
                    }
                }
            }
        });

    }

    public void showAlertConfirmDelete(String Message,String label)
    {
        //set alert for executing the task
        AlertDialog.Builder alert = new AlertDialog.Builder(SupplierEditItemDetailsActivity.this);
        alert.setTitle(""+label);
        alert.setMessage(""+Message);

        alert.setPositiveButton("NO", new DialogInterface.OnClickListener() {
            public void onClick (DialogInterface dialog, int id){
               dialog.cancel();

            }
        });

        alert.setNegativeButton("YES", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                DeleteItem();
            }
        });

        Dialog dialog = alert.create();
        //  dialog.getWindow().setType(WindowManager.LayoutParams.TYPE_SYSTEM_ALERT);
        dialog.show();
    }


    public void showAlertSuccessfulDelete(String Message,String label)
    {
        //set alert for executing the task
        AlertDialog.Builder alert = new AlertDialog.Builder(SupplierEditItemDetailsActivity.this);
        alert.setTitle(""+label);
        alert.setMessage(""+Message);

        alert.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            public void onClick (DialogInterface dialog, int id){
                dialog.cancel();
                finish();
            }
        });

        Dialog dialog = alert.create();
        //  dialog.getWindow().setType(WindowManager.LayoutParams.TYPE_SYSTEM_ALERT);
        dialog.show();
    }



    public void showAlert(String Message,String label)
    {
        //set alert for executing the task
        AlertDialog.Builder alert = new AlertDialog.Builder(SupplierEditItemDetailsActivity.this);
        alert.setTitle(""+label);
        alert.setMessage(""+Message);

        alert.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            public void onClick (DialogInterface dialog, int id){
                dialog.cancel();
            }
        });

        Dialog dialog = alert.create();
        //  dialog.getWindow().setType(WindowManager.LayoutParams.TYPE_SYSTEM_ALERT);
        dialog.show();
    }

    public void refs()
    {
        edit =findViewById(R.id.imageView_supplierEditItemDetailsEdit);
        editText_supplierEditItemDetailsNameofItem =findViewById(R.id.editText_supplierEditItemDetailsNameofItem);
        editText_supplierEditItemDetailsPriceofItem =findViewById(R.id.editText_supplierEditItemDetailsPriceofItem);
        editText_supplierEditItemDetailsPriceDescriptionofItem =findViewById(R.id.editText_supplierEditItemDetailsPriceDescriptionofItem);
        editText_supplierEditItemDetails =findViewById(R.id.editText_supplierEditItemDetails);
        editItem_itemCategorySpinner =findViewById(R.id.editItem_itemCategorySpinner);
        itemDetails_starRating =findViewById(R.id.itemDetails_starRating);
        editItemDetails_recyclerView =findViewById(R.id.editItemDetails_recyclerView);
        editItemDetails_reviewsTxtView =findViewById(R.id.editItemDetails_reviewsTxtView);
        editItemDetails_saveBtn =findViewById(R.id.editItemDetails_saveBtn);
        supplierEditItemDetailsToggleButtonForRentAndSale =findViewById(R.id.supplierEditItemDetailsToggleButtonForRentAndSale);
    }

}
