package com.example.morkince.okasyonv2.activities.ocr_activities;

import android.content.Intent;
import android.graphics.Bitmap;
import android.media.FaceDetector;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.util.SparseArray;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.example.morkince.okasyonv2.R;
import com.example.morkince.okasyonv2.activities.signup_client_activities.SignUpUserSummaryActivity;
import com.example.morkince.okasyonv2.activities.signup_supplier_activities.SignUpSupplierPart3Activity;
import com.google.android.gms.vision.Frame;
import com.google.android.gms.vision.text.TextBlock;
import com.google.android.gms.vision.text.TextRecognizer;

import java.io.IOException;

public class SignupOCRClientOrganizer extends AppCompatActivity {
    private String TAG = "ocr_supplier_registration";
    ImageView ocr_registration_client_validID_ImgView,ocr_supplier_valid_business_permit;
    ImageButton ocr_registration_client_validID_imageBtn;
    Button btn_supplier_continue;
    boolean ifValidIdImageIsSelected=false;
    boolean ifBusinessPermitImageIsSelected =false;
    private Uri filePathValidID=null;
    private Uri filePathBusinessPermit=null;
    private Bitmap bitmapBusinessPermit;
    private Bitmap bitmapValidID;
    private static final int MAXIMUM_FACE=1;
    private static FaceDetector.Face[] facesValidID = new FaceDetector.Face[MAXIMUM_FACE];
    private static FaceDetector.Face[] facesBusinessPermit = new FaceDetector.Face[MAXIMUM_FACE];
    private static final int PICK_IMAGE = 100;
    private String user_email = "";
    private String user_password = "";
    private String user_role = "";
    private String user_first_name = "";
    private String user_last_name = "";
    private String user_address = "";
    private String user_contact_no = "";
    private String user_birth_date = "";
    private String user_gender = "";
    private String store_store_name = "";
    private String store_description = "";
    String validIDOCRText="";
    String validIDOCRTextUppercase="";
    boolean checkIfValidIdUploaded=false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {


        user_email = getIntent().getStringExtra("user_email");
        user_password = getIntent().getStringExtra("user_password");
        user_role = getIntent().getStringExtra("user_role");
        user_first_name = getIntent().getStringExtra("user_first_name");
        user_last_name = getIntent().getStringExtra("user_last_name");
        user_address = getIntent().getStringExtra("user_address");
        user_contact_no = getIntent().getStringExtra("user_contact_no");
        user_birth_date = getIntent().getStringExtra("user_birth_date");
        user_gender = getIntent().getStringExtra("user_gender");

        if (getIntent().hasExtra("store_store_name"))
        {
            store_store_name = getIntent().getStringExtra("store_store_name");
        }

        if (getIntent().hasExtra("store_description"))
        {
            store_description = getIntent().getStringExtra("store_description");
        }

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup_ocrclient_organizer);
        refs();
        //btn_supplier_continue.setEnabled(false);//button will not work without proper id

