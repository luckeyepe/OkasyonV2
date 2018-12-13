package com.example.morkince.okasyonv2.activities.signup_supplier_activities

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.morkince.okasyonv2.R
import kotlinx.android.synthetic.main.activity_sign_up_supplier_part4.*

class SignUpSupplierPart4Activity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up_supplier_part4)

        imageButton_SignUpSupplierPart4Before.setOnClickListener()
        {
            onBackPressed()
            overridePendingTransition(R.anim.slide_from_left,R.anim.slide_to_right)
        }
        button_SignUpSupplierPart4CreateAccount.setOnClickListener()
        {
            Toast.makeText(applicationContext,"HigH",Toast.LENGTH_SHORT).show()
        }
    }
}
