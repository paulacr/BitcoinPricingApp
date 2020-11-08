package com.paulacr.bitcoinpricingapp

import android.graphics.Color
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.components.YAxis
import com.github.mikephil.charting.data.LineData
import com.paulacr.bitcoinpricingapp.viewstate.ViewState
import com.paulacr.data.common.setGone
import com.paulacr.data.common.setVisible
import com.paulacr.graph.DateAxisFormatter
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.view_error.*
import kotlinx.android.synthetic.main.view_loading.*
import javax.inject.Inject

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
        viewModel.graphLiveData.observe(this, {
            handleViewsVisibility(it)
        })
    }

    private fun plotGraphData(lineData: LineData) {
        graphView.data = lineData
        graphView.invalidate()
        setAxisProperties()
    }

    private fun setAxisProperties() {
        val xAxis = graphView.xAxis
        xAxis.position = XAxis.XAxisPosition.BOTTOM
        xAxis.labelRotationAngle = 0f
        xAxis.axisLineWidth = 3f
        xAxis.textSize = 13f
        xAxis.textColor = Color.BLUE
        xAxis.valueFormatter = DateAxisFormatter(graphView)

        val leftAxis: YAxis = graphView.axisLeft
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

    private fun handleViewsVisibility(viewState: ViewState<LineData>) {
        when (viewState) {
            is ViewState.Loading -> {
                loadingView.setVisible()
                graphView.setGone()
                errorView.setGone()
            }
            is ViewState.Success -> {
                loadingView.setGone()
                graphView.setVisible()
                errorView.setGone()
                plotGraphData(viewState.data)
            }
            else -> {
                errorView.setVisible()
                loadingView.setGone()
                graphView.setGone()

            }
        }
    }
}
