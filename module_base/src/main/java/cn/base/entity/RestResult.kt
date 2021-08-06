package cn.base.entity

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class RestResult<T>(
    val code: Int = -1,
    val msg: String = "",
    val data: T? = null
) {
    fun isSucceed(): Boolean = code == 0
}