package com.ilifesmart.weatherdemoapi.viewmodels

import androidx.lifecycle.MutableLiveData
import com.ilifesmart.weatherdemoapi.base.BaseViewModel
import com.ilifesmart.weatherdemoapi.databeans.HomeChapters
import com.ilifesmart.weatherdemoapi.repository.RemoteRepository
import java.util.concurrent.atomic.AtomicInteger

class HomeViewModel :BaseViewModel() {
    private val atomic = AtomicInteger(0)

    val chapters:MutableLiveData<List<HomeChapters.Data>> by lazy {
        MutableLiveData<List<HomeChapters.Data>>().also {
            loadDatas()
        }
    }

    fun loadDatas() = launchUI {
        val result = RemoteRepository.getInstance().homeChapters(atomic.getAndIncrement().toString())
        println("result: $result")
        chapters.value = result.data.datas
    }

    override fun onError() {
        super.onError()
        println("================================Error")
        atomic.getAndDecrement()

        if (atomic.get() < 0) {
            atomic.set(0)
        }
    }
}