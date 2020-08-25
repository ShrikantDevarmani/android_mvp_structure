package com.logicsoul.mvp.helper.mvp

/**
 * This is common base view for all other views(Activity/Fragment/CustomView)
 */
interface IBaseView {
    fun showBaseMessage(message: String, requestCode: Int)

    fun setBaseProgressBar(show: Boolean)

    fun onBaseThrowableError(throwable: Throwable, requestCode: Int)

    fun onBaseNetworkFailure(message: String)

    fun onBaseSearchEmpty(emptyMessage: String)

    // can be used in presenter class for accessing context with view callback.
    // Then we can remove the context form function call which is used for network call check / may be used to get the data form shared preference
//    val context: Context
}