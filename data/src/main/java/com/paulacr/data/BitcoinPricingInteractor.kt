package com.paulacr.data

interface BitcoinPricingInteractor {

    fun getGraphData() = RemoteBitcoinPricingRepository().getBitcoinPricing()
}