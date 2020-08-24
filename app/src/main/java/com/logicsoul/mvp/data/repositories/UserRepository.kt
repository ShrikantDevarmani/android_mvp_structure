package com.logicsoul.mvp.data.repositories

import android.content.Context
import android.util.Log
import com.logicsoul.mvp.R
import com.logicsoul.mvp.callback.CommonDataSourceCallback
import com.logicsoul.mvp.data.remote_data_source.UserDataSource
import com.logicsoul.mvp.helper.IntegerConstants
import com.logicsoul.mvp.helper.NetworkHelper
import com.logicsoul.mvp.helper.StringConstants
import com.logicsoul.mvp.model.User

/**
 * Data Repository class for List related services call
 */

class UserRepository(
    private val networkHelper: NetworkHelper,
    private val userDataSource: UserDataSource
) {
    /**
     * Function call to getUsers which returns the all user which are under particular agent
     */
    fun getUsers(
        context: Context,
        callbackCommon: CommonDataSourceCallback<User>
    ) {

        if (networkHelper.isNetworkAvailable(context)) {
            userDataSource.getUserList(callbackCommon)
        } else {
            Log.d(StringConstants.TAG, "DashboardRepository")
            /**
             * todo do local call for data if network is not available else do respective task
             */
            callbackCommon.onNetworkFailure(
                false,
                context.resources.getString(R.string.error_msg_no_internet_available),
                IntegerConstants.REQUEST_CODE_USER_LIST
            )
        }
    }
}