package com.paulacr.bitcoinpricingapp

import android.app.Application
import com.paulacr.bitcoinpricingapp.di.DaggerApplicationComponent

class BitCoinPricingApplication : Application() {

    val appComponent = DaggerApplicationComponent
        .builder()
        .application(this)
        .build()
}