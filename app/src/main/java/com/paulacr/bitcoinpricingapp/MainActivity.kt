package com.paulacr.bitcoinpricingapp

import android.graphics.Color
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.components.YAxis
import com.github.mikephil.charting.data.LineData
import com.paulacr.bitcoinpricingapp.viewstate.ViewState
import com.paulacr.graph.DateAxisFormatter
import javax.inject.Inject
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    @Inject
    lateinit var viewModel: GraphViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        injectDependencies()
        setupObservables()
        viewModel.fetchBitcoinPricing()
    }

    private fun setupObservables() {
        viewModel.graphLiveData.observe(this, Observer {
            when (it) {
                is ViewState.Loading -> {
                }
                is ViewState.Success -> {
                    plotGraphData(it.data)
                }
                else -> {
                }
            }
        })
    }

    private fun plotGraphData(lineData: LineData) {
        chart.data = lineData
        chart.invalidate()
        setAxisProperties()
    }

    private fun setAxisProperties() {
        val xAxis = chart.xAxis
        xAxis.position = XAxis.XAxisPosition.BOTTOM
        xAxis.labelRotationAngle = 0f
        xAxis.axisLineWidth = 3f
        xAxis.textSize = 13f
        xAxis.textColor = Color.BLUE
        xAxis.valueFormatter = DateAxisFormatter(chart)

        val leftAxis: YAxis = chart.axisLeft
        leftAxis.removeAllLimitLines()
        leftAxis.axisLineWidth = 3f
        leftAxis.setDrawAxisLine(false)
        leftAxis.setDrawGridLines(true)
        leftAxis.textSize = 13f
        leftAxis.granularity = 1f
        leftAxis.textColor = Color.BLUE
        leftAxis.setDrawZeroLine(false)
        leftAxis.setDrawLimitLinesBehindData(false)
    }

    private fun injectDependencies() {
        (applicationContext as BitCoinPricingApplication).appComponent.inject(this)
    }
}
