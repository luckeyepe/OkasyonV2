package com.example.morkince.okasyonv2.activities.client_activities

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import com.example.morkince.okasyonv2.R
import kotlinx.android.synthetic.main.activity_client_set_the_preference_summary_contents.*

class Client_Set_Preference_Summary: AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_client_set_the_preference_summary)

        val toolbar = findViewById<Toolbar>(R.id.toolbar2)
        setSupportActionBar(toolbar)
        supportActionBar!!.title = "Budget"
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)


    }


    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }
}