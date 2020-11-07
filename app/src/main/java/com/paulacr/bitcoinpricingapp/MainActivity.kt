package com.paulacr.bitcoinpricingapp

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import javax.inject.Inject

class MainActivity : AppCompatActivity() {

    @Inject
    lateinit var viewModel: GraphViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        injectDependencies()

        Log.i("Log modules", "getData() -> ${viewModel.fetchBitcoinPricing()}}")
    }

    private fun injectDependencies() {
        (applicationContext as BitCoinPricingApplication).appComponent.inject(this)
    }
}
