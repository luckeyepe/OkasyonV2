package com.example.morkince.okasyonv2.activities.homepages_for_supplier_client;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.*;
import com.example.morkince.okasyonv2.Content_Home_Page_Supplier;
import com.example.morkince.okasyonv2.R;
import com.example.morkince.okasyonv2.View_Store_Items;
import com.example.morkince.okasyonv2.activities.login_activities.MainActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.IOException;

public class SupplierHomePage extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    ImageButton ocr_registration_supplier_validID_imageBtn2;
    ImageView supplierHompage_bannerImg;
    Button supplierHomepage_viewItemsBtn;
    TextView supplierHomepage_storeName_txtView;
    private static final int PICK_IMAGE = 100;
    private Uri filePath=null;
    FirebaseUser user;
    FirebaseFirestore db;
    Button homepageSupplier_logoutBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_supplier_homepage);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Supplier Home");


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);


        //CODE STARTS HERE
        refs();
        user = FirebaseAuth.getInstance().getCurrentUser();
        setProfileInformation();
        ocr_registration_supplier_validID_imageBtn2.setOnClickListener(uploadBannerImage);
        supplierHomepage_viewItemsBtn.setOnClickListener(viewItems);
        homepageSupplier_logoutBtn.setOnClickListener(logout);
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

       if (id == R.id.nav_supplier_logout) {
           FirebaseAuth.getInstance().signOut();
           Intent intent = new Intent(SupplierHomePage.this,MainActivity.class);
           startActivity(intent);
////            // Handle the camera action
     }
////        else if (id == R.id.nav_manage) {
////
////        } else if (id == R.id.nav_share) {
////
////        } else if (id == R.id.nav_send) {
//
//        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    //THIS IS THE BACK_END CODE

    private View.OnClickListener logout = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            FirebaseAuth.getInstance().signOut();
            Intent intent = new Intent(SupplierHomePage.this,MainActivity.class);
            startActivity(intent);
        }
    };

    public void setProfileInformation()
    {
        db = FirebaseFirestore.getInstance();
        db.collection("Store").whereEqualTo("store_owner_id","1").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    for (DocumentSnapshot document : task.getResult()) {
                        /*  Log.d(TAG, document.getId() + " => " + document.getData());*/
                        String storeName= document.getString("store_store_name");
                        supplierHomepage_storeName_txtView.setText(storeName);
                    }
               /*     DocumentSnapshot document = task.getResult();
                    if (document.exists()) {


                    }
                    else
                    {
                        Toast.makeText(getApplicationContext(), "Error in Retrieving Records!!",
                                Toast.LENGTH_SHORT).show();
                    }*/
                }
            }
        });
    }

    private View.OnClickListener viewItems = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            Intent intent = new Intent(SupplierHomePage.this,View_Store_Items.class);
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
                uploadImage("116");
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
            Toast.makeText(getApplication(), "I GOT IN FILE PATH!! ", Toast.LENGTH_SHORT).show();
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



    public void refs()
    {
        ocr_registration_supplier_validID_imageBtn2=findViewById(R.id.ocr_registration_supplier_validID_imageBtn2);
        supplierHompage_bannerImg=findViewById(R.id.supplierHompage_bannerImg);
        supplierHomepage_viewItemsBtn=findViewById(R.id.supplierHomepage_viewItemsBtn);
        supplierHomepage_storeName_txtView=findViewById(R.id.supplierHomepage_storeName_txtView);
        homepageSupplier_logoutBtn=findViewById(R.id.homepageSupplier_logoutBtn);
    }
}
