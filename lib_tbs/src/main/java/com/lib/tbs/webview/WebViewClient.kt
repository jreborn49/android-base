package com.lib.tbs.webview

import android.content.Intent
import android.net.Uri
import android.net.http.SslError
import com.tencent.smtt.export.external.interfaces.SslErrorHandler
import com.tencent.smtt.sdk.WebView

class WebViewClient(var webChromeListener: WebChromeListener) :
    com.tencent.smtt.sdk.WebViewClient() {

    companion object {
        const val HTTP_PREFIX = "http://"
        const val HTTPS_PREFIX = "https://"
    }

    override fun shouldOverrideUrlLoading(view: WebView, url: String): Boolean {
        if (url.startsWith(HTTP_PREFIX) || url.startsWith(HTTPS_PREFIX)) {
            view.loadUrl(url)
        } else {
            try {
                val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
                view.context.startActivity(intent)
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
        return true
    }

    override fun onPageFinished(view: WebView, url: String) {
        super.onPageFinished(view, url)
        view.settings.blockNetworkImage = false
        webChromeListener.onPageFinish(url)
    }

    override fun onReceivedSslError(
        view: WebView?,
        handler: SslErrorHandler?,
        error: com.tencent.smtt.export.external.interfaces.SslError?
    ) {
        handler?.proceed()
    }

}
