package com.example.morkince.okasyonv2.activities.signup_supplier_activities

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.example.morkince.okasyonv2.R
import kotlinx.android.synthetic.main.activity_sign_up_supplier_part2.*

class SignUpSupplierPart2Activity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up_supplier_part2)
        supportActionBar!!.hide()
        imageButton_SignUpSupplierPart2Before.setOnClickListener()
        {
            onBackPressed()
            overridePendingTransition(R.anim.slide_from_left,R.anim.slide_to_right)
        }
        imageButton_SignUpSupplierPart2Next.setOnClickListener {
            val intent = Intent(this@SignUpSupplierPart2Activity, SignUpSupplierPart3Activity::class.java)
            startActivity(intent)
            overridePendingTransition(R.anim.slide_from_right, R.anim.slide_to_left)
        }

    }

    //when pressing back button the transation will occur from right to left
    override fun finish() {
        super.finish()
       overridePendingTransition(R.anim.slide_from_left,R.anim.slide_to_right)

    }
}
