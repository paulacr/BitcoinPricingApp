package com.paulacr.data.usecase

import com.paulacr.data.repository.BitcoinPriceRepository

class BitcoinPricingUseCase(private val repository: BitcoinPriceRepository) {

    fun fetchBitcoinPrice() = repository.getRemoteBitcoinPrice()

    fun getLocalBitcoinPrice() = repository.getLocalBitcoinPrice()
}