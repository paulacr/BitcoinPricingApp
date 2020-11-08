package com.paulacr.data.repository

import com.paulacr.data.common.logError
import com.paulacr.data.common.setDefaultValue
import com.paulacr.data.mapper.BitcoinPricingMapper
import com.paulacr.data.network.ApiService
import com.paulacr.domain.Price
import io.reactivex.Single
import javax.inject.Inject

private const val DEFAULT_PRICING_TIME_INTERVAL: String = "5weeks"
private const val DEFAULT_ROLLING_AVERAGE_INTERVAL = "8hours"

class BitcoinPriceRepositoryImpl @Inject constructor(
    private val apiService: ApiService,
    private val cache: BitcoinPriceListCache,
    private val mapper: BitcoinPricingMapper
) : BitcoinPriceRepository {

    override fun getLocalBitcoinPrice(): Single<List<Price>> = Single.just(cache.getData())

    override fun getRemoteBitcoinPrice(
        timeInterval: String?,
        rollingAverage: String?
    ): Single<List<Price>> =

        apiService.getBitcoinPricing(
            timeInterval?.setDefaultValue(DEFAULT_PRICING_TIME_INTERVAL),
            rollingAverage?.setDefaultValue(DEFAULT_ROLLING_AVERAGE_INTERVAL)
        ).doOnError {
            logError("Api error", it)
        }.flatMap {
            val pricesMapper = mapper.map(it)
            Single.just(pricesMapper.prices)
        }.doOnSuccess {
            saveBitcoinPriceInCache(it)
        }

    override fun saveBitcoinPriceInCache(prices: List<Price>) {
        cache.saveData(prices)
    }
}