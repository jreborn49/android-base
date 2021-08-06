package cn.base.entity

class PageResult<T>{
    var refresh: Boolean = false
    var empty: Boolean = false
    var end: Boolean = false
    var error: Boolean = false
    var errorThrowable: Throwable? = null
    var list: MutableList<T>? = null
}