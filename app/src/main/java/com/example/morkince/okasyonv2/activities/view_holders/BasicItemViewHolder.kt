package com.example.morkince.okasyonv2.activities.view_holders

import android.content.Context
import android.content.Intent
import com.example.morkince.okasyonv2.R
import com.example.morkince.okasyonv2.activities.client_activities.ClientItemDetailActivity
import com.google.android.gms.tasks.Task
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.FirebaseFirestore
import com.squareup.picasso.Picasso
import com.xwray.groupie.Item
import com.xwray.groupie.ViewHolder
import kotlinx.android.synthetic.main.row_viewitemcontent_client.view.*

class BasicItemViewHolder(val item_uid: String,
                          val item_rating:Double,
                          val item_price: Double,
                          val item_name: String,
                          val cart_group_uid: String,
                          val context:Context,
                          val item_display_picture_url:String, val event_event_uid: String): Item<ViewHolder>() {

    override fun getLayout(): Int {
        return R.layout.row_viewitemcontent_client
    }

    override fun bind(viewHolder: ViewHolder, position: Int) {
        val itemDisplayImage = viewHolder.itemView.imageView_rowViewItemContentItemImage
        val itemName = viewHolder.itemView.textView_rowViewItemContentItemName
        val itemPrice = viewHolder.itemView.textView_ItemPrice
        val itemRating = viewHolder.itemView.ratingBar_rowViewItemContentItemRating

        FirebaseFirestore.getInstance()
            .collection("Items")
            .document(item_uid)
            .get()
            .addOnCompleteListener {
                task: Task<DocumentSnapshot> ->
                if (task.isSuccessful){
                    var item = task.result!!.toObject(com.example.morkince.okasyonv2.activities.model.Item::class.java)!!

                    if (item.item_display_picture_url=="" || item.item_display_picture_url == "default"){
                        Picasso.get().load(R.drawable.backgroundimage).into(itemDisplayImage)
                    }else{
                        Picasso.get().load(item_display_picture_url).into(itemDisplayImage)
                    }

                    itemName.text = item.item_name
                    itemPrice.text = "₱${item.item_price}"
                    itemRating.numStars = item.item_average_rating.toInt()

                    viewHolder.itemView.ParentLayout.setOnClickListener {
                        var intent = Intent(context, ClientItemDetailActivity::class.java)
                        intent.putExtra("item_uid", item_uid)
                        intent.putExtra("event_event_uid", event_event_uid)
                        intent.putExtra("event_cart_group_uid", cart_group_uid)
                        context.startActivity(intent)
                    }
                }
            }
//        if (item_display_picture_url=="" || item_display_picture_url == "default"){
//            Picasso.get().load(R.drawable.backgroundimage).into(itemDisplayImage)
//        }else{
//            Picasso.get().load(item_display_picture_url).into(itemDisplayImage)
//        }
//
//        itemName.text = item_name
//        itemPrice.text = "₱$item_price"
//        itemRating.numStars = item_rating.toInt()
//
//        viewHolder.itemView.ParentLayout.setOnClickListener {
//            var intent = Intent(context, ClientItemDetailActivity::class.java)
//            intent.putExtra("item_uid", item_uid)
//            intent.putExtra("event_event_uid", event_event_uid)
//            intent.putExtra("event_cart_group_uid", cart_group_uid)
//            context.startActivity(intent)
//        }

    }
}