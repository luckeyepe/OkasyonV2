package com.example.morkince.okasyonv2.activities.signup_client_activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import android.util.Log
import com.example.morkince.okasyonv2.R
import com.example.morkince.okasyonv2.activities.login_activities.MainActivity
import com.example.morkince.okasyonv2.activities.signup_supplier_activities.SignUpSupplierPart2Activity
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

        textView_signupUserPart1UserType.text = "$user_role".toUpperCase()

        imagebutton_signupClientPart1Next.setOnClickListener {

            if(isCompleteData()) {
                if (isValidPassword()) {
                    var email = textInputEdittext_signupClientpart1EmailAddress.text.toString().trim()
                    var password =
                        textInputEdittext_signupClientPart1Password.text.toString().trim()
                    var mFirestore = FirebaseFirestore.getInstance()
                    var query = mFirestore.collection("User").whereEqualTo("user_email", email)

                    query.get().addOnCompleteListener {
                        task: Task<QuerySnapshot> ->
                        if (task.isSuccessful){
                            val intent: Intent?
                            var result = task.result
                            if(result!!.isEmpty){
                                if (user_role != "supplier") {
                                    intent = Intent(this, SignUpUserPart2Activity::class.java)
                                }else{
                                    intent = Intent(this, SignUpSupplierPart2Activity::class.java)
                                }

                                intent.putExtra("user_email", email)
                                intent.putExtra("user_password", password)
                                intent.putExtra("user_role", user_role)
                                // start your next activity
                                startActivity(intent)

                                //add animation
                                overridePendingTransition(R.anim.slide_from_right, R.anim.slide_to_left)
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

    private fun isMatchingPassword(): Boolean {
        return textInputEdittext_signupClientPart1Password.text.toString().trim() ==
                textInputEdittext_signupClientpart1ConfirmPassword.text.toString().trim()
    }

    private fun isCompleteData(): Boolean {
        return !(textInputEdittext_signupClientpart1EmailAddress.text.isNullOrEmpty() &&
                textInputEdittext_signupClientPart1Password.text.isNullOrEmpty() &&
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

    private fun isValidPassword(): Boolean {
        var password = textInputEdittext_signupClientPart1Password.text.toString().trim()
        var password2 = textInputEdittext_signupClientpart1ConfirmPassword.text.toString().trim()

        if(isStringContainNumber(password)){
            if(isStringContainUpperCase(password)){
                if(isStringContainLowerCase(password)){
                    if(isStringContainSpecialCharacter(password)){
                        if(password.length >=8){
                            if (password == password2){
                                return true
                            }else{
                                Log.e("ERROR", "Incomplete data")
                                var alertDialog = AlertDialog.Builder(this)
                                alertDialog.setMessage("Please make sure you have matching passwords")
                                alertDialog.setTitle("PASSWORD MISMATCH")
                                alertDialog.show()
                            }
                        }
                    }
                }
            }
        }
        return false
    }

    private fun isStringContainSpecialCharacter(string: String): Boolean {
        for(c in string.toCharArray()){
            if (!c.isLetterOrDigit())
                return true
        }

        var alertDialog = AlertDialog.Builder(this)
        alertDialog.setMessage("Password must contain at least one (1) special character")
        alertDialog.setTitle("INCORRECT PASSWORD")
        alertDialog.show()

        return false
    }

    private fun isStringContainLowerCase(string: String): Boolean {
        for(c in string.toCharArray()){
            if (c.isLowerCase())
                return true
        }

        var alertDialog = AlertDialog.Builder(this)
        alertDialog.setMessage("Password must contain at least one (1) lower case character")
        alertDialog.setTitle("INCORRECT PASSWORD")
        alertDialog.show()
        return false
    }

    private fun isStringContainUpperCase(string: String): Boolean {
        for(c in string.toCharArray()){
            if (c.isUpperCase())
                return true
        }
        var alertDialog = AlertDialog.Builder(this)
        alertDialog.setMessage("Password must contain at least one (1) upper case character")
        alertDialog.setTitle("INCORRECT PASSWORD")
        alertDialog.show()

        return false
    }

    private fun isStringContainNumber(string: String): Boolean {

        for(c in string.toCharArray()){
            if (c.isDigit())
                return true
        }

        var alertDialog = AlertDialog.Builder(this)
        alertDialog.setMessage("Password must contain at least one (1) digit")
        alertDialog.setTitle("INCORRECT PASSWORD")
        alertDialog.show()

        return false
    }
}
