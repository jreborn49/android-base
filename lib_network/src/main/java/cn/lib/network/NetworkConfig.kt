package cn.lib.network

import android.app.Application

interface NetworkConfig {

    companion object {
        lateinit var config: NetworkConfig

        fun set(baseConfig: NetworkConfig) {
            config = baseConfig
        }
    }

    fun application(): Application

    fun versionCode(): Int

    fun versionName(): String

    fun isDebug(): Boolean

    fun logTag(): String?
}