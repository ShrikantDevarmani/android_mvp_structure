package com.logicsoul.mvp.data.remote_data_source

import com.logicsoul.mvp.callback.CommonDataSourceCallback
import com.logicsoul.mvp.data.NetworkClient
import com.logicsoul.mvp.helper.IntegerConstants
import com.logicsoul.mvp.model.User
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


/**
 * All remote network data fetch and response provide to presenter is done through this class
 * This is for Remote data handling only
 */
class UserDataSource {
    // this is retrofit client
    private val networkClient = NetworkClient.getInstance()

    companion object {
        private var mInstance = UserDataSource()

        fun getUserDataSourceInstance(): UserDataSource {
            return mInstance
        }
    }

    /**
     * get list of users
     */
    fun getUserList(callbackCommon: CommonDataSourceCallback<User>) {
        // show loading on call
        callbackCommon.setProgressBar(true)

        networkClient.getUsers().enqueue(object : Callback<List<User>> {
            override fun onResponse(call: Call<List<User>>, response: Response<List<User>>) {
                response.body()?.toList()
                    ?.let {
                        callbackCommon.onSuccess(
                            it,
                            null,
                            IntegerConstants.REQUEST_CODE_USER_LIST
                        )
                    }

                // hide loading on response
                callbackCommon.setProgressBar(false)
            }

            override fun onFailure(call: Call<List<User>>, t: Throwable) {
                //send exception occurred
                callbackCommon.onThrowableError(t, IntegerConstants.REQUEST_CODE_USER_LIST)

                // hide loading on failure
                callbackCommon.setProgressBar(false)
            }
        })
    }
}
