package com.paulacr.bitcoinpricingapp

import android.util.Log
import com.paulacr.data.RepositoryInteractor
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class ViewModel {

    private val repositoryInteractor = RepositoryInteractor()

    fun getDataTest() = repositoryInteractor.getData()

    fun getChartData() = RepositoryInteractor().getChartData()
        .subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())
        .subscribe({
            Log.i("Log modules", "getChartData() -> $it")
        }, {
            Log.e("Log modules", "error -> $it")
        })
}
