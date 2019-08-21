package com.ilifesmart.weatherdemoapi

import androidx.lifecycle.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withTimeout
import java.lang.Exception

open class BaseViewModel : ViewModel(), LifecycleObserver {

    private val error by lazy { MutableLiveData<Exception>() }
    private val finally by lazy { MutableLiveData<Int>() }

    fun launchUI(block: suspend CoroutineScope.()->Unit) = viewModelScope.launch {
        try {
            withTimeout(5000) {
                block() // 此处切换到线程池的上下文.
            }
        } catch (e: Exception) {
            error.value = e
        } finally {
            finally.value = 200
        }
    }

    fun getErro(): LiveData<Exception> {
        return error
    }

    fun getFinally(): LiveData<Int> {
        return finally
    }
}