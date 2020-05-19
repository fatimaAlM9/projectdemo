package com.example.project

import android.R
import android.app.Activity
import android.app.ActivityManager
import android.app.Dialog
import android.content.Context
import android.os.Build
import android.widget.EditText
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AlertDialog
import dmax.dialog.SpotsDialog


object MY_ALERT {
    fun SET_MY_ALERT(
        Context: Context?,
        title: String?,
        message: String?,
        btn: String?
    ) {
        val builder1 =
            AlertDialog.Builder(Context!!)
        builder1.setMessage(message)
        builder1.setTitle(title)
        builder1.setCancelable(true)
        builder1.setPositiveButton(
            btn
        ) { dialog, id -> dialog.cancel() }
        val alert11 = builder1.create()
        alert11.show()
    }

    fun SET_MY_ALERT_TO_CLOSE(
        Context: Context,
        title: String?,
        message: String?,
        btn: String?
    ) {
        val builder1 =
            AlertDialog.Builder(Context)
        builder1.setMessage(message)
        builder1.setTitle(title)
        builder1.setCancelable(true)
        builder1.setPositiveButton(
            btn
        ) { dialog, id ->
            dialog.cancel()
            (Context as Activity).finish()
        }
        val alert11 = builder1.create()
        alert11.show()
    }

    fun progress_loading_close(dialog: android.app.AlertDialog) {
        dialog.cancel()
    }

    @RequiresApi(api = Build.VERSION_CODES.Q)
    fun isRunning(ctx: Context): Boolean {
        val activityManager =
            ctx.getSystemService(Context.ACTIVITY_SERVICE) as ActivityManager
        val tasks =
            activityManager.getRunningTasks(Int.MAX_VALUE)
        for (task in tasks) {
            if (ctx.packageName.equals(
                    task.baseActivity!!.packageName,
                    ignoreCase = true
                )
            ) return true
        }
        return false
    }

    fun validate(editText: EditText): Boolean { // check the lenght of the enter data in EditText and give error if its empty
        if (editText.text.toString().trim { it <= ' ' }.length > 0) {
            return true // returs true if field is not empty
        }
        editText.error = "Please Fill This"
        editText.requestFocus()
        return false
    }

    fun progress_loading_show(context: Context?): android.app.AlertDialog? {
        return SpotsDialog.Builder()
            .setContext(context)
            .setMessage("Creating your account")
            .setCancelable(false)
            .build()
    }

//    private fun setDialog(show: Boolean,builder :AlertDialog.Builder) {
//        builder.setView(R.layout.progres_dialog)
//        val dialog: Dialog = builder.create()
//        if (show) dialog.show() else dialog.dismiss()
//    }



}