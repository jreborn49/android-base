package cn.base.base

import androidx.viewbinding.ViewBinding

abstract class BaseFragment<VB : ViewBinding, VM : BaseViewModel> :
    BaseBindFragment<VB>() {

    val viewModel: VM by lazy { viewModel() }

    abstract fun viewModel(): VM

}