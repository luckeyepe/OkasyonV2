package com.example.morkince.okasyonv2.activities.signup_supplier_activities

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.example.morkince.okasyonv2.R
import com.example.morkince.okasyonv2.activities.homepage_supplier_activities.HomePage_Supplier_Activity
import com.example.morkince.okasyonv2.activities.signup_client_activities.SignUpClientPart2Activity
import kotlinx.android.synthetic.main.activity_sign_up_client_part1.*
import kotlinx.android.synthetic.main.activity_sign_up_supplier_part1.*

class SignUpSupplierPart1Activity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up_supplier_part1)
            addItem.setOnClickListener {
            val intent = Intent(this, HomePage_Supplier_Activity::class.java)
            // start your next activity
            startActivity(intent)
        }
    }
}
