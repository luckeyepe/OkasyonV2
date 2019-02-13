package com.example.morkince.okasyonv2;

import android.util.Log;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.morkince.okasyonv2.activities.model.Item;
import com.example.morkince.okasyonv2.testingphase.ItemImagesModel;
import com.example.morkince.okasyonv2.testingphase.ItemImagesRecyclerAdapter;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;

public class testingImageSlider extends AppCompatActivity {

    private ArrayList<ItemImagesModel> itemImages = new ArrayList<>();
    ItemImagesRecyclerAdapter adapter;

    RecyclerView  recyclerView_itemImages;
    FirebaseStorage firebaseStorage;
    private StorageReference mStorageRef;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_testing_image_slider);
        mStorageRef = FirebaseStorage.getInstance().getReference();
        recyclerView_itemImages = findViewById(R.id.recyclerView_itemImages);
        recyclerView_itemImages.setHasFixedSize(true);



        for(int counter=1;counter<=5;counter++) {
            // Create a reference to the file to delete
            StorageReference desertRef = mStorageRef.child("item_images").child("18BJNznRPPHHstpRpKcJ").child("18BJNznRPPHHstpRpKcJ" + counter);
            Log.e("IMAGEF HERE", desertRef.getDownloadUrl().toString());
            itemImages.add( new ItemImagesModel(desertRef.getDownloadUrl() + ""));
            Log.e("IMAGES DIRI",itemImages.toString() + "HAHA" );
        }

       Log.e("IMAGES DIRI",itemImages.toString() );

        adapter = new ItemImagesRecyclerAdapter(itemImages,testingImageSlider.this);
        recyclerView_itemImages.setLayoutManager(new LinearLayoutManager(this));
      //  recyclerView_itemImages.setLayoutManager(new GridLayoutManager(testingImageSlider.this,2));
        recyclerView_itemImages.setAdapter(adapter);
    }
}
