package com.paulacr.data.repository

import com.paulacr.data.mapper.BitcoinPricingMapper
import com.paulacr.data.network.ApiService
import com.paulacr.data.usecase.BitcoinPricingUseCase
import com.paulacr.data.usecase.BitcoinPricingUseCaseImpl
import dagger.Module
import dagger.Provides

@Module
class DataModule {

    @Provides
    fun provideBitcoinPriceUseCase(repository: BitcoinPriceRepository): BitcoinPricingUseCase =
        BitcoinPricingUseCaseImpl(repository)

    @Provides
    fun provideRemoteBitcoinPriceRepository(apiService: ApiService, cacheData: BitcoinPriceListCache, mapper: BitcoinPricingMapper): BitcoinPriceRepository =
        BitcoinPriceRepositoryImpl(apiService, cacheData, mapper)

    @Provides
    fun provideBitcoinMapper() = BitcoinPricingMapper()

    @Provides
    fun provideBitcoinPriceCache(): BitcoinPriceListCache = BitcoinPriceListCacheImpl()
}