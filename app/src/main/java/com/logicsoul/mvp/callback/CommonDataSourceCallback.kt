package com.logicsoul.mvp.callback

/**
 * on list of any type
 */
interface CommonDataSourceCallback<T> {

    fun onSuccess(responseList: List<T>?, responseObject: T?, requestCode: Int)

    fun showMessage(message: String, requestCode: Int)

    fun setProgressBar(show: Boolean)

    fun onThrowableError(throwable: Throwable, requestCode: Int)

    fun onNetworkFailure(isNetworkAvailable: Boolean, message: String, requestCode: Int)

    fun onSearchEmpty(emptyMessage: String)
}