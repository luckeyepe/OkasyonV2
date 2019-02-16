package com.example.morkince.okasyonv2.activities


import com.google.android.gms.tasks.Task
import com.google.firebase.functions.FirebaseFunctions
import java.util.ArrayList
import java.util.HashMap

class CallableFunctions {
    private var functions = FirebaseFunctions.getInstance()

    fun getRelatedItems(itemCategory: String, userUid: String): Task<ArrayList<String>> {
        val data = HashMap<String, Any>()
        data["item_category"] = itemCategory
        data["current_user_uid"] = userUid

        return functions
            .getHttpsCallable("getRelatedItems")
            .call(data)
            .continueWith{ task ->
                val result = task.result!!.data as Map<String, Any>
                result["itemUids"] as ArrayList<String>
            }
    }

    fun searchForItem(searchQuery: String, itemCategory: String): Task<ArrayList<String>> {
        val data = HashMap<String, Any>()
        data["query"] = searchQuery
        data["item_category"] = itemCategory

        return functions
            .getHttpsCallable("searchForItem")
            .call(data)
            .continueWith{ task ->
                val result = task.result!!.data as Map<String, Any>
                result["itemUids"] as ArrayList<String>
            }
    }

    fun filterItems(storeName: String, budget: Double,
                    location: String, itemScore: Int,
                    isForRent: Boolean, itemCategory: String): Task<ArrayList<String>>{
        val data = HashMap<String, Any>()
        data["store_name"] = storeName
        data["budget"] = budget
        data["location"] = location
        data["item_score"] = itemScore
        data["is_for_rent"] = isForRent
        data["item_category"] = itemCategory

        return functions
            .getHttpsCallable("filterItems")
            .call(data)
            .continueWith {task->
                val result = task.result!!.data as Map<String, Any>
                result["filterResult"] as ArrayList<String>
            }
    }
}