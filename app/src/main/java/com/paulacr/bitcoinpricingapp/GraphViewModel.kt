package com.paulacr.bitcoinpricingapp

import android.util.Log
import com.paulacr.data.repository.BitcoinPricingInteractor
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class GraphViewModel @Inject constructor(private val interactor: BitcoinPricingInteractor) {

    fun getBitcoinPricing() = interactor.getBitcoinPricing()
        .subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())
        .subscribe({
            Log.i("Log modules", "getChartData() -> $it")
        }, {
            Log.e("Log modules", "error -> $it")
        })
}
