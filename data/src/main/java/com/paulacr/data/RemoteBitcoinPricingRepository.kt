package com.paulacr.data

import com.paulacr.data.network.ApiService
import com.paulacr.data.network.Network
import com.paulacr.domain.BitcoinPricing
import io.reactivex.Single

class RemoteBitcoinPricingRepository {

    private val apiService: ApiService = Network().getService()

    fun getBitcoinPricing(): Single<BitcoinPricing> = apiService.getBitcoinPricing(
        timeInterval = "5weeks",
        rollingAverage = "8hours"
    )
}