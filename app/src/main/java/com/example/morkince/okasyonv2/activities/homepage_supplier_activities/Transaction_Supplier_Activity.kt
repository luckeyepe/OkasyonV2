package com.example.morkince.okasyonv2.activities.homepage_supplier_activities

import android.app.Dialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.Window
import com.example.morkince.okasyonv2.R
import com.example.morkince.okasyonv2.activities.adapter.TransactionRecyclerAdapter
import com.example.morkince.okasyonv2.activities.client_activities.Client_Create_Event
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.activity_transaction__supplier_.*
import kotlinx.android.synthetic.main.rows_transactions_reviews.*

class Transaction_Supplier_Activity : AppCompatActivity() {
    private lateinit var recyclerView: androidx.recyclerview.widget.RecyclerView
    private lateinit var viewAdapter: androidx.recyclerview.widget.RecyclerView.Adapter<*>
    private lateinit var viewManager: androidx.recyclerview.widget.RecyclerView.LayoutManager
    private lateinit var myDataset: ArrayList<String>
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_transaction__supplier_)
        val actionbar = supportActionBar
        actionbar!!.title = "TRANSACTION"
        actionbar.setDisplayHomeAsUpEnabled(true)
        var myDataset2 = ArrayList<String>()
        myDataset2.add("Marc Shop")
        myDataset2.add("Mikay Shop")
        myDataset2.add("Mikky Shop")
        myDataset2.add("japhet Shop")
        var viewManager = androidx.recyclerview.widget.LinearLayoutManager(this)
        var viewAdapter = TransactionRecyclerAdapter(myDataset2, this)


        transactionRecycler.adapter = viewAdapter
        transactionRecycler.layoutManager = viewManager
//        button7.setOnClickListener {
//            //setup modal
//            var dialog: Dialog?
//            var view = LayoutInflater.from(this.applicationContext).inflate(R.layout.modal_rateitemstore_client, null)//inflate the modal
//            dialog = Dialog(this)
//            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
//            dialog.setContentView(view)
//            dialog.setCancelable(false)
//            dialog.show()
//        }
    }


    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }

}
