package com.example.morkince.okasyonv2.activities.ocr_activities;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.FaceDetector;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.util.SparseArray;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;
import com.example.morkince.okasyonv2.R;
import com.example.morkince.okasyonv2.activities.signup_client_activities.SignUpUserSummaryActivity;
import com.google.android.gms.vision.Frame;
import com.google.android.gms.vision.text.TextBlock;
import com.google.android.gms.vision.text.TextRecognizer;

import java.io.IOException;

public class ocr_supplier_registration extends AppCompatActivity {

    ImageView ocr_supplier_valid_id,ocr_supplier_valid_business_permit;
    ImageButton btn_supplier_valid_id,btn_supplier_valid_business_permit;
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
    private String user_email = getIntent().getStringExtra("user_email");
    private String user_password = getIntent().getStringExtra("user_password");
    private String user_role = getIntent().getStringExtra("user_role");
    private String user_first_name = getIntent().getStringExtra("user_first_name");
    private String user_last_name = getIntent().getStringExtra("user_last_name");
    private String user_address = getIntent().getStringExtra("user_address");
    private String user_contact_no = getIntent().getStringExtra("user_contact_no");
    private String user_birth_date = getIntent().getStringExtra("user_birth_date");
    private String user_gender = getIntent().getStringExtra("user_gender");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ocr_supplier_registration);
        refs();
        btn_supplier_continue.setEnabled(false);//button will not work without proper id

        btn_supplier_valid_id.setOnClickListener(addValidID);
        btn_supplier_valid_business_permit.setOnClickListener(addBusinesspermit);
        btn_supplier_continue.setOnClickListener(goToSummary);
    }

    private View.OnClickListener goToSummary = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if (user_role != "supplier") {
                Intent intent = new Intent(getApplicationContext(), SignUpUserSummaryActivity.class);
                //firestore and firebase auth insert here





                startActivity(intent);
                finish();
            }

        }
    };

    //ON CLICK LISTENER TO ADD IMAGE TO VALID ID IMAGE VIEW AND CHOOSE FROM GALLERY
    private View.OnClickListener addValidID = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            ifValidIdImageIsSelected=true;
            openGallery();
        }
    };

    private View.OnClickListener addBusinesspermit = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            ifBusinessPermitImageIsSelected=true;
            openGallery();
        }
    };


    //METHOD FOR ADDING IMAGE TO VALID ID IMAGE VIEW AND CHOOSE FROM GALLERY
    public void openGallery()
    {
        Intent gallery = new Intent(Intent.ACTION_PICK,MediaStore.Images.Media.INTERNAL_CONTENT_URI);
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
            if(ifValidIdImageIsSelected )
            {
                filePathValidID = data.getData();
                try {
                     bitmapValidID = MediaStore.Images.Media.getBitmap(getContentResolver(),filePathValidID);



                    if( recognizeText() &&  detectFaceValidID()) {
                        ocr_supplier_valid_id.setImageBitmap(bitmapValidID);
                        btn_supplier_continue.setEnabled(true);
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

                for(int ctr=0;ctr<items.size();ctr++)
                {
                    TextBlock myItem = items.valueAt(ctr);
                    validIDText.append(myItem.getValue());
                    validIDText.append("\n");
                }

                String fullName = ""+user_last_name+", "+user_first_name+"".toUpperCase();
                if(validIDText.toString().contains(fullName))
                {
                    Toast.makeText(getApplication(), "CONTAINS NAME!! ", Toast.LENGTH_SHORT).show();
                    Log.e("TEXT : ", validIDText + "");
                    return true;
                }
                else
                {
                    return false;
                }


            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return false;
    }

    public void refs()
    {
        ocr_supplier_valid_id=findViewById(R.id.ocr_registration_supplier_validID_ImgView);
        ocr_supplier_valid_business_permit=findViewById(R.id.ocr_registration_supplier_busPermit_ImgView);
        btn_supplier_valid_id=findViewById(R.id.ocr_registration_supplier_validID_imageBtn);
        btn_supplier_valid_business_permit=findViewById(R.id.ocr_registration_supplier_businessPermit_imgBtn);
        btn_supplier_continue = findViewById(R.id.ocr_supplier_registration_completeRegistrationBtn);
    }
}