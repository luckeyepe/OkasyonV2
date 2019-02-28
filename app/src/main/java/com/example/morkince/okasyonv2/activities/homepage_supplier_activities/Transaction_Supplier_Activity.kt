package com.example.morkince.okasyonv2.activities.homepage_supplier_activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.morkince.okasyonv2.Custom_Progress_Dialog
import com.example.morkince.okasyonv2.PopUpDialogs
import com.example.morkince.okasyonv2.R
import com.example.morkince.okasyonv2.RandomMessages
import com.example.morkince.okasyonv2.activities.adapter.TransactionRecyclerAdapter
import com.example.morkince.okasyonv2.activities.common_activities.ViewTransactionDetailsActivity
import com.example.morkince.okasyonv2.activities.model.Transaction_Supplier
import com.example.morkince.okasyonv2.activities.view_holders.ClientTransactionItemViewHolder
import com.example.morkince.okasyonv2.activities.view_holders.SupplierTransactionItemViewHolder
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.ViewHolder
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
        val adapter = GroupAdapter<ViewHolder>()

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
                    adapter.clear()

                    for (document in querySnapshot){
                        val result = document.toObject(Transaction_Supplier::class.java)
                        adapter.add(SupplierTransactionItemViewHolder(result, this))
                    }

                    recyclerView_transactionSupplierActivity.adapter = adapter
                    recyclerView_transactionSupplierActivity.layoutManager = LinearLayoutManager(this)
                    custom_Progress_Dialog.dissmissDialog()
                }else{
                    custom_Progress_Dialog.dissmissDialog()
                    popUpDialogs.errorDialog("You have no transaction record","ERROR")
                }
            }

        //on click listener for each row
        adapter.setOnItemClickListener { item, view ->
            if (view.id != R.id.imageButton_transactionSupplierRowMessage) {
                val transactionItem = item as SupplierTransactionItemViewHolder
                val intent = Intent(view.context, ViewSupplierTransactionDetailsActivity::class.java)
                intent.putExtra("transactionItem", transactionItem.transactionItem)
                startActivity(intent)
            }
        }


    }
    override fun  onSupportNavigateUp():Boolean{
        onBackPressed()
        return true
    }

}
