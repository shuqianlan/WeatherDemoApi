package com.ilifesmart.weatherdemoapi.repository

import com.ilifesmart.weatherdemoapi.base.BaseRepository
import com.ilifesmart.weatherdemoapi.databeans.WXAOfficialAccounts
import com.ilifesmart.weatherdemoapi.base.ResponseData
import com.ilifesmart.weatherdemoapi.databeans.HomeChapters
import com.ilifesmart.weatherdemoapi.net.ApiService
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

class RemoteRepository private constructor(): BaseRepository() {
    private val retrofit:Retrofit
    private val service: ApiService

    init {
        val client = OkHttpClient.Builder()
            .readTimeout(10, TimeUnit.SECONDS)
            .connectTimeout(10L, TimeUnit.SECONDS)
            .addNetworkInterceptor {
                val request = it.request()
                println("OkHttpClient intercept: content $request")
                it.proceed(request)
            }.build()

        retrofit = Retrofit.Builder()
            .baseUrl("https://wanandroid.com/")
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .client(client)
            .build()

        service = retrofit.create(ApiService::class.java)
    }

    companion object {
        @Volatile
        private var instance: RemoteRepository? = null
        fun getInstance() = instance
            ?: synchronized(this) {
            instance
                ?: RemoteRepository().also { instance = it }
        }
    }

    suspend fun getDatas(): ResponseData<List<WXAOfficialAccounts>> = request {
        service.getDatas()
    }

    suspend fun homeChapters(index:String):ResponseData<HomeChapters> = request {
        println("index: $index")
        service.homePageDatas(index)
    }
}