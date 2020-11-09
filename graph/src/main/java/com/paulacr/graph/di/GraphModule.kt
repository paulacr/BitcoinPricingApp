package com.paulacr.graph.di

import com.paulacr.graph.GraphBuilder
import com.paulacr.graph.GraphBuilderInterface
import dagger.Module
import dagger.Provides

@Module
class GraphModule {
    @Provides
    fun provideGraphModule(): GraphBuilderInterface {
        return GraphBuilder()
    }
}
