package com.logicsoul.mvp.view.splash

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import com.logicsoul.mvp.R
import com.logicsoul.mvp.helper.IntegerConstants
import com.logicsoul.mvp.view.feature.UserActivity

/**
 * Splash screen
 */
class SplashActivity : Activity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        Handler().postDelayed({
            val intent = Intent(this, UserActivity::class.java)
            startActivity(intent)
            finish()
        }, IntegerConstants.SPLASH_SCREEN_TIMEOUT)
    }
}