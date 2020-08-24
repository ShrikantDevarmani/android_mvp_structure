package com.logicsoul.mvp.helper.mvp

/**
 * This is common base presenter wrt each view (Activity/Fragment/CustomView)
 * Tells active and inactive state about view
 */
interface IBasePresenter<ViewT> {
    fun onViewActive(view: ViewT)

    fun onViewInactive()

    /**
     * Called when an `view` is detached from this presenter.
     */
    fun onDetach()
}