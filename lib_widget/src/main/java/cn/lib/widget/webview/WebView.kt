package cn.lib.widget.webview

import android.content.Context
import android.os.Build
import android.util.AttributeSet
import android.view.View
import android.webkit.CookieManager
import android.webkit.WebSettings
import android.webkit.WebView
import java.lang.reflect.InvocationTargetException

class WebView : WebView {

    constructor(context: Context) : super(context) {
        initSetting()
    }

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {
        initSetting()
    }

    constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int) : super(
        context,
        attrs,
        defStyleAttr
    ) {
        initSetting()
    }

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
        settings.mixedContentMode = WebSettings.MIXED_CONTENT_ALWAYS_ALLOW
        // 先阻塞图片
        settings.blockNetworkImage = true

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            settings.allowUniversalAccessFromFileURLs = true
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

        setLayerType(View.LAYER_TYPE_HARDWARE, null)

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
}
