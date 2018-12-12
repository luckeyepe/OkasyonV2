package com.example.morkince.okasyonv2.activities.signup_supplier_activities

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.example.morkince.okasyonv2.R
import com.example.morkince.okasyonv2.activities.login_activities.SignUpSupplierPart2
import kotlinx.android.synthetic.main.activity_sign_up_supplier_part1.*

class SignUpSupplierPart1Activity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up_supplier_part1)

        SupplierPart1ImgViewBttnNext.setOnClickListener {
            val intent =Intent(this@SignUpSupplierPart1Activity, SignUpSupplierPart2 :: class.java)
            startActivity(intent)
        }

    }
}
