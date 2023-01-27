package com.example.kite.extensions

import android.view.View

/**
 * Show the view  (visibility = View.VISIBLE)
 */
fun View.show(): View {
    if (visibility != View.VISIBLE) {
        visibility = View.VISIBLE
    }
    return this
}


/*inline fun View.showIf(condition: () -> Boolean): View {
    if (visibility != View.VISIBLE && block()) {
        visibility = View.VISIBLE
    }
    return this
}*/

fun View.hide(): View {
    if (visibility != View.GONE) {
        visibility = View.GONE
    }
    return this
}

/*inline fun View.hideIf(predicate: () -> Boolean): View {
    if (visibility != View.GONE && block()) {
        visibility = View.GONE
    }
    return this
}*/
//how to use
/*
* buttonSubmit.hide()
buttonSubmit.show()
buttonSubmit.showIf {
    editTextName.text != null
}
buttonSubmit.hideIf {
    editTextName.text == null
}*/