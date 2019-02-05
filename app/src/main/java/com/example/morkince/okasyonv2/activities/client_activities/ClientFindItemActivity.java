package com.example.morkince.okasyonv2.activities.client_activities;

import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.ImageButton;
import android.widget.Toast;
import android.widget.ToggleButton;
import com.example.morkince.okasyonv2.R;
import com.example.morkince.okasyonv2.activities.client_activities_fragments.TopEvents_Fragment;
import com.example.morkince.okasyonv2.activities.client_activities_fragments.YourEvents_Fragment;
import com.example.morkince.okasyonv2.testingphase.testingpart;

public class ClientFindItemActivity extends AppCompatActivity {
    BottomNavigationView bottomNavigationView;
    ToggleButton toggleButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_client_find_item);

        bottomNavigationView = findViewById(R.id.bottomNavigationView_ActivityClientFindItemBottomNavigationforMessageandAddtoCart);
        bottomNavigationView.setOnNavigationItemSelectedListener(bottomNavigationViewListener);

        toggleButton = findViewById(R.id.toggleButton_ActivityClientFindItemToggleForRentAndSale);


    }
    private BottomNavigationView.OnNavigationItemSelectedListener bottomNavigationViewListener =
            new BottomNavigationView.OnNavigationItemSelectedListener() {
                @Override
                public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                   int id = menuItem.getItemId();
                   if (id == R.id.navigation_addtocart)
                   {
                       Toast.makeText(getApplicationContext(),"Add to Cart",Toast.LENGTH_SHORT).show();
                       if (toggleButton.isChecked())
                       {
                            Toast.makeText(getApplicationContext(), "For Rent", Toast.LENGTH_SHORT).show();
                           final Dialog dialog = new Dialog(ClientFindItemActivity.this);
                           LayoutInflater layoutInflater = LayoutInflater.from(ClientFindItemActivity.this);
                           View dialogView = layoutInflater.inflate(R.layout.modal_addtocart_for_rent,null);

                           ImageButton buttonClose = dialogView.findViewById(R.id.imageButton_modalAddToCartForRentClose);

                           dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                           dialog.setContentView(dialogView);
                           dialog.setCancelable(false);
                           dialog.show();


                           buttonClose.setOnClickListener(new View.OnClickListener() {
                               @Override
                               public void onClick(View v) {
                                   dialog.dismiss();
                               }
                           });
                       }
                       else
                       {
                           Toast.makeText(getApplicationContext(), "For Sale", Toast.LENGTH_SHORT).show();

                           final Dialog dialog = new Dialog(ClientFindItemActivity.this);
                           LayoutInflater layoutInflater = LayoutInflater.from(ClientFindItemActivity.this);
                           View dialogView = layoutInflater.inflate(R.layout.modal_addtocart_for_sale,null);

                           ImageButton buttonClose = dialogView.findViewById(R.id.imageButton_modalAddToCartForSaleClose);

                           dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                           dialog.setContentView(dialogView);
                           dialog.setCancelable(false);
                           dialog.show();


                           buttonClose.setOnClickListener(new View.OnClickListener() {
                               @Override
                               public void onClick(View v) {
                                   dialog.dismiss();
                               }
                           });
                       }
                   }
                   else if (id == R.id.navigation_message_now)
                   {
                       Toast.makeText(getApplicationContext(),"Message Now",Toast.LENGTH_SHORT).show();
                   }
                    return true;
                }
            };
}
