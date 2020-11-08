package com.paulacr.data.repository

import com.paulacr.domain.Price
import io.reactivex.Single

interface BitcoinPriceRepository {

    fun getLocalBitcoinPrice(): Single<List<Price>>

    fun getRemoteBitcoinPrice(timeInterval: String? = null, rollingAverage: String? = null): Single<List<Price>>
}