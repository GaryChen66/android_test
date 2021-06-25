package co.metalab.tech.interview.common

import android.app.Activity
import android.graphics.Typeface
import android.text.Spannable
import android.text.SpannableStringBuilder
import android.text.style.StyleSpan
import android.view.inputmethod.InputMethodManager
import androidx.core.content.ContextCompat

fun Int?.prettyPrintId(): String = "#${String.format("%03d", this)}"

fun Activity.hideKeyboard() {
    val view = this.window?.currentFocus
    if (view != null) {
        val imm = ContextCompat.getSystemService(this, InputMethodManager::class.java)
        imm?.hideSoftInputFromWindow(view.windowToken, 0)
    }
}
