package com.example.morkince.okasyonv2.testingphase;

import android.app.Dialog;
import android.content.Intent;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageButton;
import com.example.morkince.okasyonv2.R;
import com.example.morkince.okasyonv2.activities.client_activities.ClientItemDetailActivity;
import com.example.morkince.okasyonv2.activities.client_activities.EventDetailsActivity;
import com.example.morkince.okasyonv2.activities.client_activities.FoundEventDetailsActivity;
import com.example.morkince.okasyonv2.activities.homepage_supplier_activities.SupplierEditItemDetailsActivity;

public class testingpart extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_testingpart);
        Button one,two,three,four,five,six;
        one = findViewById(R.id.event1);
        two = findViewById(R.id.event2);
        three = findViewById(R.id.itemdetails);
        four = findViewById(R.id.edititemdetails);
        five = findViewById(R.id.buttonforsale);
        six = findViewById(R.id.buttonforrent);

        one.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(testingpart.this, EventDetailsActivity.class);
                startActivity(intent);
            }
        });
        two.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(testingpart.this, FoundEventDetailsActivity.class);
                startActivity(intent);
            }
        });
        three.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(testingpart.this, ClientItemDetailActivity.class);
                startActivity(intent);
            }
        });
        four.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(testingpart.this, SupplierEditItemDetailsActivity.class);
                startActivity(intent);
            }
        });
        five.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final Dialog dialog = new Dialog(testingpart.this);
                LayoutInflater layoutInflater = LayoutInflater.from(testingpart.this);
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
        });
        six.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final Dialog dialog = new Dialog(testingpart.this);
                LayoutInflater layoutInflater = LayoutInflater.from(testingpart.this);
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
        });

    }
}
