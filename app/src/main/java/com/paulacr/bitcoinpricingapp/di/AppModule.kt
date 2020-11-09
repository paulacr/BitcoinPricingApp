package com.paulacr.bitcoinpricingapp.di

import com.paulacr.bitcoinpricingapp.GraphViewModel
import com.paulacr.data.usecase.BitcoinPricingUseCase
import dagger.Module
import dagger.Provides

@Module
class AppModule {

    @Provides
    fun provideGraphViewModel(pricingUseCase: BitcoinPricingUseCase) = GraphViewModel(pricingUseCase)
}
