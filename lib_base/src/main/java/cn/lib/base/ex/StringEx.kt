package cn.lib.base.ex

const val UTF_8 = "UTF-8"
const val EMPTY = ""
const val DOT = "."

fun CharSequence?.isNotNullOrEmpty(): Boolean {
    return !this.isNullOrEmpty()
}

fun String?.judgeNull(): String {
    return if (this.isNullOrEmpty() || this == "null") {
        ""
    } else {
        this
    }
}

fun <T> splice(objs: List<T>, splice: String = ",", predicate: (T) -> String?): String {
    val buffer = StringBuffer()
    objs.forEachIndexed { index, t ->
        predicate.invoke(t)?.let {
            buffer.append(it)
        }
        if (index < (objs.size - 1)) {
            buffer.append(splice)
        }
    }
    return buffer.toString()
}

fun String?.isImageUrl(): Boolean {
    if (isNullOrEmpty()) return false
    return endsWith(".jpg", true) || endsWith(".png", true)
            || endsWith(".jpeg", true) || endsWith(".bmp", true)
            || endsWith(".gif", true) || endsWith(".webp", true)
            || endsWith(".svg", true) || endsWith(".psd", true)
}