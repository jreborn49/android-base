package cn.lib.widget.recyclerview

import android.content.Context
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.DividerItemDecoration
import cn.lib.widget.R

class DefaultItemDecoration(context: Context, orientation: Int) :
    DividerItemDecoration(context, orientation) {

    init {
        ContextCompat.getDrawable(context, R.drawable.shape_default_item_decoration)
            ?.let { setDrawable(it) }
    }
}