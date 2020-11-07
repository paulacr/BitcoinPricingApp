package com.paulacr.bitcoinpricingapp.di

import android.app.Application
import com.paulacr.bitcoinpricingapp.MainActivity
import com.paulacr.data.network.NetworkModule
import com.paulacr.data.repository.RepositoryModule
import dagger.BindsInstance
import dagger.Component

@Component(modules = [NetworkModule::class, AppModule::class, RepositoryModule::class])
interface ApplicationComponent {

    fun inject(activity: MainActivity)

    @Component.Builder
    interface Builder {
        fun build(): ApplicationComponent

        @BindsInstance
        fun application(application: Application): Builder
    }
}