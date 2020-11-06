package com.paulacr.bitcoinpricingapp

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val viewModel = ViewModel()
        Log.i("Log modules", "getData() -> ${viewModel.getDataTest()}}")
        Log.i("Log modules", "getChartData() -> ${viewModel.getChartData()}}")
    }
}
