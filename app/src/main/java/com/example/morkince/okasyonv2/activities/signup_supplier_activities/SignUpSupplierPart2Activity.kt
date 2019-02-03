package com.example.morkince.okasyonv2.activities.signup_supplier_activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import android.util.Log
import com.example.morkince.okasyonv2.R
import com.example.morkince.okasyonv2.activities.ocr_activities.ocr_supplier_registration
import kotlinx.android.synthetic.main.activity_ocr_supplier_registration.*
import kotlinx.android.synthetic.main.activity_sign_up_supplier_part2.*

class SignUpSupplierPart2Activity : AppCompatActivity() {
    private val TAG = "SignUpSupplierPart2"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up_supplier_part2)

        //grab some stuff from previous activity
        var user_email = intent.getStringExtra("user_email")
        var user_password = intent.getStringExtra("user_password")
        var user_role = intent.getStringExtra("user_role")

        Log.d(TAG, "User Email $user_email, and Password $user_password")

        supportActionBar!!.hide()
        imageButton_SignUpSupplierPart2Before.setOnClickListener()
        {
            onBackPressed()
            overridePendingTransition(R.anim.slide_from_left,R.anim.slide_to_right)
        }
        imageButton_SignUpSupplierPart2Next.setOnClickListener {

            if (isCompleteData()){
                var store_store_name = textInputEditText_SignUpSupplierPart2StoreName.text.toString().trim()
                var user_first_name = textInputEditText_SignUpSupplierPart2FirstName.text.toString().trim()
                var user_last_name = textInputEditText_SignUpSupplierPart2LastName.text.toString().trim()
                var user_address = textInputEditText_SignUpSupplierPart2Address.text.toString().trim()
                var user_contact_no = textInputEditText_SignUpSupplierPart2ContactNumber.text.toString().trim()
                var store_description = textInputEditText_SignUpSupplierPart2About.text.toString().trim()

                var intent = Intent(this, ocr_supplier_registration::class.java)
                intent.putExtra("user_email", user_email)
                intent.putExtra("user_password", user_password)
                intent.putExtra("user_role", user_role)
                intent.putExtra("store_store_name", store_store_name)
                intent.putExtra("user_first_name", user_first_name)
                intent.putExtra("user_last_name", user_last_name)
                intent.putExtra("user_address", user_address)
                intent.putExtra("user_contact_no", user_contact_no)
                intent.putExtra("store_description", store_description)

                startActivity(intent)
                overridePendingTransition(R.anim.slide_from_right, R.anim.slide_to_left)
            }else{
                //todo popup error that empty fields
                var alertDialog = AlertDialog.Builder(this)
                alertDialog.setMessage("Please enter all user details!")
                alertDialog.setTitle("INFO MISSING")
                alertDialog.show()
            }
        }

    }

    private fun isCompleteData(): Boolean {
        return !(textInputEditText_SignUpSupplierPart2StoreName.text.toString().trim().isNullOrEmpty() &&
                textInputEditText_SignUpSupplierPart2FirstName.text.toString().trim().isNullOrEmpty() &&
                textInputEditText_SignUpSupplierPart2LastName.text.toString().trim().isNullOrEmpty() &&
                textInputEditText_SignUpSupplierPart2Address.text.toString().trim().isNullOrEmpty() &&
                textInputEditText_SignUpSupplierPart2ContactNumber.text.toString().trim().isNullOrEmpty() &&
                textInputEditText_SignUpSupplierPart2About.text.toString().trim().isNullOrEmpty())
    }

    //when pressing back button the transation will occur from right to left
    override fun finish() {
        super.finish()
       overridePendingTransition(R.anim.slide_from_left,R.anim.slide_to_right)

    }
}
