package com.example.morkince.okasyonv2.activities.signup_supplier_activities

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.WindowManager
import com.example.morkince.okasyonv2.R
import kotlinx.android.synthetic.main.activity_sign_up_supplier_part1.*
import com.example.morkince.okasyonv2.activities.homepage_supplier_activities.HomePage_Supplier_Activity
import com.example.morkince.okasyonv2.activities.signup_client_activities.SignUpClientPart2Activity
import kotlinx.android.synthetic.main.activity_sign_up_supplier_part1.*

class SignUpSupplierPart1Activity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up_supplier_part1)
        supportActionBar!!.hide()
         imageButton_SignUpSupplierPart1Next.setOnClickListener {
            val intent =Intent(this@SignUpSupplierPart1Activity, SignUpSupplierPart2Activity :: class.java)
            startActivity(intent)
            //animation for activity it makes the activity slide to another activity
            overridePendingTransition(R.anim.slide_from_right, R.anim.slide_to_left)
        }

    }
}
