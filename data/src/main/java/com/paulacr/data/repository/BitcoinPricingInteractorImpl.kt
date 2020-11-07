package com.paulacr.data.repository

import com.paulacr.domain.BitcoinPricing
import io.reactivex.Single
import javax.inject.Inject

class BitcoinPricingInteractorImpl @Inject constructor(private val repository: RemoteBitcoinPricingRepository) :
    BitcoinPricingInteractor {

    override fun getBitcoinPricing(): Single<BitcoinPricing> = repository.getBitcoinPricing()

}