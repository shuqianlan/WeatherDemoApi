package com.ilifesmart.weatherdemoapi

import android.os.Bundle
import android.os.PersistableBundle
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.ilifesmart.weatherdemoapi.net.RemoteRepository
import kotlinx.coroutines.TimeoutCancellationException
import java.lang.Exception

abstract class BaseViewModelActivity<VM: BaseViewModel>: BaseActivity() {
    protected lateinit var viewModel:VM

    override fun onCreate(savedInstanceState: Bundle?) {
        initVM()
        super.onCreate(savedInstanceState)
        startObserver()
    }

    protected abstract fun providerVMClass():Class<VM>

    private fun initVM() {
        println("initVM")
        providerVMClass()?.let {
            viewModel = ViewModelProvider.NewInstanceFactory().create(it)
            lifecycle.addObserver(viewModel) // lifecycle是创建类时创建. 也就是其初始生命周期极早.
        }
    }

    private fun startObserver() {
        viewModel.run {
            getErro().observe(this@BaseViewModelActivity, Observer {
                requestError(it)
            })

            getFinally().observe(this@BaseViewModelActivity, Observer {
                requestFinally(it)
            })
        }
    }

    open fun requestFinally(it:Int?) {
        // ...
    }

    private fun requestError(it:Exception?) {
        it?.let {
            when(it) {
                is TimeoutCancellationException -> {
                    toast("请求超时")
                }
                is RemoteRepository.TokenInvalidException -> {
                    it.message?.let {
                        toast(it)
                    }
                }
                else -> {
                    println("Exception:${it.message}")
                }
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        lifecycle.removeObserver(viewModel)
    }
}