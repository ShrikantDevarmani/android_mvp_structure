package com.logicsoul.mvp.helper.mvp

/**
 * Abstract base class to be extended by any MVP Presenter.
 * It contains common attributes and methods to be shared across Presenters.
 *
 * @param <ViewT> a generic type to indicate a type of MVP View
</ViewT> */
open class BasePresenter<ViewT> : IBasePresenter<ViewT> {
    /**
     * return the attached view
     *
     * @return view
     */
    protected var view: ViewT? = null

    /**
     * If view is attached to presenter then init view with implemented activity or fragment or view
     *
     * @param view
     */
    override fun onViewActive(view: ViewT) {
        this.view = view
    }

    /**
     * If view is destroyed i.e inactive
     */
    override fun onViewInactive() {
        view = null
    }

    /**
     * on presenter detach make view GC
     */
    override fun onDetach() {
        if (view != null) {
            view = null
        }
    }

    /**
     * @return True if the view this presenter is attached to still exists and not garbage collected
     * since we are holding it through a `WeakReference`
     */
    protected val isViewAttached: Boolean
        protected get() = view != null
}