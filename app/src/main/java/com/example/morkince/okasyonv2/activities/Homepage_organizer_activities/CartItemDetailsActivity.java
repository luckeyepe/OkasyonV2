package com.example.morkince.okasyonv2.activities.Homepage_organizer_activities;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.widget.*;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import com.example.morkince.okasyonv2.R;
import com.example.morkince.okasyonv2.activities.model.Item;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.StorageReference;

public class CartItemDetailsActivity extends AppCompatActivity {
ImageButton editcartitemdetails;
Button saveitem;
TextView Itemprice,  ItemName,  ItemDetails;
EditText Itemquantity, ItemDatefrom, ItemDateto;
    Item cartitem;
    FirebaseUser item;
    FirebaseFirestore db;
    StorageReference mStorageRef;
    String user_id,user_id2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart_item_details);
        refs();

        item = FirebaseAuth.getInstance().getCurrentUser();
        Intent intent = getIntent();
        user_id= intent.getStringExtra("HAfVdDZQjRLEyI2VBil6");
//        user_id2= intent.getStringExtra("user_profPic");


        getUserDetails();
        enable(false);

        editcartitemdetails.setOnClickListener(edittheDetails)    ;
        saveitem.setOnClickListener(saveUpdatedData);

    }

    public void getUserDetails(){
//        mStorageRef = FirebaseStorage.getInstance().getReference().child("user_profPic").child(user.getUid());
//
//        mStorageRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
//            @Override
//            public void onSuccess(Uri uri) {
//                Picasso.get().load(uri.toString()).error(R.drawable.account).into(imageviewUserProfile);
//            }
//        });

        db = FirebaseFirestore.getInstance();
        db.collection("Items").document("HAfVdDZQjRLEyI2VBil6").get()
                .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {

                        if (task.isSuccessful()) {
                            DocumentSnapshot document = task.getResult();
                            if (document.exists()) {
                                cartitem=document.toObject(Item.class);
                                ItemName.setText(cartitem.getItem_name());
                                Itemprice.setText(cartitem.getItem_price()+"");
                                ItemDetails.setText(cartitem.getItem_description());

                                Log.d("", "No such document exist");
                            }
                        } else {
                            Log.d("", "Failed with ", task.getException());
                        }
                    }
                });

    }
    public View.OnClickListener edittheDetails = new View.OnClickListener() {
        @Override
        public void onClick(View v) {

            enable(true);
           editcartitemdetails.setVisibility(View.INVISIBLE);
            saveitem.setVisibility(View.VISIBLE);




        }
    };

    public void enable(boolean status)
    {

        Itemquantity.setEnabled(status);
        ItemDatefrom.setEnabled(status);
        ItemDateto.setEnabled(status);
    }
    public View.OnClickListener saveUpdatedData = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Toast.makeText(getApplicationContext(), "Save", Toast.LENGTH_SHORT).show();
            enable(false);
          editcartitemdetails.setVisibility(View.VISIBLE);
            saveitem.setVisibility(View.INVISIBLE);
//            db = FirebaseFirestore.getInstance();
//            DocumentReference userToUpdate = db.collection("User").document("" + user.getUid());
//            userToUpdate.update("user_first_name",  UserFirstname.getText().toString());
//            userToUpdate.update("user_last_name",   UserLastname.getText().toString());
//            userToUpdate.update("user_email",  UserEmailAdress.getText().toString());
//            userToUpdate.update("user_birth_date",  UserDateofbirth.getText().toString());
//            userToUpdate.update("user_contact_no",  UserContactnumber.getText().toString());
//            userToUpdate.update("user_gender",  UserGender.getText().toString());
//            userToUpdate.update("user_address",  UserAddress.getText().toString())
//                    .addOnSuccessListener(new OnSuccessListener<Void>() {
//                        @Override
//                        public void onSuccess(Void aVoid) {
////                            progressDialog.dismiss(); add progress dialog later on
//                            showAlert("Successfully Updated Data", "SUCCESS!");
//                        }
//                    });
        }
    };

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater mi = getMenuInflater();
        mi.inflate(R.menu.menu_delete_cartitem,menu);
        return super.onCreateOptionsMenu(menu);
    }
    public void refs()
    {
        Itemprice=findViewById(R.id.textView_ActivityClientFindItemPriceofTheItem2);
        Itemquantity=findViewById(R.id.editText_ItemQuantity);
        ItemName=findViewById(R.id.textView_ActivityClientFindItemNameofTheItem2);
        ItemDatefrom=findViewById(R.id.editText_datefrom);
        ItemDateto=findViewById(R.id.editText_dateto);
        ItemDetails=findViewById(R.id.textView_ActivityClientFindItemDetails2);
        editcartitemdetails=findViewById(R.id.imageButton_edititem);
        saveitem=findViewById(R.id.button_saveitem);
    }
}