        ocr_registration_client_validID_imageBtn.setOnClickListener(addValidID);
        btn_supplier_continue.setOnClickListener(goToSummary);

    }

    private View.OnClickListener goToSummary = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if(checkIfValidIdUploaded==true) {
                btn_supplier_continue.setEnabled(true);
                Log.d(TAG, "This is the user role: " + user_role);

                if (!user_role.equals("supplier")) {
                    Log.d(TAG, "This is the user role: " + user_role);
                    Intent intent = new Intent(getApplicationContext(), SignUpUserSummaryActivity.class);
                    //grab data
                    intent.putExtra("user_email", user_email);
                    intent.putExtra("user_password", user_password);
                    intent.putExtra("user_role", user_role);
                    intent.putExtra("user_first_name", user_first_name);
                    intent.putExtra("user_last_name", user_last_name);
                    intent.putExtra("user_address", user_address);
                    intent.putExtra("user_contact_no", user_contact_no);
                    intent.putExtra("user_birth_date", user_birth_date);
                    intent.putExtra("user_gender", user_gender);
                    intent.putExtra("user_validIDURL", filePathValidID.toString());

                    startActivity(intent);
                    finish();
                } else {
                    Log.d(TAG, "This is the user role: " + user_role);
                    Log.d(TAG, "User Email " + user_email + ", and Password " + user_password);

                    Intent intent = new Intent(getApplicationContext(), SignUpSupplierPart3Activity.class);
                    //grab data if supplier, still need to think of how this should go
                    intent.putExtra("user_email", user_email);
                    intent.putExtra("user_password", user_password);
                    intent.putExtra("user_role", user_role);
                    intent.putExtra("user_first_name", user_first_name);
                    intent.putExtra("user_last_name", user_last_name);
                    intent.putExtra("user_address", user_address);
                    intent.putExtra("user_contact_no", user_contact_no);
                    intent.putExtra("store_store_name", store_store_name);
                    intent.putExtra("store_description", store_description);
                    intent.putExtra("store_ownerIDURL", filePathValidID.toString());

                    startActivity(intent);
                    finish();
                }
            }

            else
            {
                Toast.makeText(getApplication(), "Please upload Valid Documents First! ", Toast.LENGTH_SHORT).show();
            }
        }
    };




    private View.OnClickListener addValidID = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            ifValidIdImageIsSelected=true;
            openGallery();
        }
    };

    public void openGallery()
    {
        Intent gallery = new Intent(Intent.ACTION_OPEN_DOCUMENT, MediaStore.Images.Media.INTERNAL_CONTENT_URI);
        startActivityForResult(gallery,PICK_IMAGE);
    }

    //THIS IS TO LOAD THE IMAGES CHOSEN FROM GALLERY TO THE IMAGE VIEWS
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == PICK_IMAGE && resultCode == RESULT_OK
                && data != null && data.getData() != null )
        {
            //CHECK IF UPLOADING A VALID ID and user is not supplier
            if(ifValidIdImageIsSelected)
            {
                filePathValidID = data.getData();
                try {
                    bitmapValidID = MediaStore.Images.Media.getBitmap(getContentResolver(),filePathValidID);

                    if( recognizeText() &&  detectFaceValidID()) {
                        ocr_registration_client_validID_ImgView.setImageBitmap(bitmapValidID);
                       // btn_supplier_continue.setEnabled(true);
                        checkIfValidIdUploaded=true;
                    }
                }
                catch (IOException e)
                {
                    e.printStackTrace();
                }



            }

            //CHECK IF UPLOADING A BUSINESS PERMIT
            //todo prevent client and organiser from using bir
            else{
                filePathBusinessPermit = data.getData();
                try {
                    bitmapBusinessPermit = MediaStore.Images.Media.getBitmap(getContentResolver(),filePathBusinessPermit);
                    ocr_supplier_valid_business_permit.setImageBitmap(bitmapBusinessPermit);
                }
                catch (IOException e)
                {
                    e.printStackTrace();
                }
            }
            ifBusinessPermitImageIsSelected=false;
            ifValidIdImageIsSelected=false;
        }
    }

    private boolean detectFaceValidID()
    {
        int face_countValidID;
        try {
            bitmapValidID = MediaStore.Images.Media.getBitmap(getContentResolver(),filePathValidID);
            FaceDetector faceDetector = new FaceDetector(bitmapValidID.getWidth(),bitmapValidID.getHeight(), MAXIMUM_FACE);
            Bitmap detectImage=bitmapValidID.copy(Bitmap.Config.RGB_565,true);
            face_countValidID =faceDetector.findFaces(detectImage,facesValidID);

            if(face_countValidID!=0) {
                Toast.makeText(getApplication(), "THERE IS  :  " + face_countValidID + " FACE IN VALID ID", Toast.LENGTH_SHORT).show();
                return true;
            }
            else
            {
                return false;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return false;
    }

    private boolean recognizeText()
    {
        boolean isMatchingName = false;
        try {
            bitmapValidID = MediaStore.Images.Media.getBitmap(getContentResolver(),filePathValidID);
            TextRecognizer textRecognizer = new TextRecognizer.Builder(getApplicationContext()).build();

            if(!textRecognizer.isOperational())
            {
                Toast.makeText(getApplication(), "COULD NOT GET TEXT! ", Toast.LENGTH_SHORT).show();
            }
            else
            {
                Frame frame = new Frame.Builder().setBitmap(bitmapValidID).build();
                SparseArray<TextBlock> items = textRecognizer.detect(frame);
                StringBuilder validIDText = new StringBuilder();
                String fullName = (""+user_last_name + " " +user_first_name+"").toUpperCase();
                //fullName="ABANTO, JAPHET TITUS HINGPIT";
                String[] firsnameArray = fullName.split(" ");


                for(int ctr=0;ctr<items.size();ctr++)
                {
                    TextBlock myItem = items.valueAt(ctr);
                    validIDText.append(myItem.getValue().trim().toUpperCase() + " ");

                }


                validIDOCRTextUppercase= validIDText.toString();

                if (validIDText.toString().contains(",")){
                    validIDOCRTextUppercase = validIDText.toString().replace(",", "");
                }else {
                    validIDOCRTextUppercase = validIDText.toString();
                }

                Log.e("VALID OCR TEXT ", validIDOCRTextUppercase);

                for (String name: firsnameArray)
                {

                    if(validIDOCRTextUppercase.contains(name.toUpperCase()))
                    {
                        /* Toast.makeText(getApplication(), "CHECK NAME : " + name , Toast.LENGTH_SHORT).show();*/
                        Log.e("CHECK NAME",name);
                        isMatchingName =true;
                    }
                    else
                    {
                        isMatchingName= false;
                        /* Toast.makeText(getApplication(), "WRONG NAME : " + name , Toast.LENGTH_SHORT).show();*/
                        Toast.makeText(getApplication(), "NAME DOES NOT MATCH : " + fullName , Toast.LENGTH_SHORT).show();
                        break;
                    }

                }


            }
        } catch (IOException e) {
            e.printStackTrace();
        }


        return isMatchingName;
    }

    public void refs()
    {
        ocr_registration_client_validID_ImgView=findViewById(R.id.ocr_registration_client_validID_ImgView);
        ocr_registration_client_validID_imageBtn=findViewById(R.id.ocr_registration_client_validID_imageBtn);
        btn_supplier_continue = findViewById(R.id.ocr_client_registration_completeRegistrationBtn);
    }
}