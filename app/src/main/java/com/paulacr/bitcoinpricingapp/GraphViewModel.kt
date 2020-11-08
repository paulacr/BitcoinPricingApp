package com.paulacr.bitcoinpricingapp

import androidx.lifecycle.MutableLiveData
import com.github.mikephil.charting.data.LineData
import com.paulacr.bitcoinpricingapp.viewstate.ViewState
import com.paulacr.data.common.logError
import com.paulacr.data.usecase.BitcoinPricingUseCase
import com.paulacr.graph.GraphBuilder
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import java.util.concurrent.TimeUnit
import javax.inject.Inject

private const val REFRESH_UPDATE = 20L

class GraphViewModel @Inject constructor(
    private val pricingUseCase: BitcoinPricingUseCase,
    private val graphBuilder: GraphBuilder
) : BaseViewModel() {

    val graphLiveData = MutableLiveData<ViewState<LineData>>()

    fun fetchBitcoinPricing() = pricingUseCase.fetchBitcoinPricing()
        .doOnSubscribe {
            graphLiveData.postValue(ViewState.Loading())
        }.retryWhen {
            it.delay(REFRESH_UPDATE, TimeUnit.SECONDS)
        }.subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())
        .repeatWhen { completed ->
            completed.delay(REFRESH_UPDATE, TimeUnit.SECONDS)
        }.subscribe({
            val graphData = graphBuilder.createGraph(it)
            graphLiveData.postValue(ViewState.Success(graphData))
        }, {
            graphLiveData.postValue(ViewState.Failure(it))
            logError("Log error", it)
        }).addToDisposables()
}
