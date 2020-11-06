package com.paulacr.bitcoinpricingapp.di

import com.paulacr.bitcoinpricingapp.GraphViewModel
import dagger.Module
import dagger.Provides

@Module
class AppModule {

    @Provides
    fun provideGraphViewModel() = GraphViewModel()
}