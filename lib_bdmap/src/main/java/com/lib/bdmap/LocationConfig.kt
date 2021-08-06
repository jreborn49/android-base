package com.lib.bdmap

import android.app.Application

interface LocationConfig {

    companion object {
        lateinit var config: LocationConfig

        fun set(baseConfig: LocationConfig) {
            config = baseConfig
        }
    }

    fun application(): Application

    fun notificationIcon(): Int

    fun locationInterval(): Int = 30_1000
}