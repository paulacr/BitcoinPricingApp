package com.paulacr.data.repository

import com.paulacr.data.network.ApiService
import dagger.Module
import dagger.Provides

@Module
class RepositoryModule {

    @Provides
    fun provideBitcoinPricingInteractor(remoteRepository: RemoteBitcoinPricingRepository): BitcoinPricingInteractor =
        BitcoinPricingInteractorImpl(remoteRepository)

    @Provides
    fun provideBitcoinPrincingRepository(apiService: ApiService): RemoteBitcoinPricingRepository =
        RemoteBitcoinPricingRepositoryImpl(apiService)
}