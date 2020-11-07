package com.paulacr.graph

import android.text.format.DateFormat
import com.github.mikephil.charting.charts.LineChart
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter

class DateAxisFormatter(private val chart: LineChart): IndexAxisValueFormatter() {

    private val dayFormat = 24 * 60 * 60 * 1000

    override fun getFormattedValue(value: Float): String {
        val format = chart.xRange / dayFormat
        val formatResult = if (format > 30 * 6) {
            "MMM"
        } else {
            "MMM"
        }

        return DateFormat.format(formatResult, value.toLong()) as String
    }


}