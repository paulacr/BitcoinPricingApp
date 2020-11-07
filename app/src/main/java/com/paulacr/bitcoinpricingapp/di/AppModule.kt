package com.paulacr.bitcoinpricingapp.di

import com.paulacr.bitcoinpricingapp.GraphViewModel
import com.paulacr.data.repository.BitcoinPricingInteractor
import dagger.Module
import dagger.Provides

@Module
class AppModule {

    @Provides
    fun provideGraphViewModel(interactor: BitcoinPricingInteractor) = GraphViewModel(interactor)
}