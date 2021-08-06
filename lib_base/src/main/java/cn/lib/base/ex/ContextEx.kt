package cn.lib.base.ex

import android.content.Context
import android.graphics.drawable.Drawable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.ColorRes
import androidx.annotation.DrawableRes
import androidx.annotation.LayoutRes
import androidx.core.content.ContextCompat

fun Context.getResColor(@ColorRes resId: Int): Int {
    return ContextCompat.getColor(this, resId)
}

fun Context.getResDrawable(@DrawableRes resId: Int): Drawable? {
    return ContextCompat.getDrawable(this, resId)
}

fun Context.getDisplayWidth(): Int {
    return resources.displayMetrics.widthPixels
}

fun Context.getDisplayHeight(): Int {
    return resources.displayMetrics.heightPixels
}

fun Context.getDensityDpi(): Int {
    return resources.displayMetrics.densityDpi
}

fun Context.getDensity(): Float {
    return resources.displayMetrics.density
}

fun Context.getScaledDensity(): Float {
    return resources.displayMetrics.scaledDensity
}

fun Context.sp2px(sp: Float): Int {
    val fontScale = getScaledDensity()
    return (sp * fontScale + 0.5f).toInt()
}

fun Context.px2sp(px: Float): Int {
    val fontScale = getScaledDensity()
    return (px / fontScale + 0.5f).toInt()
}

fun Context.dp2px(dp: Float): Int {
    val density = getDensity()
    return (dp * density + 0.5f).toInt()
}

fun Context.px2dp(px: Float): Int {
    val density = getDensity()
    return (px / density + 0.5f).toInt()
}

fun Context.inflater(
    @LayoutRes resource: Int,
    root: ViewGroup? = null,
    attachToRoot: Boolean = false
): View {
    return LayoutInflater.from(this).inflate(resource, root, attachToRoot)
}