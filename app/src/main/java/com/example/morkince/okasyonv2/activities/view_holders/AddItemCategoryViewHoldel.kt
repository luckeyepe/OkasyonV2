package com.example.morkince.okasyonv2.activities.view_holders

import com.example.morkince.okasyonv2.R
import com.google.firebase.firestore.FirebaseFirestore
import com.xwray.groupie.Item
import com.xwray.groupie.ViewHolder
import kotlinx.android.synthetic.main.row_item_category_checkbox.view.*

class AddItemCategoryViewHoldel(val itemCategoryId: String, val eventUid: String): Item<ViewHolder>() {
    override fun getLayout(): Int {
        return R.layout.row_item_category_checkbox
    }

    override fun bind(viewHolder: ViewHolder, position: Int) {
        val itemCategoryText = viewHolder.itemView.textView_itemCategoryCheckBoxRowItemCategory
        val addButton = viewHolder.itemView.imageButton_itemCategoryCheckBoxRowAdd

        if (itemCategoryId.contains("_")){
            itemCategoryText.text = itemCategoryId.replace("_", " ")
        }else{
            itemCategoryText.text = itemCategoryId
        }

        addButton.setOnClickListener {
            var map = HashMap<String, Any>()
            map["ceic_item_actual_budget"] = 0
            map["ceic_item_event_uid"] = eventUid
            map["ceic_item_item_category"] = itemCategoryId
            map["ceic_item_set_budget"] = 0

            FirebaseFirestore.getInstance()
                .collection("Custom_Event_Item_Category")
                .document(eventUid)
                .collection("ceic_item_category")
                .document(itemCategoryId)
                .set(map).addOnSuccessListener {
                    addButton.setImageResource(R.drawable.ic_check_circle_green)
                    addButton.isEnabled = false
                }
        }
    }
}