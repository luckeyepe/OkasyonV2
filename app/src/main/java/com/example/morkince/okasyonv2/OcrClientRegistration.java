package com.example.morkince.okasyonv2;

import android.content.Intent;
import android.graphics.Bitmap;
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
import com.google.android.gms.vision.Frame;
import com.google.android.gms.vision.text.TextBlock;
import com.google.android.gms.vision.text.TextRecognizer;

import java.io.IOException;

public class OcrClientRegistration extends AppCompatActivity {

    ImageView ocr_client_registration_validID_ImgView;
    ImageButton ocr_client_registration_validID_imageBtn;
    Button ocr_client_registration_completeRegistrationBtn;
    private static final int MAXIMUM_FACE=1;
    private static FaceDetector.Face[] facesValidID = new FaceDetector.Face[MAXIMUM_FACE];
    private Uri filePathValidID=null;
    private Bitmap bitmapValidID;
    private static final int PICK_IMAGE = 100;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ocr_client_registration);
        refs();

        ocr_client_registration_validID_imageBtn.setOnClickListener(addValidID);
       // btn_supplier_valid_business_permit.setOnClickListener(addBusinesspermit);
    }

    private View.OnClickListener addValidID = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            openGallery();

        }
    };

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICK_IMAGE && resultCode == RESULT_OK
                && data != null && data.getData() != null) {
            //CHECK IF UPLOADING A VALID ID
            ocr_client_registration_validID_ImgView.setImageResource(R.drawable.camera_icon);
            filePathValidID = data.getData();
            try {
                bitmapValidID = MediaStore.Images.Media.getBitmap(getContentResolver(), filePathValidID);
                if (recognizeText() && detectFaceValidID()) {
                    ocr_client_registration_validID_ImgView.setImageBitmap(bitmapValidID);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void openGallery()
    {
        Intent gallery = new Intent(Intent.ACTION_PICK,MediaStore.Images.Media.INTERNAL_CONTENT_URI);
        startActivityForResult(gallery,PICK_IMAGE);
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
                Toast.makeText(getApplication(), "THERE IS  :  0 " + " FACE IN VALID ID", Toast.LENGTH_SHORT).show();
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
                Log.e("TEXT : ", validIDText + " HERE");
                if(validIDText.toString().contains("ABANTO, JAPHET TITUS HINGPIT"))
                {
                    Toast.makeText(getApplication(), "CONTAINS NAME!! ", Toast.LENGTH_SHORT).show();

                    return true;
                }
                else
                {
                    Toast.makeText(getApplication(), "INFORMATION INPUTTED DOES NOT MATCH ID!! ", Toast.LENGTH_SHORT).show();

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
        ocr_client_registration_validID_imageBtn=findViewById(R.id.ocr_client_registration_validID_imageBtn);
        ocr_client_registration_validID_ImgView=findViewById(R.id.ocr_client_registration_validID_ImgView);
        ocr_client_registration_completeRegistrationBtn=findViewById(R.id.ocr_client_registration_completeRegistrationBtn);
    }

}
