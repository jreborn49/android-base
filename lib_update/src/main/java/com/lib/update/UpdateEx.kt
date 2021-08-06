package com.lib.update

import android.content.Context
import cn.lib.base.ex.confirm
import cn.lib.base.ex.judgeNull
import com.azhon.appupdate.config.UpdateConfiguration
import com.azhon.appupdate.listener.OnDownloadListener
import com.azhon.appupdate.manager.DownloadManager
import com.azhon.appupdate.utils.ApkUtil
import java.io.File

const val UPDATE_APP_NAME = "app_update.apk"

fun Context.deleteOldApk() {
    ApkUtil.deleteOldApk(this, cacheDir.absolutePath + File.separator + UPDATE_APP_NAME)
}

fun Context.appUpdate(
    update: VersionEntity,
    listener: OnDownloadListener? = null,
    last: (() -> Unit)? = null
) {
    if (UpdateConfig.config.versionCode() >= update.targetCode) {
        last?.invoke()
        return
    }
    confirm(
        getString(R.string.check_new_version, update.targetName),
        update.updateLog,
        cancelable = false,
        negativeText = if (update.targetMust) null else getString(R.string.cancel),
        positiveMethod = {
            download(listener, update.targetUrl)
        }
    )
}

fun Context.download(listener: OnDownloadListener? = null, url: String) {
    try {
        DownloadManager.getInstance().release()
    } catch (e: Exception) {
        e.printStackTrace()
    }
    val configuration = UpdateConfiguration()
        .setEnableLog(true)
        .setJumpInstallPage(true)
        .setShowNotification(true)
        .setShowBgdToast(false)
    if (listener == null) {
        configuration.setOnDownloadListener(DefaultDownloadListener(this))
    } else {
        configuration.setOnDownloadListener(listener)
    }
    DownloadManager.getInstance(this)
        .setApkName(UPDATE_APP_NAME)
        .setApkUrl(url)
        .setConfiguration(configuration)
        .setSmallIcon(UpdateConfig.config.notificationIcon())
        .download()
}