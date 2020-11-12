package com.paulacr.data.common

import java.time.Instant
import java.time.LocalDateTime
import java.time.ZoneId
import java.time.format.DateTimeFormatter

private const val DATE_FORMAT_PATTERN = "yyyy-MM-dd"
private const val TIME_FORMAT_PATTERN = "HH:mm:ss"

object DateConverter {

    fun Long.getFormattedDateTime(): Pair<String, String> {
        val currentDateTime =
            LocalDateTime.ofInstant(Instant.ofEpochSecond(this), ZoneId.systemDefault())

        return getFormattedDate(currentDateTime) to getFormattedTime(currentDateTime)
    }

    private fun getFormattedDate(localDateTime: LocalDateTime): String {
        val dateFormatter = DateTimeFormatter.ofPattern(DATE_FORMAT_PATTERN)
        return localDateTime.format(dateFormatter)
    }

    private fun getFormattedTime(localDateTime: LocalDateTime): String {
        val dateFormatter = DateTimeFormatter.ofPattern(TIME_FORMAT_PATTERN)
        return localDateTime.format(dateFormatter)
    }

    fun getTimeNowFormatted() = getFormattedTime(LocalDateTime.now())

}

