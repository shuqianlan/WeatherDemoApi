package com.ilifesmart.weatherdemoapi.beans

data class ResponseData<out T>(val errorCode:Int, val errorMsg:String, val data:T)