package cn.lib.network

import cn.lib.network.NetworkConfig.Companion.config

enum class ERROR(private val code: Int, private val err: String) {

    /**
     * 未知错误
     */
    UNKNOWN(1000, config.application().getString(R.string.system_error)),

    /**
     * 解析错误
     */
    PARSE_ERROR(1001, config.application().getString(R.string.system_error)),

    /**
     * 网络错误
     */
    NETWORK_ERROR(1002, config.application().getString(R.string.net_error)),

    /**
     * 协议出错
     */
    HTTP_ERROR(1003, config.application().getString(R.string.net_error)),

    /**
     * 证书出错
     */
    SSL_ERROR(1004, config.application().getString(R.string.system_error)),

    /**
     * 连接超时
     */
    TIMEOUT_ERROR(1006, config.application().getString(R.string.connect_error));

    fun getValue(): String {
        return err
    }

    fun getKey(): Int {
        return code
    }

}