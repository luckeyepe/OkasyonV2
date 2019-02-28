package com.example.morkince.okasyonv2.activities.login_activities

import android.app.AlertDialog
import android.app.Dialog
import android.app.ProgressDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.Window
import android.widget.Toast
import com.example.morkince.okasyonv2.R
//import com.example.morkince.okasyonv2.activities.signup_client_activities.SignUpClientPart1Activity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.example.morkince.okasyonv2.activities.client_activities.ClientHomePage
import com.example.morkince.okasyonv2.activities.homepage_supplier_activities.SupplierHomePage
import com.example.morkince.okasyonv2.activities.signup_client_activities.SignUpUserPart1Activity
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.UserProfileChangeRequest
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.activity_main.*
//import kotlinx.android.synthetic.main.activity_sign_up_client_part1.*
import kotlinx.android.synthetic.main.modal_user_type_selection.view.*

class MainActivity : AppCompatActivity() {

    val TAG = "MainActivity"
    var mAuth: FirebaseAuth?= null
    var mAuthListener: FirebaseAuth.AuthStateListener ?= null
    var user: FirebaseUser?= null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        mAuth = FirebaseAuth.getInstance()

        //check for logged in user
        val progress = ProgressDialog(this)
        progress.setTitle("PLEASE WAIT")
        progress.setMessage("Checking your credentials")

        progress.setCancelable(false)
        progress.show()

        val currentUser = FirebaseAuth.getInstance().currentUser

        if (currentUser != null){
            FirebaseFirestore
                .getInstance()
                .collection("User")
                .document(currentUser.uid)
                .get().addOnCompleteListener {
                        task: Task<DocumentSnapshot> ->
                    if (task.isSuccessful){
                        val userRole = task.result!!.getString("user_role")
                        val userFullname = task.result!!.getString("user_first_name") +" "+ task.result!!.getString("user_last_name")
                        Log.d(TAG,"USER ROLE $userRole")
                        when(userRole){
                            "client" ->{
                                val profileUpdates = UserProfileChangeRequest.Builder()
                                    .setDisplayName(userFullname)
                                    .build()

                                currentUser.updateProfile(profileUpdates).addOnCompleteListener { task1 ->
                                    if (task1.isSuccessful) {
                                        Log.d(TAG, "User profile updated.")
                                    }
                                }

                                startActivity(Intent(this, ClientHomePage::class.java))
                                finish()
                                progress.dismiss()
                            }

                            "organizer" ->{
                                val profileUpdates = UserProfileChangeRequest.Builder()
                                    .setDisplayName(userFullname)
                                    .build()

                                currentUser.updateProfile(profileUpdates).addOnCompleteListener { task1 ->
                                    if (task1.isSuccessful) {
                                        Log.d(TAG, "User profile updated.")
                                    }
                                }

                                startActivity(Intent(this, ClientHomePage::class.java))
                                finish()
                                progress.dismiss()
                            }

                            "supplier" ->{
                                val profileUpdates = UserProfileChangeRequest.Builder()
                                    .setDisplayName(userFullname)
                                    .build()

                                currentUser.updateProfile(profileUpdates).addOnCompleteListener { task1 ->
                                    if (task1.isSuccessful) {
                                        Log.d(TAG, "User profile updated.")
                                    }
                                }

                                startActivity(Intent(this, SupplierHomePage::class.java))
                                finish()
                                progress.dismiss()
                            }
                        }
                    }
                }
        }else{
            progress.dismiss()
        }

//        mAuthListener = FirebaseAuth.AuthStateListener {
//                firebaseAuth: FirebaseAuth ->
//            user = firebaseAuth.currentUser
//
//            val progress = ProgressDialog(this)
//            progress.setTitle("PLEASE WAIT")
//            progress.setMessage("Checking your credentials")
//
//            progress.setCancelable(false)
//            progress.show()
//
//            if (user!= null){
//
//                val currentUser = FirebaseAuth.getInstance().currentUser
//                FirebaseFirestore
//                    .getInstance()
//                    .collection("User")
//                    .document(currentUser!!.uid)
//                    .get().addOnCompleteListener {
//                            task: Task<DocumentSnapshot> ->
//                        if (task.isSuccessful){
//                            val userRole = task.result!!.getString("user_role")
//                            Log.d(TAG,"USER ROLE $userRole")
//                            when(userRole){
//                                "client" ->{
//                                    startActivity(Intent(this, ClientHomePage::class.java))
//                                    finish()
//                                    progress.dismiss()
//                                }
//
//                                "organizer" ->{
//                                    startActivity(Intent(this, ClientHomePage::class.java))
//                                    finish()
//                                    progress.dismiss()
//                                }
//
//                                "supplier" ->{
//                                    startActivity(Intent(this, SupplierHomePage::class.java))
//                                    finish()
//                                    progress.dismiss()
//                                }
//                            }
//                        }
//                    }
//            }else{
//                progress.dismiss()
//            }
//        }

//        progress.dismiss()

