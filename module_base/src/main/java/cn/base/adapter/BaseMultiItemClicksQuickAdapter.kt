package cn.base.adapter

import android.annotation.SuppressLint
import cn.lib.base.ex.click
import com.chad.library.adapter.base.BaseMultiItemQuickAdapter
import com.chad.library.adapter.base.entity.MultiItemEntity
import com.chad.library.adapter.base.viewholder.BaseViewHolder

abstract class BaseMultiItemClicksQuickAdapter<T : MultiItemEntity, K : BaseViewHolder> :
    BaseMultiItemQuickAdapter<T, K>(mutableListOf()) {

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