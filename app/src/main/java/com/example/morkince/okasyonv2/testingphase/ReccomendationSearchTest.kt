package com.example.morkince.okasyonv2.testingphase

import android.app.Dialog
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.example.morkince.okasyonv2.R
import com.google.android.gms.tasks.Task
import com.google.firebase.functions.FirebaseFunctions
import com.google.firebase.functions.FirebaseFunctionsException
import kotlinx.android.synthetic.main.activity_reccomendation_search_test.*

class ReccomendationSearchTest : AppCompatActivity() {
    private lateinit var functions: FirebaseFunctions


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_reccomendation_search_test)

        functions = FirebaseFunctions.getInstance()

        var dialog = Dialog(this)
        dialog.setTitle("Recommedation Recieved")

        button_ask.setOnClickListener {
            val query = editText_searchquery.text.toString().trim();
            dialog.show()
            getRelatedItems(query).addOnCompleteListener {task ->
                if (!task.isSuccessful) {
                    val e = task.exception
                    if (e is FirebaseFunctionsException) {
                        val code = e.code

                        val details = e.details
                    }

                    Log.w("Recommendation Error", "getRelatedItems:onFailure", e)

                    return@addOnCompleteListener
                }

                val result = task.result
                dialog.dismiss()
                var resultArray = task.result

                var stringResult = resultArray.toString()

                textView_output.text = stringResult
            }
        }

    }

    private fun getRelatedItems(text: String): Task<ArrayList<String>> {
        // Create the arguments to the callable function.
        val data = hashMapOf(
            "text" to text
        )

        return functions
            .getHttpsCallable("getRelatedItems")
            .call(data)
            .continueWith { task ->
                val result = task.result?.data as Map<String, Any>
                result["itemUids"] as ArrayList<String>
            }
    }

}
