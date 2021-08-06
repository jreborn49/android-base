package com.lib.notification

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.ContextWrapper
import android.graphics.Bitmap
import android.net.Uri
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat

class NotificationUtils(
    context: Context,
    private val channelId: String,
    private val channelName: String,
    private val ongoing: Boolean = false
) : ContextWrapper(context) {

    private var mManager: NotificationManager? = null

    private val manager: NotificationManager?
        get() {
            if (mManager == null) {
                mManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            }
            return mManager
        }

    @RequiresApi(api = Build.VERSION_CODES.O)
    fun createNotificationChannel(sound: Uri? = null, vibrate: Boolean = true) {
        val channel =
            NotificationChannel(channelId, channelName, NotificationManager.IMPORTANCE_HIGH)
        if (sound != null) {
            channel.setSound(sound, Notification.AUDIO_ATTRIBUTES_DEFAULT)
        } else {
            channel.setSound(null, null)
        }
        channel.enableVibration(vibrate)
        if (vibrate) {
            channel.vibrationPattern = longArrayOf(0, 600, 300, 400)
        }
        channel.importance = NotificationManager.IMPORTANCE_HIGH
        manager!!.createNotificationChannel(channel)
    }

    fun getNotification(
        title: String,
        content: String,
        largeIcon: Bitmap?,
        pendingIntents: PendingIntent? = null,
        sound: Uri? = null,
        vibrate: Boolean = true
    ): NotificationCompat.Builder {
        val builder = NotificationCompat.Builder(applicationContext, channelId)
            .setContentTitle(title)
            .setContentText(content)
            .setSmallIcon(NotificationConfig.config.notificationIcon())
            .setAutoCancel(true)
            .setOngoing(ongoing)
            .setShowWhen(true)
            .setWhen(System.currentTimeMillis())
            .setContentIntent(pendingIntents)
        largeIcon?.let {
            builder.setLargeIcon(it)
        }
        if (vibrate) {
            builder.setDefaults(Notification.DEFAULT_VIBRATE or Notification.DEFAULT_SOUND)
        } else {
            builder.setDefaults(Notification.DEFAULT_SOUND)
        }
        sound?.let {
            builder.setSound(it)
        }
        return builder
    }

    fun sendNotification(
        notificationId: Int,
        title: String,
        content: String,
        largeIcon: Bitmap? = null,
        pendingIntents: PendingIntent? = null,
        sound: Uri? = null,
        vibrate: Boolean = true
    ) {
        val notification =
            getNotification(title, content, largeIcon, pendingIntents, sound, vibrate).build()
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            createNotificationChannel(sound, vibrate)
        }
        manager?.notify(notificationId, notification)
    }

    companion object {

        fun cancelNotifyAll() {
            val manager = NotificationManagerCompat.from(NotificationConfig.config.application())
            manager.cancelAll()
        }
    }
}