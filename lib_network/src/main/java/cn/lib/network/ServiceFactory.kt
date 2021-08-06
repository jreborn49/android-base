package cn.lib.network

import android.util.Log
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Converter
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory
import java.util.concurrent.TimeUnit

object ServiceFactory {

    private const val TIME_OUT: Long = 60

    fun <T> createService(
        service: Class<T>,
        baseUrl: String,
        httpBuilder: OkHttpClient.Builder = createOkHttpClient(),
        converter: Converter.Factory = MoshiConverterFactory.create()
    ): T {
        return Retrofit.Builder()
            .client(httpBuilder.build())
            .baseUrl(baseUrl)
            .addConverterFactory(ScalarsConverterFactory.create())
            .addConverterFactory(converter)
            .build()
            .create(service)
    }

    fun createOkHttpClient(interceptors: List<Interceptor>? = null, timeout: Long = TIME_OUT): OkHttpClient.Builder {
        val builder = OkHttpClient.Builder()
            .connectTimeout(timeout, TimeUnit.SECONDS)
            .callTimeout(timeout, TimeUnit.SECONDS)
            .writeTimeout(timeout, TimeUnit.SECONDS)
            .readTimeout(timeout, TimeUnit.SECONDS)
        interceptors?.let { list ->
            list.forEach {
                builder.addInterceptor(it)
            }
        }

        if (NetworkConfig.config.isDebug()) {
            val logging = HttpLoggingInterceptor { Log.i("service_api", it) }
            logging.level = HttpLoggingInterceptor.Level.BODY

            builder.addInterceptor(LogLevelInterceptor(logging))
            builder.addInterceptor(logging)
        }

        return builder
    }

}