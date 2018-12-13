package com.example.morkince.okasyonv2.activities.signup_client_activities

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.example.morkince.okasyonv2.R
import com.example.morkince.okasyonv2.activities.homepage_supplier_activities.HomePage_Supplier_Activity
import kotlinx.android.synthetic.main.activity_sign_up_client_part2.*
import kotlinx.android.synthetic.main.activity_sing_up_summary_client.*

class SingUpSummaryClientActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sing_up_summary_client)
        imageButton_signupclientPart3NextButton.setOnClickListener {
            val intent = Intent(this,HomePage_Supplier_Activity ::class.java)
            // start your next activity
            startActivity(intent)
        }
    }
}
