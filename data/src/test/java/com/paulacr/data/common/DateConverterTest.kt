package com.paulacr.data.common

import com.paulacr.data.common.DateConverter.getFormattedDateTime
import org.junit.Assert.assertEquals
import org.junit.Test

class DateConverterTest {

    @Test
    fun shouldConvertMillisecondsInFormattedDate() {

        val dateInMillis = 1601741700.toLong()
        val formattedDate = dateInMillis.getFormattedDateTime()

        assertEquals(formattedDate.first, "2020-10-03")
    }
}