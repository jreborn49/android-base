package com.lib.lbs

import android.util.Log
import com.amap.api.location.AMapLocationClient
import com.amap.api.location.AMapLocationClientOption
import com.amap.api.location.AMapLocationListener
import java.util.*

object LocationMgr {

    private val locListeners: LinkedList<LocResultListener> by lazy { LinkedList() }

    private val locationClient: AMapLocationClient by lazy {
        val client = AMapLocationClient(LocationConfig.config.application())
        val options = AMapLocationClientOption()
        options.locationMode =
            AMapLocationClientOption.AMapLocationMode.Hight_Accuracy//会同时使用网络定位和GPS定位，优先返回最高精度的定位结果，以及对应的地址描述信息
        options.isNeedAddress = true//设置是否返回地址信息（默认返回地址信息）
        options.interval = LocationConfig.config.locationInterval().toLong()//设置定位间隔
        options.isLocationCacheEnable = false//关闭缓存机制
        client.setLocationOption(options)
        client.setLocationListener(locationListener)
        client
    }

    private val locationListener: AMapLocationListener by lazy {
        AMapLocationListener {
            if (it.errorCode == 0) {
                val loc = LocResult(it.latitude, it.longitude, it.address)
                locListeners.forEach { listener -> listener.onLocResult(loc) }
            } else {
                //定位失败时，可通过ErrCode（错误码）信息来确定失败的原因，errInfo是错误信息，详见错误码表。
                Log.i(
                    "AmapError",
                    "location Error, ErrCode:" + it.errorCode + ", errInfo:" + it.errorInfo
                )
            }
        }
    }

    fun startLocation(listener: LocResultListener) {
        if(!locListeners.contains(listener)) {
            locListeners.add(listener)
        }
        locationClient.startLocation()
    }

    fun stopLocation(listener: LocResultListener) {
        locListeners.remove(listener)
        if (locListeners.isEmpty()) {
            locationClient.stopLocation()
        }
    }

    fun destory() {
        locListeners.clear()
        locationClient.onDestroy()
    }
}