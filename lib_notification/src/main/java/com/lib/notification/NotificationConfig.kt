package com.lib.notification

import android.app.Application

interface NotificationConfig {

    companion object {
        lateinit var config: NotificationConfig

        fun set(baseConfig: NotificationConfig) {
            config = baseConfig
        }
    }

    fun application(): Application

    fun notificationIcon(): Int
}