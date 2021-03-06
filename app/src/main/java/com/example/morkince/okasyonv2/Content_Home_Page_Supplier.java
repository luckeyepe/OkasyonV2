package com.example.morkince.okasyonv2;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.view.View;
import android.widget.*;
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

public class Content_Home_Page_Supplier extends AppCompatActivity {
    ImageButton ocr_registration_supplier_validID_imageBtn2;
    ImageView supplierHompage_bannerImg;
    Button supplierHomepage_viewItemsBtn;
    TextView supplierHomepage_storeName_txtView;
    private static final int PICK_IMAGE = 100;
    private Uri filePath=null;
    FirebaseUser user;
    FirebaseFirestore db;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_supplier_homepage);
        refs();
        user = FirebaseAuth.getInstance().getCurrentUser();
        setProfileInformation();
        ocr_registration_supplier_validID_imageBtn2.setOnClickListener(uploadBannerImage);
        supplierHomepage_viewItemsBtn.setOnClickListener(viewItems);

    }

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
            Intent intent = new Intent(Content_Home_Page_Supplier.this,View_Store_Items.class);
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
            final ProgressDialog progressDialog = new ProgressDialog(Content_Home_Page_Supplier.this);
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
        AlertDialog.Builder alert = new AlertDialog.Builder(Content_Home_Page_Supplier.this);
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

    }

}
