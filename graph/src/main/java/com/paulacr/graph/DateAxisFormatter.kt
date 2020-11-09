package com.paulacr.graph

import android.text.format.DateFormat
import com.github.mikephil.charting.charts.LineChart
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter

class DateAxisFormatter: IndexAxisValueFormatter() {

    override fun getFormattedValue(value: Float): String {
        val formatResult = "MMM"
        return DateFormat.format(formatResult, value.toLong() * 1000) as String
    }


}