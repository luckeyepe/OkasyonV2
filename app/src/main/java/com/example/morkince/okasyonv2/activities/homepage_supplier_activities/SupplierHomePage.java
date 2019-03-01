package com.example.morkince.okasyonv2.activities.homepage_supplier_activities;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.*;
import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.morkince.okasyonv2.*;
import com.example.morkince.okasyonv2.R;
import com.example.morkince.okasyonv2.activities.Homepage_organizer_activities.UserProfileActivity;
import com.example.morkince.okasyonv2.activities.chat_activities.LatestMessagesActivity;
import com.example.morkince.okasyonv2.activities.client_activities.ClientHomePage;
import com.example.morkince.okasyonv2.activities.login_activities.MainActivity;
import com.example.morkince.okasyonv2.activities.model.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomnavigation.BottomNavigationView;
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

public class SupplierHomePage extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    ImageButton ocr_registration_supplier_validID_imageBtn2;
    ImageView supplierHompage_bannerImg,SupplierHomepage_addressImageView;
    Button supplierHomepage_viewItemsBtn,supplierHomepage_saveBtn,supplierHomepage_editBtn;
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
    String userID;

    ImageView drawerImage;
    TextView drawerUsername;
    TextView drawerAccount;
    private ArrayList<Reviews> reviews = new ArrayList<>();
    StoreReviewsAdapter adapter;
    RecyclerView supplierHomePage_recyclerView;
    int size = 0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_supplier_homepage);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setTitle("Supplier");

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

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view1);
        navigationView.setNavigationItemSelectedListener(this);


        //CODE STARTS HERE

        refs();
        user = FirebaseAuth.getInstance().getCurrentUser();
        userID=user.getUid();
        updateSupplierProfileInfo();
        enableEditText(false);
        supplierHomepage_editBtn.setOnClickListener(editInfo);
        supplierHomepage_saveBtn.setOnClickListener(saveInfo);
        SupplierHomepage_addressImageView.setOnClickListener(getDirections);




        ocr_registration_supplier_validID_imageBtn2.setOnClickListener(uploadBannerImage);
        supplierHomepage_viewItemsBtn.setOnClickListener(viewItems);
        setProfileInformation();



        //ADD REVIEWS TO RECYCLER VIEW

       getStoreID();
       //FIRST GET THE ID OF STORE TO LOCATE THE REVIEWS OF THE SPECIFIC STORE
        db = FirebaseFirestore.getInstance();
        db.collection("Store").whereEqualTo("store_owner_id",user.getUid()).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    for (DocumentSnapshot document : task.getResult()) {
                        documentID = document.getId();
                        db = FirebaseFirestore.getInstance();
                        db.collection("Store_Rating").document("store_rating").collection(documentID).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
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
                                    adapter = new StoreReviewsAdapter(reviews, SupplierHomePage.this);
                                    supplierHomePage_recyclerView.setAdapter(adapter);
                                    supplierHomePage_recyclerView.addItemDecoration(new DividerItemDecoration(SupplierHomePage.this, LinearLayoutManager.VERTICAL));
                                    supplierHomePage_recyclerView.setLayoutManager(new LinearLayoutManager(SupplierHomePage.this));
                                } else {
                                    Toast.makeText(getApplicationContext(), "Error in Retrieving Records!!",
                                            Toast.LENGTH_SHORT).show();
                                }
                                supplierHomepage_reviewTxtView.setText("REVIEWS(" + reviews.size() + ")");
                            }
                        });
                    }
                }
            }
        });

/*
        reviews.add(new Reviews("kaylokoto",4,"YOUR STORE IS REALLY GREAT! I WANT TO RECOMMEND YOU!I WANT TO RECOMMEND YOU!I WANT TO RECOMMEND YOU!I WANT TO RECOMMEND YOU!"));
        reviews.add(new Reviews("colinamarc",3,"YOUR STORE IS REALLY AMAZING!"));
        reviews.add(new Reviews("japhet",2,"YOUR STORE IS REALLY NICE!"));*/




        //LOAD IMAGE BANNER
        mStorageRef = FirebaseStorage.getInstance().getReference().child("images").child(user.getUid());
        mStorageRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {
                Picasso.get().load(uri.toString()).error(R.mipmap.ic_launcher).into(supplierHompage_bannerImg);

            }
        });


    }


    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        // Inflate the menu; this adds items to the action bar if it is present.
