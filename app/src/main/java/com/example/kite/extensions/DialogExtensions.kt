package com.example.kite.extensions

import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.view.LayoutInflater
import android.widget.Button
import android.widget.ImageView
import androidx.annotation.LayoutRes
import androidx.appcompat.app.AlertDialog

object DialogExtensions {
    fun showDialog(context: Context, @LayoutRes layoutRes: Int, close: Int, ok: Int): AlertDialog {
        val builder = AlertDialog.Builder(context)
            .create()
        builder.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        val view = LayoutInflater.from(context).inflate(layoutRes, null)
        builder.setView(view)
        val imgClose: ImageView = view.findViewById(close)
        val btnClose: Button = view.findViewById(ok)
        btnClose.setOnClickListener {
            builder.dismiss()
        }
        imgClose.setOnClickListener {
            builder.dismiss()
        }
        builder.setCanceledOnTouchOutside(false)
        return builder
    }
}
