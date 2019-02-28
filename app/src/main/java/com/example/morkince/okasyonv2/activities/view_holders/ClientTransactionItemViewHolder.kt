package com.example.morkince.okasyonv2.activities.view_holders

import android.content.Context
import android.content.Intent
import android.opengl.Visibility
import android.view.View
import android.widget.Toast
import com.example.morkince.okasyonv2.Custom_Progress_Dialog
import com.example.morkince.okasyonv2.R
import com.example.morkince.okasyonv2.RandomMessages
import com.example.morkince.okasyonv2.activities.chat_activities.ChatLogActivitiy
import com.example.morkince.okasyonv2.activities.model.Transaction_Client
import com.google.android.gms.tasks.OnFailureListener
import com.google.android.gms.tasks.OnSuccessListener
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.FirebaseFirestore
import com.xwray.groupie.Item
import com.xwray.groupie.ViewHolder
import kotlinx.android.synthetic.main.row_transaction_client.view.*

class ClientTransactionItemViewHolder(val transactionItem: Transaction_Client, val context: Context):Item<ViewHolder>() {
    override fun getLayout(): Int {
        return R.layout.row_transaction_client
    }

    override fun bind(viewHolder: ViewHolder, position: Int) {
        var textView_transactionClientRowItemName = viewHolder.itemView.textView_transactionClientRowItemName
        var button_transactionClientRowReview = viewHolder.itemView.button_transactionClientRowReview
        var textView_transactionClientRowCost = viewHolder.itemView.textView_transactionClientRowCost
        var imageButton_transactionClientRowMessage = viewHolder.itemView.imageButton_transactionClientRowMessage
        var textView_transactionClientRowItemStatus = viewHolder.itemView.textView_transactionClientRowItemStatus

        textView_transactionClientRowItemName.text = transactionItem.transaction_client_item_name

        if (transactionItem.transaction_client_status != "closed") {
            button_transactionClientRowReview.visibility = View.GONE
        } else {
            button_transactionClientRowReview.visibility = View.VISIBLE
        }

        textView_transactionClientRowItemStatus.text = "Order Status: ${transactionItem.transaction_client_status!!.toUpperCase()}"

        textView_transactionClientRowCost.text = "₱${transactionItem.transaction_client_order_cost}"

        /////////////////////
        imageButton_transactionClientRowMessage.setOnClickListener {
            val currentUser = FirebaseAuth.getInstance().currentUser!!
            val currentUserUid = currentUser.uid
            val custom_progress_dialog = Custom_Progress_Dialog(context)
            val randomMessages = RandomMessages()
            custom_progress_dialog.showDialog("LOADING", randomMessages.getRandomMessage())


            FirebaseFirestore.getInstance()
                .collection("Items")
                .document(transactionItem.transaction_client_item_uid!!)
                .get().addOnCompleteListener { task: Task<DocumentSnapshot> ->
                    if (task.isSuccessful) {
                        val store_uid = task.result!!.getString("item_store_id")!!

                        FirebaseFirestore.getInstance()
                            .collection("Store")
                            .document(store_uid)
                            .get()
                            .addOnSuccessListener { documentSnapshot ->
                                val ownerUid = documentSnapshot.get("store_owner_id")!!.toString()

                                FirebaseFirestore.getInstance()
                                    .collection("User")
                                    .document(currentUserUid)
                                    .get()
                                    .addOnSuccessListener { documentSnapshot ->
                                        val sendingUser =
                                            documentSnapshot.toObject(com.example.morkince.okasyonv2.activities.model.User::class.java)

                                        FirebaseFirestore.getInstance()
                                            .collection("User")
                                            .document(ownerUid)
                                            .get()
                                            .addOnSuccessListener { documentSnapshot ->
                                                val receivingUser =
                                                    documentSnapshot.toObject(com.example.morkince.okasyonv2.activities.model.User::class.java)

                                                val intent = Intent(context, ChatLogActivitiy::class.java)
                                                intent.putExtra("sendingUser", sendingUser)
                                                intent.putExtra("receivingUser", receivingUser)
                                                context.startActivity(intent)

                                                custom_progress_dialog.dissmissDialog()
                                            }
                                            .addOnFailureListener { custom_progress_dialog.dissmissDialog() }
                                    }
                                    .addOnFailureListener { custom_progress_dialog.dissmissDialog() }
                            }
                            .addOnFailureListener {
                                custom_progress_dialog.dissmissDialog()
                                Toast.makeText(context, "Coudn't Reach the Dude", Toast.LENGTH_LONG)
                            }
                    } else {
                        custom_progress_dialog.dissmissDialog()
                    }
                }
        }
    }
}