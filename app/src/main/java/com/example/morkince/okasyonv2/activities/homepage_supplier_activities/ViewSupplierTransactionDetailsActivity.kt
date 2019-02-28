package com.example.morkince.okasyonv2.activities.homepage_supplier_activities

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
import com.example.morkince.okasyonv2.activities.model.Transaction_Supplier
import com.example.morkince.okasyonv2.activities.view_holders.ImagesViewHolder
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.ViewHolder
import kotlinx.android.synthetic.main.activity_view_supplier_transaction_details.*

class ViewSupplierTransactionDetailsActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_view_supplier_transaction_details)
        var transactionItem = intent.getParcelableExtra<Transaction_Supplier>("transactionItem")

        //get item images
        loadItemImages(transactionItem.transaction_supplier_item_uid!!)

        //load item name
        FirebaseFirestore.getInstance()
            .collection("Items")
            .document(transactionItem.transaction_supplier_item_uid!!)
            .get()
            .addOnCompleteListener {
                    task: Task<DocumentSnapshot> ->
                if (task.isSuccessful){
                    var result = task.result!!
                    var itemName = result.getString("item_name")!!

                    textView__viewSupplierTransactionDetailsItemName.text = itemName.toUpperCase()
                    textView_viewSupplierTransactionDetailsCost.text = "₱${transactionItem.transaction_supplier_order_cost}"
                    textView_viewSupplierTransactionDetailsOrderQuantity.text = "Quantity: ${transactionItem.transaction_supplier_item_count}"

                    FirebaseFirestore.getInstance()
                        .collection("User")
                        .document(transactionItem.transaction_supplier_buyer_uid!!)
                        .get()
                        .addOnCompleteListener {
                            task: Task<DocumentSnapshot> ->
                            if  (task.isSuccessful){
                                textView_viewSupplierTransactionDetailsBuyerName.text = "Buyer: ${task.result!!.getString("user_first_name")}" +
                                        " ${task.result!!.getString("user_last_name")}"
                            }
                        }


                    //delivery location
                    if (transactionItem.transaction_supplier_deliver_location.isNullOrEmpty()){
                        textView_viewSupplierTransactionDetailsDeliveryLocation.text = "Delivery Option: Store Pickup"
                    }else{
                        textView_viewSupplierTransactionDetailsDeliveryLocation.text = "Delivery Location: ${transactionItem.transaction_supplier_deliver_location}"
                    }

                    if(transactionItem.transaction_supplier_rent_start_date.isNullOrEmpty()){
                        textView_viewSupplierTransactionDetailsDurationLabel.visibility = View.GONE
                        textView_viewSupplierTransactionDetailsRentDuration.visibility = View.GONE
                    }else{
                        textView_viewSupplierTransactionDetailsDurationLabel.visibility = View.VISIBLE
                        textView_viewSupplierTransactionDetailsRentDuration.visibility = View.VISIBLE
                        textView_viewSupplierTransactionDetailsRentDuration.text = "${transactionItem.transaction_supplier_rent_start_date} " +
                                "- ${transactionItem.transaction_supplier_rent_end_date}"
                    }
                }else{
                    //do something or not
                }
            }

        imageButton_viewSupplierTransactionDetailsMessage.setOnClickListener {
            val currentUser = FirebaseAuth.getInstance().currentUser!!
            val currentUserUid = currentUser.uid
            val custom_progress_dialog = Custom_Progress_Dialog(this)
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

        if(transactionItem.transaction_supplier_item_rating != 0.0){
            textView_viewSupplierTransactionDetailsWriteReviewLabel.visibility = View.VISIBLE
            ratingBar_viewSupplierTransactionDetailsStare.visibility = View.VISIBLE
            scrollView_viewSupplierTransacitionDetailsScroll.visibility = View.VISIBLE

            ratingBar_viewSupplierTransactionDetailsStare.rating = transactionItem.transaction_supplier_item_rating!!.toFloat()
            textView_viewSupplierTransactionDetailsReview.text = transactionItem.transaction_supplier_review
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

        recyclerView_viewSupplierTransactionDetails.adapter = adapter
        recyclerView_viewSupplierTransactionDetails.layoutManager = LinearLayoutManager(
            this,
            LinearLayoutManager.HORIZONTAL,
            false)


    }
}
