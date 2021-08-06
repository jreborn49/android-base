package cn.lib.base.ex

import android.annotation.SuppressLint
import android.app.Activity
import android.app.ActivityManager
import android.content.ContentResolver
import android.content.Context
import android.content.Intent
import android.location.LocationManager
import android.net.Uri
import android.os.Build
import android.os.PowerManager
import android.provider.Settings
import androidx.core.app.NotificationManagerCompat

fun Context.notificationEnable(): Boolean {
    val notificationManagerCompat = NotificationManagerCompat.from(this)
    return notificationManagerCompat.areNotificationsEnabled()
}

@SuppressLint("ObsoleteSdkInt")
fun Context.showNotificationSetting() {
    val intent = Intent()
    when {
        Build.VERSION.SDK_INT >= Build.VERSION_CODES.O -> {
            intent.action = Settings.ACTION_APP_NOTIFICATION_SETTINGS
            intent.putExtra(Settings.EXTRA_APP_PACKAGE, packageName)
        }
        Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP -> {
            intent.action = "android.settings.APP_NOTIFICATION_SETTINGS"
            intent.putExtra("app_package", packageName)
            intent.putExtra("app_uid", applicationInfo.uid)
        }
        Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT -> {
            intent.action = Settings.ACTION_APPLICATION_DETAILS_SETTINGS
            intent.addCategory(Intent.CATEGORY_DEFAULT)
            intent.data = Uri.fromParts("package", packageName, null)
        }
        else -> {
            intent.action = Settings.ACTION_SETTINGS
        }
    }
    startActivity(intent)
}

fun Context.addWhiteList() {
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
        val pm: PowerManager = getSystemService(Context.POWER_SERVICE) as PowerManager
        if (!pm.isIgnoringBatteryOptimizations(packageName)) {
            val intent = Intent()
            intent.action = Settings.ACTION_REQUEST_IGNORE_BATTERY_OPTIMIZATIONS
            intent.data = Uri.parse("package:${packageName}")
            if (this !is Activity) {
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            }
            startActivity(intent)
        }
    }
}

fun Context.isRunningTaskExist(processName: String): Boolean {
    val am = getSystemService(Context.ACTIVITY_SERVICE) as ActivityManager
    val processList = am.runningAppProcesses
    for (info in processList) {
        if (info.processName == processName) {
            return true
        }
    }
    return false
}

@Suppress("DEPRECATION")
@SuppressLint("ObsoleteSdkInt")
fun Context.isLocationEnabled(): Boolean {
    return when {
        Build.VERSION.SDK_INT >= Build.VERSION_CODES.P -> {
            val manager =
                getSystemService(Context.LOCATION_SERVICE) as LocationManager? ?: return false
            manager.isLocationEnabled
        }
        Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT -> {
            try {
                val resolver: ContentResolver = contentResolver
                val locationMode = Settings.Secure.getInt(resolver, Settings.Secure.LOCATION_MODE)
                locationMode != Settings.Secure.LOCATION_MODE_OFF
            } catch (e: Settings.SettingNotFoundException) {
                e.printStackTrace()
                false
            }
        }
        else -> {
            val resolver: ContentResolver = contentResolver
            val provider =
                Settings.Secure.getString(resolver, Settings.Secure.LOCATION_PROVIDERS_ALLOWED)
            provider.isNotEmpty()
        }
    }
}

fun Activity.locationSetting(requestCode: Int = -1) {
    val intent = Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS)
    startActivityForResult(intent, requestCode)
}

fun openDocumentIntent(): Intent {
    val intent = Intent(Intent.ACTION_OPEN_DOCUMENT)
    intent.type = "*/*"
    intent.addCategory(Intent.CATEGORY_OPENABLE)
    return intent
}

fun Activity.openDocument(requestCode: Int = -1) {
    startActivityForResult(openDocumentIntent(), requestCode)
}