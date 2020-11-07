package com.paulacr.data.usecase

import com.paulacr.data.repository.RemoteBitcoinPricingRepository

class BitcoinPricingUseCase(private val repository: RemoteBitcoinPricingRepository) {

    fun fetchBitcoinPricing() = repository.getBitcoinPrice()
}