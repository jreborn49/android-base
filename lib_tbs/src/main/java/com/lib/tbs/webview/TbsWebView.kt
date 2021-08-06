package com.lib.tbs.webview

import android.content.Context
import android.os.Build
import android.util.AttributeSet
import android.view.View
import com.tencent.smtt.sdk.CookieManager
import com.tencent.smtt.sdk.WebSettings
import com.tencent.smtt.sdk.WebView
import java.lang.reflect.InvocationTargetException

class TbsWebView : WebView {

    private fun initSetting() {
        setBackgroundColor(0)
        isHorizontalScrollBarEnabled = false
        isVerticalScrollBarEnabled = false
        settings.javaScriptEnabled = true
        settings.javaScriptCanOpenWindowsAutomatically = true
        //getSettings().setUserAgentString("Android/1.0 " + getSettings().getUserAgentString());
        settings.setRenderPriority(WebSettings.RenderPriority.HIGH)
        //设置存储模式
        settings.cacheMode = WebSettings.LOAD_DEFAULT
        //设置支持DomStorage
        settings.domStorageEnabled = true
        // 设置支持本地存储
        settings.databaseEnabled = true
        //设置缓存
        settings.setAppCacheEnabled(true)
        val cacheDirPath = context.cacheDir.absolutePath + "/web_cache"
        settings.databasePath = cacheDirPath
        settings.setAppCachePath(cacheDirPath)
        //设置适应屏幕
        settings.useWideViewPort = true
        settings.loadWithOverviewMode = true
        settings.setSupportZoom(true)
        settings.builtInZoomControls = true
        settings.displayZoomControls = false
        //允许webview对文件的操作
        settings.allowFileAccess = true
        // 开启混合模式，可以加载https和http，一般判决图片是http的问题
        settings.mixedContentMode = 0//WebSettings.MIXED_CONTENT_ALWAYS_ALLOW
        // 先阻塞图片
        settings.blockNetworkImage = true

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            settings.setAllowUniversalAccessFromFileURLs(true)
        } else {
            try {
                val clazz = settings.javaClass
                val method = clazz.getMethod(
                    "setAllowUniversalAccessFromFileURLs",
                    Boolean::class.javaPrimitiveType!!
                )
                method?.invoke(settings, true)
            } catch (e: NoSuchMethodException) {
                e.printStackTrace()
            } catch (e: InvocationTargetException) {
                e.printStackTrace()
            } catch (e: IllegalAccessException) {
                e.printStackTrace()
            }

        }

        val cookieManager = CookieManager.getInstance()
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            cookieManager.setAcceptThirdPartyCookies(this, true)
        }
        cookieManager.setAcceptCookie(true)

//        setLayerType(View.LAYER_TYPE_HARDWARE, null)

        removeJavascriptInterface("searchBoxJavaBridge_")
        removeJavascriptInterface("accessibility")
        removeJavascriptInterface("accessibilityTraversal")

        clearHistory()
    }

    override fun goBack() {
        settings.cacheMode = WebSettings.LOAD_CACHE_ELSE_NETWORK
        super.goBack()
    }

    override fun goBackOrForward(steps: Int) {
        settings.cacheMode = WebSettings.LOAD_CACHE_ELSE_NETWORK
        super.goBackOrForward(steps)
    }

    constructor(p0: Context?) : super(p0) {
        initSetting()
    }

    constructor(p0: Context?, p1: Boolean) : super(p0, p1) {
        initSetting()
    }

    constructor(p0: Context?, p1: AttributeSet?) : super(p0, p1) {
        initSetting()
    }

    constructor(p0: Context?, p1: AttributeSet?, p2: Int) : super(p0, p1, p2) {
        initSetting()
    }

    constructor(p0: Context?, p1: AttributeSet?, p2: Int, p3: Boolean) : super(p0, p1, p2, p3) {
        initSetting()
    }

    constructor(
        p0: Context?,
        p1: AttributeSet?,
        p2: Int,
        p3: MutableMap<String, Any>?,
        p4: Boolean
    ) : super(p0, p1, p2, p3, p4) {
        initSetting()
    }

}