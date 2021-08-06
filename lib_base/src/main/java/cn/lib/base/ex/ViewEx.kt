package cn.lib.base.ex

import android.app.Application
import android.os.Build
import android.view.View
import android.webkit.WebView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.ViewPager2
import cn.lib.base.R

fun configWebViewCacheDirWithAndroidP(packageName: String) {
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
        val processName = Application.getProcessName()
        if (packageName != processName) {
            WebView.setDataDirectorySuffix(processName)
        }
    }
}

var <T : View> T.lastClickTime: Long
    get() = getTag(R.id.view_last_click_tag) as? Long ?: 0
    set(value) {
        setTag(R.id.view_last_click_tag, value)
    }

inline fun <T : View> T.click(time: Long = 500, crossinline block: (T) -> Unit) {
    setOnClickListener {
        val currentTimeMillis = System.currentTimeMillis()
        if (currentTimeMillis - lastClickTime > time) {
            lastClickTime = currentTimeMillis
            block(this)
        }
    }
}

inline fun viewsClick(vararg views: View, time: Long = 500, crossinline block: (View) -> Unit) {
    val onClickListener = View.OnClickListener { v ->
        val currentTimeMillis = System.currentTimeMillis()
        if (currentTimeMillis - v.lastClickTime > time) {
            v.lastClickTime = currentTimeMillis
            block(v)
        }
    }
    views.forEach {
        it.setOnClickListener(onClickListener)
    }
}

fun View.gone() {
    if (this.visibility != View.GONE) {
        this.visibility = View.GONE
    }
}

fun View.visible() {
    if (this.visibility != View.VISIBLE) {
        this.visibility = View.VISIBLE
    }
}

fun View.invisible() {
    if (this.visibility != View.INVISIBLE) {
        this.visibility = View.INVISIBLE
    }
}

fun View.setGone(gone: Boolean) {
    if (gone) {
        this.gone()
    } else {
        this.visible()
    }
}

fun TextView.str(): String {
    return this.text.toString()
}

fun TextView.textCheck(text: CharSequence?, vararg views: View) {
    if (text.isNullOrEmpty()) {
        gone()
        views.forEach { it.gone() }
    } else {
        this.text = text
        visible()
        views.forEach { it.visible() }
    }
}

fun checkGone(predicate: Boolean, visibleBlock: (() -> Unit)? = null, vararg view: View) {
    if (predicate) {
        view.forEach { it.gone() }
    } else {
        view.forEach { it.visible() }
        visibleBlock?.invoke()
    }
}

fun ViewPager2.overScrollNever() {
    val child: View = getChildAt(0)
    if (child is RecyclerView) {
        child.overScrollMode = View.OVER_SCROLL_NEVER
    }
}