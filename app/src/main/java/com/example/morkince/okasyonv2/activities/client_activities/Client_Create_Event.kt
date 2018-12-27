package com.example.morkince.okasyonv2.activities.client_activities

import android.media.Image
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.Toolbar
import android.text.Html
import android.view.Menu
import com.example.morkince.okasyonv2.R
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
