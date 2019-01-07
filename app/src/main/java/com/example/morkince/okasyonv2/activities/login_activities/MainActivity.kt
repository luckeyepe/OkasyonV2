package com.example.morkince.okasyonv2.activities.login_activities

import android.app.Dialog
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.Window
import android.widget.Button
import android.widget.Toast
import com.example.morkince.okasyonv2.R
import com.example.morkince.okasyonv2.activities.PlaceHolderActivity
import com.example.morkince.okasyonv2.activities.signup_client_activities.SignUpUserPart1Activity
import com.example.morkince.okasyonv2.activities.signup_supplier_activities.SignUpSupplierPart1Activity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.example.morkince.okasyonv2.activities.homepages_for_supplier_client.ClientHomePage
import com.example.morkince.okasyonv2.activities.homepages_for_supplier_client.SupplierHomePage
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.activity_sign_up_client_part1.*
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
        mAuthListener = FirebaseAuth.AuthStateListener {
                firebaseAuth: FirebaseAuth ->
            user = firebaseAuth.currentUser
            if (user!= null){


                startActivity(Intent(this, PlaceHolderActivity::class.java))
                finish()
            }
        }

        button_mainLogIn.setOnClickListener {
            Log.d(TAG, "Sign In Button Pressed")
            var username = textInputEditText_mainUsername.text.toString().trim()
            var password = textInputEditText_mainPassword.text.toString().trim()

            Toast.makeText(this, "Username: $username and Password $password", Toast.LENGTH_SHORT).show()


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

    public override fun onStart() {
        super.onStart()
        mAuth!!.addAuthStateListener(mAuthListener!!)
    }

    override fun onStop() {
        super.onStop()
        if (mAuthListener != null){
            mAuth!!.removeAuthStateListener(mAuthListener!!)
        }
    }
}
