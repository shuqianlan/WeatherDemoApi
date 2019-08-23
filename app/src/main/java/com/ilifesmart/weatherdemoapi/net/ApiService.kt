package com.ilifesmart.weatherdemoapi.net

import com.ilifesmart.weatherdemoapi.databeans.WXAOfficialAccounts
import com.ilifesmart.weatherdemoapi.base.ResponseData
import com.ilifesmart.weatherdemoapi.databeans.HomeChapters
import retrofit2.http.Field
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface ApiService {
    // 获取公众号列表
    @GET("wxarticle/chapters/json")
    suspend fun getDatas(): ResponseData<List<WXAOfficialAccounts>>

    // 获取某公众号历史数据
    @GET("wxarticle/list/{ID}/{page}/json")
    suspend fun getHistoryDatas(@Path("ID") ID:String, @Path("page") page:String): ResponseData<Any>

    // 公众号内搜索历史文章
    @GET("wxarticle/list/{ID}/{page}/json?k={words}")
    suspend fun getHistoryDatas(@Path("ID") ID:String, @Path("page") page:String, @Path("words") words:String): ResponseData<Any>

    // 首页文章列表
    @GET("article/list/{index}/json")
    suspend fun homePageDatas(@Path("index") index:String): ResponseData<HomeChapters>

    // 首页Banner
    @GET("banner/json")
    suspend fun bannerDatas(): ResponseData<Any>

    // 常用网站
    @GET("friend/json")
    suspend fun friendDatas(): ResponseData<Any>

    // 热词搜索
    @GET("hotkey/json")
    suspend fun hotkeyDatas(): ResponseData<Any>

    // 置顶文章
    @GET("article/top/json")
    suspend fun TopArticleDatas(): ResponseData<Any>

    // 体系
    @GET("tree/json")
    suspend fun treeDatas(): ResponseData<Any>

    // 体系下的文章
    @GET("article/list/{index}/json?cid={id}")
    suspend fun treeIdDatas(): ResponseData<Any>

    // 项目类表
    @GET("project/tree/json")
    suspend fun treeProjectDatas(): ResponseData<Any>

    // 某项目列表
    @GET("project/list/{index}/json?cid={cid}")
    suspend fun projectDatas(): ResponseData<Any>

    // 登录
    @POST("user/login")
    suspend fun userLogin(@Field("username") username:String, @Field("password") password:String): ResponseData<Any>

    // 注册
    @POST("user/register")
    suspend fun userLogin(@Field("username") username:String, @Field("password") password:String, @Field("repassword") repassword:String): ResponseData<Any>

    // 注销
    @GET("user/logout/json")
    suspend fun logout(): ResponseData<Any>

    // 收藏文章列表
    @GET("lg/collect/list/0/json")
    suspend fun collectDatas(): ResponseData<Any>

    // 收藏文章
    @GET("lg/collect/{id}/json")
    suspend fun collectData(@Path("id") id:String): ResponseData<Any>

    // 取消收藏
    @GET("lg/uncollect_originId/{id}/json")
    suspend fun uncollectData(@Path("id") id:String): ResponseData<Any>
}