package com.ilifesmart.weatherdemoapi.base

data class ResponseData<out T>(val errorCode:Int, val errorMsg:String, val data:T)