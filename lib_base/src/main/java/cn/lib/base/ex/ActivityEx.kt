package cn.lib.base.ex

import android.app.Activity
import androidx.appcompat.app.AppCompatActivity

fun Activity.backgroundAlpha(alpha: Float) {
    val lp = this.window?.attributes
    lp?.alpha = alpha
    this.window?.attributes = lp
}