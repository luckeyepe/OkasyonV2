package com.example.morkince.okasyonv2.activities.common_activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.morkince.okasyonv2.R
import com.example.morkince.okasyonv2.activities.model.Transaction_Client

class ViewTransactionDetailsActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_view_transaction_details)

        var transactionItem = intent.getParcelableExtra<Transaction_Client>("transactionItem")


    }
}
