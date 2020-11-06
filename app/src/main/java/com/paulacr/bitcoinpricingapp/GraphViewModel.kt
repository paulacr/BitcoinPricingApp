package com.paulacr.bitcoinpricingapp

import android.util.Log
import com.paulacr.data.BitcoinPricingInteractor
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class GraphViewModel {

    private val repositoryInteractor = BitcoinPricingInteractor()

    fun getDataTest() = repositoryInteractor.getData()

    fun getChartData() = BitcoinPricingInteractor().getChartData()
        .subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())
        .subscribe({
            Log.i("Log modules", "getChartData() -> $it")
        }, {
            Log.e("Log modules", "error -> $it")
        })
}
