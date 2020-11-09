package com.paulacr.data.repository

import com.paulacr.data.mapper.BitcoinPricingMapper
import com.paulacr.data.network.ApiService
import com.paulacr.domain.Price
import io.reactivex.Flowable
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import java.util.concurrent.TimeUnit
import javax.inject.Inject

private const val REFRESH_UPDATE = 20L

class BitcoinPriceRepositoryImpl @Inject constructor(
    private val apiService: ApiService,
    private val cache: BitcoinPriceListCache,
    private val mapper: BitcoinPricingMapper
) : BitcoinPriceRepository {

    override fun fetchBitcoinPrice(): Flowable<List<Price>> {
        return getRemoteBitcoinPrice().startWith(cache.getData())
    }

    override fun getLocalBitcoinPrice(): List<Price>? {
        return cache.getData()
    }

    override fun getRemoteBitcoinPrice(
        timeInterval: String?,
        rollingAverage: String?
    ): Flowable<List<Price>> =

        apiService.getBitcoinPricing()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .retryWhen {
                it.delay(REFRESH_UPDATE, TimeUnit.SECONDS)
            }.flatMap {
                val pricesMapper = mapper.map(it)
                saveBitcoinPriceInCache(pricesMapper.prices)
                Single.just(pricesMapper.prices)
            }.repeatWhen { completed ->
                completed.delay(REFRESH_UPDATE, TimeUnit.SECONDS)
            }

    override fun saveBitcoinPriceInCache(prices: List<Price>) {
        cache.saveData(prices)
    }
}