package cn.base.adapter

import com.chad.library.adapter.base.entity.MultiItemEntity
import com.chad.library.adapter.base.module.LoadMoreModule
import com.chad.library.adapter.base.viewholder.BaseViewHolder

abstract class BaseListAdapter<L : MultiItemEntity>(layoutIds: Map<Int, Int>) :
    BaseMultiItemClicksQuickAdapter<L, BaseViewHolder>(), LoadMoreModule {

    init {
        for ((k, v) in layoutIds) {
            addItemType(k, v)
        }
    }
}