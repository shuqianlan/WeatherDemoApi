package com.ilifesmart.weatherdemoapi

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.os.PersistableBundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

open abstract class BaseActivity : AppCompatActivity() {

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
        Toast.makeText(this, msg, duration).show()
    }

    abstract fun initView()
    abstract fun initData()
    abstract fun getLayoutResId():Int
}