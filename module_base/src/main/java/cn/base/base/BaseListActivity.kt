package cn.base.base

import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import androidx.viewbinding.ViewBinding
import cn.base.adapter.BaseListAdapter
import cn.base.databinding.LayoutBaseListBinding
import cn.base.databinding.LayoutEmptyBinding
import cn.lib.base.ex.toast
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.entity.MultiItemEntity
import com.chad.library.adapter.base.listener.OnItemClickListener

abstract class BaseListActivity<VB : ViewBinding, VM : BaseListViewModel<T, R>, T : MultiItemEntity, R : BaseListRepository<T>> :
    BaseActivity<VB, VM>(), OnItemClickListener {

    val adapter: BaseListAdapter<T> by lazy { initAdapter() }
    val rv: RecyclerView? by lazy { if (binding is LayoutBaseListBinding) (binding as LayoutBaseListBinding).recyclerView else createRecyclerView() }
    val srl: SwipeRefreshLayout? by lazy { if (binding is LayoutBaseListBinding) (binding as LayoutBaseListBinding).refreshLayout else createRefreshLayout() }

    override fun initView() {
        adapter.setOnItemClickListener(this)
        adapter.loadMoreModule.setOnLoadMoreListener {
            viewModel.nextPage(loadParams())
        }

        rv?.layoutManager = getRecyeclerViewManager()
        rv?.adapter = adapter

        srl?.setColorSchemeResources(cn.base.R.color.colorAccent)
        srl?.setOnRefreshListener { viewModel.refresh(loadParams()) }
    }

    override fun viewModelObserve() {
        viewModel.toastEvent.observe(this) {
            toast(it)
        }

        viewModel.pageData.observe(this) { page ->
            if (page.refresh) {
                adapter.setList(page.list)
                srl?.isRefreshing = false
                if(page.list.isNullOrEmpty()) {
                    initEmptyView()?.let { adapter.setEmptyView(it) }
                }
                if (page.end) {
                    adapter.loadMoreModule.isEnableLoadMore = false
                }
            } else {
                page.list?.let { adapter.addData(it) }
                when {
                    page.error -> {
                        adapter.loadMoreModule.loadMoreFail()
                    }
                    page.end -> {
                        adapter.loadMoreModule.loadMoreEnd()
                    }
                    else -> {
                        adapter.loadMoreModule.loadMoreComplete()
                    }
                }
            }
        }
    }

    override fun initData() {
        srl?.isRefreshing = true
        viewModel.refresh(loadParams())
    }

    open fun loadParams(): HashMap<String, String>? = null

    open fun createRefreshLayout(): SwipeRefreshLayout? = null

    open fun createRecyclerView(): RecyclerView? = null

    abstract fun initAdapter(): BaseListAdapter<T>

    open fun getRecyeclerViewManager(): RecyclerView.LayoutManager =
        LinearLayoutManager(this)

    open fun initEmptyView(): View? = LayoutEmptyBinding.inflate(layoutInflater).root

    override fun onItemClick(adapter: BaseQuickAdapter<*, *>, view: View, position: Int) {

    }
}