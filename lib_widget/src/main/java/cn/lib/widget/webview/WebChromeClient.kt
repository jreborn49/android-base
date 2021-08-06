package cn.lib.widget.webview

import android.net.Uri
import android.webkit.*
import android.webkit.WebChromeClient
import android.webkit.WebView
import android.widget.EditText
import androidx.appcompat.app.AlertDialog

class WebChromeClient(var webChromeListener: WebChromeListener) : WebChromeClient() {

    override fun onReceivedTitle(view: WebView, title: String) {
        super.onReceivedTitle(view, title)
        webChromeListener.onReceivedTitle(title)
    }

    override fun onProgressChanged(view: WebView, newProgress: Int) {
        super.onProgressChanged(view, newProgress)
        webChromeListener.onProgressChanged(newProgress)
    }

    override fun onJsAlert(view: WebView, url: String, message: String, result: JsResult): Boolean {
        val builder = AlertDialog.Builder(view.context)
        builder.setTitle("提示")
                .setMessage(message)
                .setPositiveButton("确定") { dialog, which -> result.confirm() }
                .setOnKeyListener { dialog, keyCode, event -> true }
                .create()
                .show()
        return true
    }

    override fun onJsConfirm(view: WebView, url: String, message: String, result: JsResult): Boolean {
        val builder = AlertDialog.Builder(view.context)
        builder.setTitle("提示")
                .setMessage(message)
                .setPositiveButton("确定") { dialog, which -> result.confirm() }
                .setNeutralButton("取消") { dialog, which -> result.cancel() }
                .setOnCancelListener { result.cancel() }
                // 屏蔽keycode等于84之类的按键，避免按键后导致对话框消息而页面无法再弹出对话框的问题
                .setOnKeyListener { dialog, keyCode, event -> true }
                .create()
                .show()

        return true
    }

    override fun onJsPrompt(view: WebView, url: String, message: String, defaultValue: String, result: JsPromptResult): Boolean {
        val et = EditText(view.context)
        et.setSingleLine()
        et.setText(defaultValue)
        val builder = AlertDialog.Builder(view.context)
        builder.setTitle("提示")
                .setMessage(message)
                .setView(et)
                .setPositiveButton("确定") { dialog, which -> result.confirm() }
                .setNeutralButton("取消") { dialog, which -> result.cancel() }
                .setOnCancelListener { result.cancel() }
                // 屏蔽keycode等于84之类的按键，避免按键后导致对话框消息而页面无法再弹出对话框的问题
                .setOnKeyListener { dialog, keyCode, event -> true }
                .create()
                .show()
        return true
    }

    override fun onShowFileChooser(
        webView: WebView?,
        filePathCallback: ValueCallback<Array<Uri>>?,
        fileChooserParams: FileChooserParams?
    ): Boolean {
        webChromeListener.onShowFileChooser(filePathCallback, fileChooserParams)
        return true
    }

}
