package com.paulacr.data.mapper

import com.paulacr.domain.BitcoinPricing
import com.paulacr.domain.Pricing

object BitcoinPricingMapper {

    fun map(bitcoinPricing: BitcoinPricing) = Pricing(
        name = bitcoinPricing.name,
        period = bitcoinPricing.period,
        description = bitcoinPricing.description,
        coordinatesValues = bitcoinPricing.coordinatesValues
    )
}