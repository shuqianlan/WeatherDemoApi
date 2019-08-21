package com.ilifesmart.weatherdemoapi.net

import com.ilifesmart.weatherdemoapi.beans.Data
import com.ilifesmart.weatherdemoapi.beans.ResponseData
import retrofit2.http.GET

interface ApiService {
    @GET("wxarticle/chapters/json")
    suspend fun getDatas(): ResponseData<List<Data>>

}