package com.ilifesmart.weatherdemoapi.base

import androidx.lifecycle.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withTimeout
import java.lang.Exception

open class BaseViewModel : ViewModel(), LifecycleObserver {

    private val error by lazy { MutableLiveData<Exception>() }
    private val loading by lazy { MutableLiveData<Boolean>() }

    fun launchUI(block: suspend CoroutineScope.()->Unit) = viewModelScope.launch {
        loading.value = true
        try {
            withTimeout(5000) { // 超时则抛出异常TimeoutCancellationException
                block() // 此处切换到线程池的上下文.
            }
        } catch (e: Exception) {
            onError()
            error.value = e
        } finally {
            loading.value = false
        }
    }

    fun getError(): LiveData<Exception> {
        return error
    }

    fun loading(): LiveData<Boolean> {
        return loading
    }

    open fun onError() {}
}