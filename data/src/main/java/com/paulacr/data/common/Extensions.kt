package com.paulacr.data.common

import android.util.Log
import android.view.View

fun Any.logError(tag: String = "", throwable: Throwable) {
    val message = if (tag.isEmpty()) "Exception -> "
    else tag

    Log.e(message, throwable.localizedMessage ?: throwable.message ?: "")
}

fun View.setVisible() {
    visibility = View.VISIBLE
}

fun View.setVisibility(isVisible: Boolean) {
    visibility = if (isVisible) View.VISIBLE
    else View.GONE
}

fun View.isVisible(): Boolean {
    return visibility == View.VISIBLE
}