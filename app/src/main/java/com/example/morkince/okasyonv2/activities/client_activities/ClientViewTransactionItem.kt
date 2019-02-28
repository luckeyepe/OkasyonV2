package com.example.morkince.okasyonv2.activities.client_activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.morkince.okasyonv2.Custom_Progress_Dialog
import com.example.morkince.okasyonv2.PopUpDialogs
import com.example.morkince.okasyonv2.R
import com.example.morkince.okasyonv2.RandomMessages
import com.example.morkince.okasyonv2.activities.Homepage_organizer_activities.CartItemDetailsActivity
import com.example.morkince.okasyonv2.activities.common_activities.ViewTransactionDetailsActivity
import com.example.morkince.okasyonv2.activities.model.Transaction_Client
import com.example.morkince.okasyonv2.activities.view_holders.TransactionItemViewHolder
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.ViewHolder
import kotlinx.android.synthetic.main.activity_transaction__client_view.*

class ClientViewTransactionItem : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_transaction__client_view)

        val currentUser = FirebaseAuth.getInstance().currentUser!!
        var eventUid=""

        if (intent.hasExtra("eventUid")){
            eventUid = intent.getStringExtra("eventUid")
        }else{
            finish()
        }

        val popupDialog = PopUpDialogs(this)
        val dialog = Custom_Progress_Dialog(this)
        val randomMessages = RandomMessages()

        dialog.showDialog("LOADING", randomMessages.getRandomMessage())

        val adapter = GroupAdapter<ViewHolder>()

        FirebaseFirestore.getInstance()
            .collection("Transaction_Client")
            .document(currentUser.uid)
            .collection("events")
            .document(eventUid)
            .collection("transaction_client")
            .addSnapshotListener { querySnapshot, firebaseFirestoreException ->
                if (firebaseFirestoreException != null){
                    dialog.dissmissDialog()
                    popupDialog.errorDialog("Something went wrong", "ERROR")
                    return@addSnapshotListener
                }

                if(querySnapshot != null && !querySnapshot.isEmpty){
                    adapter.clear()
                    //populate recycler
                    for (document in querySnapshot) {
                        var result = document.toObject(Transaction_Client::class.java)
                        adapter.add(TransactionItemViewHolder(result, this))
                    }

                    recyclerView_transactionViewActivtyRecyler.adapter = adapter
                    recyclerView_transactionViewActivtyRecyler.layoutManager = LinearLayoutManager(this)
                    dialog.dissmissDialog()
                }else{
                    dialog.dissmissDialog()
                    popupDialog.infoDialog("Your transaction list in empty", "INFO")
                }
            }

        //on click listener for each row
        adapter.setOnItemClickListener { item, view ->
            if (view.id != R.id.imageButton_transactionClientRowMessage) {
                val transactionItem = item as TransactionItemViewHolder
                val intent = Intent(view.context, ViewTransactionDetailsActivity::class.java)
                intent.putExtra("transactionItem",transactionItem.transactionItem)
                startActivity(intent)
            }
        }

    }
}
