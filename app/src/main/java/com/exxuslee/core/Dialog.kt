package com.exxuslee.core

import android.app.AlertDialog
import android.content.Context
import android.view.View


interface Dialog {
    fun apply(title: String, message: String, view: View, ok: ((Unit) -> Unit))

    class Base(context: Context?) : Dialog, AlertDialog(context) {
        override fun apply (title: String, message: String, view: View, ok: ((Unit) -> Unit)) {
            val dialog = Builder(context)
                .setTitle(title)
                .setView(view)
                .setPositiveButton(android.R.string.ok) { _, _ -> ok.invoke(Unit) }
                .setNegativeButton(android.R.string.cancel, null)
                .setIcon(android.R.drawable.ic_dialog_info)
            if (message.isNotEmpty()) dialog.setMessage(message)
            dialog.show()
        }
    }
}