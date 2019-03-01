package com.example.morkince.okasyonv2.activities.view_holders

import android.content.Context
import android.graphics.Color
import android.net.Uri
import com.example.morkince.okasyonv2.Events
import com.example.morkince.okasyonv2.R
import com.google.android.gms.tasks.Task
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import com.kd.dynamic.calendar.generator.ImageGenerator
import com.squareup.picasso.Picasso
import com.xwray.groupie.Item
import com.xwray.groupie.ViewHolder
import kotlinx.android.synthetic.main.client_top_events_to_be_recycle.view.*
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*

class EventViewHolder(val eventUid: String, val context: Context): Item<ViewHolder>(){
    override fun getLayout(): Int {
        return R.layout.client_top_events_to_be_recycle
    }

    override fun bind(viewHolder: ViewHolder, position: Int) {
        var ImageView_clientTopEventsPicture = viewHolder.itemView.ImageView_clientTopEventsPicture
        var imageView_topEventsToBeRecycled = viewHolder.itemView.imageView_topEventsToBeRecycled
        var textView_clientTopEventsToBeRecycleNameoftheEvent = viewHolder.itemView.textView_clientTopEventsToBeRecycleNameoftheEvent
        var textView_clientTopEventsToBeRecycleLocationoftheEvent = viewHolder.itemView.textView_clientTopEventsToBeRecycleLocationoftheEvent

        FirebaseFirestore.getInstance()
            .collection("Event")
            .document(eventUid)
            .get()
            .addOnCompleteListener {
                task: Task<DocumentSnapshot> ->
                if (task.isSuccessful){
                    val event = task.result!!.toObject(Events::class.java)!!
                    textView_clientTopEventsToBeRecycleNameoftheEvent.text = event.event_name
                    textView_clientTopEventsToBeRecycleLocationoftheEvent.text = event.event_location

                    //////////////////////////////////////
                    val currentDate = Calendar.getInstance()
                    val sdf = SimpleDateFormat("dd-MM-yyyy", Locale.ENGLISH)

                    currentDate.time = sdf.parse(event.event_date)// all done

                    val imageGenerator = ImageGenerator(context)
                    imageGenerator.setIconSize(100, 100)
                    imageGenerator.setDateSize(40f)
                    imageGenerator.setMonthSize(20f)

                    imageGenerator.setDatePosition(80)
                    imageGenerator.setMonthPosition(24)

                    imageGenerator.setDateColor(Color.parseColor("#D81B60"))
                    imageGenerator.setMonthColor(Color.parseColor("#ffffff"))

                    imageGenerator.setStorageToSDCard(true)

                    val generatedateIcon = imageGenerator.generateDateImage(currentDate, R.drawable.calendar_icon)
                    imageView_topEventsToBeRecycled.setImageBitmap(generatedateIcon)

                    //////////////////////////////////////////

                    FirebaseStorage.getInstance()
                        .reference.child("event_images")
                        .child(eventUid)
                        .downloadUrl
                        .addOnCompleteListener {
                            task: Task<Uri> ->
                            Picasso.get().load( task.result).into(ImageView_clientTopEventsPicture)
                        }

                }else{

                }
            }
    }
}