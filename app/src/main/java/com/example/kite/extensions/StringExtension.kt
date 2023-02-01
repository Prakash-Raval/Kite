package com.example.kite.extensions

import android.view.View

fun View.getString(stringResId: Int): String = resources.getString(stringResId)