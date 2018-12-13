package com.example.morkince.okasyonv2.activities.signup_client_activities

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Spinner
import com.example.morkince.okasyonv2.R
import kotlinx.android.synthetic.main.activity_sign_up_client_part2.*

class SignUpClientPart2Activity : AppCompatActivity() {
    var gender = arrayOf( "Male", "Female")
    var spinner: Spinner? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up_client_part2)

        //grab some stuff from previous activity
        var user_email = intent.getStringExtra("user_email")
        var user_password = intent.getStringExtra("user_password")

        imageButton_signupclientPart2BackButton.setOnClickListener {
            onBackPressed()
        }

        imageButton_signupclientPart2NextButton.setOnClickListener {
            //grab data
            if(isCompleteData()){
                if(checkbox_signupClientPart2TermsAndCondition.isActivated){
                    var user_first_name = textInputEditText_signupClientPart2FirstName.text.toString().trim()
                    var user_last_name = textInputEditText_signupClientPart2LastName.text.toString().trim()
                    var user_address = textInputEditText_signupClientPart2Address.text.toString().trim()
                    var user_contact_no = textInputEditText_signupClientPart2ContactNumber.text.toString().trim()
                    var user_birth_date = textInputEditText_signupClientPart2DateOfBirth.text.toString().trim()
                    if(spinner!!.equals("Male")){
                        var user_gender = "m"
                    }else{
                        var user_gender = "f"
                    }

                    val intent = Intent(this,SingUpSummaryClientActivity::class.java)
                    // start your next activity
                    startActivity(intent)


                }else{
                    //todo popup error that terms and conditions have not been agreed
                }
            }else{
                //todo popup error for incomplete data
            }



        }


        spinner = this.Spinner_signupclientpart2Gender

        // Create an ArrayAdapter using a simple spinner layout and languages array
        val aa = ArrayAdapter(this, android.R.layout.simple_spinner_item, gender)
        // Set layout to use when the list of choices appear
        aa.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        // Set Adapter to Spinner
        spinner!!.adapter = aa
    }

    private fun isCompleteData(): Boolean {
        return !(textInputEditText_signupClientPart2FirstName.text.isNullOrEmpty() && textInputEditText_signupClientPart2LastName.text.isNullOrEmpty()
                && textInputEditText_signupClientPart2Address.text.isNullOrEmpty() && textInputEditText_signupClientPart2ContactNumber.text.isNullOrEmpty()
                && textInputEditText_signupClientPart2DateOfBirth.text.isNullOrEmpty())
    }
}
