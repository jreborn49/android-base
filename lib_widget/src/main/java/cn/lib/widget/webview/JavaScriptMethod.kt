package cn.lib.widget.webview

import androidx.appcompat.app.AppCompatActivity

open class JavaScriptMethod(val activity: AppCompatActivity) {

    var onFinish: (() -> Unit)? = {
        activity.finish()
    }
}