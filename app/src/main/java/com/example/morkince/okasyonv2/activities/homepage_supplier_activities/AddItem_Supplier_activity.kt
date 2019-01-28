
package com.example.morkince.okasyonv2.activities.homepage_supplier_activities

import android.app.Activity
import android.content.ClipData
import android.content.Intent
import android.net.Uri
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.support.v7.app.ActionBarDrawerToggle
import android.support.v7.widget.Toolbar
import android.util.Log
import android.view.Menu
import com.example.morkince.okasyonv2.R
import kotlinx.android.synthetic.main.app_bar_home_page__supplier_.*
import kotlinx.android.synthetic.main.app_bar_home_page__supplier_.view.*
import android.view.MenuInflater
import android.view.View
import android.widget.ArrayAdapter
import android.widget.Spinner
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_add_item__supplier_activity.*
//import kotlinx.android.synthetic.main.activity_sign_up_client_part2.*
import java.io.IOException
import android.R.attr.data




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
    override fun  onSupportNavigateUp():Boolean{onBackPressed()
        return true
    }

   override fun onCreateOptionsMenu(menu: Menu): Boolean {
        val mi = menuInflater
        mi.inflate(R.menu.actionbar1, menu)
        return super.onCreateOptionsMenu(menu)
    }


}




