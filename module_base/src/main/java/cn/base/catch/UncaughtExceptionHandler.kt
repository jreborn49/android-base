package cn.base.catch

import com.blankj.utilcode.util.AppUtils

class UncaughtExceptionHandler :
    Thread.UncaughtExceptionHandler {

    companion object {

        val instance by lazy { UncaughtExceptionHandler() }

        fun init() {
            instance.init()
        }
    }

    fun init() {
        Thread.setDefaultUncaughtExceptionHandler(this)
    }

    override fun uncaughtException(t: Thread, e: Throwable) {
        e.printStackTrace()

        AppUtils.exitApp()
    }
}