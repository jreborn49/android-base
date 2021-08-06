package cn.lib.base.ex

import android.content.Context
import android.text.TextUtils
import androidx.lifecycle.LifecycleOwner
import cn.lib.base.R
import com.afollestad.materialdialogs.MaterialDialog
import com.afollestad.materialdialogs.lifecycle.lifecycleOwner
import com.afollestad.materialdialogs.list.ItemListener
import com.afollestad.materialdialogs.list.listItems

fun Context.basicConfirm(
    title: String? = null,
    message: String
) {
    confirm(title, message, positiveText = null, negativeText = null)
}

fun Context.simpleConfirm(
    title: String? = null,
    message: String,
    cancelable: Boolean = true,
    positiveText: String = getString(R.string.confirm),
    positiveMethod: (() -> Unit)? = null
) {
    confirm(title, message, cancelable, positiveText, positiveMethod, negativeText = null)
}

fun Context.basicListConfirm(
    title: String? = null,
    items: List<String>,
    selectMethod: (Int) -> Unit
) {
    confirm(
        title,
        positiveText = null,
        negativeText = null,
        items = items,
        selectMethod = selectMethod
    )
}

fun Context.simpleListConfirm(
    title: String? = null,
    cancelable: Boolean = true,
    items: List<String>,
    selectMethod: (Int) -> Unit,
    positiveText: String = getString(R.string.confirm),
) {
    confirm(
        title,
        cancelable = cancelable,
        positiveText = positiveText,
        negativeText = null,
        items = items,
        selectMethod = selectMethod
    )
}

fun Context.confirm(
    title: String? = null,
    message: String? = null,
    cancelable: Boolean = true,
    positiveText: String? = getString(R.string.confirm),
    positiveMethod: (() -> Unit)? = null,
    negativeText: String? = getString(R.string.cancel),
    negativeMethod: (() -> Unit)? = null,
    items: List<String>? = null,
    selectMethod: ((Int) -> Unit)? = null,
) {
    val dialog = MaterialDialog(this)
    if (!TextUtils.isEmpty(title)) {
        dialog.title(text = title)
    }
    dialog.cancelable(cancelable)
        .cancelOnTouchOutside(cancelable)

    message?.let {
        dialog.message(text = it)
    }

    items?.let {
        dialog.listItems(items = items, selection = object : ItemListener {
            override fun invoke(dialog: MaterialDialog, index: Int, text: CharSequence) {
                selectMethod?.invoke(index)
            }
        })
    }
    positiveText?.let {
        dialog.positiveButton(text = it) {
            positiveMethod?.invoke()
        }
    }
    negativeText?.let {
        dialog.negativeButton(text = it) {
            negativeMethod?.invoke()
        }
    }
    dialog.show {
        if (this@confirm is LifecycleOwner) {
            lifecycleOwner(this@confirm)
        }
    }
}