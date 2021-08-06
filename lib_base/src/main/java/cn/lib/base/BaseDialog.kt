package cn.lib.base

import android.app.Dialog
import android.content.Context
import android.os.Bundle

open class BaseDialog(context: Context, resId: Int, themeResId: Int = R.style.BaseDialog) :
    Dialog(context, themeResId) {

    init {
        this.setContentView(resId)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        initAttribute()
        initView()
        initData()
    }

    open fun initAttribute() {

    }

    open fun initData() {

    }

    open fun initView() {

    }
}