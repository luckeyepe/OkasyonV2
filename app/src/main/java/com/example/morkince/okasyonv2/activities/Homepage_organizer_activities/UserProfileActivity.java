package com.example.morkince.okasyonv2.activities.Homepage_organizer_activities;

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
import android.view.View;
import android.widget.*;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import com.example.morkince.okasyonv2.R;
import com.example.morkince.okasyonv2.activities.model.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.kd.dynamic.calendar.generator.ImageGenerator;
import com.squareup.picasso.Picasso;

import java.io.IOException;

public class UserProfileActivity extends AppCompatActivity {
EditText UserEmailAdress, UserFirstname, UserLastname, UserAddress, UserContactnumber, UserDateofbirth, UserGender;
TextView User_rolename;
ImageView imageviewUserProfile;
ImageButton imgbtnAddPic, imgBtnEditProfile;
Button btnsave,btnCancel;
User userprofile;
FirebaseUser user;
FirebaseFirestore db;
StorageReference mStorageRef;
    String userAddress;
String user_id,user_id2;
    Bitmap generatedateIcon;
    ImageGenerator imageGenerator;
private static final int PICK_IMAGE = 100;
private Uri filePath=null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_profile);
        getSupportActionBar().setTitle("Profile");

        refs();
        user = FirebaseAuth.getInstance().getCurrentUser();
        Intent intent = getIntent();
        user_id= intent.getStringExtra("user_uid");
        user_id2= intent.getStringExtra("user_profPic");


        getUserDetails();
        enable(false);

        imgBtnEditProfile.setOnClickListener(edittheDetails)    ;
        imgbtnAddPic.setOnClickListener(pickEventImage);
        imgBtnEditProfile.setOnClickListener(edittheDetails);
        btnsave.setOnClickListener(saveUpdatedData);
        btnCancel.setOnClickListener(CancelData);

    }
    public void getUserDetails(){
        mStorageRef = FirebaseStorage.getInstance().getReference().child("user_profPic").child(user.getUid());

        mStorageRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {
                Picasso.get().load(uri.toString()).error(R.drawable.account).into(imageviewUserProfile);
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
                                 userAddress= document.getString("user_address");
                                userprofile =document.toObject(User.class);
                                UserFirstname.setText(userprofile.getUser_first_name());
                                UserLastname.setText(userprofile.getUser_last_name());
                                UserEmailAdress.setText(userprofile.getUser_email());
                                User_rolename.setText(userprofile.getUser_role());
                                UserDateofbirth.setText(userprofile.getUser_birth_date());
                                UserContactnumber.setText(userprofile.getUser_contact_no());
                                UserGender.setText(userprofile.getUser_gender());
                                UserAddress.setText(userAddress);
                               } else {
                                Log.d("", "No such document exist");
                            }
                        } else {
                            Log.d("", "Failed with ", task.getException());
                        }
                    }
                });

    }
    public View.OnClickListener pickEventImage = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
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
                imageviewUserProfile.setImageBitmap(bitmap);
                uploadImage(user.getUid());
