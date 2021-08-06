package cn.base.ex

import android.widget.TextView
import androidx.annotation.IdRes
import cn.lib.base.ex.gone
import cn.lib.base.ex.invisible
import cn.lib.base.ex.visible
import com.chad.library.adapter.base.viewholder.BaseViewHolder

fun BaseViewHolder.setTextWithCheck(
    @IdRes viewId: Int,
    value: CharSequence?,
    gone: Boolean = true
): BaseViewHolder {
    val tv = getView<TextView>(viewId)
    if (value.isNullOrEmpty()) {
        if (gone) {
            tv.gone()
        } else {
            tv.invisible()
        }
    } else {
        tv.visible()
        tv.text = value
    }
    return this
}