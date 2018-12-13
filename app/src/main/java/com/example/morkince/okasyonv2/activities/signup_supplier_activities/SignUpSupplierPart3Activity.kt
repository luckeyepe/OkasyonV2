package com.example.morkince.okasyonv2.activities.signup_supplier_activities

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.WindowManager
import com.example.morkince.okasyonv2.R
import kotlinx.android.synthetic.main.activity_sign_up_supplier_part3.*

class SignUpSupplierPart3Activity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up_supplier_part3)

        textInputEditText_SignUpSupplierPart3StoreName.isEnabled = false
        textInputEditText_SignUpSupplierPart3Name.isEnabled =false
        textInputEditText_SignUpSupplierPart3Address.isEnabled = false
        textInputEditText_SignUpSupplierPart3EmailAddress.isEnabled =false
        textInputEditText_SignUpSupplierPart3ContactNumber.isEnabled =false
        textInputEditText_SignUpSupplierPart3About.isEnabled =false



        imageButton_SignUpSupplierPart3Before.setOnClickListener()
        {
            onBackPressed()
            overridePendingTransition(R.anim.slide_from_left,R.anim.slide_to_right)

        }
        imageButton_SignUpSupplierPart3Next.setOnClickListener()
        {
            val intent = Intent(this@SignUpSupplierPart3Activity, SignUpSupplierPart4Activity::class.java)
            startActivity(intent)
            overridePendingTransition(R.anim.slide_from_right, R.anim.slide_to_left)
        }
    }
    override fun finish() {
        super.finish()
        overridePendingTransition(R.anim.slide_from_left,R.anim.slide_to_right)

    }
}
