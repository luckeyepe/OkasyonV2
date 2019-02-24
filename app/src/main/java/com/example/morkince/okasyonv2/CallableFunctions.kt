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

    fun filterItems(itemCategory: String, storeName: String, budget: Double,
                    location: String, itemRating: Int,
                    isForSale: Boolean): Task<ArrayList<String>>{
        val data = HashMap<String, Any>()
        data["item_category"] = itemCategory
        data["store_name"] = storeName
        data["budget"] = budget
        data["location"] = location
        data["item_rating"] = itemRating
        data["is_for_sale"] = isForSale

        return functions
            .getHttpsCallable("filterItems")
            .call(data)
            .continueWith {task->
                val result = task.result!!.data as Map<String, Any>
                result["filterResult"] as ArrayList<String>
            }
    }

    fun getUnusedItemCategories(eventUid: String): Task<ArrayList<String>>{
        val data = HashMap<String, Any>()
        data["event_uid"] = eventUid

        return functions
            .getHttpsCallable("getUnusedItemCategories")
            .call(data)
            .continueWith {task->
                val result = task.result!!.data as Map<String, Any>
                result["itemCategories"] as ArrayList<String>
            }
    }
}