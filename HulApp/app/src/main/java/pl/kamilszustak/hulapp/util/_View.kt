package pl.kamilszustak.hulapp.util

import android.content.res.ColorStateList
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.CompoundButton
import pl.kamilszustak.hulapp.util.getSystemService

fun View.showKeyboard() {
    requestFocus()
    val inputMethodManager = context.getSystemService<InputMethodManager>()
    inputMethodManager.showSoftInput(this, InputMethodManager.SHOW_IMPLICIT)
}

fun View.hideKeyboard() {
    val inputMethodManager = context.getSystemService<InputMethodManager>()
    inputMethodManager.hideSoftInputFromWindow(windowToken, 0)
}

fun View.setVisible() {
    visibility = View.VISIBLE
}

fun View.setGone() {
    visibility = View.GONE
}

fun CompoundButton.setTintListByColor(color: Int) {
    buttonTintList = ColorStateList.valueOf(color)
}