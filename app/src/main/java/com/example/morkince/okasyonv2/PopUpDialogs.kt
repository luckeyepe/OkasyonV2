package com.example.morkince.okasyonv2

import android.app.AlertDialog
import android.content.Context

class PopUpDialogs(val context: Context) {

    fun errorDialog(message: String, title: String) {
        var alertDialog = AlertDialog.Builder(context)
        alertDialog.setIcon(R.drawable.ic_error_outline_black_24dp)
        alertDialog.setMessage(message)
        alertDialog.setTitle(title)
        alertDialog.setCancelable(false)
        alertDialog.setPositiveButton("OK") { dialog, which ->
            dialog.dismiss()
        }
        alertDialog.show()
    }

    fun successDialog(message: String, title: String) {
        var alertDialog = AlertDialog.Builder(context)
        alertDialog.setIcon(R.drawable.ic_check_circle_black_24dp)
        alertDialog.setMessage(message)
        alertDialog.setTitle(title)
        alertDialog.setCancelable(false)

        alertDialog.setPositiveButton("OK") { dialog, which ->
            dialog.dismiss()
        }
        alertDialog.show()
    }

    fun infoDialog(message: String, title: String) {
        var alertDialog = AlertDialog.Builder(context)
        alertDialog.setIcon(R.drawable.ic_info_outline_black_24dp)
        alertDialog.setMessage(message)
        alertDialog.setTitle(title)
        alertDialog.setCancelable(false)

        alertDialog.setPositiveButton("OK") { dialog, which ->
            dialog.dismiss()
        }
        alertDialog.show()
    }
}