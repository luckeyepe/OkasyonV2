package com.example.morkince.okasyonv2.activities.signup_client_activities

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.app.AlertDialog
import android.util.Log
import com.example.morkince.okasyonv2.R
import com.example.morkince.okasyonv2.activities.login_activities.MainActivity
import com.google.android.gms.tasks.Task
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.QuerySnapshot
import kotlinx.android.synthetic.main.activity_sign_up_user_part1.*

class SignUpUserPart1Activity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up_user_part1)

        //grab some stuff from previous activity
        var user_role = intent.getStringExtra("user_role")

        imagebutton_signupClientPart1Next.setOnClickListener {

            if(isCompleteData()) {
                if (isProperPassword()) {
                    if (isMatchingPassword()) {
                        var email = textInputEdittext_signupClientpart1Emailaddress.text.toString().trim()
                        var password =
                            textInputEdittext_signupClienpart1Password.text.toString().trim()
                        var mFirestore = FirebaseFirestore.getInstance()
                        var query = mFirestore.collection("User").whereEqualTo("user_email", email)

                        query.get().addOnCompleteListener {
                            task: Task<QuerySnapshot> ->
                            if (task.isSuccessful){
                                var result = task.result
                                if(result!!.isEmpty){
                                    val intent = Intent(this, SignUpUserPart2Activity::class.java)
                                    intent.putExtra("user_email", email)
                                    intent.putExtra("user_password", password)
                                    intent.putExtra("user_role", user_role)
                                    // start your next activity
                                    startActivity(intent)
                                }else{
                                    Log.d("Emails", result.toString())
                                    //todo popup error email taken
                                    var alertDialog = AlertDialog.Builder(this)
                                    alertDialog.setMessage("Your email is already taken")
                                    alertDialog.setTitle("EMAIL TAKEN")
                                    alertDialog.show()
                                }

                            }else{
                                //todo firebase auth error
                            }
                        }
                    } else {
                        //todo popup error that passwords do not match
                        var alertDialog = AlertDialog.Builder(this)
                        alertDialog.setMessage("Make sure both passwords match")
                        alertDialog.setTitle("PASSWORD MISMATCH")
                        alertDialog.show()
                    }
                } else {
                    //todo popup error that password is not in the right format
                    var alertDialog = AlertDialog.Builder(this)
                    alertDialog.setMessage("Password length is less than 6 characters")
                    alertDialog.setTitle("PASSWORD TOO SHORT")
                    alertDialog.show()
                }
            }else{
                //todo popup error that datafields are not full
                var alertDialog = AlertDialog.Builder(this)
                alertDialog.setMessage("Please enter all user details")
                alertDialog.setTitle("INFO MISSING")
                alertDialog.show()
            }
        }
    }

    private fun isProperPassword(): Boolean {
        var password = textInputEdittext_signupClienpart1Password.text.toString().trim()
        return password.length >= 6
    }

    private fun isMatchingPassword(): Boolean {
        return textInputEdittext_signupClienpart1Password.text.toString().trim() ==
                textInputEdittext_signupClientpart1ConfirmPassword.text.toString().trim()
    }

    private fun isCompleteData(): Boolean {
        return !(textInputEdittext_signupClientpart1Emailaddress.text.isNullOrEmpty() &&
                textInputEdittext_signupClienpart1Password.text.isNullOrEmpty() &&
                textInputEdittext_signupClientpart1ConfirmPassword.text.isNullOrEmpty())
    }

    override fun onBackPressed() {
//        super.onBackPressed()
        var alertDialog = AlertDialog.Builder(this)
        alertDialog.setMessage("Do you want to stop signing up?")
        alertDialog.setTitle("CANCEL SIGN UP")

        alertDialog.setPositiveButton("YES") { dialog, which ->
            finish()
            startActivity(Intent(this, MainActivity::class.java))
        }

        alertDialog.setNegativeButton("NO") { dialog, which ->
            dialog.dismiss()
        }

        alertDialog.show()
    }
}
