package com.paulacr.bitcoinpricingapp

import androidx.lifecycle.MutableLiveData
import com.paulacr.bitcoinpricingapp.viewstate.ViewState
import com.paulacr.data.common.logError
import com.paulacr.data.usecase.BitcoinPricingUseCase
import com.paulacr.domain.Price
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class GraphViewModel @Inject constructor(
    private val pricingUseCase: BitcoinPricingUseCase
) : BaseViewModel() {

    var graphLiveData = MutableLiveData<ViewState<List<Price>>>()

    // Populate view initially with local data
    fun fetchBitcoinPricing() {
        graphLiveData.postValue(ViewState.Loading())
        pricingUseCase.fetchBitcoinPrice()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                if (it.isNotEmpty()) graphLiveData.postValue(ViewState.Success(it))
                else graphLiveData.postValue(ViewState.Failure(Exception("no data to display")))
            }, {
                logError("Log local data error", it)
            })
            .addToDisposables()
    }

    fun stopFetchingData() {
        super.onCleared()
    }
}
