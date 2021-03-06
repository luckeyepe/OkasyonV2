package com.example.morkince.okasyonv2;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.GridView;
import com.example.morkince.okasyonv2.activities.homepage_supplier_activities.SupplierHomePage;
import com.example.morkince.okasyonv2.activities.model.Item_Images;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.google.protobuf.Any;
import droidninja.filepicker.FilePickerBuilder;
import droidninja.filepicker.FilePickerConst;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;

public class Select_Multiple_Images extends AppCompatActivity {

    ArrayList<String> filePaths= new ArrayList<>();
    ArrayList<String> e = new ArrayList<String>();
    GridView gridLayout;
    String itemID;
    private Uri filePathtoUpload=null;
    ProgressDialog progressDialog=null;
    Item_Images item_images;



    Button selectMultipleImages_selectImagesBtn,selectMultipleImages_uploadImages;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select__multiple__images);
        refs();
        selectMultipleImages_selectImagesBtn.setOnClickListener(selectImages);
        selectMultipleImages_uploadImages.setOnClickListener(uploadImages);


        Intent intent = getIntent();
        itemID = intent.getStringExtra("itemID");
    }



    private View.OnClickListener uploadImages = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            if (filePaths.isEmpty()) {
                showAlert("Please Select Images First","WARNING!");
            } else {
                int count = 1;
                Log.e("MESSAG ERROR","NOT NULL FILEPATH");
                ProgressDialog progressDialogTemporary = new ProgressDialog(Select_Multiple_Images.this);
                progressDialog = progressDialogTemporary;
                progressDialog.setTitle("Uploading...");
                // THIS IS ALTERNATE //progressDialog.getWindow().setType(WindowManager.LayoutParams.TYPE_TOAST);
                //  progressDialog.getWindow().setType(WindowManager.LayoutParams.TYPE_SYSTEM_ALERT);
                progressDialog.show();
                progressDialog.setCancelable(false);
                try {
                    for (String path : filePaths) {
                        filePathtoUpload = Uri.fromFile(new File(path));

                        uploadImages(itemID + count, filePathtoUpload);
                        count++;
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    };

    private View.OnClickListener selectImages = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
          filePaths.clear();

            FilePickerBuilder.getInstance().setMaxCount(5)
                    .setSelectedFiles(filePaths)
                   // .setActivityTheme(R.style.AppTheme)
                    .pickPhoto(Select_Multiple_Images.this);
        }
    };


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        switch(requestCode)
        {
            case FilePickerConst.REQUEST_CODE:
                if(resultCode==RESULT_OK && data!=null)
                {
                    filePaths = data.getStringArrayListExtra(FilePickerConst.KEY_SELECTED_PHOTOS);
                    Images image;
                    ArrayList<Images> images=new ArrayList<>();

                    try{
                        for(String path:filePaths){
                            image=new Images();
                            image.setImageURI(Uri.fromFile(new File(path)));
                            images.add(image);
                        }
                        gridLayout.setAdapter(new CustomAdapter(this,images));
                    }catch(Exception e)
                    {
                        e.printStackTrace();
                    }
                    selectMultipleImages_uploadImages.setEnabled(true);
                }
        }
    }

    public void uploadImages(final String txtid, final Uri filePath){
        if(filePath != null)
        {
            Log.e("THIS IS THE USER UID", txtid);
            final StorageReference ref = FirebaseStorage.getInstance().getReference().child("item_images").child(itemID).child(txtid);
            ref.putFile(filePath)
                    .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            ref.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                @Override
                                public void onSuccess(Uri uri) {
                                    // if (filePaths.size() == 1) {
                                    //   Item_Images item_images = new Item_Images(e, itemID);
                                    e.add(uri.toString());
                                    if (txtid.equals(itemID + filePaths.size() + "")) {
                                        progressDialog.dismiss();
                                        showAlertSuccessfulUpload("Successfully Uploaded!", "Success");

//                                        item_images.setItem_images_images_url(e);
//                                        item_images.setItem_images_item_uid(itemID);
//                                        FirebaseFirestore.getInstance()
//                                                .collection("Item_Images")
//                                                .document(itemID).set(item_images)
//                                                .addOnCompleteListener(new OnCompleteListener<Void>() {
//                                                    @Override
//                                                    public void onComplete(@NonNull Task<Void> task) {
//                                                        progressDialog.dismiss();
//                                                        showAlertSuccessfulUpload("Successfully Uploaded!", "Success");
//                                                    }
//                                                });
                                    }

                                    //    }else {
                                    //   Item_Images item_images = new Item_Images(e, itemID);
                                         /*   FirebaseFirestore.getInstance()
                                                    .collection("Item_Images")
                                                    .document(itemID).update("item_images_images_url", FieldValue.arrayUnion(uri.toString()))
                                                    .addOnSuccessListener(new OnSuccessListener<Void>() {
                                                        @Override
                                                        public void onSuccess(Void aVoid) {
                                                            progressDialog.dismiss();
                                                            showAlertSuccessfulUpload("Successfully Uploaded!", "Success");
                                                        }
                                                    });*/
                                    //  }
                                }

                            });


                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            progressDialog.dismiss();
                            showAlertSuccessfulUpload("An Error Occured","ERROR");


                        }
                    })
                    .addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {
                           // double progress = (100.0*taskSnapshot.getBytesTransferred()/taskSnapshot
                           //         .getTotalByteCount());
                          //  progressDialog.setMessage("Uploading : "+(int)progress+"%");
                        }
                    });
        }
        else
        {
           // showAlert("Please Select Images First","WARNING!");
        }

    }


    public void showAlert(String Message,String label)
    {
        //set alert for executing the task
        AlertDialog.Builder alert = new AlertDialog.Builder(Select_Multiple_Images.this);
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

    public void showAlertSuccessfulUpload(String Message,String label)
    {
        //set alert for executing the task
        AlertDialog.Builder alert = new AlertDialog.Builder(Select_Multiple_Images.this);
        alert.setTitle(""+label);
        alert.setMessage(""+Message);
        alert.setCancelable(false);

        alert.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            public void onClick (DialogInterface dialog, int id){
                dialog.cancel();
                Intent intent = new Intent(Select_Multiple_Images.this,SupplierHomePage.class);
                startActivity(intent);
                finish();
            }
        });

        Dialog dialog = alert.create();
        //  dialog.getWindow().setType(WindowManager.LayoutParams.TYPE_SYSTEM_ALERT);
        dialog.show();
    }




    public void refs()
    {
        selectMultipleImages_selectImagesBtn=findViewById(R.id.selectMultipleImages_selectImagesBtn);
        gridLayout=findViewById(R.id.selectMultipleImages_gridView);
        selectMultipleImages_uploadImages=findViewById(R.id.selectMultipleImages_uploadImages);
    }

}
