package com.example.morkince.okasyonv2.activities.adapter

import android.content.Context
import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import com.example.morkince.okasyonv2.R
import com.example.morkince.okasyonv2.activities.holder.MyViewHolder

class RecyclerAdapter(private val myDataset: ArrayList<String>, private val context: Context) : RecyclerView.Adapter<MyViewHolder>(){
    override fun onCreateViewHolder(p0: ViewGroup, p1: Int): MyViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.rows_content_reviews, p0, false)
        return MyViewHolder(view, context)
    }

    override fun getItemCount(): Int {
        return myDataset.size
    }

    override fun onBindViewHolder(p0: MyViewHolder, p1: Int) {
        p0.bindItem(myDataset[p1])
    }


}
