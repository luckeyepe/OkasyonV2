package com.example.morkince.okasyonv2

import android.app.ProgressDialog
import android.content.Context

class Custom_Progress_Dialog(val context: Context) {
    val progress = ProgressDialog(context)
    var title = "Custom Dialog"
    var message = "Loading..."

    fun showDialog() {
        progress.setTitle(title)
        progress.setMessage(message)
        progress.setCancelable(false)
        progress.show()
    }

    fun showDialog(title: String, message: String) {
        progress.setTitle(title)
        progress.setMessage(message)
        progress.setCancelable(false)
        progress.show()
    }

    fun dissmissDialog(){
        progress.dismiss()
    }
}