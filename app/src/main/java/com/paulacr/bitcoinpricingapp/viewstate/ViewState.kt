package com.paulacr.bitcoinpricingapp.viewstate

sealed class ViewState<T> {

    class Success<T>(val data: T) : ViewState<T>()

    class Failure<T>(throwable: Throwable) : ViewState<T>()

    class EmptyState<T>() : ViewState<T>()

    class Loading<T> : ViewState<T>()
}
