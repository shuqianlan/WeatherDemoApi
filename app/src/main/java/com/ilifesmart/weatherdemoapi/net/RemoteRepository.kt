package com.ilifesmart.weatherdemoapi.net

import com.ilifesmart.weatherdemoapi.beans.Data
import com.ilifesmart.weatherdemoapi.beans.ResponseData
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

class RemoteRepository private constructor() {
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
        private var instance:RemoteRepository? = null
        fun getInstance() = instance ?: synchronized(this) {
            instance ?: RemoteRepository().also { instance = it }
        }
    }

    suspend fun getDatas(): ResponseData<List<Data>> = request {
        service.getDatas()
    }

    private suspend fun <T:Any> request(call: suspend () -> ResponseData<T>): ResponseData<T> {
        return withContext(Dispatchers.Default) {
            call.invoke()
        }.apply {
            when (errorCode) {
                100 -> throw TokenInvalidException()
            }
        }
    }

    class TokenInvalidException(msg: String="token invalid"):java.lang.Exception(msg)
}