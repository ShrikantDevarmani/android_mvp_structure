package com.logicsoul.mvp.view.feature

import android.content.Context
import android.util.Log
import com.logicsoul.mvp.callback.CommonDataSourceCallback
import com.logicsoul.mvp.data.repositories.UserRepository
import com.logicsoul.mvp.helper.StringConstants
import com.logicsoul.mvp.helper.mvp.BasePresenter
import com.logicsoul.mvp.model.User

/**
 * DashboardPresenter class which is wrt DashboardActivity
 */

class UserPresenter(
    view: UserContract.View,
    private var userRepository: UserRepository
) : BasePresenter<UserContract.View>(), UserContract.Presenter {

    init {
        // referring to
        this.view = (view)
    }

    override fun getUsers(context: Context, callbackCommon: CommonDataSourceCallback<User>) {

        if (!isViewAttached) return

        callbackCommon.setProgressBar(true)

        userRepository.getUsers(
            context,
            object : CommonDataSourceCallback<User> {
                override fun onSuccess(
                    responseList: List<User>?,
                    responseObject: User?,
                    requestCode: Int
                ) {
                    Log.d(StringConstants.TAG, "onSuccessOfList in DashboardPresenter")
                    callbackCommon.setProgressBar(false)
                    // todo call view callback for success as below / as per requirement
                    view?.showUsers(responseList)
                }

                override fun showMessage(message: String, requestCode: Int) {
                    callbackCommon.showMessage(message, requestCode)
                }

                override fun setProgressBar(show: Boolean) {
                    callbackCommon.setProgressBar(show)
                }

                override fun onThrowableError(throwable: Throwable, requestCode: Int) {
                    Log.d(StringConstants.TAG, "onFailure in DashboardPresenter")
                    //todo hide progress and show error message as below / as per requirement
                    callbackCommon.setProgressBar(false)
                    callbackCommon.onThrowableError(throwable, requestCode)
                }

                override fun onNetworkFailure(
                    isNetworkAvailable: Boolean,
                    message: String,
                    requestCode: Int
                ) {
                    Log.d(
                        StringConstants.TAG,
                        "onNetworkFailure in DashboardPresenter"
                    )
                    callbackCommon.setProgressBar(false)
                    // todo show network error as below / as per requirement
                    callbackCommon.onNetworkFailure(true, message, requestCode)
                }

                override fun onSearchEmpty(emptyMessage: String) {
                    // call if search functionality there
                }
            })
    }

    override fun onDetach() {
        super.onDetach()
    }
}