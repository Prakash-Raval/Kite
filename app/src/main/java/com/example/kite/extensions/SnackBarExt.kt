package com.example.kite.extensions

import android.annotation.SuppressLint
import android.graphics.Color
import android.view.View
import androidx.annotation.IntegerRes
import androidx.annotation.StringRes
import androidx.core.content.ContextCompat
import com.example.kite.R
import com.google.android.material.snackbar.Snackbar


inline fun View.snack(message: String, length: Int = Snackbar.LENGTH_LONG, f: Snackbar.() -> Unit) {
    val snack = Snackbar.make(this, message, length)
    snack.f()
    snack.show()
}

fun View.snackError(message: String, length: Int = Snackbar.LENGTH_LONG) {
    val snack = Snackbar.make(this, message, length)
    snack.setBackgroundTint(resources.getColor(R.color.bg_main))
    snack.anchorView?.parent
    snack.show()
}

inline fun View.snack(
    @StringRes messageRes: Int,
    @SuppressLint("Range") length: Int = Snackbar.LENGTH_LONG,
    f: Snackbar.() -> Unit
) {
    val snack = Snackbar.make(this, messageRes, length)
    snack.f()
    snack.show()
}

@SuppressLint("ResourceType")
fun Snackbar.action(@IntegerRes actionRes: Int, color: Int? = null, listener: (View) -> Unit) {
    action(view.resources.getString(actionRes), color, listener)
}

fun Snackbar.action(action: String, color: Int? = null, listener: (View) -> Unit) {
    setAction(action, listener)
    color?.let { setActionTextColor(ContextCompat.getColor(context, color)) }
}