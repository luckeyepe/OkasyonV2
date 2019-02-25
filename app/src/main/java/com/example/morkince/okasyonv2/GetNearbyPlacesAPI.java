package com.example.morkince.okasyonv2;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.morkince.okasyonv2.activities.model.Item;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

public class GetNearbyPlacesAPI  extends AsyncTask<Object,String,String> {

    RecyclerView showNearbyPlacesRecyclerView;
    ShowNearbyPlacesAdapter adapter;
    private NearbyPlacesModel nearbyPlacesModel;

    GoogleMap mMap;
    Context context;
    String url;
    InputStream is;
    BufferedReader bufferedReader;
    StringBuilder stringBuilder;
    String data;
    FirebaseFirestore db;
    FirebaseUser user;

    public GetNearbyPlacesAPI(Context context) {
        this.context=context;
    }


    @Override
    protected String doInBackground(Object... objects) {

        mMap=(GoogleMap) objects[0];
        url=(String) objects[1];

        try {
            URL myUrl = new URL(url);
            HttpURLConnection httpURLConnection = (HttpURLConnection)myUrl.openConnection();
            httpURLConnection.connect();
            is = httpURLConnection.getInputStream();
            bufferedReader=new BufferedReader(new InputStreamReader(is));

            String line="";
            stringBuilder= new StringBuilder();
            while((line=bufferedReader.readLine()) !=null)
            {
                stringBuilder.append(line);
            }
            data=stringBuilder.toString();

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return data;
    }

    @Override
    protected void onPostExecute(String s) {
        try {
            JSONObject parentObject =  new JSONObject(s);
            JSONArray resultsArray = parentObject.getJSONArray("results");
            Log.e("MESSAGE","I GOT HERE INSIDE!" + resultsArray.length());
            for(int counter=0;counter<resultsArray.length();counter++)
            {
                JSONObject jsonObject = resultsArray.getJSONObject(counter);
                JSONObject locationObj = jsonObject.getJSONObject("geometry").getJSONObject("location");
                String latitude = locationObj.getString("lat");
                String longitude = locationObj.getString("lng");
                Log.e("KAABOY PAKo","KAABOT PAKO DIRI" + latitude + " " + longitude);
                JSONObject nameofObject = resultsArray.getJSONObject(counter);
                String nameOfStore=nameofObject.getString("name");

                String vicinityOfStore=nameofObject.getString("vicinity");

                String imageUrlOfStore=nameofObject.getString("icon");

                String starRatingofStore=nameofObject.getString("rating" + "");
                String totalNumberOfRatings = nameofObject.getString("user_ratings_total" + "");
                //String openingHours=nameofObject.getString("opening_hours" + "");
               // String openingHours=nameofObject.getJSONObject("opening_hours").getString("open_now") + "";
               // Log.e("KAABOY PAKo","KAABOT PAKO DIRI" + openingHours);

                user = FirebaseAuth.getInstance().getCurrentUser();
                db = FirebaseFirestore.getInstance();
                nearbyPlacesModel= new NearbyPlacesModel(nameOfStore,vicinityOfStore,imageUrlOfStore,Double.parseDouble(starRatingofStore),"true",totalNumberOfRatings);
                db.collection("NearbyPlaces").document(user.getUid()).collection("nearby_places")
                        .add(nearbyPlacesModel)
                        .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                            @Override
                            public void onSuccess(DocumentReference documentReference) {
                                Log.d("", "DocumentSnapshot added with ID: " + documentReference.getId());
                            }
                        })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Log.w("", "Error adding document", e);
                            }
                        });


                LatLng latLng= new LatLng(Double.parseDouble(latitude),Double.parseDouble(longitude));
                MarkerOptions options = new MarkerOptions();
                options.position(latLng);
                options.title(nameOfStore + " : " + vicinityOfStore);
                mMap.addMarker(options);

                Log.e("KAABOT PAKO DIRI", "KAABOT pa dyud PAKO");

                Log.e("INFO NAME : " ,nameOfStore);
                Log.e("INFO  : " ,vicinityOfStore + " STAR RATING " + starRatingofStore + " Opening Hours  : " + "true" );



            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}


/*
        db.collection("NearbyPlaces").document("123").collection("nearby_places").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
@Override
public void onComplete(@NonNull Task<QuerySnapshot> task) {
        for (QueryDocumentSnapshot document : task.getResult()) {
        document.getReference().delete();
        }

        }
        });
*/
