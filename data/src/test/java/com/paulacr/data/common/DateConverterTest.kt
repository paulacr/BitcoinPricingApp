package com.paulacr.data.common

import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

class DateConverterTest {

    @Before
    fun onStart() {

    }

    @Test
    fun shouldConvertMillisecondsInFormattedDate() {

        val dateInMillis = 1601741700.toLong()
        val formattedDate = dateInMillis.getFormattedDateTime()

        assertEquals(formattedDate.first, "2020-10-03")
    }
}