package com.paulacr.bitcoinpricingapp.di

import com.paulacr.bitcoinpricingapp.GraphViewModel
import com.paulacr.data.usecase.BitcoinPricingUseCase
import com.paulacr.graph.GraphBuilder
import dagger.Module
import dagger.Provides

@Module
class AppModule {

    @Provides
    fun provideGraphViewModel(pricingUseCase: BitcoinPricingUseCase, graphBuilder: GraphBuilder) = GraphViewModel(pricingUseCase, graphBuilder)
}
