package com.example.morkince.okasyonv2.activities.homepage_supplier_activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.morkince.okasyonv2.R
import com.example.morkince.okasyonv2.activities.adapter.TransactionRecyclerAdapter
import kotlinx.android.synthetic.main.activity_transaction__supplier_.*

class Transaction_Supplier_Activity : AppCompatActivity() {
    private lateinit var recyclerView: RecyclerView
    private lateinit var viewAdapter: RecyclerView.Adapter<*>
    private lateinit var viewManager: RecyclerView.LayoutManager
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
        var viewManager = LinearLayoutManager(this)
        var viewAdapter = TransactionRecyclerAdapter(myDataset2, this)


      transactionRecycler.adapter = viewAdapter
     transactionRecycler.layoutManager = viewManager

    }
    override fun  onSupportNavigateUp():Boolean{
        onBackPressed()
        return true
    }

}
