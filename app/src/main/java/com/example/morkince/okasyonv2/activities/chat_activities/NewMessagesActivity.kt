package com.example.morkince.okasyonv2.activities.chat_activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.morkince.okasyonv2.R
import com.example.morkince.okasyonv2.activities.model.User
import com.example.morkince.okasyonv2.activities.view_holders.UsersViewHolder
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.QuerySnapshot
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.ViewHolder
import kotlinx.android.synthetic.main.activity_new_messsages.*

class NewMessagesActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_new_messsages)

        fetchAvailableUsers()
    }

    private fun fetchAvailableUsers() {
        val adapter = GroupAdapter<ViewHolder>()
        val currentUser = FirebaseAuth.getInstance().currentUser
        val db = FirebaseFirestore.getInstance().collection("User")

        recylerView_newMessages.layoutManager = LinearLayoutManager(this)

        db.get().addOnCompleteListener {
                task: Task<QuerySnapshot> ->
            if (task.isSuccessful){
                for(document in task.result!!){
                    val user = document.toObject(User::class.java)
                    if (user.user_email != currentUser!!.email)
                        adapter.add(UsersViewHolder(user))
                }

                recylerView_newMessages.adapter = adapter

                //launch chatlog activity
                adapter.setOnItemClickListener { item, view ->
                    val userItem = item as UsersViewHolder
                    val intent = Intent(view.context, ChatLogActivitiy::class.java)

                    Log.d("NewMassagesActivity", "User UID: ${userItem.user.user_uid}")
                    //pass on entire user object
                    FirebaseFirestore.getInstance()
                        .collection("User")
                        .document(FirebaseAuth.getInstance().currentUser!!.uid)
                        .get().addOnCompleteListener {
                                task: Task<DocumentSnapshot> ->
                            if (task.isSuccessful){
                                val document = task.result!!.toObject(User::class.java)
                                intent.putExtra("sendingUser", document)
                                intent.putExtra("receivingUser", userItem.user)
                                startActivity(intent)
                                finish()
                            }
                        }

                }
            }
        }
    }
}
