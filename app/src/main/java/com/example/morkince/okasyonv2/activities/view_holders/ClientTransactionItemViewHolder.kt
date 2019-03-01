package com.example.morkince.okasyonv2.activities.view_holders

import android.app.Dialog
import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.Window
import android.widget.Toast
import com.example.morkince.okasyonv2.Custom_Progress_Dialog
import com.example.morkince.okasyonv2.PopUpDialogs
import com.example.morkince.okasyonv2.R
import com.example.morkince.okasyonv2.RandomMessages
import com.example.morkince.okasyonv2.activities.chat_activities.ChatLogActivitiy
import com.example.morkince.okasyonv2.activities.model.Transaction_Client
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.FirebaseFirestore
import com.xwray.groupie.Item
import com.xwray.groupie.ViewHolder
import kotlinx.android.synthetic.main.modal_user_review.view.*
import kotlinx.android.synthetic.main.row_transaction_client.view.*
import android.view.WindowManager



class ClientTransactionItemViewHolder(val transactionItem: Transaction_Client, val context: Context):Item<ViewHolder>() {
    override fun getLayout(): Int {
        return com.example.morkince.okasyonv2.R.layout.row_transaction_client
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
            if (transactionItem.transaction_client_item_rating!!>0){
                button_transactionClientRowReview.isClickable = false
                button_transactionClientRowReview.isFocusable = false
                button_transactionClientRowReview.isEnabled = false
                button_transactionClientRowReview.text = "Review Submitted"
            }
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
                                Toast.makeText(context, "Coudn't Reach the Dude", Toast.LENGTH_LONG).show()
                            }
                    } else {
                        custom_progress_dialog.dissmissDialog()
                    }
                }
        }

        ///write review
        button_transactionClientRowReview.setOnClickListener {
            var dialog: Dialog?
            var view = LayoutInflater.from(context).inflate(com.example.morkince.okasyonv2.R.layout.modal_user_review, null)//inflate the modal

            //assign buttons from the modal
            var ratingBar_modalUserReviewRating = view.ratingBar_modalUserReviewRating
            var editText_modalUserReviewComments = view.editText_modalUserReviewComments
            var button_modalUserReviewSumbitButton = view.button_modalUserReviewSumbitButton
            var button_modalUserReviewCancelButton = view.button_modalUserReviewCancelButton

            //instantiate the dialog
            dialog = Dialog(context)
            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
            dialog.setContentView(view)
            dialog.setCancelable(true)


            val lp = WindowManager.LayoutParams()
            lp.copyFrom(dialog.window.attributes)
            lp.width = WindowManager.LayoutParams.MATCH_PARENT
            lp.height = WindowManager.LayoutParams.WRAP_CONTENT


            button_modalUserReviewCancelButton.setOnClickListener {
                dialog.dismiss()
            }

            button_modalUserReviewSumbitButton.setOnClickListener {
                val rating = ratingBar_modalUserReviewRating.rating

                if (rating<=0){
                    PopUpDialogs(context).infoDialog("You must give at least 1 star", "RATING")
                }else {
                    val review =
                        if (editText_modalUserReviewComments.text.toString().trim().isEmpty()) "" else editText_modalUserReviewComments.text.toString().trim()

                    FirebaseFirestore.getInstance()
                        .collection("Transaction_Client")
                        .document(transactionItem.transaction_client_buyer_uid!!)
                        .collection("events")
                        .document(transactionItem.transaction_client_event_uid!!)
                        .collection("transaction_client")
                        .document(transactionItem.transaction_client_uid!!)
                        .update(
                            "transaction_client_review", review,
                            "transaction_client_item_rating", rating
                        )
                        .addOnCompleteListener { task: Task<Void> ->
                            if (task.isSuccessful) {
                                dialog.dismiss()
                            } else {
                                //dedede
                            }
                        }
                }
            }

            dialog.show()
            dialog.window.attributes = lp
        }

    }
}