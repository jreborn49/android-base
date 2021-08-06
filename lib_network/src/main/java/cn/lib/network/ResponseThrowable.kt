package cn.lib.network

open class ResponseThrowable : Throwable {

    companion object {
        val SKIP_CODE_RESP by lazy {
            ResponseThrowable(701, "skip handle response throwable with code 701")
        }
    }

    var code: Int

    constructor(error: ERROR) : super(error.getValue()) {
        code = error.getKey()
    }

    constructor(code: Int, msg: String) : super(msg) {
        this.code = code
    }
}

