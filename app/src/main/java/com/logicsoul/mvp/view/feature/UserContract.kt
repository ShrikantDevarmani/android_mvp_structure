package com.logicsoul.mvp.view.feature

import android.content.Context
import com.logicsoul.mvp.callback.CommonDataSourceCallback
import com.logicsoul.mvp.helper.mvp.IBasePresenter
import com.logicsoul.mvp.helper.mvp.IBaseView
import com.logicsoul.mvp.model.User

/**
 * List contract class :  which has Inner class
 *    1. View which is to be implemented by Activity/Fragment/CustomView
 *    2. Presenter which is implemented by respective View-Presenter class (1-1 one view must have one presenter)
 */

interface UserContract {

    interface View : IBaseView {
        fun showUsers(usersList: List<User>?)
    }

    interface Presenter : IBasePresenter<View> {
        fun getUsers(context: Context, callbackCommon: CommonDataSourceCallback<User>)
    }
}