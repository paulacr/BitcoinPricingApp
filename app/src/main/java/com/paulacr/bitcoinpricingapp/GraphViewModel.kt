package com.paulacr.bitcoinpricingapp

import androidx.lifecycle.MutableLiveData
import com.github.mikephil.charting.data.LineData
import com.paulacr.bitcoinpricingapp.viewstate.ViewState
import com.paulacr.data.common.logError
import com.paulacr.data.usecase.BitcoinPricingUseCase
import com.paulacr.graph.GraphBuilder
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

private const val REFRESH_UPDATE = 20L

class GraphViewModel @Inject constructor(
    private val pricingUseCase: BitcoinPricingUseCase,
    private val graphBuilder: GraphBuilder
) : BaseViewModel() {

    val graphLiveData = MutableLiveData<ViewState<LineData>>()

    // Populate view initially with local data
    fun fetchBitcoinPricing() {
        graphLiveData.postValue(ViewState.Loading())
        pricingUseCase.fetchBitcoinPrice()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                if (it.isNotEmpty()) graphLiveData.postValue(ViewState.Success(graphBuilder.createGraph(it)))
            }, {
                logError("Log local data error", it)
            })
            .addToDisposables()
    }
//        pricingUseCase.getLocalBitcoinPrice()
//            .subscribeOn(Schedulers.io())
//            .observeOn(AndroidSchedulers.mainThread())
//            .subscribe({
//                if (it.isNotEmpty()) {
//                    val graphData = graphBuilder.createGraph(it)
//                    graphLiveData.postValue(ViewState.Success(graphData))
//                }
//                fetchRemoteBitcoinPrice()
//            }, {
//                logError("Log local data error", it)
//            })
//            .addToDisposables()
//    }
//
//    // Add timer for rescheduling data each Refresh update interval
//    private fun fetchRemoteBitcoinPrice() {
//        pricingUseCase.fetchBitcoinPrice()
//            .retryWhen {
//                it.delay(REFRESH_UPDATE, TimeUnit.SECONDS)
//            }.subscribeOn(Schedulers.io())
//            .observeOn(AndroidSchedulers.mainThread())
//            .repeatWhen { completed ->
//                completed.delay(REFRESH_UPDATE, TimeUnit.SECONDS)
//            }.subscribe({
//                graphLiveData.postValue(ViewState.Success(graphBuilder.createGraph(it)))
//            }, {
//                graphLiveData.postValue(ViewState.Failure(it))
//                logError("Log error", it)
//            }).addToDisposables()
//    }

    fun stopFetchingData() {
        super.onCleared()
    }
}
