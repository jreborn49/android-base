package cn.base.adapter

import android.annotation.SuppressLint
import cn.lib.base.ex.click
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.viewholder.BaseViewHolder

abstract class BaseClicksQuickAdapter<T, K : BaseViewHolder> : BaseQuickAdapter<T, K> {

    constructor(layoutResId: Int, data: MutableList<T>) : super(layoutResId, data)

    constructor(layoutResId: Int) : super(layoutResId)

    override fun convert(holder: K, item: T) {
        convertEx(holder, item)
        bindViewClickListener(holder)
    }

    abstract fun convertEx(holder: K, item: T)

    @SuppressLint("CheckResult")
    private fun bindViewClickListener(holder: K) {
        getOnItemClickListener()?.let {
            holder.itemView.click {
                setOnItemClick(holder.itemView, holder.adapterPosition - headerLayoutCount)
            }
        }
    }
}