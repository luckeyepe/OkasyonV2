package com.example.morkince.okasyonv2.activities.client_activities

import android.content.Intent
import android.media.Image
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.appcompat.widget.Toolbar
import android.text.Html
import android.view.Menu
import com.example.morkince.okasyonv2.R
import com.example.morkince.okasyonv2.activities.homepages_for_supplier_client.ClientHomePage
import com.kd.dynamic.calendar.generator.ImageGenerator
import kotlinx.android.synthetic.main.activity_client_create_event_contents.*


class Client_Create_Event : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_client_create_event)

        val toolbar = findViewById<Toolbar>(R.id.toolbar)
        setSupportActionBar(toolbar)
        supportActionBar!!.title = "Create Event"
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)

        textInputEditText_clientCreateEventDate.isEnabled =false

        button_CreateEvent.setOnClickListener{
            startActivity(Intent(this, Client_Set_Preference::class.java))
        }

    }

    override fun onSupportNavigateUp(): Boolean {
      onBackPressed()
        return true
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.menu_for_toolbar, menu)
        return super.onCreateOptionsMenu(menu)
    }
}
