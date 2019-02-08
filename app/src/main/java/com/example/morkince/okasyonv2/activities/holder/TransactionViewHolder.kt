package com.example.morkince.okasyonv2.activities.holder

import android.content.Context
import androidx.recyclerview.widget.RecyclerView
import android.view.View
import android.widget.TextView
import com.example.morkince.okasyonv2.R

class TransactionViewHolder (itemView: View, var context: Context): RecyclerView.ViewHolder(itemView) {
    fun bindItem(string: String) {
        var textView = itemView.findViewById<TextView>(R.id.textView_transaction_chat)
        textView.text = string

    }
}