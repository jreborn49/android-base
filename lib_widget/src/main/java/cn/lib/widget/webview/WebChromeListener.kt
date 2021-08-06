package cn.lib.widget.webview

import android.net.Uri
import android.webkit.ValueCallback
import android.webkit.WebChromeClient

interface WebChromeListener {

    fun onPageFinish(url: String)

    fun onReceivedTitle(title: String)

    fun onProgressChanged(newProgress: Int)

    fun onShowFileChooser(
        filePathCallback: ValueCallback<Array<Uri>>?,
        fileChooserParams: WebChromeClient.FileChooserParams?
    )
}
