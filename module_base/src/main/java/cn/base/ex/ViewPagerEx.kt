package cn.base.ex

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager.widget.ViewPager
import androidx.viewpager2.widget.ViewPager2
import cn.base.adapter.BaseFragmentPagerAdapter
import cn.base.adapter.BaseFragmentStateAdapter
import cn.base.entity.ViewPagerInfo

fun ViewPager2.bindAdapter(fm: FragmentManager, lifecycle: Lifecycle, fragments: List<Fragment>) {
    this.adapter = BaseFragmentStateAdapter(fm, lifecycle, fragments)
    this.offscreenPageLimit = fragments.size
}

fun ViewPager.bindAdapter(fm: FragmentManager, pagers: List<ViewPagerInfo>) {
    this.adapter = BaseFragmentPagerAdapter(fm, pagers)
    this.offscreenPageLimit = pagers.size
}