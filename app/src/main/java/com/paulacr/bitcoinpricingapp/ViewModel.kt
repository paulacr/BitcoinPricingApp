package com.paulacr.bitcoinpricingapp

import com.paulacr.data.RepositoryInteractor

class ViewModel {

    fun getDataTest() = RepositoryInteractor().getData()
}
