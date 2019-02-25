package com.example.morkince.okasyonv2;

import android.util.Log;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.morkince.okasyonv2.activities.homepage_supplier_activities.SupplierHomePage;
import com.example.morkince.okasyonv2.activities.model.Item;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

public class ShowNearbyPlaces extends AppCompatActivity {
RecyclerView showNearbyPlacesRecyclerView;
ShowNearbyPlacesAdapter adapter;
private ArrayList<NearbyPlacesModel> nearbyPlacesModelList = new ArrayList<NearbyPlacesModel>();

GetNearbyPlacesAPI getNearbyPlaces;
    FirebaseFirestore db;
    FirebaseUser user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_nearby_places);
     //   nearbyPlacesModel.add(new Item(itemName,numStars,itemPrice,item_uid,item_category,item_description,isForSale,isPerSquareUnit,price_description,store_id,item_tag));
        user = FirebaseAuth.getInstance().getCurrentUser();
        db = FirebaseFirestore.getInstance();
        showNearbyPlacesRecyclerView=findViewById(R.id.showNearbyPlacesRecyclerView);

        db.collection("NearbyPlaces").document(user.getUid()).collection("nearby_places").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {

                    for (QueryDocumentSnapshot document : task.getResult()) {
                        NearbyPlacesModel  nearbyPlacesModel = document.toObject(NearbyPlacesModel.class);
                        nearbyPlacesModelList.add(nearbyPlacesModel);
                    }

                    adapter = new ShowNearbyPlacesAdapter(nearbyPlacesModelList,ShowNearbyPlaces.this);
                    showNearbyPlacesRecyclerView.addItemDecoration(new DividerItemDecoration(ShowNearbyPlaces.this, LinearLayoutManager.VERTICAL));
                    showNearbyPlacesRecyclerView.setLayoutManager(new LinearLayoutManager(ShowNearbyPlaces.this));
                    showNearbyPlacesRecyclerView.setAdapter(adapter);

                    if(nearbyPlacesModelList.isEmpty())
                    {
                        Toast.makeText(getApplicationContext(), "No records to show!",
                                Toast.LENGTH_SHORT).show();
                    }

                } else {
                    Toast.makeText(getApplicationContext(), "Error in Retrieving Records!!",
                            Toast.LENGTH_SHORT).show();
                }
            }
        });





    }
}
