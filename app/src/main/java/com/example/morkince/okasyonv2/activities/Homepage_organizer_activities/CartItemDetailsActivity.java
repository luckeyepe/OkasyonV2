package com.example.morkince.okasyonv2.activities.Homepage_organizer_activities;

import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import com.example.morkince.okasyonv2.R;

public class CartItemDetailsActivity extends AppCompatActivity {
Button btndelete;
TextView Itemprice, Itemquantity, ItemName, ItemDatefrom, ItemDateto;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart_item_details);
        refs();
    }
    public void refs()
    {
        btndelete=findViewById(R.id.Button_DeleteItemDetails);
        Itemprice=findViewById(R.id.textView_ActivityClientFindItemPriceofTheItem2);
        Itemquantity=findViewById(R.id.textview_itemquantity);
        ItemName=findViewById(R.id.textView_ActivityClientFindItemNameofTheItem2);
        ItemDatefrom=findViewById(R.id.textView_ItemDate);
        ItemDateto=findViewById(R.id.textView_ItemDate2);

    }
}
