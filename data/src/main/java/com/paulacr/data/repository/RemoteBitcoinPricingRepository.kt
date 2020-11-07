package com.paulacr.data.repository

import com.paulacr.domain.BitcoinPricing
import io.reactivex.Single

interface RemoteBitcoinPricingRepository {

    fun getBitcoinPricing(): Single<BitcoinPricing>
}