//        getMenuInflater().inflate(R.menu.activity_supplier_homepage_main, menu);
//        return true;
//    }

//    @Override
//    public boolean onOptionsItemSelected(MenuItem item) {
//        // Handle action bar item clicks here. The action bar will
//        // automatically handle clicks on the Home/Up button, so long
//        // as you specify a parent activity in AndroidManifest.xml.
//        int id = item.getItemId();
//
//        //noinspection SimplifiableIfStatement
//        if (id == R.id.action_settings) {
//            return true;
//        }
//
//        return super.onOptionsItemSelected(item);
//    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
       int id = item.getItemId();

       switch(id){
           case R.id.nav_supplier_logout:
               FirebaseAuth.getInstance().signOut();
               Intent intent = new Intent(SupplierHomePage.this,MainActivity.class);
               startActivity(intent);
               finish();
               break;

           case R.id.nsv_supplier_messages:
               Intent intent1 = new Intent (SupplierHomePage.this, LatestMessagesActivity.class);
               startActivity(intent1);
               break;

           case R.id.nav_supplier_transaction:
               Intent intent2 = new Intent (SupplierHomePage.this, Transaction_Supplier_Activity.class);
               startActivity(intent2);
               break;

           case R.id.nav_supplier_notification:
               break;

           default: break;

       }

