package com.paulacr.graph.di

import com.paulacr.graph.GraphBuilder
import dagger.Module
import dagger.Provides

@Module
class GraphModule {
    @Provides
    fun provideGraphModule(): GraphBuilder {
        return GraphBuilder()
    }
}
