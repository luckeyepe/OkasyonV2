package com.example.morkince.okasyonv2.activities.view_holders

import android.content.Context
import android.content.Intent
import com.example.morkince.okasyonv2.R
import com.example.morkince.okasyonv2.activities.chat_activities.MaxImageActivity
import com.squareup.picasso.Picasso
import com.xwray.groupie.Item
import com.xwray.groupie.ViewHolder
import kotlinx.android.synthetic.main.activity_testing_image_slider_imagehandlers.view.*

class ImagesViewHolder(val downloadUrl: String, val context: Context):Item<ViewHolder>(){
    override fun getLayout(): Int {
        return R.layout.activity_testing_image_slider_imagehandlers
    }

    override fun bind(viewHolder: ViewHolder, position: Int) {
        var imageViewforImages = viewHolder.itemView.imageViewforImages

        if (downloadUrl.isEmpty()){
            Picasso.get().load(R.drawable.ic_error_black_24dp).into(imageViewforImages)
        }else{
            Picasso.get().load(downloadUrl).into(imageViewforImages)
        }

        imageViewforImages.setOnClickListener {
            var intent = Intent(context, MaxImageActivity::class.java)
            intent.putExtra("sourceUrl", downloadUrl)
            context.startActivity(intent)
        }

    }

}