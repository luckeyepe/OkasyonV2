package com.example.morkince.okasyonv2;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import androidx.annotation.NonNull;
import com.google.android.material.textfield.TextInputEditText;
import androidx.core.app.NavUtils;
import androidx.appcompat.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.*;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.*;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;

public class SupplierAddItem extends AppCompatActivity {

    TextInputEditText addItem_itemName,addItem_itemPrice,addItem_priceDescription,addItem_itemTag,addItem_itemDescription,addItem_termsandCondtion;
    Spinner addItem_itemCategorySpinner;
    ToggleButton addItem_forRentforSale;
    boolean item_for_sale=true;
    ArrayList<String> listOfItemCategories = new ArrayList<>();
    CheckBox checkBox_soldPersquareunit;
    String itemId,item_category_id = "";
    FirebaseUser user;
    FirebaseFirestore db;
    ArrayList<String> categoriesForSale = new ArrayList<>();
    ArrayList<String> categoriesForRent = new ArrayList<>();
    private StorageReference mStorageRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_item__supplier_activity);
        getSupportActionBar().setTitle("Add Item");
        refs();

        addItem_forRentforSale.setVisibility(View.GONE);
        user = FirebaseAuth.getInstance().getCurrentUser();
        categoriesForSale.add("Cake and Pastries");
        categoriesForSale.add("Food");
        categoriesForSale.add("Event Give-Aways");
        categoriesForSale.add("Flowers");
        categoriesForSale.add("Jewelry");
        categoriesForSale.add("Printed Materials");

        categoriesForRent.add("Catering Service");
        categoriesForRent.add("Church");
        categoriesForRent.add("Event Coordinator");
        categoriesForRent.add("Event Entertainer");
        categoriesForRent.add(" Event Stylist");
        categoriesForRent.add("Hair and Make-up");
        categoriesForRent.add("Host");
        categoriesForRent.add("Lights");
        categoriesForRent.add("Photography");
        categoriesForRent.add("Sounds");
        categoriesForRent.add("Venue");
        categoriesForRent.add("Videography");
        categoriesForRent.add("Wedding Vehicle");

        //GET ITEM CATEGORIES
         fillSpinner();


        addItem_forRentforSale.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    item_for_sale=true;


                } else {
                    // The toggle is disabled

                    item_for_sale = false;

                }
            }
        });

        addItem_itemCategorySpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                String itemCategory=parent.getItemAtPosition(position).toString();
                if(categoriesForSale.contains(itemCategory))
                {
                  /*  Toast.makeText(getApplicationContext(), itemCategory + " FOR SALE",
                            Toast.LENGTH_SHORT).show();*/
                    item_for_sale=true;
                    addItem_forRentforSale.setVisibility(View.GONE);
                }
                else if(categoriesForRent.contains(itemCategory))
                {
                    /*Toast.makeText(getApplicationContext(), itemCategory + " FOR RENT",
                            Toast.LENGTH_SHORT).show();*/
                    item_for_sale=false;
                    addItem_forRentforSale.setVisibility(View.GONE);

                }
                else
                {

                        addItem_forRentforSale.setVisibility(View.VISIBLE);

                }


            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater mi = getMenuInflater();
        mi.inflate(R.menu.save_item, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
//        Fragment selectedFragment = null;
        // Handle item selection
        switch (item.getItemId()) {
            case R.id.saveItemButton:
                if(addItem_itemName.getText().toString().isEmpty()) {
                    addItem_itemName.setError("Blank Item Name");

                } else if(addItem_itemPrice.getText().toString().isEmpty()) {
                    addItem_itemPrice.setError("Blank Item Price");
                }
                else if(addItem_priceDescription.getText().toString().isEmpty())
                {
                    addItem_priceDescription.setError("Blank Item Price Description");
                }
                else if(addItem_itemTag.getText().toString().isEmpty())
                {
                    addItem_itemTag.setError("Blank Item Tag");
                }
                else if(addItem_itemDescription.getText().toString().isEmpty()){
                    addItem_itemDescription.setError("Blank Item Description");
                }
                else if(addItem_termsandCondtion.getText().toString().isEmpty()){
                    addItem_termsandCondtion.setError("Blank Terms and Conditions");
                }

                else {

                    final ProgressDialog progressDialog = new ProgressDialog(SupplierAddItem.this);
                    progressDialog.setTitle("Adding Item...");
                    // THIS IS ALTERNATE //progressDialog.getWindow().setType(WindowManager.LayoutParams.TYPE_TOAST);
                    //  progressDialog.getWindow().setType(WindowManager.LayoutParams.TYPE_SYSTEM_ALERT);
                    progressDialog.show();
                    progressDialog.setCancelable(false);
                    // Create a new user with a first and last name

                    if (addItem_itemCategorySpinner.getSelectedItem().toString().contains(" ")){
                        item_category_id = addItem_itemCategorySpinner.getSelectedItem()
                                .toString().replace(" ", "_");
                    }else {
                        item_category_id = addItem_itemCategorySpinner.getSelectedItem().toString();
                    }


                    String item_description = addItem_itemDescription.getText().toString();
                    String item_tag = addItem_itemDescription.getText().toString();
                    String item_terms_and_conditions = addItem_termsandCondtion.getText().toString();
                    boolean item_is_per_sqr_unit_of_measurement;
                    String item_name = addItem_itemName.getText().toString();
                    double item_price = Double.parseDouble(addItem_itemPrice.getText().toString());
                    String item_price_description = addItem_priceDescription.getText().toString();
                    String item_store_id;
                    String item_uid = "123";

                    if (checkBox_soldPersquareunit.isChecked())
                        item_is_per_sqr_unit_of_measurement = true;
                    else
                        item_is_per_sqr_unit_of_measurement = false;


                    //GET STORE ID
                    Intent intent = getIntent();
                    item_store_id = intent.getStringExtra("storeID");
                    ItemDetailsModel newItem = new ItemDetailsModel(item_category_id, item_description, item_for_sale, item_is_per_sqr_unit_of_measurement, item_name, item_price, item_price_description, item_store_id, item_uid, item_tag,item_terms_and_conditions);


                    // Add a new document with a generated ID
                    db = FirebaseFirestore.getInstance();
                    db.collection("Items")
                            .add(newItem)
                            .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                                @Override
                                public void onSuccess(DocumentReference documentReference) {
                                    // Log.d("", "DocumentSnapshot added with ID: " + documentReference.getId());
                                    itemId = documentReference.getId();
                                    FirebaseFirestore.getInstance().collection("Items").document(itemId).update("item_uid", documentReference.getId());
                                    progressDialog.dismiss();
                                    showAlert("Successfully Saved Item", "SUCCESS!");

                                }
                            })
                            .addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    Log.w("", "Error adding document", e);
                                }
                            });
                }

                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
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
                    ArrayAdapter<String> adapter = new ArrayAdapter<String>(SupplierAddItem.this,android.R.layout.simple_spinner_dropdown_item,listOfItemCategories);
                    addItem_itemCategorySpinner.setAdapter(adapter);
                }
            }
        });

    }

    public void showAlert(String Message,String label)
    {
        //set alert for executing the task
        AlertDialog.Builder alert = new AlertDialog.Builder(SupplierAddItem.this);
        alert.setTitle(""+label);
        alert.setMessage(""+Message);


        alert.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            public void onClick (DialogInterface dialog, int id){
                dialog.cancel();
                Intent intent = new Intent(SupplierAddItem.this,Select_Multiple_Images.class);
                intent.putExtra("itemID",itemId);
                startActivity(intent);
                finish();
            }
        });

        Dialog dialog = alert.create();
        //  dialog.getWindow().setType(WindowManager.LayoutParams.TYPE_SYSTEM_ALERT);
        dialog.show();
    }

    public void refs(){
        addItem_itemName=findViewById(R.id.addItem_itemName);
        addItem_itemPrice=findViewById(R.id.addItem_itemPrice);
        addItem_priceDescription=findViewById(R.id.addItem_priceDescription);
        addItem_itemTag=findViewById(R.id.addItem_itemTag);
        addItem_itemDescription=findViewById(R.id.addItem_itemDescription);
        addItem_itemCategorySpinner=findViewById(R.id.addItem_itemCategorySpinner);
        checkBox_soldPersquareunit=findViewById(R.id.checkBox_soldPersquareunit);
        addItem_forRentforSale=findViewById(R.id.addItem_forRentforSale);
        addItem_termsandCondtion =findViewById(R.id.addItem_termsandCondition);
    }
}
