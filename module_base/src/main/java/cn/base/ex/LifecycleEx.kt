package cn.base.ex

import androidx.lifecycle.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.CoroutineStart
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import kotlin.coroutines.CoroutineContext
import kotlin.coroutines.EmptyCoroutineContext

fun LifecycleOwner.launchLifeScope(
    context: CoroutineContext = EmptyCoroutineContext,
    start: CoroutineStart = CoroutineStart.DEFAULT,
    block: suspend CoroutineScope.() -> Unit
): Job {
    return lifecycleScope.launch(context, start, block)
}

fun LifecycleOwner.startedLifeScope(
    block: suspend CoroutineScope.() -> Unit
): Job {
    return lifecycleScope.launchWhenStarted(block)
}

fun LifecycleOwner.createdLifeScope(
    block: suspend CoroutineScope.() -> Unit
): Job {
    return lifecycleScope.launchWhenCreated(block)
}

fun LifecycleOwner.resumedLifeScope(
    block: suspend CoroutineScope.() -> Unit
): Job {
    return lifecycleScope.launchWhenResumed(block)
}

fun <T : ViewModel> ViewModelStoreOwner.getViewModel(modelClass: Class<T>): T {
    return ViewModelProvider(this).get(modelClass)
}