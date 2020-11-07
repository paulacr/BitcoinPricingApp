package com.paulacr.data.common

import android.util.Log

fun Any.logError(tag: String = "", throwable: Throwable) {
    val message = if (tag.isEmpty()) "Exception -> "
    else tag

    Log.e(message, throwable.localizedMessage ?: throwable.message ?: "")
}

fun String.setDefaultValue(defaultValue: String): String {
    if (isEmpty()) replace("", defaultValue)

    return this
}