//                uploadImage(event_id);
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
            Log.e("THIS IS THE user UID", txtid);

            final ProgressDialog progressDialog = new ProgressDialog(UserProfileActivity.this);
            progressDialog.setTitle("Uploading...");
            progressDialog.show();
            progressDialog.setCancelable(false);

            StorageReference ref = FirebaseStorage
                    .getInstance()
                    .getReference()
                    .child("user_profPic")
                    .child(user.getUid());

            ref.putFile(filePath)
                    .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                            progressDialog.dismiss();
                            showAlert("Successfully Updated Image!","Success");

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
//    public void uploadImage(String txtid){
//        if(filePath != null)
//        {
//            Log.e("THIS IS THE userprofile UID", txtid);
//
//            final ProgressDialog progressDialog = new ProgressDialog(UserProfileActivity.this);
//            progressDialog.setTitle("Uploading...");
//            progressDialog.show();
//            progressDialog.setCancelable(false);
//
//            StorageReference ref = FirebaseStorage
//                    .getInstance()
//                    .getReference()
//                    .child("images")
//                    .child(txtid);
//
//            ref.putFile(filePath)
//                    .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
//                        @Override
//                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
//
//                            progressDialog.dismiss();
//                            showAlert("Successfully Updated Image!","Success");
//
//                        }
//                    })
//                    .addOnFailureListener(new OnFailureListener() {
//                        @Override
//                        public void onFailure(@NonNull Exception e) {
//                            progressDialog.dismiss();
//                            showAlert("An Error Occured","ERROR");
//
//
//                        }
//                    })
//                    .addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
//                        @Override
//                        public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {
//                            double progress = (100.0*taskSnapshot.getBytesTransferred()/taskSnapshot
//                                    .getTotalByteCount());
//                            progressDialog.setMessage("Uploading : "+(int)progress+"%");
//                        }
//                    });
//        }
//
//    }
    public void showAlert(String Message,String label)
    {
        //set alert for executing the task
        AlertDialog.Builder alert = new AlertDialog.Builder(UserProfileActivity.this);
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

    public View.OnClickListener edittheDetails = new View.OnClickListener() {
        @Override
        public void onClick(View v) {

            enable(true);
            imgBtnEditProfile.setVisibility(View.INVISIBLE);
            btnsave.setVisibility(View.VISIBLE);
            btnCancel.setVisibility(View.VISIBLE);




        }
    };

    public void enable(boolean status)
    {

        UserAddress.setEnabled(status);
        UserContactnumber.setEnabled(status);

    }
    public View.OnClickListener CancelData = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            imgBtnEditProfile.setVisibility(View.VISIBLE);
            btnsave.setVisibility(View.INVISIBLE);
            btnCancel.setVisibility(View.INVISIBLE);
            enable(false);
        }
    };
    public View.OnClickListener saveUpdatedData = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Toast.makeText(getApplicationContext(), "Save", Toast.LENGTH_SHORT).show();
            enable(false);
            imgBtnEditProfile.setVisibility(View.VISIBLE);
            btnsave.setVisibility(View.INVISIBLE);
            btnCancel.setVisibility(View.INVISIBLE);
            db = FirebaseFirestore.getInstance();
            DocumentReference userToUpdate = db.collection("User").document("" + user.getUid());
            userToUpdate.update("user_first_name",  UserFirstname.getText().toString());
            userToUpdate.update("user_last_name",   UserLastname.getText().toString());
            userToUpdate.update("user_email",  UserEmailAdress.getText().toString());
            userToUpdate.update("user_birth_date",  UserDateofbirth.getText().toString());
            userToUpdate.update("user_contact_no",  UserContactnumber.getText().toString());
            userToUpdate.update("user_gender",  UserGender.getText().toString());
            userToUpdate.update("user_address",  UserAddress.getText().toString())
                    .addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {
//                            progressDialog.dismiss(); add progress dialog later on
                            showAlert("Successfully Updated Data", "SUCCESS!");
                        }
                    });
        }
    };

    public void refs()
    {
        UserEmailAdress=findViewById(R.id.editText_ProfileEmailAddress2);
        UserFirstname=findViewById(R.id.editText_ProfileFirstName2);
        UserLastname=findViewById(R.id.editText_ProfileLastName2);
        UserAddress=findViewById(R.id.editText_ProfileAddress2);
        UserContactnumber=findViewById(R.id.editText_ProfileContactNumber2);
        UserDateofbirth=findViewById(R.id.editText_ProfileDateOfBirth2);
        UserGender=findViewById(R.id.editText_ProfileGender2);
        User_rolename=findViewById(R.id.textView_homepageSupplierEmail);
        imageviewUserProfile=findViewById(R.id.ImageView_UserProfilePic);
        imgBtnEditProfile=findViewById(R.id.imageButton_EditProfile);
        imgbtnAddPic=findViewById(R.id.imageButton_ProfileAddPic);
        btnsave=findViewById(R.id.button_saveUserDetails);
        btnCancel=findViewById(R.id.button_cancelUserDetails);
    }
}