        button_mainLogIn.setOnClickListener {
            Log.d(TAG, "Sign In Button Pressed")

            var email = textInputEditText_mainUsername.text.toString().trim()
            var password = textInputEditText_mainPassword.text.toString().trim()

            if(email.isNullOrEmpty() && password.isNullOrEmpty()){
                errorDialog("Please fill out all the fields", "MISSING DATA")
            }else {
                val progress = ProgressDialog(this)
                progress.setTitle("Loading")
                progress.setMessage("Grabbing your data, please wait...")
                progress.setCancelable(false)
                progress.show()

                FirebaseAuth.getInstance().signInWithEmailAndPassword(email, password)
                    .addOnCompleteListener { task: Task<AuthResult> ->
                        if (task.isSuccessful) {
                            val currentUser = FirebaseAuth.getInstance().currentUser
                            FirebaseFirestore
                                .getInstance()
                                .collection("User")
                                .document(currentUser!!.uid)
                                .get().addOnCompleteListener { task: Task<DocumentSnapshot> ->
                                    if (task.isSuccessful) {
                                        val userRole = task.result!!.getString("user_role")
                                        Log.d(TAG, "USER ROLE $userRole")
                                        when (userRole) {
                                            "client" -> {
                                                progress.dismiss()
                                                startActivity(Intent(this, ClientHomePage::class.java))
                                                finish()
                                            }

                                            "organizer" -> {
                                                progress.dismiss()
                                                startActivity(Intent(this, ClientHomePage::class.java))
                                                finish()
                                            }

                                            "supplier" -> {
                                                progress.dismiss()
                                                startActivity(Intent(this, SupplierHomePage::class.java))
                                                finish()
                                            }
                                        }
                                    }
                                }
                        } else {
                            progress.dismiss()
                            errorDialog("Username and password not in the database", "INVALID CREDENTIALS")
                            Toast.makeText(this, "Invalid Credentials", Toast.LENGTH_LONG).show()
                        }
                    }
            }
        }

        textView_mainSignUp.setOnClickListener {
            Log.d(TAG, "Sign Up Button Pressed")
            //setup modal
            var dialog: Dialog?
            var view = LayoutInflater.from(this).inflate(R.layout.modal_user_type_selection, null)//inflate the modal

            //assign buttons from the modal
            var buttonClient = view.button_modalUserTypeSelectionClient
            var buttonOrganizer = view.button_modalCreateOwnEvent
            var buttonSupplier = view.button_modalCreateEventBusinessEvents
            var buttonClose = view.imageButton_modalUserTypeSelectionClose

            //instantiate the dialog
            dialog = Dialog(this)
            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
            dialog.setContentView(view)
            dialog.setCancelable(false)
            dialog.show()


            //add functionality to buttons
            buttonClient.setOnClickListener {
                Log.d(TAG, "Sign Up Client Button Pressed")
                var intent = Intent(this, SignUpUserPart1Activity::class.java)
                intent.putExtra("user_role", "client")
                startActivity(intent)
            }

            buttonOrganizer.setOnClickListener {
                Log.d(TAG, "Sign Up Organizer Button Pressed")
                var intent = Intent(this, SignUpUserPart1Activity::class.java)
                intent.putExtra("user_role", "organizer")
                startActivity(intent)
            }

            buttonSupplier.setOnClickListener {
                Log.d(TAG, "Sign Up Supplier Button Pressed")
                var intent = Intent(this, SignUpUserPart1Activity::class.java)
                intent.putExtra("user_role", "supplier")
                startActivity(intent)
            }

                buttonClose.setOnClickListener {
                    Log.d(TAG, "Popup Close Button Pressed")
                    dialog.dismiss()
                }

        }
    }

//    public override fun onStart() {
//        super.onStart()
//        mAuth!!.addAuthStateListener(mAuthListener!!)
//    }

//    override fun onStop() {
//        super.onStop()
//        if (mAuthListener != null){
//            mAuth!!.removeAuthStateListener(mAuthListener!!)
//        }
//    }

    private fun errorDialog(message: String, title: String) {
        var alertDialog = AlertDialog.Builder(this)
        alertDialog.setIcon(R.drawable.ic_error_outline_black_24dp)
        alertDialog.setMessage(message)
        alertDialog.setTitle(title)
        alertDialog.setCancelable(false)
        alertDialog.setPositiveButton("OK") { dialog, which ->
            dialog.dismiss()
        }
        alertDialog.show()
    }
}
