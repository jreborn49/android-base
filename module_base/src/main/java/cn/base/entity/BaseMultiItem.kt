package cn.base.entity

import com.chad.library.adapter.base.entity.MultiItemEntity

open class BaseMultiItem : MultiItemEntity {

    companion object {
        const val DEFAULT = 0
    }

    override val itemType: Int
        get() = DEFAULT

}