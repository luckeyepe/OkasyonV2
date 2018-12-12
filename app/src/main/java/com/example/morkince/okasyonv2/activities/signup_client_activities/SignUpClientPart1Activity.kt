package com.example.morkince.okasyonv2.activities.signup_client_activities

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.example.morkince.okasyonv2.R
import kotlinx.android.synthetic.main.activity_sign_up_client_part1.*

class SignUpClientPart1Activity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up_client_part1)
        imagebutton_signupClientPart1Next.setOnClickListener {
            val intent = Intent(this, SignUpClientPart2Activity::class.java)
            // start your next activity
            startActivity(intent)
        }
    }
}
