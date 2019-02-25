package com.example.morkince.okasyonv2;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.provider.MediaStore;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.*;
import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.morkince.okasyonv2.activities.chat_activities.LatestMessagesActivity;
import com.example.morkince.okasyonv2.activities.homepage_supplier_activities.SupplierHomePage;
import com.example.morkince.okasyonv2.activities.login_activities.MainActivity;
import com.example.morkince.okasyonv2.activities.model.Item;
import com.example.morkince.okasyonv2.activities.model.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.*;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.InstanceIdResult;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

import java.io.IOException;
import java.util.ArrayList;

public class ClientViewStoreDetails extends AppCompatActivity {

    ImageButton ocr_registration_supplier_validID_imageBtn2;
    ImageView supplierHompage_bannerImg,SupplierHomepage_addressImageView;
    Button supplierHomepage_viewItemsBtn;
    EditText supplierHomepage_storeName_txtView,supplierHomepage_storeContact;
    TextView supplierHomepage_storeAddress,supplierHomepage_reviewTxtView;
    String documentID;
    String documentIDofStore;
    User usersupplier;
    private static final int PICK_IMAGE = 100;
    private Uri filePath=null;
    FirebaseUser user;
    FirebaseFirestore db;
    private StorageReference mStorageRef;

    ImageView drawerImage;
    TextView drawerUsername;
    TextView drawerAccount;
    private ArrayList<Reviews> reviews = new ArrayList<>();
    StoreReviewsAdapter adapter;
    String store_uid;
    RecyclerView supplierHomePage_recyclerView;
    int size = 0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_client_view_store_details);

        Intent intent = getIntent();
         store_uid=intent.getStringExtra("store_uid");

        getSupportActionBar().setTitle("Store");

        if (getIntent().hasExtra("isNewUser")){
            AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
            alertDialogBuilder.setMessage("Welcome to Okasyon");
            alertDialogBuilder.setCancelable(true);

            AlertDialog alert = alertDialogBuilder.create();
            alert.show();
        }


        FirebaseInstanceId.getInstance().getInstanceId().addOnSuccessListener(new OnSuccessListener<InstanceIdResult>() {
            @Override
            public void onSuccess(InstanceIdResult instanceIdResult) {
                FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
                DocumentReference db = FirebaseFirestore.getInstance().collection("Users")
                        .document(currentUser.getUid());
                db.update("user_token", instanceIdResult.getToken());
                db.update("user_instanceID", instanceIdResult.getId());
                Log.d("Dashboard", "Token: "+instanceIdResult.getToken());
            }
        });




        //CODE STARTS HERE

        refs();
        user = FirebaseAuth.getInstance().getCurrentUser();
     //   updateSupplierProfileInfo();
        enableEditText(false);

        SupplierHomepage_addressImageView.setOnClickListener(getDirections);



        setProfileInformation(store_uid);
        supplierHomepage_viewItemsBtn.setOnClickListener(viewItems);




        //ADD REVIEWS TO RECYCLER VIEW

        db = FirebaseFirestore.getInstance();
        db.collection("Store_Rating").document("store_rating").collection(store_uid).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    for (QueryDocumentSnapshot document : task.getResult()) {
                        /*   Log.e("THIS IS THE DATA", document.getId() + " => " + document.getData());*/
                        String evaluatorName=document.getString("storeRating_reviewerName");
                        Long numStars=(Long)document.get("storeRating_starRating");
                        String reviewComment=document.getString("storeRating_review");
                        Log.e("HERE IT IS ", evaluatorName + numStars + " " + reviewComment );
                        reviews.add(new Reviews(evaluatorName,numStars,reviewComment));
                    }
                    adapter = new StoreReviewsAdapter(reviews,ClientViewStoreDetails.this);
                    supplierHomePage_recyclerView.setAdapter(adapter);
                    supplierHomePage_recyclerView.addItemDecoration(new DividerItemDecoration(ClientViewStoreDetails.this, LinearLayoutManager.VERTICAL));
                    supplierHomePage_recyclerView.setLayoutManager(new LinearLayoutManager(ClientViewStoreDetails.this));
                } else {
                    Toast.makeText(getApplicationContext(), "Error in Retrieving Records!!",
                            Toast.LENGTH_SHORT).show();
                }

            }
        });
        supplierHomepage_reviewTxtView.setText("REVIEWS(" + reviews.size() + ")");
