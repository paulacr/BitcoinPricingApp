package com.paulacr.bitcoinpricingapp

import android.graphics.Color
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.components.YAxis
import com.github.mikephil.charting.data.LineData
import com.paulacr.bitcoinpricingapp.databinding.ActivityMainBinding
import com.paulacr.bitcoinpricingapp.viewstate.ViewState
import com.paulacr.data.common.setVisibility
import com.paulacr.graph.DateAxisFormatter
import javax.inject.Inject

class MainActivity : AppCompatActivity() {

    lateinit var binding: ActivityMainBinding

    @Inject
    lateinit var viewModel: GraphViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

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
        binding.viewGraphContainer.graphView.data = lineData
        binding.viewGraphContainer.graphView.invalidate()
        setAxisProperties()
    }

    private fun setAxisProperties() {
        val graphView = binding.viewGraphContainer.graphView
        val xAxis = graphView.xAxis
        xAxis.position = XAxis.XAxisPosition.BOTTOM
        xAxis.labelRotationAngle = 0f
        xAxis.axisLineWidth = 3f
        xAxis.textSize = 13f
        xAxis.textColor = Color.BLUE
        xAxis.valueFormatter = DateAxisFormatter(binding.viewGraphContainer.graphView)

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
        val loadingViewVisibility: Boolean
        val graphViewVisibility: Boolean
        val errorViewVisibility: Boolean

        when (viewState) {

            is ViewState.Loading -> {
                loadingViewVisibility = true
                graphViewVisibility = false
                errorViewVisibility = false
            }
            is ViewState.Success -> {
                loadingViewVisibility = false
                graphViewVisibility = true
                errorViewVisibility = false
                plotGraphData(viewState.data)
            }
            else -> {
                loadingViewVisibility = false
                graphViewVisibility = false
                errorViewVisibility = true
            }
        }

        binding.viewErrorContainer.errorView.setVisibility(errorViewVisibility)
        binding.viewLoadingContainer.loadingView.setVisibility(loadingViewVisibility)
        binding.viewGraphContainer.graphContainer.setVisibility(graphViewVisibility)
    }
}
