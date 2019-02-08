package com.example.morkince.okasyonv2.activities.adapter

import android.content.Context
import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import com.example.morkince.okasyonv2.R
import com.example.morkince.okasyonv2.activities.holder.TransactionViewHolder

class TransactionRecyclerAdapter (private val myDataset2: ArrayList<String>, private val context: Context) : androidx.recyclerview.widget.RecyclerView.Adapter<TransactionViewHolder>() {
    override fun onCreateViewHolder(p0: ViewGroup, p1: Int): TransactionViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.rows_transactions_reviews, p0, false)
        return TransactionViewHolder(
            view,
            context
        )
    }

    override fun getItemCount(): Int {
        return myDataset2.size
    }

    override fun onBindViewHolder(p0: TransactionViewHolder, p1: Int) {
        p0.bindItem(myDataset2[p1])
    }
}