/*
        reviews.add(new Reviews("kaylokoto",4,"YOUR STORE IS REALLY GREAT! I WANT TO RECOMMEND YOU!I WANT TO RECOMMEND YOU!I WANT TO RECOMMEND YOU!I WANT TO RECOMMEND YOU!"));
        reviews.add(new Reviews("colinamarc",3,"YOUR STORE IS REALLY AMAZING!"));
        reviews.add(new Reviews("japhet",2,"YOUR STORE IS REALLY NICE!"));*/




        //LOAD IMAGE BANNER
        db = FirebaseFirestore.getInstance();
        db.collection("Store").document(store_uid).get()
                .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {

                        if (task.isSuccessful()) {
                            DocumentSnapshot document = task.getResult();
                            if (document.exists()) {
                              String storeOwner_id = document.getString("store_owner_id");

                                mStorageRef = FirebaseStorage.getInstance().getReference().child("images").child(storeOwner_id);
                                mStorageRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                    @Override
                                    public void onSuccess(Uri uri) {
                                        Picasso.get().load(uri.toString()).error(R.mipmap.ic_launcher).into(supplierHompage_bannerImg);

                                    }
                                });

                            } else {
                                Log.d("", "No such document exist");
                            }
                        } else {
                            Log.d("", "Failed with ", task.getException());
                        }
                    }
                });



    }








    public void setProfileInformation(String store_uid)
    {
        db = FirebaseFirestore.getInstance();
        db.collection("Store").document(store_uid).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if(task.isSuccessful())
                {
                    DocumentSnapshot document = task.getResult();
                    if(document.exists()) {
                        String storeName = document.getString("store_store_name");
                        String store_owner_id=document.getString("store_owner_id");
                        supplierHomepage_storeName_txtView.setText(storeName);

                        db.collection("User").document(store_owner_id).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                            @Override
                            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                                if (task.isSuccessful()) {
                                    DocumentSnapshot document = task.getResult();
                                    if (document.exists()) {

                                        String user_contact_no = document.getString("user_contact_no");
                                        String user_address = document.getString("user_address");

                                        supplierHomepage_storeContact.setText(user_contact_no);
                                        supplierHomepage_storeAddress.setText(user_address);
                                    }
                                    else
                                    {
                                        Toast.makeText(getApplicationContext(), "Error in Retrieving Records!!",
                                                Toast.LENGTH_SHORT).show();
                                    }
                                }
                            }
                        });

                    }
                }
                else
                {
                    Toast.makeText(getApplicationContext(), "Error in Retrieving Records!!",
                            Toast.LENGTH_SHORT).show();
                }
            }
        });


    }


    private View.OnClickListener viewItems = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            Intent intent = new Intent(ClientViewStoreDetails.this,ClientViewStoreItems.class);
            intent.putExtra("storeID",store_uid);
            startActivity(intent);
        }
    };




    public void uploadImage(String txtid){
        if(filePath != null)
        {
            Log.e("THIS IS supplier UID", txtid);

            final ProgressDialog progressDialog = new ProgressDialog(ClientViewStoreDetails.this);
            progressDialog.setTitle("Uploading...");
            // THIS IS ALTERNATE //progressDialog.getWindow().setType(WindowManager.LayoutParams.TYPE_TOAST);
            //  progressDialog.getWindow().setType(WindowManager.LayoutParams.TYPE_SYSTEM_ALERT);
            progressDialog.show();
            progressDialog.setCancelable(false);
            StorageReference ref = FirebaseStorage.getInstance().getReference().child("images").child(txtid);
            ref.putFile(filePath)
                    .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                            progressDialog.dismiss();
                            showAlert("Successfully Updated!","Success");

                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            progressDialog.dismiss();
                            showAlert("An Error Occured","ERROR");


                        }
                    })
                    .addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {
                            double progress = (100.0*taskSnapshot.getBytesTransferred()/taskSnapshot
                                    .getTotalByteCount());
                            progressDialog.setMessage("Uploading : "+(int)progress+"%");
                        }
                    });
        }

    }



    public void showAlert(String Message,String label)
    {
        //set alert for executing the task
        AlertDialog.Builder alert = new AlertDialog.Builder(ClientViewStoreDetails.this);
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

    public void enableEditText(boolean booleanState)
    {
        supplierHomepage_storeName_txtView.setEnabled(booleanState);
        supplierHomepage_storeContact.setEnabled(booleanState);

    }

    //ON CLICK LISTENERS

    private View.OnClickListener getDirections = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            Intent intent = new Intent(ClientViewStoreDetails.this,GetStoreDirections.class);
            intent.putExtra("storeLocation",supplierHomepage_storeAddress.getText().toString());
            startActivity(intent);
        }
    };


    public void updateSupplierProfileInfo()
    {
        mStorageRef = FirebaseStorage.getInstance().getReference().child("images").child(user.getUid());

        mStorageRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {
                Picasso.get().load(uri.toString()).error(R.mipmap.ic_launcher_round).into(drawerImage);
//                Picasso.get().load(uri.toString()).into(drawerImage);
            }
        });
        db = FirebaseFirestore.getInstance();
        db.collection("User").document(user.getUid()).get()
                .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {

                        if (task.isSuccessful()) {
                            DocumentSnapshot document = task.getResult();
                            if (document.exists()) {
                                usersupplier =document.toObject(User.class);
                                drawerUsername.setText(usersupplier.getUser_first_name()+ usersupplier.getUser_last_name());
                                drawerAccount.setText(usersupplier.getUser_email());
                                Log.e("NAA DISPLAY", usersupplier.getUser_first_name());
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
        ocr_registration_supplier_validID_imageBtn2=findViewById(R.id.ocr_registration_supplier_validID_imageBtn2);
        supplierHompage_bannerImg=findViewById(R.id.clientViewStoreDetails_bannerImg);
        supplierHomepage_viewItemsBtn=findViewById(R.id.clientViewStoreDetails_viewItemsBtn);
        supplierHomepage_storeName_txtView=findViewById(R.id.clientViewStoreDetails_storeName_txtView);
        supplierHomePage_recyclerView=findViewById(R.id.clientViewStoreDetails_recyclerView);
        supplierHomepage_storeContact=findViewById(R.id.clientViewStoreDetails_storeContact);
        supplierHomepage_storeAddress=findViewById(R.id.clientViewStoreDetails_storeAddress);
        SupplierHomepage_addressImageView=findViewById(R.id.clientViewStoreDetails_addressImageView);
        supplierHomepage_reviewTxtView=findViewById(R.id.clientViewStoreDetails_reviewTxtView);


    }
}
