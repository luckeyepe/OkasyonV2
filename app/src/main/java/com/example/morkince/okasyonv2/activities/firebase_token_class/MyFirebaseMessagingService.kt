package com.example.morkince.okasyonv2.activities.firebase_token_class

import android.util.Log
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage

class MyFirebaseMessagingService: FirebaseMessagingService() {
    override fun onNewToken(token: String?) {
//        super.onNewToken(p0)
        Log.d("Firebase Token", token)
        sendTokenToFirestore(token)
    }

    private fun sendTokenToFirestore(token: String?) {
        val currentUser = FirebaseAuth.getInstance().currentUser

        if (currentUser!=null) {
            val db = FirebaseFirestore.getInstance().collection("User").document(currentUser.uid)
            db.update("user_token", token)
        }
    }

    override fun onMessageReceived(message: RemoteMessage?) {
//        super.onMessageReceived(p0)
        if (message?.notification != null){
            Log.d("FCM", "Data: "+message.notification!!.body.toString())
        }
    }
}