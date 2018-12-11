package com.example.morkince.okasyonv2.activities.login_activities

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.morkince.okasyonv2.R
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        button_mainLogIn.setOnClickListener {
            var username = textInputEditText_mainUsername.text.toString().trim()
            var password = textInputEditText_mainPassword.text.toString().trim()

            Toast.makeText(this, "Username: $username and Password $password", Toast.LENGTH_SHORT).show()
        }
    }
}
