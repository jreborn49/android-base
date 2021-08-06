package com.lib.update

import android.app.Application

interface UpdateConfig {

    companion object {
        lateinit var config: UpdateConfig

        fun set(baseConfig: UpdateConfig) {
            config = baseConfig
        }
    }

    fun application(): Application

    fun notificationIcon(): Int

    fun versionCode(): Int

    fun versionName(): String
}