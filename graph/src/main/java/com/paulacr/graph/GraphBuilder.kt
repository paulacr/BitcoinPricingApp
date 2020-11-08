package com.paulacr.graph

import android.graphics.Color
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.data.LineDataSet
import com.paulacr.domain.Price
import javax.inject.Inject

class GraphBuilder @Inject constructor() {

    fun createGraph(prices: List<Price>): LineData {

        val entries: MutableList<Entry> = mutableListOf()
        prices.forEachIndexed { index, price ->
            entries.add(Entry(price.dateTimeInMillis.toFloat(), price.price.toFloat()))
        }

        val lineDataSet = LineDataSet(entries, "Label")
        val lineData = LineData(lineDataSet)

        lineDataSet.setDrawIcons(false)
        lineDataSet.color = Color.BLACK
        lineDataSet.setCircleColor(Color.BLACK)
        lineDataSet.circleHoleRadius = 0.5f
        lineDataSet.circleRadius = 1f
        lineDataSet.lineWidth = 1f
        lineDataSet.setDrawFilled(false)
        lineDataSet.formLineWidth = 1f
        lineDataSet.formSize = 15f

        return lineData
    }
}