package com.example.morkince.okasyonv2.activities.holder

import android.content.Context
import android.view.View
import android.widget.TextView
import com.example.morkince.okasyonv2.R

class TransactionViewHolder (itemView: View, var context: Context): androidx.recyclerview.widget.RecyclerView.ViewHolder(itemView) {
    fun bindItem(string: String) {
        var textView = itemView.findViewById<TextView>(R.id.textView_transactionClientRowItemName)
        textView.text = string

    }
}