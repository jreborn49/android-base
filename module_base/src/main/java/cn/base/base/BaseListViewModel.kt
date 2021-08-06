package cn.base.base

import androidx.lifecycle.MutableLiveData
import cn.base.entity.PageResult
import com.chad.library.adapter.base.entity.MultiItemEntity
import kotlinx.coroutines.Dispatchers

abstract class BaseListViewModel<T : MultiItemEntity, R : BaseListRepository<T>> : BaseViewModel() {

    val repository: R by lazy { getListRepository() }
    val pageData = MutableLiveData<PageResult<T>>()

    abstract fun getListRepository(): R

    open fun refresh(loadParams: HashMap<String, String>? = null) {
        launchScope(Dispatchers.IO) {
            pageData.postValue(repository.refresh(loadParams))
        }
    }

    open fun nextPage(loadParams: HashMap<String, String>? = null) {
        launchScope(Dispatchers.IO) {
            pageData.postValue(repository.nextPage(loadParams))
        }
    }


}