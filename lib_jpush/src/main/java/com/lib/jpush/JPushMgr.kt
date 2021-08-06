package com.lib.jpush

import android.content.Context
import cn.jpush.android.api.BasicPushNotificationBuilder
import cn.jpush.android.api.JPushInterface

object JPushMgr {

    fun init(context: Context, statusBarDrawable: Int, debug: Boolean) {
        JPushInterface.setDebugMode(debug)
        JPushInterface.init(context)

        val builder = BasicPushNotificationBuilder(context)
        builder.statusBarDrawable = statusBarDrawable
        JPushInterface.setDefaultPushNotificationBuilder(builder)
    }

    fun setAlias(context: Context, alias: String) {
        JPushInterface.setAlias(context, 0, alias)
    }

    fun deleteAlias(context: Context) {
        JPushInterface.deleteAlias(context, 0)
        JPushInterface.clearAllNotifications(context)
    }
}