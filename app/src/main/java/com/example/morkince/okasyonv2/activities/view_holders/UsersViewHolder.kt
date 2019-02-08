package com.example.morkince.okasyonv2.activities.view_holders

import com.example.morkince.okasyonv2.R
import com.example.morkince.okasyonv2.activities.model.User
import com.squareup.picasso.Picasso
import com.xwray.groupie.Item
import com.xwray.groupie.ViewHolder
import kotlinx.android.synthetic.main.row_user.view.*

class UsersViewHolder(val user: User): Item<ViewHolder>() {
    override fun bind(viewHolder: ViewHolder, position: Int) {
        val userPhoto = viewHolder.itemView.circleImageView_userRowUserPhoto
        val userFullName = viewHolder.itemView.textView_userRowFullName

        if (user.user_profPic != "default") {
            Picasso.get().load(user.user_profPic).into(userPhoto)
        }else{
            Picasso.get().load(R.drawable.default_avata).into(userPhoto)
        }

        userFullName.text = "${user.user_first_name} ${user.user_last_name}"
    }

    override fun getLayout(): Int {
        return R.layout.row_user
    }
}