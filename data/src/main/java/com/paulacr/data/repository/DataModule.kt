package com.paulacr.data.repository

import com.paulacr.data.mapper.BitcoinPricingMapper
import com.paulacr.data.network.ApiService
import com.paulacr.data.usecase.BitcoinPricingUseCase
import dagger.Module
import dagger.Provides

@Module
class DataModule {

    @Provides
    fun provideBitcoinPriceUseCase(repository: BitcoinPriceRepository) =
        BitcoinPricingUseCase(repository)

    @Provides
    fun provideRemoteBitcoinPriceRepository(apiService: ApiService, cacheData: CacheBitcoinPrice, mapper: BitcoinPricingMapper): BitcoinPriceRepository =
        BitcoinPriceRepositoryImpl(apiService, cacheData, mapper)

    @Provides
    fun provideBitcoinMapper() = BitcoinPricingMapper

    @Provides
    fun provideBitcoinPriceCache() = CacheBitcoinPrice()
}