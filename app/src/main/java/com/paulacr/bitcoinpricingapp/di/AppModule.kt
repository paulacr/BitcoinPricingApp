package com.paulacr.bitcoinpricingapp.di

import com.paulacr.bitcoinpricingapp.ViewModel
import dagger.Module
import dagger.Provides

@Module
class AppModule {

    @Provides
    fun provideGraphViewModel() = ViewModel()
}