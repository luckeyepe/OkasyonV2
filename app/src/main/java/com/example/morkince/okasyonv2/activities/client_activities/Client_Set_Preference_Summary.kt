package com.example.morkince.okasyonv2.activities.client_activities

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.morkince.okasyonv2.R
import com.example.morkince.okasyonv2.activities.view_holders.ItemBudgetViewHolder
import com.google.firebase.firestore.FirebaseFirestore
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.ViewHolder
import kotlinx.android.synthetic.main.activity_client_set_the_preference_summary_contents.*
import java.text.DecimalFormat

class Client_Set_Preference_Summary: AppCompatActivity() {
    private var eventUid: String?=null
    private var eventName: String?=null
    private var eventAddress: String ?= null
    private var eventSetBudget: Double ?= null
    private var eventSpentBudget: Double ?= null
    private var projectedBudget:Double ?= null
    private var cartGroup: String ?= null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_client_set_the_preference_summary)

        eventUid = intent.getStringExtra("event_event_uid")
        cartGroup = intent.getStringExtra("event_cart_group_uid")

        val toolbar = findViewById<Toolbar>(R.id.toolbar2)
        setSupportActionBar(toolbar)
        supportActionBar!!.title = "Budget"
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)

        disableEditText(editText_ActivityClientSetThePreferenceSummaryBudget)

        //update total set budget
        imageButton_ActivityClientSetThePreferenceSummaryEditBudget.setOnClickListener {
            if (editText_ActivityClientSetThePreferenceSummaryBudget.isEnabled){
                disableEditText(editText_ActivityClientSetThePreferenceSummaryBudget)
                //update
                val newBudget = editText_ActivityClientSetThePreferenceSummaryBudget.text.toString().trim().toDouble()

                FirebaseFirestore.getInstance()
                    .collection("Event")
                    .document(eventUid!!)
                    .update("event_set_budget", newBudget)
                    .addOnSuccessListener {
                        editText_ActivityClientSetThePreferenceSummaryBudget.setText(DecimalFormat("##.##").format(newBudget))
                    }
            }else{
                enableEditText(editText_ActivityClientSetThePreferenceSummaryBudget)
            }
        }

        imageButton_ActivityClientSetThePreferenceSummaryAddItemCategory.setOnClickListener {
            //add itemcategory
            val intent = Intent(this, ClientAddItemCategoriesActivity::class.java)
            intent.putExtra("event_event_uid", eventUid)
            startActivity(intent)
        }


        getEventBasicInfo()

        getCustomItemCategories()

    }

    private fun disableEditText(editText: EditText) {
        editText.isEnabled = false
        editText.isCursorVisible = false
    }

    private fun enableEditText(editText: EditText) {
        editText.isEnabled = true
        editText.isCursorVisible = true
    }

    private fun getEventBasicInfo() {
        FirebaseFirestore.getInstance()
            .collection("Event")
            .document(eventUid!!)
            .addSnapshotListener { documentSnapshot, firebaseFirestoreException ->
                if (firebaseFirestoreException!= null){
                    Log.e("Budget Summary", firebaseFirestoreException.toString())
                    return@addSnapshotListener
                }

                val data = documentSnapshot!!.data!!
                eventName = data["event_name"] as String
                eventAddress = data["event_location"] as String
                eventSetBudget = data["event_set_budget"].toString().toDouble()
                eventSpentBudget = data["event_budget_spent"].toString().toDouble()
                projectedBudget = data["event_projected_budget_spent"].toString().toDouble()

                textView_ActivityClientSetThePreferenceSummaryNameofParty.text = eventName
                textView_ActivityClientSetThePreferenceSummaryAddressofParty.text = eventAddress
                editText_ActivityClientSetThePreferenceSummaryBudget.setText(eventSetBudget.toString())
                textView_ActivityClientSetThePreferenceSummaryBudgetSummary.text = "${eventSpentBudget.toString()}/${eventSetBudget.toString()}"

                var spentBudgetDifference = eventSetBudget!! - eventSpentBudget!!
                if(spentBudgetDifference>=0){
                    if(spentBudgetDifference == 0.0){
                        textView_ActivityClientSetThePreferenceSummaryMessage.visibility = View.INVISIBLE
                    }else {
                        textView_ActivityClientSetThePreferenceSummaryMessage.visibility = View.VISIBLE
                        textView_ActivityClientSetThePreferenceSummaryMessage.text =
                            "You still have P$spentBudgetDifference left to spend"
                        textView_ActivityClientSetThePreferenceSummaryMessage.setTextColor(Color.GREEN)
                    }
                }else{
                    textView_ActivityClientSetThePreferenceSummaryMessage.visibility = View.VISIBLE
                    textView_ActivityClientSetThePreferenceSummaryMessage.text = "You are P${spentBudgetDifference*-1} over the set budget"
                    textView_ActivityClientSetThePreferenceSummaryMessage.setTextColor(Color.RED)
                }

                var setBudgetDifference = eventSetBudget!! - projectedBudget!!
                if(setBudgetDifference<0){
                    textView_ActivityClientSetThePreferenceSummaryOverBudgetMassage.visibility = View.VISIBLE
                    textView_ActivityClientSetThePreferenceSummaryOverBudgetMassage.text = "Your current budget is P${setBudgetDifference*-1} more than the set budget"
                    textView_ActivityClientSetThePreferenceSummaryOverBudgetMassage.setTextColor(Color.RED)
                }else{
                    textView_ActivityClientSetThePreferenceSummaryOverBudgetMassage.visibility = View.INVISIBLE
                }
            }
//            .get()
//            .addOnCompleteListener { task: Task<DocumentSnapshot> ->
//                if (task.isSuccessful) {
//                    val data = task.result!!.data!!
//                    eventName = data["event_name"] as String
//                    eventAddress = data["event_location"] as String
//                    eventSetBudget = data["event_set_budget"].toString().toDouble()
//                    eventSpentBudget = data["event_budget_spent"].toString().toDouble()
//                    projectedBudget = data["event_projected_budget_spent"].toString().toDouble()
//
//                    textView_ActivityClientSetThePreferenceSummaryNameofParty.text = eventName
//                    textView_ActivityClientSetThePreferenceSummaryAddressofParty.text = eventAddress
//                    editText_ActivityClientSetThePreferenceSummaryBudget.setText(eventSetBudget.toString())
//                    textView_ActivityClientSetThePreferenceSummaryBudgetSummary.text = "${eventSpentBudget.toString()}/${eventSetBudget.toString()}"
//
//                    var difference = eventSetBudget!! - projectedBudget!! - eventSpentBudget!!
//                    if(difference>0){
//                        textView_ActivityClientSetThePreferenceSummaryMessage.text = "You still have P$difference left to spend"
//                        textView_ActivityClientSetThePreferenceSummaryMessage.setTextColor(Color.GREEN)
//                    }else{
//                        textView_ActivityClientSetThePreferenceSummaryMessage.text = "You are P${difference*-1} over budget"
//                        textView_ActivityClientSetThePreferenceSummaryMessage.setTextColor(Color.RED)
//                    }
//
//                }
//            }
    }

    private fun getCustomItemCategories() {
        var adapter = GroupAdapter<ViewHolder>()

        FirebaseFirestore.getInstance()
            .collection("Custom_Event_Item_Category")
            .document(eventUid!!)
            .collection("ceic_item_category")
            .addSnapshotListener { querySnapshot, firebaseFirestoreException ->
                if (querySnapshot != null) {
                    adapter.clear()

                    for (result in querySnapshot) {
                        val data = result.data
                        val itemCategory = data["ceic_item_item_category"] as String
                        val budgetSpent = data["ceic_item_actual_budget"].toString().toDouble()
                        val budgetSet = data["ceic_item_set_budget"].toString().toDouble()
                        val context = this

                        adapter.add(ItemBudgetViewHolder(itemCategory, budgetSpent, budgetSet, eventUid!!, cartGroup!!, context))
                    }
                } else {
                    Log.e("Budget Summary", firebaseFirestoreException.toString())
                }
            }

        val layoutManager = LinearLayoutManager(this)

        recyclerView_ActivityClientSetThePreferenceSummaryRecyclerViewForItemNeeded.layoutManager = layoutManager
        recyclerView_ActivityClientSetThePreferenceSummaryRecyclerViewForItemNeeded.adapter = adapter
    }


    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }
}