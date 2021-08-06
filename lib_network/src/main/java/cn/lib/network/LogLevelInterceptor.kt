package cn.lib.network

import okhttp3.Interceptor
import okhttp3.Response
import okhttp3.logging.HttpLoggingInterceptor

const val HEADER_UPLOAD = "Upload-File"
const val HEADER_UPLOAD_VALUE = "$HEADER_UPLOAD:true"

class LogLevelInterceptor(private val loggingInterceptor: HttpLoggingInterceptor) : Interceptor {

    val level = loggingInterceptor.level

    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()
        loggingInterceptor.level = if (!request.header(HEADER_UPLOAD).isNullOrEmpty()) {
            HttpLoggingInterceptor.Level.HEADERS
        } else {
            level
        }

        return chain.proceed(request)
    }
}