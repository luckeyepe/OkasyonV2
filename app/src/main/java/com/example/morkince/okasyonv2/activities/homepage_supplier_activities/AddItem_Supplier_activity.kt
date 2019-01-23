
package com.example.morkince.okasyonv2.activities.homepage_supplier_activities

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import com.example.morkince.okasyonv2.R
import android.widget.ArrayAdapter
import android.widget.Spinner
import kotlinx.android.synthetic.main.activity_add_item__supplier_activity.*


class AddItem_Supplier_activity : AppCompatActivity() {
    var ItemCategory = arrayOf( "Male", "Female")
    var spinner: Spinner? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_item__supplier_activity)
        val actionbar = supportActionBar
        actionbar!!.title = "Add Item"
        actionbar.setDisplayHomeAsUpEnabled(true)
        spinner = this.addItem_itemCategorySpinner

        // Create an ArrayAdapter using a simple spinner layout and languages array
        val aa = ArrayAdapter(this, android.R.layout.simple_spinner_item, ItemCategory)
        // Set layout to use when the list of choices appear
        aa.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        // Set Adapter to Spinner
        spinner!!.setAdapter(aa)
    }
    override fun  onSupportNavigateUp():Boolean{
      onBackPressed()
        return true
    }

   override fun onCreateOptionsMenu(menu: Menu): Boolean {
        val mi = menuInflater
        mi.inflate(R.menu.actionbar1, menu)
        return super.onCreateOptionsMenu(menu)
    }


}




