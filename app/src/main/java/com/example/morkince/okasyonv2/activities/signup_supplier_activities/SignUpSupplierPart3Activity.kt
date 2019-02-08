package com.example.morkince.okasyonv2.activities.signup_supplier_activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.example.morkince.okasyonv2.R
import com.example.morkince.okasyonv2.activities.homepage_supplier_activities.SupplierHomePage
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.activity_sign_up_supplier_part3.*

class SignUpSupplierPart3Activity : AppCompatActivity() {

    private var user_email: String ?= null
    private var user_password: String ?= null
    private var user_role: String ?= null
    private var user_first_name: String ?= null
    private var user_last_name: String ?= null
    private var user_address: String ?= null
    private var user_contact_no: String ?= null
    private var store_store_name: String ?= null
    private var store_description: String ?= null
    private val TAG = "SignUpSupplierPart3"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up_supplier_part3)
        supportActionBar!!.hide()
        textInputEditText_SignUpSupplierPart3StoreName.isEnabled = false
        textInputEditText_SignUpSupplierPart3FirstName.isEnabled =false
        textInputEditText_SignUpSupplierPart3LastName.isEnabled =false
        textInputEditText_SignUpSupplierPart3Address.isEnabled = false
        textInputEditText_SignUpSupplierPart3ContactNumber.isEnabled =false
        textInputEditText_SignUpSupplierPart3About.isEnabled =false


        //grab some stuff from previous activity
        user_email = intent.getStringExtra("user_email")
        user_password = intent.getStringExtra("user_password")
        user_role = intent.getStringExtra("user_role")
        user_first_name = intent.getStringExtra("user_first_name")
        user_last_name = intent.getStringExtra("user_last_name")
        user_address = intent.getStringExtra("user_address")
        user_contact_no = intent.getStringExtra("user_contact_no")
        store_store_name = intent.getStringExtra("store_store_name")
        store_description = intent.getStringExtra("store_description")

        createUser()

        fillInData()

        imageButton_SignUpSupplierPart3EditSummary.setOnClickListener()
        {
            textInputEditText_SignUpSupplierPart3StoreName.isEnabled = true
            textInputEditText_SignUpSupplierPart3FirstName.isEnabled =true
            textInputEditText_SignUpSupplierPart3LastName.isEnabled =true
            textInputEditText_SignUpSupplierPart3Address.isEnabled = true
            textInputEditText_SignUpSupplierPart3ContactNumber.isEnabled =true
            textInputEditText_SignUpSupplierPart3About.isEnabled =true
        }
        imageButton_SignUpSupplierPart3Before.setOnClickListener()
        {
            onBackPressed()
            overridePendingTransition(R.anim.slide_from_left,R.anim.slide_to_right)

        }

        imageButton_SignUpSupplierPart3Next.setOnClickListener()
        {
            val intent = Intent(this, SupplierHomePage::class.java)
            startActivity(intent)
            overridePendingTransition(R.anim.slide_from_right, R.anim.slide_to_left)
        }
    }

    private fun fillInData() {
        textInputEditText_SignUpSupplierPart3StoreName.setText(store_store_name)
        textInputEditText_SignUpSupplierPart3FirstName.setText(user_first_name)
        textInputEditText_SignUpSupplierPart3LastName.setText(user_last_name)
        textInputEditText_SignUpSupplierPart3Address.setText(user_address)
        textInputEditText_SignUpSupplierPart3About.setText(store_description)
        textInputEditText_SignUpSupplierPart3ContactNumber.setText(user_contact_no)
    }

    private fun createUser()
    {
        var mAuth = FirebaseAuth.getInstance()
        var db = FirebaseFirestore.getInstance()

        Log.d(TAG, "User Email $user_email, and Password $user_password")

        mAuth.createUserWithEmailAndPassword(user_email!!, user_password!!)
            .addOnCompleteListener {
                task: Task<AuthResult> ->
                run {
                    if (task.isSuccessful) {
                        var currentUser = mAuth.currentUser
                        var userHashMap = HashMap<String, String>()
                        userHashMap["user_email"] = user_email!!
                        userHashMap["user_role"] = user_role!!
                        userHashMap["user_first_name"] = user_first_name!!
                        userHashMap["user_last_name"] = user_last_name!!
                        userHashMap["user_address"] = user_address!!
                        userHashMap["user_contact_no"] = user_contact_no!!
                        userHashMap["user_uid"] = currentUser!!.uid

                        var storeMap= HashMap<String,String>()
                        storeMap["store_store_name"] =store_store_name!!
                        storeMap["store_description"] =store_description!!
                        storeMap["store_owner_id"] = currentUser!!.uid
                        storeMap["store_location"] = user_address.toString()

                        Log.d(TAG, "Success SignUp ${mAuth.currentUser!!.uid}")
                        db.collection("User").document("${currentUser.uid}")
                            .set(userHashMap as Map<String, Any>).addOnCompleteListener {
                                task: Task<Void> ->
                                run {
                                    if (task.isSuccessful) {
                                        if (task.isSuccessful){
                                            db.collection("Store").add(storeMap as Map<String, Any>)
                                                .addOnCompleteListener {
                                                        task: Task<DocumentReference> ->
                                                    run {

                                                    }
                                                }
                                        }
                                    }
                                }
                            }
                    } else {
                        Log.e(TAG, task.exception.toString())
                    }
                }
            }
    }

    override fun finish() {
        super.finish()
        overridePendingTransition(R.anim.slide_from_left,R.anim.slide_to_right)

    }
}
