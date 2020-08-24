package com.logicsoul.mvp.view.feature

import android.os.Bundle
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import com.logicsoul.mvp.R
import com.logicsoul.mvp.callback.CommonDataSourceCallback
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
class UserActivity : BaseActivity(), UserContract.View, CommonDataSourceCallback<User> {

    // init binding lately before use else represent uninitialized property exception
    private lateinit var binding: ActivityMainBinding
    lateinit var userRepository: UserRepository
    lateinit var presenter: UserPresenter

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

        presenter = UserPresenter(this, userRepository)

        presenter.getUsers(this, this@UserActivity)

        val adapter = UsersAdapter()
        binding.rvList.adapter = adapter
    }

    override fun onResume() {
        super.onResume()
        presenter.onViewActive(this)
    }

    override fun onPause() {
        presenter.onViewInactive()
        super.onPause()
    }

    override fun showUsers(usersList: List<User>?) {
        Toast.makeText(
            this,
            resources.getString(R.string.success_msg_user_list_fetch),
            Toast.LENGTH_LONG
        ).show()

        //setting through extension method
        setItems(binding.rvList, usersList)

        // or as regular classic / common way
//        usersList?.let { adapter.setUsers(it) }
    }

    override fun onSuccess(responseList: List<User>?, responseObject: User?, requestCode: Int) {
        Toast.makeText(this, "onSuccessOfList", Toast.LENGTH_LONG).show()
    }

    override fun showMessage(message: String, requestCode: Int) {
        super.showBaseMessage(message, requestCode)
    }

    override fun setProgressBar(show: Boolean) {
        super.setBaseProgressBar(show)
    }

    override fun onThrowableError(throwable: Throwable, requestCode: Int) {
        super.onBaseThrowableError(throwable, requestCode)
    }

    override fun onNetworkFailure(isNetworkAvailable: Boolean, message: String, requestCode: Int) {
        super.onBaseNetworkFailure(isNetworkAvailable, message, requestCode)
    }

    override fun onSearchEmpty(emptyMessage: String) {
        super.onBaseSearchEmpty(emptyMessage)
    }
}