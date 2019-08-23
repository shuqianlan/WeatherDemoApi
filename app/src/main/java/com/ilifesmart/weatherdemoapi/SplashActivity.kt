package com.ilifesmart.weatherdemoapi

import android.content.Intent
import com.ilifesmart.weatherdemoapi.base.BaseActivity
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class SplashActivity : BaseActivity() {

    override fun getLayoutResId(): Int = R.layout.activity_splash
    override fun initData() {
        launch {
            delay(1000L)
            val i = Intent(this@SplashActivity, MainActivity::class.java).apply {
                addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK)
            }

            startActivity(i)
            finish()

//            overridePendingTransition(0, android.R.anim.fade_out)
        }
    }

}
