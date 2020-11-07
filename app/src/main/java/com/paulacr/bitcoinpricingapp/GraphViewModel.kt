package com.paulacr.bitcoinpricingapp

import android.util.Log
import com.paulacr.data.common.logError
import com.paulacr.data.usecase.BitcoinPricingUseCase
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class GraphViewModel @Inject constructor(private val pricingUseCase: BitcoinPricingUseCase) {

    fun fetchBitcoinPricing() = pricingUseCase.fetchBitcoinPricing()
        .subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())
        .subscribe({
            Log.i("Log modules", "getChartData() -> $it")
        }, {
            logError("Log error", it)
        })
}
