package com.example.morkince.okasyonv2.activities.view_holders

import android.content.Context
import android.content.Intent
import android.util.Log
import android.view.View
import android.widget.Toast
import com.example.morkince.okasyonv2.Custom_Progress_Dialog
import com.example.morkince.okasyonv2.PopUpDialogs
import com.example.morkince.okasyonv2.R
import com.example.morkince.okasyonv2.RandomMessages
import com.example.morkince.okasyonv2.activities.chat_activities.ChatLogActivitiy
import com.example.morkince.okasyonv2.activities.model.Transaction_Supplier
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.FirebaseFirestore
import com.xwray.groupie.Item
import com.xwray.groupie.ViewHolder
import kotlinx.android.synthetic.main.row_transaction_supplier.view.*

class SupplierTransactionItemViewHolder(val transactionItem: Transaction_Supplier, val context: Context):Item<ViewHolder>() {
    override fun getLayout(): Int {
        return R.layout.row_transaction_supplier
    }

    override fun bind(viewHolder: ViewHolder, position: Int) {
        var textView_transactionSupplierRowItemName = viewHolder.itemView.textView_transactionSupplierRowItemName
        var button_transactionSupplierRowReview = viewHolder.itemView.button_transactionSupplierRowUpdateStatus
        var textView_transactionSupplierRowCost = viewHolder.itemView.textView_transactionSupplierRowCost
        var imageButton_transactionSupplierRowMessage = viewHolder.itemView.imageButton_transactionSupplierRowMessage
        var textView_transactionSupplierRowItemStatus = viewHolder.itemView.textView_transactionSupplierRowItemStatus

        val currentUser = FirebaseAuth.getInstance().currentUser!!

        textView_transactionSupplierRowItemName.text = transactionItem.transaction_supplier_item_name

        when(transactionItem.transaction_supplier_status){
            "closed"->{
                button_transactionSupplierRowReview.text = "Transaction has been closed"
                button_transactionSupplierRowReview.isClickable = false
            }

            "open"->{
                button_transactionSupplierRowReview.text = "Close transaction"
                button_transactionSupplierRowReview.isClickable = true
            }

            "pending"->{
                button_transactionSupplierRowReview.text = "Open transaction"
                button_transactionSupplierRowReview.isClickable = true
            }
        }

        button_transactionSupplierRowReview.setOnClickListener {
            when(button_transactionSupplierRowReview.text){
                "Close transaction"->{
                    button_transactionSupplierRowReview.text = "Transaction has been closed"
                    button_transactionSupplierRowReview.isClickable = false

                    var dialog = Custom_Progress_Dialog(context)
                    dialog.showDialog("Loading",RandomMessages().getRandomMessage())

                    FirebaseFirestore.getInstance()
                        .collection("Transaction_Supplier")
                        .document(currentUser.uid)
                        .collection("transaction_supplier")
                        .document(transactionItem.transaction_supplier_uid!!)
                        .update("transaction_supplier_status", "closed")
                        .addOnCompleteListener {
                            task: Task<Void> ->
                            if (task.isSuccessful){
                                dialog.dissmissDialog()
                                FirebaseFirestore.getInstance()
                                    .collection("Transaction_Client")
                                    .document(transactionItem.transaction_supplier_buyer_uid!!)
                                    .collection("events")
                                    .document(transactionItem.transaction_supplier_event_uid!!)
                                    .collection("transaction_client")
                                    .document(transactionItem.transaction_supplier_uid!!)
                                    .update("transaction_client_status", "closed")
                            }else{
                                dialog.dissmissDialog()
                                Log.e("trans sup", task.exception.toString())
                                PopUpDialogs(context).errorDialog("Error Time", "Error")
                            }
                        }


                }

                "Open transaction"->{
                    button_transactionSupplierRowReview.text = "Close transaction"
                    button_transactionSupplierRowReview.isClickable = true

                    var dialog = Custom_Progress_Dialog(context)
                    dialog.showDialog("Loading",RandomMessages().getRandomMessage())

                    FirebaseFirestore.getInstance()
                        .collection("Transaction_Supplier")
                        .document(currentUser.uid)
                        .collection("transaction_supplier")
                        .document(transactionItem.transaction_supplier_uid!!)
                        .update("transaction_supplier_status", "open")
                        .addOnCompleteListener {
                            task: Task<Void> ->
                            if (task.isSuccessful){
                                dialog.dissmissDialog()
                                FirebaseFirestore.getInstance()
                                    .collection("Transaction_Client")
                                    .document(transactionItem.transaction_supplier_buyer_uid!!)
                                    .collection("events")
                                    .document(transactionItem.transaction_supplier_event_uid!!)
                                    .collection("transaction_client")
                                    .document(transactionItem.transaction_supplier_uid!!)
                                    .update("transaction_client_status", "open")
                            }else{
                                dialog.dissmissDialog()
                                Log.e("trans sup", task.exception.toString())
                                PopUpDialogs(context).errorDialog("Error Time", "Error")
                            }
                        }
                }
            }
        }

        textView_transactionSupplierRowItemStatus.text = "Order Status: ${transactionItem.transaction_supplier_status!!.toUpperCase()}"

        textView_transactionSupplierRowCost.text = "₱${transactionItem.transaction_supplier_order_cost}"

        /////////////////////
        imageButton_transactionSupplierRowMessage.setOnClickListener {
            val currentUser = FirebaseAuth.getInstance().currentUser!!
            val currentUserUid = currentUser.uid
            val custom_progress_dialog = Custom_Progress_Dialog(context)
            val randomMessages = RandomMessages()
            custom_progress_dialog.showDialog("LOADING", randomMessages.getRandomMessage())

            FirebaseFirestore.getInstance()
                .collection("User")
                .document(currentUserUid)
                .get()
                .addOnSuccessListener { documentSnapshot ->
                    val sendingUser =
                        documentSnapshot.toObject(com.example.morkince.okasyonv2.activities.model.User::class.java)

                    FirebaseFirestore.getInstance()
                        .collection("User")
                        .document(transactionItem.transaction_supplier_buyer_uid!!)
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
    }
}