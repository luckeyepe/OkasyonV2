package com.example.morkince.okasyonv2.activities.common_activities

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.morkince.okasyonv2.Custom_Progress_Dialog
import com.example.morkince.okasyonv2.R
import com.example.morkince.okasyonv2.RandomMessages
import com.example.morkince.okasyonv2.activities.model.Transaction_Client
import com.example.morkince.okasyonv2.activities.view_holders.ImagesViewHolder
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
