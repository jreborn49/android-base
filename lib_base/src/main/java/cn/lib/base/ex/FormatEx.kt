package cn.lib.base.ex

fun second2minute(second: Int): Array<Int> {
    if (second <= 0) {
        return arrayOf(0, 0)
    }
    return arrayOf(second / 60, second % 60)
}