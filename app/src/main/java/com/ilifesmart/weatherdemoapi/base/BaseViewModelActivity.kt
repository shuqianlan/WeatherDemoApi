package com.ilifesmart.weatherdemoapi.base

import android.os.Bundle
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
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
        providerVMClass().run {
            viewModel = ViewModelProvider.NewInstanceFactory().create(this)
            lifecycle.addObserver(viewModel) // lifecycle是创建类时创建. 也就是其初始生命周期极早.
        }
    }

    private fun startObserver() {
        viewModel.run {
            getError().observe(this@BaseViewModelActivity, Observer {
                requestError(it)
            })

            loading().observe(this@BaseViewModelActivity, Observer {
                showLoading(it)
            })
        }
    }

    open fun showLoading(it:Boolean) {
        println("LoadingShow:$it")
    }

    private fun requestError(it:Exception?) {
        it?.let {
            when(it) {
                is TimeoutCancellationException -> {
                    toast("请求超时")
                }
                is BaseRepository.TokenInvalidException -> {
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