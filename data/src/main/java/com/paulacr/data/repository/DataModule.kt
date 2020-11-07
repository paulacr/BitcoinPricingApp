package com.paulacr.data.repository

import com.paulacr.data.mapper.BitcoinPricingMapper
import com.paulacr.data.network.ApiService
import com.paulacr.data.usecase.BitcoinPricingUseCase
import dagger.Module
import dagger.Provides

@Module
class DataModule {

    @Provides
    fun provideBitcoinPricingUseCase(remoteRepository: RemoteBitcoinPricingRepository) =
        BitcoinPricingUseCase(remoteRepository)

    @Provides
    fun provideBitcoinPrincingRepository(apiService: ApiService, mapper: BitcoinPricingMapper): RemoteBitcoinPricingRepository =
        RemoteBitcoinPricingRepositoryImpl(apiService, mapper)

    @Provides
    fun provideBitcoinMapper() = BitcoinPricingMapper
}