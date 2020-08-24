package com.logicsoul.mvp.helper.base

import android.os.Bundle
import android.view.MenuItem
import android.view.View
import android.widget.ProgressBar
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import com.logicsoul.mvp.R
import com.logicsoul.mvp.helper.mvp.IBaseView

/**
 * The abstract base container responsible for showing and destroying [Fragment] and handling
 * back and up navigation using the Fragment back stack. This is based on the
 * Fragment Oriented Architecture explained here
 * http://vinsol.com/blog/2014/09/15/advocating-fragment-oriented-applications-in-android/
 */
abstract class BaseActivity : AppCompatActivity(), IBaseView,
    FragmentManager.OnBackStackChangedListener {

    protected var baseProgressBar: ProgressBar? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportFragmentManager.addOnBackStackChangedListener(this)
    }

    fun <T : Fragment?> showFragment(
        fragmentClass: Class<T>, bundle: Bundle?,
        addToBackStack: Boolean
    ) {
        val fragmentTransaction = supportFragmentManager.beginTransaction()
        var fragment = supportFragmentManager.findFragmentByTag(
            fragmentClass.simpleName
        )
        if (fragment == null) {
            try {
                fragment = fragmentClass.newInstance()
                fragment!!.arguments = bundle
            } catch (e: InstantiationException) {
                throw RuntimeException("New Fragment should have been created", e)
            } catch (e: IllegalAccessException) {
                throw RuntimeException("New Fragment should have been created", e)
            }
        }
        fragmentTransaction.setCustomAnimations(
            R.anim.slide_in_right, R.anim.slide_out_left, android.R.anim.slide_in_left,
            android.R.anim.slide_out_right
        )
        fragmentTransaction.replace(R.id.fragmentPlaceHolder, fragment, fragmentClass.simpleName)
        if (addToBackStack) {
            fragmentTransaction.addToBackStack(null)
        }
        fragmentTransaction.commit()
    }

    fun <T : Fragment?> showFragment(fragmentClass: Class<T>) {
        showFragment(fragmentClass, null, false)
    }

    fun popFragmentBackStack() {
        if (supportFragmentManager.backStackEntryCount > 0) {
            supportFragmentManager.popBackStack()
        }
    }

    private fun shouldShowActionBarUpButton() {
        if (supportFragmentManager.backStackEntryCount == 0) {
            supportActionBar!!.setDisplayHomeAsUpEnabled(false)
        } else {
            supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val id = item.itemId
        if (id == android.R.id.home) {
            popFragmentBackStack()
            return true
        }
        return super.onOptionsItemSelected(item)
    }

    /**
     * Methods form Base
     */
    override fun onBackStackChanged() {
        shouldShowActionBarUpButton()
    }

    override fun showBaseMessage(message: String, requestCode: Int) {
        Toast.makeText(this, message, Toast.LENGTH_LONG).show()
    }

    override fun setBaseProgressBar(show: Boolean) {
        if (show) {
            baseProgressBar!!.visibility = View.VISIBLE
        } else {
            baseProgressBar!!.visibility = View.GONE
        }
    }

    override fun onBaseThrowableError(throwable: Throwable, requestCode: Int) {
        Toast.makeText(
            this,
            this.resources.getString(R.string.error_msg_something_wrong),
            Toast.LENGTH_SHORT
        ).show()
    }

    override fun onBaseNetworkFailure(
        isNetworkAvailable: Boolean,
        message: String,
        requestCode: Int
    ) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }

    override fun onBaseSearchEmpty(emptyMessage: String) {
        Toast.makeText(this, emptyMessage, Toast.LENGTH_SHORT).show()
    }
}