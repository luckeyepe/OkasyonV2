package com.example.morkince.okasyonv2;

import android.content.Intent;
import android.net.Uri;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.GridView;
import droidninja.filepicker.FilePickerBuilder;
import droidninja.filepicker.FilePickerConst;

import java.io.File;
import java.util.ArrayList;

public class Select_Multiple_Images extends AppCompatActivity {

    ArrayList<String> filePaths= new ArrayList<>();
    GridView gridLayout;
    Button selectMultipleImages_selectImagesBtn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select__multiple__images);
        refs();
        selectMultipleImages_selectImagesBtn.setOnClickListener(selectImages);
    }

    private View.OnClickListener selectImages = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
          filePaths.clear();

            FilePickerBuilder.getInstance().setMaxCount(6)
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
                }
        }
    }

    public void refs()
    {
        selectMultipleImages_selectImagesBtn=findViewById(R.id.selectMultipleImages_selectImagesBtn);
        gridLayout=findViewById(R.id.selectMultipleImages_gridView);
    }

}
