package com.logicsoul.mvp.view.feature

import android.os.Bundle
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import com.logicsoul.mvp.R
import com.logicsoul.mvp.data.remote_data_source.UserDataSource
import com.logicsoul.mvp.data.repositories.UserRepository
import com.logicsoul.mvp.databinding.ActivityMainBinding
import com.logicsoul.mvp.helper.NetworkHelper
import com.logicsoul.mvp.helper.base.BaseActivity
import com.logicsoul.mvp.helper.extension.setItems
import com.logicsoul.mvp.model.User

/**
 * Home activity
 */
class UserActivity : BaseActivity() {

    // init binding lately before use else represent uninitialized property exception
    private lateinit var binding: ActivityMainBinding
    private lateinit var userRepository: UserRepository
    private lateinit var presenter: UserPresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)

        //apply is scoped function
        binding.apply {
            lifecycleOwner = this@UserActivity
            baseProgressBar = progressCircular
        }

        userRepository = UserRepository(
            NetworkHelper.getNetworkHelperInstance(),
            UserDataSource.getUserDataSourceInstance()
        )

        presenter = UserPresenter(userRepository)

        //todo : check internet connectivity here
        presenter.getUsers(this)

        initObserver()

        val adapter = UsersAdapter()
        binding.rvList.adapter = adapter
    }

    /**
     * Observer for live data instead of callback
     */
    private fun initObserver() {
        presenter.listOfUsersLiveData.observe(this, Observer {
            showUsers(it)
        })

        presenter.onMessageLiveData.observe(this, Observer {
            // here request code is used to differentiate the response in case of multiple response
            showMessage(it, 100)
        })

        presenter.onThrowableErrorLiveData.observe(this, Observer {
            // here request code is used to differentiate the response in case of multiple response
            onThrowableError(it, 100)
        })

        presenter.onNetworkFailureLiveData.observe(this, Observer {
            onNetworkFailure(it)
        })

        presenter.onProgressLoaderLiveData.observe(this, Observer {
            setProgressBar(it)
        })
    }

    private fun showUsers(usersList: List<User>?) {
        showMessage(resources.getString(R.string.success_msg_user_list_fetch), 100)
        //setting through extension method
        setItems(binding.rvList, usersList)
    }

    private fun showMessage(message: String, requestCode: Int) {
        super.showBaseMessage(message, requestCode)
    }

    private fun setProgressBar(show: Boolean) {
        super.setBaseProgressBar(show)
    }

    private fun onThrowableError(throwable: Throwable, requestCode: Int) {
        super.onBaseThrowableError(throwable, requestCode)
    }

    private fun onNetworkFailure(message: String) {
        super.onBaseNetworkFailure(message)
    }
}