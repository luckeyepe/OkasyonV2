package com.example.morkince.okasyonv2.activities.client_activities;

import android.os.Bundle;
import androidx.annotation.NonNull;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import androidx.appcompat.app.AppCompatActivity;
import android.view.MenuItem;
import android.widget.Toast;
import com.example.morkince.okasyonv2.R;

public class ClientFindItemActivity extends AppCompatActivity {
    BottomNavigationView bottomNavigationView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_client_find_item);

        bottomNavigationView = findViewById(R.id.bottomNavigationView_ActivityClientFindItemBottomNavigationforMessageandAddtoCart);
        bottomNavigationView.setOnNavigationItemSelectedListener(bottomNavigationViewListener);



    }
    private BottomNavigationView.OnNavigationItemSelectedListener bottomNavigationViewListener =
            new BottomNavigationView.OnNavigationItemSelectedListener() {
                @Override
                public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                   int id = menuItem.getItemId();
                   if (id == R.id.navigation_addtocart)
                   {
                       Toast.makeText(getApplicationContext(),"Add to Cart",Toast.LENGTH_SHORT).show();

                   }
                   else if (id == R.id.navigation_message_now)
                   {
                       Toast.makeText(getApplicationContext(),"Message Now",Toast.LENGTH_SHORT).show();
                   }
                    return true;
                }
            };
}
