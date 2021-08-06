package cn.base.entity

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment

data class ViewPagerInfo(
    val clazz: Class<out Fragment>? = null,
    val title: String? = null,
    val icon: Int = 0,
    var customView: View? = null,
    val args: Bundle? = null,
)