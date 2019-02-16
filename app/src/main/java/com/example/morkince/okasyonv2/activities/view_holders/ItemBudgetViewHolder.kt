package com.example.morkince.okasyonv2.activities.view_holders

import android.content.Context
import android.content.Intent
import com.example.morkince.okasyonv2.R
import com.example.morkince.okasyonv2.activities.client_activities.ClientViewItemsActivity
import com.google.firebase.firestore.FirebaseFirestore
import com.xwray.groupie.Item
import com.xwray.groupie.ViewHolder
import kotlinx.android.synthetic.main.row_item_budget_summary.view.*

class ItemBudgetViewHolder(val itemCategory: String,
                           val budgetSpent: Double,
                           val budgetSet: Double,
                           val eventUid: String,
                           val context: Context): Item<ViewHolder>() {
    override fun getLayout(): Int {
        return R.layout.row_item_budget_summary
    }

    override fun bind(viewHolder: ViewHolder, position: Int) {
        //
        var itemCategoryTextView = viewHolder.itemView.textView_itemBudgetSummaryRowItemCategory
        var itemSpentBudgetTextView = viewHolder.itemView.textView_itemBudgetSummaryRowSpentBudge
        var itemSetBudgetTextView = viewHolder.itemView.textView_itemBudgetSummaryRowSetBudget
        var deleteButton = viewHolder.itemView.imageButton_itemBudgetSummaryRowDelete

        itemCategoryTextView.text = itemCategory
        itemSetBudgetTextView.text = budgetSet.toString()
        itemSpentBudgetTextView.text = budgetSpent.toString()

        deleteButton.setOnClickListener {
            FirebaseFirestore.getInstance()
                .collection("Custom_Event_Item_Category")
                .document(eventUid)
                .collection("ceic_item_category")
                .document(itemCategory).delete()
        }

        itemCategoryTextView.setOnClickListener {
            var intent = Intent(context, ClientViewItemsActivity::class.java)
            intent.putExtra("item_category", itemCategory)
            intent.putExtra("event_event_uid", eventUid)

            context.startActivity(intent)
        }
    }
}