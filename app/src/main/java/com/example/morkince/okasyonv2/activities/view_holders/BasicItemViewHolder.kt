package com.example.morkince.okasyonv2.activities.view_holders

import android.content.Context
import com.example.morkince.okasyonv2.R
import com.squareup.picasso.Picasso
import com.xwray.groupie.Item
import com.xwray.groupie.ViewHolder
import kotlinx.android.synthetic.main.row_viewitemcontent_client.view.*

class BasicItemViewHolder(val item_uid: String,
                          val item_rating:Double,
                          val item_price: Double,
                          val item_name: String,
                          val context:Context,
                          val item_display_picture_url:String): Item<ViewHolder>() {

    override fun getLayout(): Int {
        return R.layout.row_viewitemcontent_client
    }

    override fun bind(viewHolder: ViewHolder, position: Int) {
        val itemDisplayImage = viewHolder.itemView.imageView_rowViewItemContentItemImage
        val itemName = viewHolder.itemView.textView_rowViewItemContentItemName
        val itemPrice = viewHolder.itemView.textView_ItemPrice
        val itemRating = viewHolder.itemView.ratingBar_rowViewItemContentItemRating

        if (item_display_picture_url==null || item_display_picture_url == "default"){
            Picasso.get().load(R.drawable.backgroundimage).into(itemDisplayImage)
        }else{
            Picasso.get().load(item_display_picture_url).into(itemDisplayImage)
        }

        itemName.text = item_name
        itemPrice.text = "₱$item_price"
        itemRating.numStars = item_rating.toInt()
    }
}