package com.paulacr.data.usecase

import com.paulacr.data.repository.BitcoinPriceRepository
import com.paulacr.domain.Price
import io.reactivex.Flowable
import io.reactivex.Single
import javax.inject.Inject

interface BitcoinPricingUseCase {

    fun fetchBitcoinPrice(): Flowable<List<Price>>
}

class BitcoinPricingUseCaseImpl @Inject constructor(private val repository: BitcoinPriceRepository) :
    BitcoinPricingUseCase {

    override fun fetchBitcoinPrice() = repository.fetchBitcoinPrice()
}