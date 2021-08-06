package cn.lib.base

import android.app.Application

interface BaseConfig {

    companion object {
        lateinit var config: BaseConfig

        fun set(baseConfig: BaseConfig) {
            config = baseConfig
        }
    }

    fun application(): Application

    fun versionCode(): Int

    fun versionName(): String

    fun appName(): String

    fun appNameZh(): String

    fun isDebug(): Boolean

    fun logTag(): String

    fun routerName(): String

    fun icon(): Int

    fun notificationIcon(): Int

    fun projectPageUrl(): String
}