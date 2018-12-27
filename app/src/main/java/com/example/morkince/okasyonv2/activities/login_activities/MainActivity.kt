package com.example.morkince.okasyonv2.activities.login_activities

import android.app.Dialog
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.Window
import android.widget.Toast
import com.example.morkince.okasyonv2.R
import com.example.morkince.okasyonv2.activities.signup_organizer_activities.SignUpOrganizerPart1Activity
import com.example.morkince.okasyonv2.activities.signup_supplier_activities.SignUpSupplierPart1Activity
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.modal_user_type_selection.view.*

class MainActivity : AppCompatActivity() {

    val TAG = "MainActivity"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        supportActionBar!!.hide()
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
            var buttonOrganizer = view.button_modalUserTypeSelectionOrganizer
            var buttonSupplier = view.button_modalUserTypeSelectionSupplier
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
                startActivity(Intent(this, SignUpSupplierPart1Activity::class.java))
            }

            buttonOrganizer.setOnClickListener {
                Log.d(TAG, "Sign Up Organizer Button Pressed")
                startActivity(Intent(this, SignUpOrganizerPart1Activity::class.java))
            }

            buttonSupplier.setOnClickListener {
                Log.d(TAG, "Sign Up Supplier Button Pressed")
                startActivity(Intent(this, SignUpSupplierPart1Activity::class.java))
            }

            buttonClose.setOnClickListener {
                Log.d(TAG, "Popup Close Button Pressed")
                dialog.dismiss()
            }

        }
    }
}
