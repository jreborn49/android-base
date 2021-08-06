package cn.base.base

import cn.base.entity.PageResult
import cn.base.entity.RestResult
import cn.lib.base.Constants
import com.chad.library.adapter.base.entity.MultiItemEntity

abstract class BaseListRepository<T : MultiItemEntity> : BaseRepository() {

    var pageNo = 1
    private var pageSize = Constants.PAGE_SIZE

    suspend fun refresh(loadParams: HashMap<String, String>? = null): PageResult<T> {
        pageNo = 1
        val page = PageResult<T>()
        page.refresh = true
        try {
            val result = getSource(defaultLoadParams(loadParams))
            if (result.isSucceed()) {
                if (result.data?.isNullOrEmpty() == true) {
                    page.empty = true
                } else {
                    if (result.data.size < pageSize) {
                        page.end = true
                    } else {
                        pageNo++
                    }
                    page.list = result.data
                }
            } else {
                page.error = true
            }
        } catch (e: Throwable) {
            e.printStackTrace()
            page.errorThrowable = e
            page.error = true
        }
        return page
    }

    suspend fun nextPage(loadParams: HashMap<String, String>? = null): PageResult<T> {
        val page = PageResult<T>()
        try {
            val result = getSource(defaultLoadParams(loadParams))
            if (result.isSucceed()) {
                if (result.data == null || result.data.size < pageSize) {
                    page.end = true
                } else {
                    pageNo++
                }
                page.list = result.data
            } else {
                page.error = true
            }
        } catch (e: Throwable) {
            e.printStackTrace()
            page.errorThrowable = e
            page.error = true
        }
        return page
    }

    private fun defaultLoadParams(loadParams: HashMap<String, String>? = null): HashMap<String, String> {
        val defaultParams = hashMapOf(
            Constants.PAGE to pageNo.toString(),
            Constants.ROWS to pageSize.toString()
        )
        loadParams?.let {
            defaultParams.putAll(it)
        }
        return defaultParams
    }

    abstract suspend fun getSource(params: HashMap<String, String>): RestResult<MutableList<T>>
}