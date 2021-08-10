package cn.lib.base.ex

import android.content.Context
import android.content.pm.PackageInfo

fun Context.appLabel(): String {
    val packageInfo: PackageInfo = packageManager.getPackageInfo(packageName, 0)
    return packageInfo.applicationInfo.loadLabel(packageManager).toString()
}