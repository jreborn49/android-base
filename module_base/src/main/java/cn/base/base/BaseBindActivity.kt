package cn.base.base

import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.viewbinding.ViewBinding
import cn.base.R
import cn.base.widget.TitleBar
import cn.lib.base.ex.gone
import cn.lib.base.ex.logD
import cn.lib.base.ex.visible
import com.blankj.utilcode.util.BarUtils
import com.xiaojinzi.component.Component
import org.greenrobot.eventbus.EventBus
import java.lang.reflect.ParameterizedType

abstract class BaseBindActivity<VB : ViewBinding> : AppCompatActivity() {

    val binding: VB by lazy { createViewBinding() }
    var titleBar: TitleBar? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        BarUtils.setStatusBarColor(this, Color.TRANSPARENT)
        BarUtils.setStatusBarLightMode(this, isUserLightMode())

        Component.inject(this)

        setContentView(binding.root)

        if (eventBus() && !EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().register(this)
        }
        initAttribute()
        initTitleBar()
        initView()
        viewModelObserve()
        initData()

        logD("${javaClass.simpleName} onCreate")
    }

    private fun initTitleBar() {
        titleBar = binding.root.findViewById(R.id.title_bar)
        initTitle()?.let {
            titleBar?.bindTitle(it)
        }
    }

    open fun initTitle(): TitleBar.Title? {
        return if (titleText() == null) {
            null
        } else {
            TitleBar.Title(title = titleText()!!)
        }
    }

    open fun titleText(): String? = null

    open fun initAttribute() {}

    open fun initView() {}

    open fun initData() {}

    open fun isUserLightMode(): Boolean = true

    open fun eventBus(): Boolean = false

    abstract fun viewModelObserve()

    override fun onResume() {
        super.onResume()
        logD("${javaClass.simpleName} onResume")
    }

    override fun onStop() {
        super.onStop()
        logD("${javaClass.simpleName} onStop")
    }

    override fun onPause() {
        super.onPause()
        logD("${javaClass.simpleName} onPause")
    }

    override fun onDestroy() {
        if (eventBus() && EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().unregister(this)
        }
        super.onDestroy()
        logD("${javaClass.simpleName} onDestroy")
    }

    @Suppress("UNCHECKED_CAST")
    open fun createViewBinding(): VB {
        val type = javaClass.genericSuperclass as ParameterizedType
        val clazz = type.actualTypeArguments[0] as Class<VB>
        val method = clazz.getMethod("inflate", LayoutInflater::class.java)
        method.isAccessible = true
        return method.invoke(null, layoutInflater) as VB
    }

    fun holderHide() {
        holderView()?.gone()
    }

    fun holderShow() {
        holderView()?.visible()
    }

    open fun holderView(): View? = null
}
