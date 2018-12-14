package com.example.morkince.okasyonv2.activities.signup_client_activities

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.example.morkince.okasyonv2.R
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.activity_sign_up_user_summary.*

class SignUpUserSummaryActivity : AppCompatActivity() {

    private var user_email = intent.getStringExtra("user_email")
    private var user_password = intent.getStringExtra("user_password")
    private var user_role = intent.getStringExtra("user_role")
    private val user_first_name = intent.getStringExtra("user_first_name")
    private val user_last_name = intent.getStringExtra("user_last_name")
    private val user_address = intent.getStringExtra("user_address")
    private val user_contact_no = intent.getStringExtra("user_contact_no")
    private val user_birth_date = intent.getStringExtra("user_birth_date")
    private val user_gender = intent.getStringExtra("user_gender")


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up_user_summary)

        //fill up the data fields
        fillEditTexts()

        //finish signup
        button_summaryClientCreateAccount.setOnClickListener {
            var mAuth = FirebaseAuth.getInstance()
            mAuth.createUserWithEmailAndPassword(user_email, user_password)
                .addOnCompleteListener {
                task: Task<AuthResult> ->
                    run {
                        if (task.isSuccessful) {
                            var currentUser = mAuth.currentUser
                            var userMap = HashMap<String, String>()
                            userMap["user_address"] = user_address
                            userMap["user_birth_date"] = user_birth_date
                            userMap["user_contact_no"] = user_contact_no
                            userMap["user_email"] = currentUser!!.email.toString()
                            userMap["user_first_name"] = user_first_name
                            userMap["user_gender"] = user_gender
                            userMap["user_last_name"] = user_last_name
                            userMap["user_role"] = user_role
                            var db = FirebaseFirestore.getInstance().collection("User").document(currentUser!!.uid)
                                .set(userMap as Map<String, Any>).addOnCompleteListener {
                                    task: Task<Void> ->
                                    if (task.isSuccessful){

                                    }
                                }
                        } else {
                            //todo trap
                        }
                    }
            }
        }
    }

    private fun fillEditTexts() {
        editText_summaryEmailAddress.setText(user_email)
        editText_summaryName.setText("$user_first_name $user_last_name")
        editText_summaryAddress.setText(user_address)
        editText_summaryContactNumber.setText(user_contact_no)
        editText_summaryDateOfBirth.setText(user_birth_date)
        editText_summaryGender.setText(user_gender)
    }
}
