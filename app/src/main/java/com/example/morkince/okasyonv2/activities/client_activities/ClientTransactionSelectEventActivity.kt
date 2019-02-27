package com.example.morkince.okasyonv2.activities.client_activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.morkince.okasyonv2.R
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.ViewHolder

class ClientTransactionSelectEventActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_client_transaction_select_event)

        var adapter = GroupAdapter<ViewHolder>()
    }
}
