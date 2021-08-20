package cn.base.ex

import android.text.Editable
import android.text.TextWatcher
import android.widget.TextView
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.buffer
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.debounce

@FlowPreview
@ExperimentalCoroutinesApi
fun TextView.textWatcherFlow(duration: Long = 500): Flow<String> = callbackFlow {
    val watcher = object : TextWatcher {
        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}

        override fun afterTextChanged(s: Editable?) {
            offer(s.toString())
        }
    }
    addTextChangedListener(watcher)
    awaitClose {
        removeTextChangedListener(watcher)
    }
}.buffer(Channel.CONFLATED).debounce(duration)