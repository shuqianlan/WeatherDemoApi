package com.ilifesmart.weatherdemoapi.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.ilifesmart.weatherdemoapi.base.BaseViewModel
import com.ilifesmart.weatherdemoapi.databeans.WXAOfficialAccounts
import com.ilifesmart.weatherdemoapi.repository.RemoteRepository

class MainDemoViewModel: BaseViewModel() {
    val repository = RemoteRepository.getInstance()
    private val datas:MutableLiveData<List<WXAOfficialAccounts>> by lazy {MutableLiveData<List<WXAOfficialAccounts>>().also {
        loadDatas()
    }}

    private fun loadDatas() = launchUI {
        val result = repository.getDatas()
        datas.value = result.data
    }

    fun getArticls(): LiveData<List<WXAOfficialAccounts>> {
        return datas
    }

    override fun onCleared() {
        super.onCleared()
        println("end_of_view_models")
    }
}