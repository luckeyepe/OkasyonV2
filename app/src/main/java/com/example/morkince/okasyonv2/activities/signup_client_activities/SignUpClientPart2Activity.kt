package com.example.morkince.okasyonv2.activities.signup_client_activities

import android.annotation.SuppressLint
import android.app.DatePickerDialog
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.ArrayAdapter
import android.widget.Spinner
import com.example.morkince.okasyonv2.R
import kotlinx.android.synthetic.main.activity_sign_up_client_part2.*
import java.util.*

class SignUpClientPart2Activity : AppCompatActivity() {
    var gender = arrayOf( "Male", "Female")

    var spinner: Spinner? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up_client_part2)
        imageButton_signupclientPart2BackButton.setOnClickListener {
            val intent = Intent(this, SignUpClientPart1Activity::class.java)
            // start your next activity
            startActivity(intent)
        }
        imageButton_signupclientPart2NextButton.setOnClickListener {
            val intent = Intent(this,SingUpSummaryClientActivity::class.java)
            // start your next activity
            startActivity(intent)
        }
        spinner = this.Spinner_signupclientpart2Gender

        // Create an ArrayAdapter using a simple spinner layout and languages array
        val aa = ArrayAdapter(this, android.R.layout.simple_spinner_item, gender)
        // Set layout to use when the list of choices appear
        aa.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        // Set Adapter to Spinner
        spinner!!.setAdapter(aa)
    }
    @SuppressLint("SetTextI18n")
    fun funDate (view: View){
        val c = Calendar.getInstance()
        val day = c.get(Calendar.DAY_OF_MONTH)
        val month = c.get(Calendar.MONTH)
        val year = c.get(Calendar.YEAR)

        val dpd = DatePickerDialog(this, android.R.style.Theme_DeviceDefault_Dialog, DatePickerDialog.OnDateSetListener { datePicker, year, monthOfYear, dayOfMonth ->
            textView_signupClientPart2DateOfBirth.text ="$dayOfMonth, $monthOfYear $year"
        }, year, month, day)

        //show datepicker
        dpd.show()
    }
}
