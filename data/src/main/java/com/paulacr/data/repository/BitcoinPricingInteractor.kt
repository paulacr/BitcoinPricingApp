package com.paulacr.data.repository

import com.paulacr.domain.Pricing
import io.reactivex.Single

interface BitcoinPricingInteractor {

    fun getBitcoinPricing(): Single<Pricing>
}