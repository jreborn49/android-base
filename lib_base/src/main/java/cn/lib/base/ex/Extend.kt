package cn.lib.base.ex

import android.os.Build
import android.util.Log
import cn.lib.base.BaseConfig.Companion.config
import com.blankj.utilcode.util.ToastUtils

fun toast(msg: String) {
    if(msg.isEmpty()) {
        return
    }
    ToastUtils.showShort(msg)
}

fun toast(resId: Int) {
    ToastUtils.showShort(resId)
}

fun logE(msg: String, tag: String = config.logTag()) {
    if (config.isDebug()) {
        Log.e(tag, msg)
    }
}

fun logD(msg: String, tag: String = config.logTag()) {
    if (config.isDebug()) {
        Log.d(tag, msg)
    }
}

fun fromM() = fromSpecificVersion(Build.VERSION_CODES.M)
fun beforeM() = beforeSpecificVersion(Build.VERSION_CODES.M)
fun fromN() = fromSpecificVersion(Build.VERSION_CODES.N)
fun beforeN() = beforeSpecificVersion(Build.VERSION_CODES.N)
fun fromO() = fromSpecificVersion(Build.VERSION_CODES.O)
fun beforeO() = beforeSpecificVersion(Build.VERSION_CODES.O)
fun fromP() = fromSpecificVersion(Build.VERSION_CODES.P)
fun beforeP() = beforeSpecificVersion(Build.VERSION_CODES.P)
fun fromSpecificVersion(version: Int): Boolean = Build.VERSION.SDK_INT >= version
fun beforeSpecificVersion(version: Int): Boolean = Build.VERSION.SDK_INT < version

fun checkNull(
    objs: List<Any?>,
    predicate: ((Any?) -> Boolean)? = null,
    succ: (List<Any?>) -> Unit,
    fail: ((List<Any?>) -> Unit)? = null
) {
    val nulls = objs.filter { predicate?.invoke(it) ?: (it == null) }
    if (nulls.isNullOrEmpty()) {
        succ.invoke(objs)
    } else {
        fail?.invoke(nulls)
    }
}
