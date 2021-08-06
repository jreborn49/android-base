package com.lib.bdmap

import android.app.Application
import android.util.Log
import com.baidu.location.BDAbstractLocationListener
import com.baidu.location.BDLocation
import com.baidu.location.LocationClient
import com.baidu.location.LocationClientOption
import com.baidu.mapapi.CoordType
import com.baidu.mapapi.SDKInitializer
import java.util.*

object LocationMgr {

    private val locListeners: LinkedList<LocResultListener> by lazy { LinkedList() }

    private val locationClient: LocationClient by lazy {
        val client = LocationClient(LocationConfig.config.application())
        val options = LocationClientOption()
        options.locationMode = LocationClientOption.LocationMode.Hight_Accuracy//设置定位模式，高精度，低功耗，仅设备
        options.setCoorType("bd09ll")
        options.setIsNeedAddress(true)
        options.setIsNeedLocationDescribe(true)
        options.setNeedDeviceDirect(false)
        options.isLocationNotify = false
        options.SetIgnoreCacheException(false)
        options.isOpenGps = true
        options.setIgnoreKillProcess(true)
        options.setNeedNewVersionRgc(true)
        options.setScanSpan(LocationConfig.config.locationInterval())
        client.locOption = options
        client.registerLocationListener(locationListener)
        client
    }

    private val locationListener: BDAbstractLocationListener by lazy {
        return@lazy object : BDAbstractLocationListener() {
            override fun onReceiveLocation(location: BDLocation?) {
                if (null != location) {
                    if (location.locType == BDLocation.TypeNetWorkLocation || location.locType == BDLocation.TypeGpsLocation) {
                        val loc = LocResult(location.latitude, location.longitude, location.addrStr, location.locationDescribe)
                        locListeners.forEach { listener -> listener.onLocResult(loc) }
                    } else {
                        Log.i("BdmapError", "location Error, locType:" + location.locType)
                    }
                }
            }
        }
    }

    fun init(application: Application) {
        SDKInitializer.initialize(application)
        SDKInitializer.setCoordType(CoordType.BD09LL)
    }

    fun startLocation(listener: LocResultListener) {
        if (!locListeners.contains(listener)) {
            locListeners.add(listener)
        }
        locationClient.start()
    }

    fun stopLocation(listener: LocResultListener) {
        locListeners.remove(listener)
        if (locListeners.isEmpty()) {
            locationClient.stop()
        }
    }

    fun destory() {
        locListeners.clear()
        locationClient.stop()
    }
}