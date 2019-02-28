package com.example.morkince.okasyonv2.activities.common_activities

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.morkince.okasyonv2.Custom_Progress_Dialog
import com.example.morkince.okasyonv2.R
import com.example.morkince.okasyonv2.RandomMessages
import com.example.morkince.okasyonv2.activities.chat_activities.ChatLogActivitiy
import com.example.morkince.okasyonv2.activities.model.Transaction_Client
import com.example.morkince.okasyonv2.activities.view_holders.ImagesViewHolder
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.ViewHolder
import kotlinx.android.synthetic.main.activity_view_transaction_details.*

class ViewTransactionDetailsActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_view_transaction_details)

        var transactionItem = intent.getParcelableExtra<Transaction_Client>("transactionItem")

        //get item images
        loadItemImages(transactionItem.transaction_client_item_uid!!)

        //load item name
        FirebaseFirestore.getInstance()
            .collection("Items")
            .document(transactionItem.transaction_client_item_uid!!)
            .get()
            .addOnCompleteListener {
                task: Task<DocumentSnapshot> ->
                if (task.isSuccessful){
                    var result = task.result!!
                    var itemName = result.getString("item_name")!!

                    textView__viewTransactionDetailsItemName.text = itemName.toUpperCase()
                    textView_viewTransactionDetailsCost.text = "₱${transactionItem.transaction_client_order_cost}"
                    textView_viewTransactionDetailsOrderQuantity.text = "Quantity: ${transactionItem.transaction_client_item_count}"

                    //delivery location
                    if (transactionItem.transaction_client_deliver_location.isNullOrEmpty()){
                        textView_viewTransactionDetailsDeliveryLocation.text = "Delivery Option: Store Pickup"
                    }else{
                        textView_viewTransactionDetailsDeliveryLocation.text = "Delivery Location: ${transactionItem.transaction_client_deliver_location}"
                    }

                    if(transactionItem.transaction_client_rent_start_date.isNullOrEmpty()){
                        textView_viewTransactionDetailsDurationLabel.visibility = View.GONE
                        textView_viewTransactionDetailsRentDuration.visibility = View.GONE
                    }else{
                        textView_viewTransactionDetailsDurationLabel.visibility = View.VISIBLE
                        textView_viewTransactionDetailsRentDuration.visibility = View.VISIBLE
                        textView_viewTransactionDetailsRentDuration.text = "${transactionItem.transaction_client_rent_start_date} " +
                                "- ${transactionItem.transaction_client_rent_end_date}"
                    }

                    if(transactionItem.transaction_client_status == "closed" && transactionItem.transaction_client_item_rating == 0.0){
                        button_viewTransactionDetailsWriteReview.visibility = View.VISIBLE
                    }else{
                        button_viewTransactionDetailsWriteReview.visibility = View.GONE
                    }
                }else{
                    //do something or not
                }
            }

        button_viewTransactionDetailsWriteReview.setOnClickListener {
            //launch modal
        }

        imageButton_viewTransactionDetailsMessage.setOnClickListener {
            val currentUser = FirebaseAuth.getInstance().currentUser!!
            val currentUserUid = currentUser.uid
            val custom_progress_dialog = Custom_Progress_Dialog(this)
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

                                                val intent = Intent(this, ChatLogActivitiy::class.java)
                                                intent.putExtra("sendingUser", sendingUser)
                                                intent.putExtra("receivingUser", receivingUser)
                                                this.startActivity(intent)

                                                custom_progress_dialog.dissmissDialog()
                                            }
                                            .addOnFailureListener { custom_progress_dialog.dissmissDialog() }
                                    }
                                    .addOnFailureListener { custom_progress_dialog.dissmissDialog() }
                            }
                            .addOnFailureListener {
                                custom_progress_dialog.dissmissDialog()
                                Toast.makeText(this, "Coudn't Reach the Dude", Toast.LENGTH_LONG)
                            }
                    } else {
                        custom_progress_dialog.dissmissDialog()
                    }
                }
        }

        if(transactionItem.transaction_client_item_rating != 0.0){
            textView_viewTransactionDetailsWriteReviewLabel.visibility = View.VISIBLE
            ratingBar_viewTransactionDetailsStare.visibility = View.VISIBLE
            scrollView_viewTransacitionDetailsScroll.visibility = View.VISIBLE

            ratingBar_viewTransactionDetailsStare.rating = transactionItem.transaction_client_item_rating!!.toFloat()
            textView_viewTransactionDetailsReview.text = transactionItem.transaction_client_review
        }
    }

    fun loadItemImages(item_uid: String) {
        val dialog = Custom_Progress_Dialog(this)
        val randomMessages = RandomMessages()
        var inCounter: Int
        val adapter = GroupAdapter<ViewHolder>()

        adapter.clear()

        for (counter in 1..5) {
            dialog.showDialog("Loading", randomMessages.getRandomMessage())
            // Create a reference to the file to delete
            val desertRef = FirebaseStorage.getInstance().reference
                .child("item_images")
                .child(item_uid)
                .child(item_uid + counter)

            inCounter = counter

            desertRef.downloadUrl
                .addOnSuccessListener { uri ->
                    if (inCounter <= 5) {
                        adapter.add(ImagesViewHolder(uri.toString(), this))
                    }else{
                        dialog.dissmissDialog()
                    }
                }
                .addOnFailureListener {
                    dialog.dissmissDialog()
                }
        }

        recyclerView_viewTransactionDetails.adapter = adapter
        recyclerView_viewTransactionDetails.layoutManager = LinearLayoutManager(
            this,
            LinearLayoutManager.HORIZONTAL,
            false)


    }
}
