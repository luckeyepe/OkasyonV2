package com.example.morkince.okasyonv2.activities.homepage_supplier_activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.morkince.okasyonv2.Custom_Progress_Dialog
import com.example.morkince.okasyonv2.PopUpDialogs
import com.example.morkince.okasyonv2.R
import com.example.morkince.okasyonv2.RandomMessages
import com.example.morkince.okasyonv2.activities.adapter.TransactionRecyclerAdapter
import com.example.morkince.okasyonv2.activities.model.Transaction_Supplier
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.activity_transaction__supplier_.*

class Transaction_Supplier_Activity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_transaction__supplier_)
        val actionbar = supportActionBar
        actionbar!!.title = "TRANSACTION"
        actionbar.setDisplayHomeAsUpEnabled(true)

        val popUpDialogs = PopUpDialogs(this)
        val custom_Progress_Dialog = Custom_Progress_Dialog(this)
        val randomMessages = RandomMessages()

        custom_Progress_Dialog.showDialog("Loading", randomMessages.getRandomMessage())

        //current user
        val currentUser = FirebaseAuth.getInstance().currentUser!!
        //
        FirebaseFirestore.getInstance()
            .collection("Transaction_Supplier")
            .document(currentUser.uid)
            .collection("transaction_supplier")
            .addSnapshotListener { querySnapshot, firebaseFirestoreException ->
                if (firebaseFirestoreException!=null){
                    custom_Progress_Dialog.dissmissDialog()
                    popUpDialogs.errorDialog("Something went wrong","ERROR")
                    return@addSnapshotListener
                }

                if (querySnapshot!=null && !querySnapshot.isEmpty){
                    for (document in querySnapshot){
                        val result = document.toObject(Transaction_Supplier::class.java)

                    }
                }
            }


    }
    override fun  onSupportNavigateUp():Boolean{
        onBackPressed()
        return true
    }

}
