package com.paulacr.data

class BitcoinPricingInteractor {

    fun getData() = LocalBitcoinPricingRepository().getData()

    fun getChartData() = RemoteBitcoinPricingRepository().getChartData()
}