package cn.base.base

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import cn.base.entity.Result
import cn.base.event.SingleLiveEvent
import cn.lib.base.ex.logE
import cn.lib.network.ExceptionHandler
import cn.lib.network.ResponseThrowable
import cn.lib.network.ResponseThrowable.Companion.SKIP_CODE_RESP
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.HttpException
import kotlin.coroutines.CoroutineContext
import kotlin.coroutines.EmptyCoroutineContext

open class BaseViewModel : ViewModel() {

    val showDialogEvent by lazy { SingleLiveEvent<String?>() }
    val dismissDialogEvent by lazy { SingleLiveEvent<String?>() }
    val toastEvent by lazy { SingleLiveEvent<String>() }

    fun launchScope(
        context: CoroutineContext = EmptyCoroutineContext,
        block: suspend CoroutineScope.() -> Unit
    ) = viewModelScope.launch(context) { block() }

    fun <T> launch(
        block: suspend CoroutineScope.() -> Result<T>,
        success: ((T?) -> Unit)? = null,
        successCode: ((T?, Int) -> Unit)? = null,
        error: ((ResponseThrowable) -> Unit)? = {
            toastEvent.postValue(it.message)
        },
        complete: (() -> Unit)? = null,
        showDialog: Boolean = true,
        dialogMsg: String? = null
    ) {
        launchScope {
            try {
                if (showDialog) showDialogEvent.value = dialogMsg
                val result = withContext(Dispatchers.IO) { block() }
                if (result is Result.Success) {
                    success?.invoke(result.data)
                    successCode?.invoke(result.data, result.code)
                } else if (result is Result.Error) {
                    error?.invoke(result.exception)
                }
            } catch (e: Throwable) {
                if ((e is HttpException && e.code() == SKIP_CODE_RESP.code) || e.message == SKIP_CODE_RESP.message) {
                    logE("skip handle response throwable")
                } else {
                    error?.invoke(ExceptionHandler.handleException(e))
                }
            } finally {
                if (showDialog) dismissDialogEvent.value = dialogMsg
                complete?.invoke()
            }
        }
    }

    fun <T> launch(
        block: suspend () -> T
    ): Flow<T> {
        return flow {
            emit(block())
        }.flowOn(Dispatchers.IO)
    }

}