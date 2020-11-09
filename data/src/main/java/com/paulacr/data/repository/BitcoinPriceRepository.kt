package com.paulacr.data.repository

import com.paulacr.domain.Price
import io.reactivex.Flowable

interface BitcoinPriceRepository {

    fun fetchBitcoinPrice(): Flowable<List<Price>>

    fun getLocalBitcoinPrice(): List<Price>?

    fun getRemoteBitcoinPrice(timeInterval: String? = null, rollingAverage: String? = null): Flowable<List<Price>>

    fun saveBitcoinPriceInCache(prices: List<Price>)
}