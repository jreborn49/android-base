package cn.base.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import cn.base.entity.ViewPagerInfo

class BaseFragmentPagerAdapter(
    fm: FragmentManager,
    private val pagers: List<ViewPagerInfo>
) : FragmentPagerAdapter(fm, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT) {

    override fun getCount() = pagers.size

    override fun getItem(position: Int): Fragment {
        val item = pagers[position]
        val fragment = item.clazz!!.newInstance()
        fragment.arguments = item.args
        return fragment
    }

    override fun getPageTitle(position: Int): CharSequence? {
        return pagers[position].title
    }
}