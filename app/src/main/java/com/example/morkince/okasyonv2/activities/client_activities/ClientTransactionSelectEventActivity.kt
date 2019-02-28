package com.example.morkince.okasyonv2.activities.client_activities

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.morkince.okasyonv2.Custom_Progress_Dialog
import com.example.morkince.okasyonv2.PopUpDialogs
import com.example.morkince.okasyonv2.R
import com.example.morkince.okasyonv2.RandomMessages
import com.example.morkince.okasyonv2.activities.view_holders.BasicEventViewHolder
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.ViewHolder
import kotlinx.android.synthetic.main.activity_client_transaction_select_event.*

class ClientTransactionSelectEventActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_client_transaction_select_event)

        val currentUser = FirebaseAuth.getInstance().currentUser!!
        title = currentUser.displayName

        val popupDialog = PopUpDialogs(this)
        val dialog = Custom_Progress_Dialog(this)
        val randomMessages = RandomMessages()

        dialog.showDialog("LOADING", randomMessages.getRandomMessage())

        val adapter = GroupAdapter<ViewHolder>()

        FirebaseFirestore.getInstance()
            .collection("Transaction_Client")
            .document(currentUser.uid)
            .collection("events")
            .addSnapshotListener { querySnapshot, firebaseFirestoreException ->
                if (firebaseFirestoreException != null){
                    dialog.dissmissDialog()

                    popupDialog.errorDialog("Something went wrong", "ERROR")

                    return@addSnapshotListener
                }

                if (querySnapshot!=null && !querySnapshot.isEmpty){
                    adapter.clear()
                    //populate recyler
                    for(document in querySnapshot){
                        val eventUid = document.id
                        adapter.add(BasicEventViewHolder(eventUid))
                    }

                    recylerView_clientTransactionSelectEventRecylerView.adapter = adapter
                    recylerView_clientTransactionSelectEventRecylerView.layoutManager = LinearLayoutManager(this)
                    dialog.dissmissDialog()
                }else{
                    dialog.dissmissDialog()
                    popupDialog.infoDialog( "You don't have any transaction records", "INFO")
                }
            }

        //on click listener for each row
        adapter.setOnItemClickListener { item, view ->
            val eventItem = item as BasicEventViewHolder
            val intent = Intent(view.context, ClientViewTransactionItem::class.java)
            intent.putExtra("eventUid", eventItem.eventUid)
            startActivity(intent)
        }
    }
}
