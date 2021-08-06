package com.lib.bdmap

data class LocResult(
    val latitude: Double,//纬度
    val longitude: Double,//经度
    val address: String?,//地址
    val describe: String?
)
