package com.example.tent6

import android.support.v7.app.AppCompatActivity
import android.app.AlertDialog
import android.content.DialogInterface
import android.os.Bundle

class PopUpWindow(activity: AppCompatActivity, title: String, content: String, val onOK: (() -> Unit)? = null, val onCancel: (() -> Unit)? = null) {

    private val dialogActivity: AppCompatActivity = activity
    private val title: String = title
    private val content: String = content

    init {
        showPopupWindow()
    }

    private fun showPopupWindow() {
        val alertDialogBuilder = AlertDialog.Builder(dialogActivity)

        // Set the dialog title and message
        alertDialogBuilder.setTitle(title)
        alertDialogBuilder.setMessage(content)

        // Set a positive button and its click listener
        alertDialogBuilder.setPositiveButton("OK") { dialog: DialogInterface, which: Int ->
            // Do something when the OK button is clicked
            onOK?.invoke()
        }

        // Set a negative button and its click listener
        if (onCancel != null) {
            alertDialogBuilder.setNegativeButton("Cancel") { dialog: DialogInterface, which: Int ->
                // Do something when the Cancel button is clicked
                onCancel.invoke()
            }
        }

        // Create and show the AlertDialog
        val alertDialog = alertDialogBuilder.create()
        alertDialog.show()
    }

}