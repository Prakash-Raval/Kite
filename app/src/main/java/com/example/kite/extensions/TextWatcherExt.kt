package com.example.kite.utils

import android.text.Editable
import android.text.TextWatcher
import androidx.appcompat.widget.AppCompatEditText
import com.google.android.material.textfield.TextInputEditText

inline fun AppCompatEditText.onTextChanged(crossinline listener: (String) -> Unit) {
    this.addTextChangedListener(object : TextWatcher {
        override fun beforeTextChanged(charSequence: CharSequence?, p1: Int, p2: Int, p3: Int) {
        }

        override fun onTextChanged(charSequence: CharSequence?, p1: Int, p2: Int, p3: Int) {
            listener(charSequence.toString())
        }

        override fun afterTextChanged(p0: Editable?) {
        }

    })
}

inline fun AppCompatEditText.afterTextChanged(crossinline listener: (String) -> Unit) {
    this.addTextChangedListener(object : TextWatcher {
        override fun beforeTextChanged(charSequence: CharSequence?, p1: Int, p2: Int, p3: Int) {
        }

        override fun onTextChanged(charSequence: CharSequence?, p1: Int, p2: Int, p3: Int) {

        }

        override fun afterTextChanged(p0: Editable?) {
            listener(p0.toString())
        }

    })
}

inline fun TextInputEditText.onTextChanged(crossinline listener: (String) -> Unit) {
    this.addTextChangedListener(object : TextWatcher {
        override fun beforeTextChanged(charSequence: CharSequence?, p1: Int, p2: Int, p3: Int) {
        }

        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {

            listener(s.toString())

        }

        override fun afterTextChanged(p0: Editable?) {
        }

    })
}
