package com.lib.tbs.webview

import android.net.Uri
import com.tencent.smtt.sdk.ValueCallback
import com.tencent.smtt.sdk.WebChromeClient

interface WebChromeListener {

    fun onPageFinish(url: String)

    fun onReceivedTitle(title: String)

    fun onProgressChanged(newProgress: Int)

    fun onShowFileChooser(
        filePathCallback: ValueCallback<Array<Uri>>?,
        fileChooserParams: WebChromeClient.FileChooserParams?
    )
}
