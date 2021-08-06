package com.lib.update

import android.content.Context
import android.os.Handler
import android.os.Message
import android.view.KeyEvent
import android.widget.ProgressBar
import cn.lib.base.BaseDialog
import com.azhon.appupdate.listener.OnDownloadListener
import java.io.File

class DefaultDownloadListener(val context: Context) : OnDownloadListener {

    val progressDialog = BaseDialog(context, R.layout.dialog_new_version_progress)
    val progerssBar = progressDialog.findViewById<ProgressBar>(R.id.progressBar)

    private val handler = object : Handler(context.mainLooper) {
        override fun handleMessage(msg: Message) {
            super.handleMessage(msg)
            when (msg.what) {
                0 -> {
                    progressDialog.show()
                }
                1 -> {
                    progerssBar.progress = (msg.arg1 * 100F / msg.arg2).toInt()
                }
                2 -> {
                    progressDialog.dismiss()
                }
            }
        }
    }

    init {
        progressDialog.setCancelable(false)
        progressDialog.setCanceledOnTouchOutside(false)
        progressDialog.setOnKeyListener { dialog, keyCode, event -> keyCode == KeyEvent.KEYCODE_SEARCH }
    }

    override fun downloading(max: Int, progress: Int) {
        val msg = Message()
        msg.arg1 = progress
        msg.arg2 = max
        msg.what = 1
        handler.sendMessage(msg)
    }

    override fun start() {
        val msg = Message()
        msg.what = 0
        handler.sendMessage(msg)
    }

    override fun done(apk: File?) {
        val msg = Message()
        msg.what = 2
        handler.sendMessage(msg)
    }

    override fun cancel() {
        val msg = Message()
        msg.what = 2
        handler.sendMessage(msg)
    }

    override fun error(e: Exception?) {
        e?.printStackTrace()
        val msg = Message()
        msg.what = 2
        handler.sendMessage(msg)
    }
}