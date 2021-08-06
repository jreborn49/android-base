package cn.base.base

import androidx.viewbinding.ViewBinding
import cn.base.ex.getViewModel
import java.lang.reflect.ParameterizedType

abstract class BaseActivity<VB : ViewBinding, VM : BaseViewModel> : BaseBindActivity<VB>() {

    val viewModel: VM by lazy { createViewModel() }

    @Suppress("UNCHECKED_CAST")
    open fun createViewModel(): VM {
        val type = javaClass.genericSuperclass as ParameterizedType
        val clazz = type.actualTypeArguments[1] as Class<VM>
        return getViewModel(clazz)
    }

}
