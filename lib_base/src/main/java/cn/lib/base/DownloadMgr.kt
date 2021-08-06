package cn.lib.base

import android.app.DownloadManager
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.net.Uri
import android.os.Environment
import cn.lib.base.ex.toast
import java.io.File

object DownloadMgr {

    val DOWNLOAD_PATH = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS).absolutePath

    fun download(
        context: Context,
        url: String,
        title: String,
        des: String? = null,
        toastShow: Boolean = true
    ): Long {
        val uri = Uri.parse(url)
        val req = DownloadManager.Request(uri)
        req.setAllowedOverRoaming(true)
        req.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED)
        req.setTitle(title)
        if (des != null) {
            req.setDescription(des)
        }
//        val path = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
//            context.getExternalFilesDir(Environment.DIRECTORY_PICTURES)!!.absolutePath
//        } else {
//            Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS).absolutePath
//        }
//        req.setDestinationInExternalPublicDir(path, title)

//        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.Q) {
//            val path = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS).absolutePath
        req.setDestinationUri(Uri.fromFile(File(DOWNLOAD_PATH, title)))
//        }
        val downloadManager = context.getSystemService(Context.DOWNLOAD_SERVICE) as DownloadManager
        if (toastShow) {
            toast(context.getString(R.string.download_start, title))
        }
        val id = downloadManager.enqueue(req)

        val intentFilter = IntentFilter(DownloadManager.ACTION_DOWNLOAD_COMPLETE)
        val broadcastReceiver = object : BroadcastReceiver() {
            override fun onReceive(receiverCxt: Context, intent: Intent) {
                if (intent.getLongExtra(DownloadManager.EXTRA_DOWNLOAD_ID, -1) == id) {
                    toast(receiverCxt.getString(R.string.download_complete, title))
                }
                receiverCxt.unregisterReceiver(this)
            }
        }
        context.registerReceiver(broadcastReceiver, intentFilter)

        return id
    }
}