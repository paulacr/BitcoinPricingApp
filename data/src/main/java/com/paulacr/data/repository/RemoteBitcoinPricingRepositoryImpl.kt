package com.paulacr.data.repository

import com.paulacr.data.mapper.BitcoinPricingMapper
import com.paulacr.data.network.ApiService
import com.paulacr.domain.Pricing
import io.reactivex.Single
import javax.inject.Inject

class RemoteBitcoinPricingRepositoryImpl @Inject constructor(private val apiService: ApiService, private val mapper: BitcoinPricingMapper) : RemoteBitcoinPricingRepository {

    override fun getBitcoinPricing(): Single<Pricing> = apiService.getBitcoinPricing(
        timeInterval = "5weeks",
        rollingAverage = "8hours"
    ).flatMap {
        val pricing = mapper.map(it)
        Single.just(pricing)
    }
}