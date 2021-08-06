package cn.lib.glide.xpopup

import android.graphics.Color
import android.widget.ImageView
import com.lxj.xpopup.XPopup

fun ImageView.imageViewer(url: String?, raduis: Int = 0, save: Boolean = true) {
    if(url.isNullOrEmpty()) return
    XPopup.Builder(context)
        .isDestroyOnDismiss(true)
        .asImageViewer(
            this,
            url,
            false,
            -1,
            -1,
            raduis,
            save,
            Color.BLACK,
            ImageLoader(),
            null
        )
        .show()
}

fun ImageView.imageViewer(
    urls: List<String>,
    index: Int,
    bindViews: List<ImageView>? = null,
    raduis: Int = 0,
    save: Boolean = true
) {
    XPopup.Builder(context)
        .isDestroyOnDismiss(true)
        .asImageViewer(
            this,
            index,
            urls,
            false,
            false,
            -1,
            -1,
            raduis,
            save,
            Color.BLACK,
            { popupView, position -> bindViews?.let { popupView.updateSrcView(it[position]) }},
            ImageLoader(),
            null
        )
        .show()
}