package com.paulacr.data

class RepositoryInteractor {

    fun getData() = LocalPricingRepository().getData()
}