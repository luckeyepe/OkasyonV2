package com.example.morkince.okasyonv2.activities.view_holders

import android.content.Context
import android.content.Intent
import com.example.morkince.okasyonv2.R
import com.example.morkince.okasyonv2.activities.common_activities.ViewTransactionDetailsActivity
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.FirebaseFirestore
import com.xwray.groupie.Item
import com.xwray.groupie.ViewHolder
import kotlinx.android.synthetic.main.row_basic_notification_details.view.*

class NotificationViewHolder(val userUid: String,val notificationUid: String, val notificationType: String ,val context: Context): Item<ViewHolder>() {
    override fun getLayout(): Int {
        return R.layout.row_basic_notification_details
    }

    override fun bind(viewHolder: ViewHolder, position: Int) {
        var title = viewHolder.itemView.textView_basicNotificationDetailsTitle
        var message = viewHolder.itemView.textView_basicNotificationDetailsMessageHere
        var layount = viewHolder.itemView.constaint_notification

        FirebaseFirestore.getInstance()
            .collection("Notification")
            .document(userUid)
            .collection(notificationType)
            .document(notificationUid)
            .get()
            .addOnCompleteListener {
                task: Task<DocumentSnapshot> ->
                if (task.isSuccessful){
                    val result = task.result
                    if (result != null){
                        var notificationMessage = result.getString("notification_message")
                        var isSeen = result.getBoolean("notification_is_seen")
                        var transactionUid = result.getString("notification_transaction_uid")

                        if (isSeen != null){
                            if(isSeen) {
                                title.text = "Old Notification"
                            }else {
                                title.text = "New Notification"
                            }
                        }else{
                            title.text = "New Notification"
                        }

                        message.text = notificationMessage
                    }
                }
            }


//        layount.setOnClickListener {
//            var intent = Intent(context, ViewTransactionDetailsActivity::class.java)
//            FirebaseFirestore.getInstance()
//            start
//        }
    }
}