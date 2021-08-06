package cn.base.activity

import android.view.View
import android.view.ViewGroup
import android.webkit.WebView
import android.widget.ProgressBar
import androidx.viewbinding.ViewBinding
import cn.base.base.BaseBindActivity
import cn.base.databinding.ActivityWebviewBinding
import cn.lib.base.ex.EMPTY
import cn.lib.base.ex.logE
import cn.lib.widget.webview.WebChromeClient
import cn.lib.widget.webview.WebChromeListener
import cn.lib.widget.webview.WebViewClient

abstract class WebviewActivity<VB : ViewBinding> : BaseBindActivity<VB>(),
    WebChromeListener {

    private lateinit var url: String
    val wv: WebView? by lazy { if (binding is ActivityWebviewBinding) (binding as ActivityWebviewBinding).webview else createWebView() }
    private val progress: ProgressBar? by lazy { if (binding is ActivityWebviewBinding) (binding as ActivityWebviewBinding).progress else createProgressBar() }

    abstract fun initUrl(): String

    override fun initAttribute() {
        url = initUrl()
    }

    override fun initView() {
        wv?.webChromeClient = createWebChromeClient()
        wv?.webViewClient = createWebViewClient()
        logE("load url: $url")
        logE("load useragent: ${wv?.settings?.userAgentString}")
        wv?.loadUrl(url)
    }

    override fun onResume() {
        wv?.onResume()
        wv?.resumeTimers()
        super.onResume()
    }

    override fun onPause() {
        wv?.onPause()
        wv?.pauseTimers()
        super.onPause()
    }

    override fun onDestroy() {
        val parent = wv?.parent
        if (parent != null && wv != null) {
            (parent as ViewGroup).removeView(wv)
        }
        wv?.webChromeClient = null
        wv?.settings?.javaScriptEnabled = false
        wv?.stopLoading()
        wv?.clearHistory()
        wv?.removeAllViews()
        wv?.destroy()
        super.onDestroy()
    }

    override fun onBackPressed() {
        back()
    }

    fun back() {
        when {
            url == wv?.url -> finish()
            wv?.canGoBack() == true -> wv?.goBack()
            else -> finish()
        }
    }

    override fun onReceivedTitle(title: String) {
        logE("web title: $title")
        if (title.startsWith("about:") || title.startsWith("http://")
            || title.startsWith("https://") || title.startsWith("www.")
            || title.contains("undefined")
        ) {
            titleBar?.setTitleText(EMPTY)
        } else {
            titleBar?.setTitleText(title)
        }
    }

    override fun titleText(): String = EMPTY

    override fun onProgressChanged(newProgress: Int) {
        progress?.progress = newProgress
        if (newProgress >= 100) {
            progress?.visibility = View.GONE
        } else {
            if (progress?.visibility == View.GONE) {
                progress?.visibility = View.VISIBLE
            }
        }
    }

    override fun onPageFinish(url: String) {

    }

    open fun createWebChromeClient(): android.webkit.WebChromeClient {
        return WebChromeClient(this)
    }

    open fun createWebViewClient(): android.webkit.WebViewClient {
        return WebViewClient(this)
    }

    open fun createWebView(): WebView? = null

    open fun createProgressBar(): ProgressBar? = null

    override fun viewModelObserve() {

    }

}
