package com.example.morkince.okasyonv2.activities.client_activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.morkince.okasyonv2.Custom_Progress_Dialog
import com.example.morkince.okasyonv2.PopUpDialogs
import com.example.morkince.okasyonv2.R
import com.example.morkince.okasyonv2.RandomMessages
import com.google.firebase.firestore.FirebaseFirestore
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.ViewHolder

class ClientTransactionSelectEventActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_client_transaction_select_event)

        val popupDialog = PopUpDialogs(this)
        val custom_Progress_Dialog = Custom_Progress_Dialog(this)
        val randomMessages = RandomMessages()

        custom_Progress_Dialog.showDialog("LOADING", randomMessages.getRandomMessage())

        var adapter = GroupAdapter<ViewHolder>()

        FirebaseFirestore.getInstance()
            .collection("Transaction_Client")
            .addSnapshotListener { querySnapshot, firebaseFirestoreException ->
                if (firebaseFirestoreException != null){
                    custom_Progress_Dialog.dissmissDialog()

                    popupDialog.errorDialog("Something went wrong", "ERROR")

                    return@addSnapshotListener
                }

                if (querySnapshot!!.size()>0){
                    //populate recyler
                }else{

                }
            }
    }
}
