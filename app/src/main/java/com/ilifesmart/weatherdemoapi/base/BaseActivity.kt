package com.ilifesmart.weatherdemoapi.base

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.cancel

@UseExperimental(kotlinx.coroutines.ExperimentalCoroutinesApi::class)
abstract class BaseActivity : AppCompatActivity(), CoroutineScope by MainScope() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(getLayoutResId())
        initView()
        initData()
    }

    inline fun <reified T:AppCompatActivity> Activity.startActivity() {
        startActivity(Intent(this, T::class.java))
    }

    inline fun Activity.toast(msg:String, duration:Int=Toast.LENGTH_SHORT) {
        if (msg.isNotEmpty()) {
            Toast.makeText(this, msg, duration).show()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        cancel()
    }

    open fun initView() {}
    abstract fun initData()
    abstract fun getLayoutResId():Int
}