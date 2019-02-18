package com.example.morkince.okasyonv2.activities.view_holders

import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.view.LayoutInflater
import com.example.morkince.okasyonv2.R
import com.example.morkince.okasyonv2.activities.client_activities.ClientViewItemsActivity
import com.google.firebase.firestore.FirebaseFirestore
import com.xwray.groupie.Item
import com.xwray.groupie.ViewHolder
import kotlinx.android.synthetic.main.modal_update_set_budget.view.*
import kotlinx.android.synthetic.main.row_item_budget_summary.view.*
import java.text.DecimalFormat

class ItemBudgetViewHolder(val itemCategory: String,
                           val budgetSpent: Double,
                           val budgetSet: Double,
                           val eventUid: String,
                           val cartGroup: String,
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

        if (itemCategory.contains("_")){
            itemCategoryTextView.text = itemCategory.replace("_", " ")
        }else{
            itemCategoryTextView.text = itemCategory
        }

        if (budgetSet<budgetSpent){
            itemSetBudgetTextView.setTextColor(Color.RED)
        }

        itemSetBudgetTextView.text = budgetSet.toString()
        itemSpentBudgetTextView.text = budgetSpent.toString()


        itemSetBudgetTextView.setOnClickListener {
            //update budget
            var dialogBuilder: AlertDialog.Builder?
            var alert: AlertDialog.Builder?
            var dialog: AlertDialog?

            //start making the popup after checking the value of the id Number
            var view = LayoutInflater.from(context).inflate(R.layout.modal_update_set_budget, null)

            var popupBudget = view.editText_updateSetBudgetModalBudget
            var popupItemCategory = view.textView_updateSetBudgetModalCategory
            var popupUpdateButton = view.button_updateSetBudgetModalUpdate
            var popupCancelButton = view.button_updateSetBudgetModalCancel


            if (itemCategory.contains("_")){
                popupItemCategory.text = itemCategory.replace("_", " ")
            }else{
                popupItemCategory.text = itemCategory
            }

            popupBudget.hint ="P ${DecimalFormat("##.##").format(budgetSet)}"

            dialogBuilder = AlertDialog.Builder(context).setView(view)
            dialog = dialogBuilder!!.create()
            dialog?.show()


            popupUpdateButton.setOnClickListener {
                //popup info
                val newBudget = popupBudget.text.toString().trim().toDouble()
                FirebaseFirestore.getInstance()
                    .collection("Custom_Event_Item_Category")
                    .document(eventUid)
                    .collection("ceic_item_category")
                    .document(itemCategory)
                    .update("ceic_item_set_budget", newBudget)
                    .addOnSuccessListener {
                        dialog.dismiss()
                    }
            }

            popupCancelButton.setOnClickListener {
                dialog.dismiss()
            }
        }


        deleteButton.setOnClickListener {
            var alertDialog = AlertDialog.Builder(context)

            alertDialog.setIcon(R.drawable.ic_info_outline_accent)
            alertDialog.setMessage("Deleting this item category will also all items in your cart that belongs to this category")
            alertDialog.setTitle("CONFIRM DELETE")
            alertDialog.setCancelable(false)

            alertDialog.setPositiveButton("OK") { dialog, which ->
                FirebaseFirestore.getInstance()
                    .collection("Custom_Event_Item_Category")
                    .document(eventUid)
                    .collection("ceic_item_category")
                    .document(itemCategory).delete().addOnSuccessListener {
                        dialog.dismiss()
                    }
            }

            alertDialog.setNegativeButton("Cancel"){
                dialog, which ->
                dialog.dismiss()
            }

            alertDialog.show()
        }

        itemCategoryTextView.setOnClickListener {
            var intent = Intent(context, ClientViewItemsActivity::class.java)
            intent.putExtra("item_category", itemCategory)
            intent.putExtra("event_event_uid", eventUid)
            intent.putExtra("event_cart_group_uid", cartGroup)

            context.startActivity(intent)
        }
    }
}