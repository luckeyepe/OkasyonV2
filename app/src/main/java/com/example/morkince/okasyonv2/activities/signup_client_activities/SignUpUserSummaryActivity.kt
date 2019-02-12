package com.example.morkince.okasyonv2.activities.signup_client_activities

import android.app.DatePickerDialog
import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import android.util.Log
import android.widget.Toast
import com.example.morkince.okasyonv2.R
import com.example.morkince.okasyonv2.activities.client_activities.ClientHomePage
import com.google.android.gms.tasks.Continuation
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.UploadTask
import kotlinx.android.synthetic.main.activity_sign_up_user_summary.*
import java.net.URI
import java.util.*

class SignUpUserSummaryActivity : AppCompatActivity() {

    private var user_email: String ?= null
    private var user_password: String ?= null
    private var user_role: String ?= null
    private var user_first_name: String ?= null
    private var user_last_name: String ?= null
    private var user_address: String ?= null
    private var user_contact_no: String ?= null
    private var user_birth_date: String ?= null
    private var user_gender: String ?= null
    private var user_validIDURL: String ?= null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up_user_summary)

        user_email = intent.getStringExtra("user_email")
        user_password = intent.getStringExtra("user_password")
        user_role = intent.getStringExtra("user_role")
        user_first_name = intent.getStringExtra("user_first_name")
        user_last_name = intent.getStringExtra("user_last_name")
        user_address = intent.getStringExtra("user_address")
        user_contact_no = intent.getStringExtra("user_contact_no")
        user_birth_date = intent.getStringExtra("user_birth_date")
        user_gender = intent.getStringExtra("user_gender")
        user_validIDURL = intent.getStringExtra("user_validIDURL")

        //fill up the data fields
        fillEditTexts()

        //create user after loading summary
        createUser()

        editText_summaryDateOfBirth.setOnClickListener{
            popupDateTimePicker()
        }

        //finish signup
        button_summaryClientCreateAccount.setOnClickListener {
            //update
            var mAuth = FirebaseAuth.getInstance()
            var currentUser = mAuth.currentUser
            var userMap = HashMap<String, String>()
            userMap["user_address"] = editText_summaryAddress.text.toString().trim()
            userMap["user_birth_date"] = editText_summaryDateOfBirth.text.toString().trim()
            userMap["user_contact_no"] = editText_summaryContactNumber.text.toString().trim()
            userMap["user_email"] = currentUser!!.email.toString()
            userMap["user_first_name"] = editText_summaryFirstName.text.toString().trim()
            userMap["user_gender"] = editText_summaryGender.text.toString().trim()
            userMap["user_last_name"] = editText_summaryLastName.text.toString().trim()
            userMap["user_validIDURL"] = user_validIDURL.toString()

            var db = FirebaseFirestore.getInstance().collection("User").document(currentUser!!.uid)
                .update(userMap as Map<String, Any>).addOnCompleteListener {
                        task: Task<Void> ->
                    if (task.isSuccessful){
                        //upload image
                        val database = FirebaseFirestore.getInstance().collection("User").document(currentUser.uid)
                        val mStorage = FirebaseStorage.getInstance().reference

                        val thumbnailPath = mStorage!!.child("user_profPic").child("${currentUser.uid}.jpg")

                        val uri = Uri.parse(user_validIDURL!!)

                        val uploadTask = thumbnailPath.putFile(uri)

                        var urlTask = uploadTask.continueWithTask(Continuation<UploadTask.TaskSnapshot, Task<Uri>> { task ->
                            if (!task.isSuccessful) {
                                task.exception?.let {
                                    throw it
                                }
                            }
                            return@Continuation thumbnailPath.downloadUrl
                        }).addOnCompleteListener {
                                task ->
                            if(task.isSuccessful){
                                val downloadUri = task.result
                                var userAddedDetails = HashMap<String, String>()
                                userAddedDetails["user_profilePictureURL"] = downloadUri.toString()

                                database.update(userAddedDetails as Map<String, Any>).addOnCompleteListener {
                                        task: Task<Void> ->
                                    if (task.isSuccessful) {
                                        var alertDialog = AlertDialog.Builder(this)
                                        alertDialog.setMessage("Welcome to Okasyon, ${userMap[user_first_name!!]}")
                                        alertDialog.setTitle("WELCOME")
                                        alertDialog.show()

                                        startActivity(Intent(this, ClientHomePage::class.java))
                                    }
                                    else{
                                        //loading end
                                        Toast.makeText(applicationContext, "Upload error", Toast.LENGTH_LONG).show()
                                    }
                                }
                            }else{
                                //loading end
                                Toast.makeText(applicationContext, "Upload error", Toast.LENGTH_LONG).show()
                            }
                        }

                    }else{
                        var alertDialog = AlertDialog.Builder(this)
                        alertDialog.setMessage("Please check your internet connection and try again")
                        alertDialog.setTitle("ERROR")
                        alertDialog.show()
                    }
                }
        }
    }

    private fun createUser() {
        var mAuth = FirebaseAuth.getInstance()
        mAuth.createUserWithEmailAndPassword(this!!.user_email!!, this!!.user_password!!)
            .addOnCompleteListener { task: Task<AuthResult> ->
                run {
                    if (task.isSuccessful) {
                        var currentUser = mAuth.currentUser
                        var userMap = HashMap<String, String>()
                        userMap["user_address"] = user_address!!
                        userMap["user_birth_date"] = user_birth_date!!
                        userMap["user_contact_no"] = user_contact_no!!
                        userMap["user_email"] = currentUser!!.email.toString()
                        userMap["user_first_name"] = user_first_name!!
                        userMap["user_gender"] = user_gender!!
                        userMap["user_last_name"] = user_last_name!!
                        userMap["user_role"] = user_role!!
                        userMap["user_uid"] = currentUser.uid

                        var db = FirebaseFirestore.getInstance().collection("User").document(currentUser!!.uid)
                            .set(userMap as Map<String, Any>).addOnCompleteListener { task: Task<Void> ->
                                if (task.isSuccessful) {
                                    if (task.isSuccessful){
                                        Log.d("SIGN UP USER PT 2", "User ${currentUser.uid} created")
                                    }
                                }

                            }
                    } else {
                        Log.e("SIGN UP USER PT 2", task.exception.toString())
                    }
                }
            }
    }

    private fun fillEditTexts() {
        editText_summaryEmailAddress.setText(user_email)
        editText_summaryFirstName.setText(user_first_name)
        editText_summaryLastName.setText(user_last_name)
        editText_summaryAddress.setText(user_address)
        editText_summaryContactNumber.setText(user_contact_no)
        editText_summaryDateOfBirth.setText(user_birth_date)
        editText_summaryGender.setText(user_gender)
    }

    private fun popupDateTimePicker() {
        val c = Calendar.getInstance()
        val year = c.get(Calendar.YEAR)
        val month = c.get(Calendar.MONTH)
        val day = c.get(Calendar.DAY_OF_MONTH)

        val dpd = DatePickerDialog(this, DatePickerDialog.OnDateSetListener { view, year, monthOfYear, dayOfMonth ->
            // Display Selected date in textbox
            var date = "$monthOfYear/$dayOfMonth/$year"
            editText_summaryDateOfBirth.setText(date)
        }, year, month, day)

        dpd.show()
    }
}
