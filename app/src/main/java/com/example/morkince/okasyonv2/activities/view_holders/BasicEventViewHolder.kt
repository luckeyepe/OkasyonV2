package com.example.morkince.okasyonv2.activities.view_holders

import android.net.Uri
import com.example.morkince.okasyonv2.R
import com.google.android.gms.tasks.Task
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import com.squareup.picasso.Picasso
import com.xwray.groupie.Item
import com.xwray.groupie.ViewHolder
import kotlinx.android.synthetic.main.row_basic_event_details.view.*

class BasicEventViewHolder(var eventUid: String):Item<ViewHolder>() {

    override fun getLayout(): Int {
        return R.layout.row_basic_event_details
    }

    override fun bind(viewHolder: ViewHolder, position: Int) {
        val eventNameTextView = viewHolder.itemView.textView_basicEventDetailsRowEventName
        val eventPicture = viewHolder.itemView.imageView_basicEventDetailsRowEventName

        FirebaseFirestore.getInstance()
            .collection("Event")
            .document(eventUid)
            .get()
            .addOnCompleteListener {
                task: Task<DocumentSnapshot> ->
                if (task.isSuccessful){
                    val result = task.result!!
                    val eventName = result.get("event_name").toString()

                    eventNameTextView.text = eventName

                    //grab picture
                    FirebaseStorage.getInstance().reference
                        .child("event_images")
                        .child(eventUid)
                        .downloadUrl
                        .addOnCompleteListener {
                            task1: Task<Uri> ->
                            if(task1.isSuccessful){
                                val uri = task1.result
                                Picasso.get().load(uri.toString()).into(eventPicture)
                            }else{
                                Picasso.get().load(R.drawable.default_avata).into(eventPicture)
                            }
                        }
                }else{
                    //
                }
            }
    }
}