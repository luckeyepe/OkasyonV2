package com.example.morkince.okasyonv2.activities.client_activities

import android.app.ProgressDialog
import android.opengl.Visibility
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.LinearLayout
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.morkince.okasyonv2.R
import com.example.morkince.okasyonv2.activities.CallableFunctions
import com.example.morkince.okasyonv2.activities.view_holders.AddItemCategoryViewHoldel
import com.google.android.gms.tasks.Task
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.ViewHolder
import kotlinx.android.synthetic.main.activity_cilent_add_item_categories.*
import java.util.ArrayList

class CilentAddItemCategoriesActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cilent_add_item_categories)

        val eventUid = intent.getStringExtra("event_event_uid")

        val progress = ProgressDialog(this)
        progress.setTitle("PLEASE WAIT")
        progress.setMessage("Checking available item categories")

        progress.setCancelable(false)
        progress.show()

        val callableFunctions = CallableFunctions()
        callableFunctions.getUnusedItemCategories(eventUid).addOnCompleteListener {
            task: Task<ArrayList<String>> ->
            if (task.isSuccessful){
                val result = task.result!!

                if (result.size == 0){
                    textView_clientAddItemCategoriesEmpty.visibility = View.VISIBLE
                    progress.dismiss()
                    return@addOnCompleteListener
                }

                var adapter = GroupAdapter<ViewHolder>()

                for(category in result){
                    adapter.add(AddItemCategoryViewHoldel(category, eventUid))
                }

                recylerView_clientAddItemCategories.adapter = adapter
                progress.dismiss()
                recylerView_clientAddItemCategories.layoutManager = LinearLayoutManager(this)
            }
        }

        button_clientAddItemCategoriesFinish.setOnClickListener {
            finish()
        }
    }
}
