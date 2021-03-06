package com.example.morkince.okasyonv2.activities.view_holders

import com.example.morkince.okasyonv2.R
import com.example.morkince.okasyonv2.activities.model.Message
import com.example.morkince.okasyonv2.activities.model.User
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.FirebaseFirestore
import com.squareup.picasso.Picasso
import com.xwray.groupie.Item
import com.xwray.groupie.ViewHolder
import kotlinx.android.synthetic.main.row_user_with_last_message.view.*

class LatestMessagesViewHolder(val message: Message): Item<ViewHolder>() {
    override fun getLayout(): Int {
        return R.layout.row_user_with_last_message
    }

    override fun bind(viewHolder: ViewHolder, position: Int) {
        val currentUser = FirebaseAuth.getInstance().currentUser
        var profilePicture = viewHolder.itemView.circleImageView_userWithLastMessageRowProfilePicture
        var lastMessage = viewHolder.itemView.textViewuserWithLastMessageRowLastMessage
        val fullname = viewHolder.itemView.textView_userWithLastMessageRowFullName

        if (currentUser != null) {
            if (message.message_senderID != currentUser.uid){
                val userDB = FirebaseFirestore.getInstance()
                    .collection("User")
                    .document(message.message_senderID!!)

                userDB.get()
                    .addOnCompleteListener {
                            task: Task<DocumentSnapshot> ->
                        if (task.isSuccessful){
                            val document = task.result!!.toObject(User::class.java)
                            if (document != null) {
                                if (document.user_profPic != "default") {
                                    Picasso.get().load(document.user_profPic).into(profilePicture)
                                }else{
                                    Picasso.get().load(R.drawable.default_avata).into(profilePicture)
                                }

                                fullname.text = "${document.user_first_name} ${document.user_last_name}"

                                when(message.message_type){
                                    "image" ->{
                                        lastMessage.text = "Received an image"
                                    }

                                    "text" ->{
                                        lastMessage.text = message.message_messageContent
                                    }

                                    "emergency" ->{
                                        lastMessage.text = message.message_messageContent
                                    }
                                }

                            }
                        }
                    }
            }else{
                val userDB = FirebaseFirestore.getInstance()
                    .collection("User")
                    .document(message.message_recieverID!!)

                userDB.get()
                    .addOnCompleteListener {
                            task: Task<DocumentSnapshot> ->
                        if (task.isSuccessful){
                            val document = task.result!!.toObject(User::class.java)
                            if (document != null) {

                                if (document.user_profPic != "default") {
                                    Picasso.get().load(document.user_profPic).into(profilePicture)
                                }else{
                                    Picasso.get().load(R.drawable.default_avata).into(profilePicture)
                                }

                                fullname.text = "${document.user_first_name} ${document.user_last_name}"
                                lastMessage.text = message.message_messageContent

                                when(message.message_type){
                                    "image" ->{
                                        lastMessage.text = "Sent an image"
                                    }

                                    "text" ->{
                                        lastMessage.text = message.message_messageContent
                                    }

                                    "confirm" ->{

                                    }
                                }
                            }
                        }
                    }
            }
        }
    }
}