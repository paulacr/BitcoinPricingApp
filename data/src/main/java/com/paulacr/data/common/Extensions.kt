package com.paulacr.data.common

import android.util.Log
import android.view.View

fun Any.logError(tag: String = "", throwable: Throwable) {
    val message = if (tag.isEmpty()) "Exception -> "
    else tag

    Log.e(message, throwable.localizedMessage ?: throwable.message ?: "")
}

fun String.setDefaultValue(defaultValue: String): String {
    if (isEmpty()) replace("", defaultValue)

    return this
}

fun View.setVisible() {
    visibility = View.VISIBLE
}

fun View.setGone() {
    visibility = View.GONE
}

fun View.setVisibility(isVisible: Boolean) {
    visibility = if (isVisible) View.VISIBLE
    else View.GONE
}

fun View.isVisible(): Boolean {
    return visibility == View.VISIBLE
}