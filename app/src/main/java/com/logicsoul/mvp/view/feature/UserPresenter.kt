package com.logicsoul.mvp.view.feature

import android.content.Context
import androidx.lifecycle.MutableLiveData
import com.logicsoul.mvp.callback.CommonDataSourceCallback
import com.logicsoul.mvp.data.repositories.UserRepository
import com.logicsoul.mvp.model.User


/**
 * UserPresenter class which is wrt DashboardActivity
 * For Live data reference : Reference : https://medium.com/@techadroit89/using-livedata-in-mvp-1dc3425edf9c
 * Just read [informative]: https://medium.com/androiddevelopers/viewmodels-and-livedata-patterns-antipatterns-21efaef74a54
 */

class UserPresenter(
    private var userRepository: UserRepository
) {

    var listOfUsersLiveData = MutableLiveData<List<User>>()
    var onMessageLiveData = MutableLiveData<String>()
    var onNetworkFailureLiveData = MutableLiveData<String>()
    var onProgressLoaderLiveData = MutableLiveData<Boolean>(false)
    var onThrowableErrorLiveData = MutableLiveData<Throwable>()
    // add search empty live data also if you needed as like above


    fun getUsers(context: Context) {
        onProgressLoaderLiveData.value = true

        userRepository.getUsers(
            context,
            object : CommonDataSourceCallback<User> {
                override fun onSuccess(
                    responseList: List<User>?,
                    responseObject: User?,
                    requestCode: Int
                ) {
                    onProgressLoaderLiveData.value = false
                    listOfUsersLiveData.value = responseList
                }

                override fun showMessage(message: String, requestCode: Int) {
                    onMessageLiveData.value = message
                }

                override fun setProgressBar(show: Boolean) {
                    onProgressLoaderLiveData.value = show
                }

                override fun onThrowableError(throwable: Throwable, requestCode: Int) {
                    onProgressLoaderLiveData.value = false
                    onThrowableErrorLiveData.value = throwable
                }

                override fun onNetworkFailure(
                    message: String
                ) {
                    onProgressLoaderLiveData.value = false
                    onNetworkFailureLiveData.value = message
                }

                override fun onSearchEmpty(emptyMessage: String) {
                    // call if search functionality there
                }
            })
    }
}