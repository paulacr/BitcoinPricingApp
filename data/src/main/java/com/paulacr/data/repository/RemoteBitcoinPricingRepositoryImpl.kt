package com.paulacr.data.repository

import com.paulacr.data.common.logError
import com.paulacr.data.common.setDefaultValue
import com.paulacr.data.mapper.BitcoinPricingMapper
import com.paulacr.data.network.ApiService
import com.paulacr.domain.BitcoinPrice
import io.reactivex.Single
import javax.inject.Inject

private const val DEFAULT_PRICING_TIME_INTERVAL: String = "5weeks"
private const val DEFAULT_ROLLING_AVERAGE_INTERVAL = "8hours"

class RemoteBitcoinPricingRepositoryImpl @Inject constructor(
    private val apiService: ApiService,
    private val mapper: BitcoinPricingMapper
) : RemoteBitcoinPricingRepository {

    override fun getBitcoinPricing(timeInterval: String?, rollingAverage: String?): Single<BitcoinPrice> =
        apiService.getBitcoinPricing(
            timeInterval?.setDefaultValue(DEFAULT_PRICING_TIME_INTERVAL),
            rollingAverage?.setDefaultValue(DEFAULT_ROLLING_AVERAGE_INTERVAL)
        ).doOnError {
            logError("Api error", it)
        }.flatMap {
            val pricing = mapper.map(it)
            Single.just(pricing)
        }
}