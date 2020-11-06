package com.paulacr.data

import com.paulacr.domain.PricingUseCase

internal class LocalPricingRepository {

    fun getData() = PricingUseCase().getPricing()
}