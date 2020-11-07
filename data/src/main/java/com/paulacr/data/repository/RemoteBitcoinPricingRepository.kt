package com.paulacr.data.repository

import com.paulacr.domain.BitcoinPrice
import com.paulacr.domain.Price
import io.reactivex.Single

interface RemoteBitcoinPricingRepository {

    fun getBitcoinPricing(timeInterval: String? = null, rollingAverage: String? = null): Single<List<Price>>
}