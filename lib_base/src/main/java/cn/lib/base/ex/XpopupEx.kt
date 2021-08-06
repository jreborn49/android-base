package cn.lib.base.ex

import android.content.Context
import com.lxj.xpopup.XPopup
import com.lxj.xpopup.impl.BottomListPopupView

fun Context.bottomListPopup(
    title: String? = null,
    data: List<String>,
    cancelable: Boolean = true,
    selected: ((Int) -> Unit)? = null
) {
    XPopup.Builder(this)
        .dismissOnTouchOutside(cancelable)
        .dismissOnBackPressed(cancelable)
        .isDestroyOnDismiss(true)
        .asCustom(
            BottomListPopupView(this, 0, 0)
                .setStringData(title, data.toTypedArray(), null)
                .setOnSelectListener { position, text ->
                    selected?.invoke(position)
                }
        )
        .show()
}