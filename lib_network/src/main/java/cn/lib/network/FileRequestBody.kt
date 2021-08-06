package cn.lib.network

import okhttp3.MediaType
import okhttp3.RequestBody
import okio.BufferedSink
import okio.source
import java.io.InputStream

class FileRequestBody(
    private val inputStream: InputStream,
    private val contentType: MediaType? = null
) : RequestBody() {

    override fun contentType(): MediaType? {
        return contentType
    }

    override fun contentLength(): Long {
        return if (inputStream.available() == 0) -1 else inputStream.available().toLong()
    }

    override fun writeTo(sink: BufferedSink) {
        inputStream.source().use {
            sink.writeAll(it)
        }
    }
}