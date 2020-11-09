package com.paulacr.bitcoinpricingapp

import android.graphics.Color
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.components.YAxis
import com.paulacr.bitcoinpricingapp.databinding.ActivityMainBinding
import com.paulacr.bitcoinpricingapp.viewstate.ViewState
import com.paulacr.data.common.getTimeNowFormatted
import com.paulacr.data.common.isVisible
import com.paulacr.data.common.setVisibility
import com.paulacr.data.common.setVisible
import com.paulacr.domain.Price
import com.paulacr.graph.DateAxisFormatter
import com.paulacr.graph.GraphBuilder
import javax.inject.Inject

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    @Inject
    lateinit var viewModel: GraphViewModel

    @Inject
    lateinit var graphBuilder: GraphBuilder

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        injectDependencies()
        setupObservables()
        setupRetryClickListener()
    }

    override fun onResume() {
        super.onResume()
        viewModel.fetchBitcoinPricing()
    }

    override fun onPause() {
        super.onPause()
        viewModel.stopFetchingData()
    }

    private fun setupObservables() {
        viewModel.graphLiveData.observe(this, { viewState ->
            handleViewsVisibility(viewState).also {
                if (viewState is ViewState.Success) {
                    updateGraphData(viewState.data)
                    setLastBitcoinUpdateText()
                }
            }
        })
    }

    private fun setLastBitcoinUpdateText() {
        binding.viewGraphContainer.lastUpdateText
            .apply {
                if (!this.isVisible()) {
                    this.setVisible()
                }
                this.text = getLastUpdatedDateTime()
            }
    }

    private fun getLastUpdatedDateTime(): String {
        val lastUpdateDateTime = getTimeNowFormatted()
        return ("last update: ").plus(lastUpdateDateTime)
    }

    private fun setupRetryClickListener() {
        binding.viewErrorContainer.retryButton.setOnClickListener {
            viewModel.fetchBitcoinPricing()
            it.isEnabled = false
        }
    }

    private fun updateGraphData(prices: List<Price>) {
        binding.viewGraphContainer.graphView.data = graphBuilder.createGraph(prices)
        setAxisProperties()
        if (binding.viewGraphContainer.graphView.isVisible()) binding.viewGraphContainer.graphView.invalidate()
    }

    private fun setAxisProperties() {
        val graphView = binding.viewGraphContainer.graphView
        val xAxis = graphView.xAxis
        xAxis.position = XAxis.XAxisPosition.BOTTOM
        xAxis.labelRotationAngle = 0f
        xAxis.axisLineWidth = 3f
        xAxis.textSize = 13f
        xAxis.textColor = Color.BLUE
        xAxis.valueFormatter = DateAxisFormatter()

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

    private fun handleViewsVisibility(viewState: ViewState<List<Price>>) {
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
        binding.viewErrorContainer.retryButton.isEnabled = errorViewVisibility
    }
}
