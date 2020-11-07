package com.paulacr.data.repository

import com.paulacr.domain.BitcoinPrice
import io.reactivex.Single

interface RemoteBitcoinPricingRepository {

    fun getBitcoinPricing(timeInterval: String? = null, rollingAverage: String? = null): Single<BitcoinPrice>
}