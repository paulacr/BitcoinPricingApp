package com.paulacr.data.repository

import com.paulacr.domain.Pricing
import io.reactivex.Single

interface RemoteBitcoinPricingRepository {

    fun getBitcoinPricing(): Single<Pricing>
}