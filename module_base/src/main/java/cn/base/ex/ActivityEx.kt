package cn.base.ex

import androidx.activity.ComponentActivity
import permissions.dispatcher.PermissionRequest

fun ComponentActivity.onShowRationale(request: PermissionRequest) {
    request.proceed()
}
