package cn.base.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.viewbinding.ViewBinding
import cn.lib.base.ex.logE
import org.greenrobot.eventbus.EventBus

abstract class BaseBindFragment<VB : ViewBinding> : Fragment() {

    val binding: VB by lazy { viewBinding() }

    var loadedData = false
    var inited = false

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        if (!inited) {
            inited = true

            if (eventBus() && !EventBus.getDefault().isRegistered(this)) {
                EventBus.getDefault().register(this)
            }

            initAttribute()
            initView()
            viewModelObserve()
            if (!isLazyMode()) {
                initData()
            }
        }
    }

    override fun onResume() {
        super.onResume()
        if (isLazyMode() && !loadedData && inited) {
            loadedData = true
            initData()
        }
    }

    override fun onDestroyView() {
        if (eventBus()) {
            EventBus.getDefault().unregister(this)
        }
        super.onDestroyView()
    }

    open fun eventBus(): Boolean = false

    open fun initAttribute() {}

    open fun initView() {}

    open fun initData() {}

    open fun isLazyMode(): Boolean = true

    abstract fun viewBinding(): VB

    abstract fun viewModelObserve()
}