package com.example.morkince.okasyonv2.activities.signup_client_activities

import android.annotation.SuppressLint
import android.app.DatePickerDialog
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.app.AlertDialog
import android.view.View
import android.widget.ArrayAdapter
import android.widget.Spinner
import com.example.morkince.okasyonv2.R
import kotlinx.android.synthetic.main.activity_sign_up_client_part2.*
import java.util.*
import javax.xml.datatype.DatatypeConstants.MONTHS

class SignUpClientPart2Activity : AppCompatActivity() {
    var gender = arrayOf( "Male", "Female")
    var spinner: Spinner? = null
    var calendar = Calendar.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up_client_part2)

        //grab some stuff from previous activity
        var user_email = intent.getStringExtra("user_email")
        var user_password = intent.getStringExtra("user_password")

        imageButton_signupclientPart2BackButton.setOnClickListener {
            onBackPressed()
        }

        //calendar popup
        val c = Calendar.getInstance()
        val year = c.get(Calendar.YEAR)
        val month = c.get(Calendar.MONTH)
        val day = c.get(Calendar.DAY_OF_MONTH)


//        val date = DatePickerDialog.OnDateSetListener { view, year, monthOfYear, dayOfMonth ->
//            // TODO Auto-generated method stub
//            calendar.set(Calendar.YEAR, year)
//            calendar.set(Calendar.MONTH, monthOfYear)
//            calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth)
//            updatetextInputEditText_signupClientPart2DateOfBirth()
//        }

        textInputLayout6.setOnClickListener {
            val c = Calendar.getInstance()
            val year = c.get(Calendar.YEAR)
            val month = c.get(Calendar.MONTH)
            val day = c.get(Calendar.DAY_OF_MONTH)

            val dpd = DatePickerDialog(this, DatePickerDialog.OnDateSetListener { view, year, monthOfYear, dayOfMonth ->
                // Display Selected date in textbox
                var date = "$monthOfYear/$dayOfMonth/$year"
                textInputEditText_signupclientPart2DateOfBirth.setText(date)
            }, year, month, day)

            dpd.show()
        }



        imageButton_signupclientPart2NextButton.setOnClickListener {
            //grab data
            if(isCompleteData()){
                if(checkbox_signupPart2TermsAndCondition.isActivated){
                    var user_first_name = textInputEditText_signupclientPart2FirstName.text.toString().trim()
                    var user_last_name = textInputEditText_signupclientPart2LastName.text.toString().trim()
                    var user_address = textInputEditText_signupclientPart2Address.text.toString().trim()
                    var user_contact_no = textInputEditText_signupClientclientPart2ContactNumber.text.toString().trim()
                    var user_birth_date = textInputEditText_signupclientPart2DateOfBirth.text.toString().trim()

                    var user_gender: String?
                    if(spinner!!.equals("Male")){
                        user_gender= "m"
                    }else{
                        user_gender = "f"
                    }

                    val intent = Intent(this,SingUpSummaryClientActivity::class.java)
                    //put extra data
                    intent.putExtra("user_first_name", user_first_name)
                    intent.putExtra("user_last_name", user_last_name)
                    intent.putExtra("user_address", user_address)
                    intent.putExtra("user_contact_no", user_contact_no)
                    intent.putExtra("user_birth_date", user_birth_date)
                    intent.putExtra("user_gender", user_gender)
                    intent.putExtra("user_email", user_gender)
                    intent.putExtra("user_password", user_gender)

                    // start your next activity
                    startActivity(intent)
                }else{
                    //popup error that terms and conditions have not been agreed
                    var alertDialog = AlertDialog.Builder(this)
                    alertDialog.setMessage("Make sure you agree to our tems and services")
                    alertDialog.setTitle("TERMS AND SERVICES")
                    alertDialog.show()
                }
            }else{
                //todo popup error for incomplete data
                var alertDialog = AlertDialog.Builder(this)
                alertDialog.setMessage("Please enter all user details")
                alertDialog.setTitle("INFO MISSING")
                alertDialog.show()
            }

        }


        //set up drop down list for genders
        spinner = this.Spinner_signupclientpart2Gender

        // Create an ArrayAdapter using a simple spinner layout and languages array
        val arrayAdapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, gender)
        // Set layout to use when the list of choices appear
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        // Set Adapter to Spinner
        spinner!!.adapter = arrayAdapter
    }

    private fun isCompleteData(): Boolean {
        return !(textInputEditText_signupclientPart2FirstName.text.isNullOrEmpty() && textInputEditText_signupclientPart2LastName.text.isNullOrEmpty()
                && textInputEditText_signupclientPart2Address.text.isNullOrEmpty() && textInputEditText_signupClientclientPart2ContactNumber.text.isNullOrEmpty()
                && textInputEditText_signupclientPart2DateOfBirth.text.isNullOrEmpty())
    }

}