//       if (id == R.id.nav_supplier_logout) {
//           FirebaseAuth.getInstance().signOut();
//           Intent intent = new Intent(SupplierHomePage.this,MainActivity.class);
//           startActivity(intent);
//           finish();
//     }
//
//     else if(id == R.id.nsv_supplier_messages)
//       {
//           Intent intent = new Intent (SupplierHomePage.this, LatestMessagesActivity.class);
//           startActivity(intent);
//       }


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    //THIS IS THE BACK_END CODE
    //

    //THIS IS THE BACK_END CODE

    public void setProfileInformation()
    {
        db = FirebaseFirestore.getInstance();
        db.collection("Store").whereEqualTo("store_owner_id",user.getUid()).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    for (DocumentSnapshot document : task.getResult()) {
                        /*  Log.d(TAG, document.getId() + " => " + document.getData());*/
                        String storeName= document.getString("store_store_name");
                        supplierHomepage_storeName_txtView.setText(storeName);
                    }

                }
            }
        });

        db.collection("User").document("" + user.getUid()).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
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


    private View.OnClickListener viewItems = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            Intent intent = new Intent(SupplierHomePage.this,View_Store_Items.class);
            intent.putExtra("storeID",documentID);
            Log.e("DOCUMENT OUTSIDE",documentID);
            startActivity(intent);
        }
    };

    private View.OnClickListener uploadBannerImage = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            openGallery();
        }
    };

    public void openGallery()
    {
        Intent gallery = new Intent(Intent.ACTION_PICK,MediaStore.Images.Media.INTERNAL_CONTENT_URI);
        startActivityForResult(gallery,PICK_IMAGE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == PICK_IMAGE && resultCode == RESULT_OK
                && data != null && data.getData() != null ) {
            filePath = data.getData();
            try {
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), filePath);
                supplierHompage_bannerImg.setImageBitmap(bitmap);
                uploadImage(user.getUid());
            }
            catch (IOException e)
            {
                e.printStackTrace();
            }
        }
    }

    public void uploadImage(String txtid){
        if(filePath != null)
        {
            Log.e("THIS IS supplier UID", txtid);

            final ProgressDialog progressDialog = new ProgressDialog(SupplierHomePage.this);
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
        AlertDialog.Builder alert = new AlertDialog.Builder(SupplierHomePage.this);
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
        supplierHomepage_saveBtn.setEnabled(booleanState);
    }

    //ON CLICK LISTENERS

    private View.OnClickListener getDirections = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            Intent intent = new Intent(SupplierHomePage.this,GetStoreDirections.class);
            intent.putExtra("storeLocation",supplierHomepage_storeAddress.getText().toString());
            startActivity(intent);
        }
    };
    private View.OnClickListener editInfo = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            enableEditText(true);
        }
    };

    private View.OnClickListener saveInfo = new View.OnClickListener() {
        @Override
        public void onClick(View view) {

            if (supplierHomepage_storeName_txtView.getText().toString().isEmpty()) {
                supplierHomepage_storeName_txtView.setError("Store Name is Blank");
            } else if (supplierHomepage_storeContact.getText().toString().isEmpty()) {
                supplierHomepage_storeContact.setError("Contact Number is Blank");
            } else {
                final ProgressDialog progressDialog = new ProgressDialog(SupplierHomePage.this);
                progressDialog.setTitle("Updating...");
                // THIS IS ALTERNATE //progressDialog.getWindow().setType(WindowManager.LayoutParams.TYPE_TOAST);
                //  progressDialog.getWindow().setType(WindowManager.LayoutParams.TYPE_SYSTEM_ALERT);
                progressDialog.show();
                progressDialog.setCancelable(false);


                db = FirebaseFirestore.getInstance();
                db.collection("Store").whereEqualTo("store_owner_id", user.getUid()).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (DocumentSnapshot document : task.getResult()) {
                                documentID = document.getId();

                                FirebaseFirestore.getInstance().collection("Store").document(documentID).update("store_store_name", supplierHomepage_storeName_txtView.getText().toString());
                            }
                        }
                    }
                });

                db = FirebaseFirestore.getInstance();
                DocumentReference userToUpdate = db.collection("User").document("" + user.getUid());
                userToUpdate.update("user_contact_no", supplierHomepage_storeContact.getText().toString())
                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {
                                progressDialog.dismiss();
                                showAlert("Successfully Updated Data", "SUCCESS!");
                            }
                        });

                enableEditText(false);
            }
        }
    };

    //THIS IS A METHOD TO GET STORE ID BOTH IN SAVING DATA AND UPON LOADING OF THE UI
            public void getStoreID(){
                db = FirebaseFirestore.getInstance();
                db.collection("Store").whereEqualTo("store_owner_id",user.getUid()).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (DocumentSnapshot document : task.getResult()) {
                                documentID = document.getId();
                                documentIDofStore=document.getId();
                                Log.e("DOCUMENT INSIDEEE",documentID);

                            }
                        }
                    }
                });
            }
    public void updateSupplierProfileInfo()
    {
        mStorageRef = FirebaseStorage.getInstance().getReference().child("images").child(userID);

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

        //     Intent intent = new Intent(MainActivity.this, homepage.class);
        //    intent.putExtra("name", personName+"");     }

        NavigationView drawer =  findViewById(R.id.nav_view1);
        View headerView = drawer.getHeaderView(0);
        drawerImage =  headerView.findViewById(R.id.imageView_SupplierImage);
        drawerUsername =  headerView.findViewById(R.id.textView_SupplierName);
        drawerAccount =headerView.findViewById(R.id.textView_SupplierEmail);


    }


    public void refs()
    {
        ocr_registration_supplier_validID_imageBtn2=findViewById(R.id.ocr_registration_supplier_validID_imageBtn2);
        supplierHompage_bannerImg=findViewById(R.id.supplierHompage_bannerImg);
        supplierHomepage_viewItemsBtn=findViewById(R.id.supplierHomepage_viewItemsBtn);
        supplierHomepage_storeName_txtView=findViewById(R.id.supplierHomepage_storeName_txtView);
        supplierHomePage_recyclerView=findViewById(R.id.supplierHomePage_recyclerView);
        supplierHomepage_storeContact=findViewById(R.id.supplierHomepage_storeContact);
        supplierHomepage_storeAddress=findViewById(R.id.supplierHomepage_storeAddress);
        supplierHomepage_saveBtn=findViewById(R.id.supplierHomepage_saveBtn);
        supplierHomepage_editBtn=findViewById(R.id.supplierHomepage_editBtn);
        SupplierHomepage_addressImageView=findViewById(R.id.SupplierHomepage_addressImageView);
        supplierHomepage_reviewTxtView=findViewById(R.id.supplierHomepage_reviewTxtView);


    }
}
