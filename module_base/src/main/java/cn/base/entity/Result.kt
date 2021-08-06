package cn.base.entity

import cn.lib.network.ResponseThrowable

sealed class Result<T> {

    data class Success<T>(val data: T? = null, val code: Int = 0) : Result<T>() {

        fun isSucceed(): Boolean = code == 0
    }

    data class Error<T>(val exception: ResponseThrowable) : Result<T>()
}