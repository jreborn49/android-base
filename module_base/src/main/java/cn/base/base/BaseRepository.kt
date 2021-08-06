package cn.base.base

import cn.lib.network.ResponseThrowable
import cn.base.entity.RestResult
import cn.base.entity.Result

open class BaseRepository {

    suspend fun <T> apiCall(call: suspend () -> RestResult<T>): Result<T> {
        val result = call.invoke()
        return if (result.isSucceed()) {
            Result.Success(result.data, result.code)
        } else {
            Result.Error(ResponseThrowable(result.code, result.msg))
        }
    }

    suspend fun <T : Any> call(call: suspend () -> T): Result<T> {
        val result = call.invoke()
        return Result.Success(result)
    }

}