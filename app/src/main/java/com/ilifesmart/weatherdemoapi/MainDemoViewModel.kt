package com.ilifesmart.weatherdemoapi

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ilifesmart.weatherdemoapi.beans.Data
import com.ilifesmart.weatherdemoapi.net.RemoteRepository
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MainDemoViewModel:BaseViewModel() {
    val repository = RemoteRepository.getInstance()
    private val datas:MutableLiveData<List<Data>> by lazy {MutableLiveData<List<Data>>().also {
        loadDatas()
    }}

    private fun loadDatas() = launchUI {
        val result = repository.getDatas()
        datas.value = result.data
    }

    fun getArticls(): LiveData<List<Data>> {
        return datas
    }

    override fun onCleared() {
        super.onCleared()
        println("end_of_view_models")
    }
}