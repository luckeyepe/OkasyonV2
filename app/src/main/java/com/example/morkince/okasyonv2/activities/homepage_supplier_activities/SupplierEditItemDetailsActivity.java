package com.example.morkince.okasyonv2.activities.homepage_supplier_activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;
import com.example.morkince.okasyonv2.R;

public class SupplierEditItemDetailsActivity extends AppCompatActivity {
    EditText itemname,priceitem,itemdetails;
    ImageView edit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_supplier_edit_item_details);

        edit =findViewById(R.id.imageView_supplierEditItemDetailsEdit);
        itemname =findViewById(R.id.editText_supplierEditItemDetailsNameofItem);
        priceitem =findViewById(R.id.editText_supplierEditItemDetailsPriceofItem);
        itemdetails =findViewById(R.id.editText_supplierEditItemDetails);

        edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                itemname.setEnabled(true);
                priceitem.setEnabled(true);
                itemdetails.setEnabled(true);
            }
        });



    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        int id = item.getItemId();
        if(id == R.id.delete_item)
        {
            Toast.makeText(getApplicationContext(), "Delete This Item?", Toast.LENGTH_SHORT).show();
        }

        return super.onOptionsItemSelected(item);
    }
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_activity_supplier_edit_item_details, menu);
        return true;
    }
}
