package com.paulacr.data.repository

import com.paulacr.data.network.ApiService
import com.paulacr.domain.BitcoinPricing
import io.reactivex.Single
import javax.inject.Inject

class RemoteBitcoinPricingRepositoryImpl @Inject constructor(private val apiService: ApiService) : RemoteBitcoinPricingRepository {

    override fun getBitcoinPricing(): Single<BitcoinPricing> = apiService.getBitcoinPricing(
        timeInterval = "5weeks",
        rollingAverage = "8hours"
    )
}