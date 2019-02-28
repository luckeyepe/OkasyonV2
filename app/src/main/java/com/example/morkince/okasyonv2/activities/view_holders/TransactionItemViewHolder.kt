package com.example.morkince.okasyonv2.activities.view_holders

import com.example.morkince.okasyonv2.R
import com.example.morkince.okasyonv2.activities.model.Transaction_Client
import com.xwray.groupie.Item
import com.xwray.groupie.ViewHolder
import kotlinx.android.synthetic.main.row_transaction_client.view.*

class TransactionItemViewHolder(val transactionItem: Transaction_Client):Item<ViewHolder>() {
    override fun getLayout(): Int {
        return R.layout.row_transaction_client
    }

    override fun bind(viewHolder: ViewHolder, position: Int) {
        var textView_transactionClientRowItemName = viewHolder.itemView.textView_transactionClientRowItemName
        var button_transactionClientRowReview = viewHolder.itemView.button_transactionClientRowReview
        var switch_transactionClientRowOpenOrClose = viewHolder.itemView.switch_transactionClientRowOpenOrClose
        var textView_transactionClientRowCost = viewHolder.itemView.textView_transactionClientRowCost
        var imageButton_transactionClientRowMessage = viewHolder.itemView.imageButton_transactionClientRowMessage
        var ratingBar_transactionClientRow = viewHolder.itemView.ratingBar_transactionClientRow

        textView_transactionClientRowItemName.text = transactionItem.transaction_client_item_name
    }
}