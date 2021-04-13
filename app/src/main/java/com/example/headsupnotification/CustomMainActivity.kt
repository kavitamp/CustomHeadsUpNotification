package com.example.headsupnotification

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.view.View
import android.widget.RemoteViews
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.NotificationCompat

class CustomMainActivity : AppCompatActivity() {

    private var remoteView: RemoteViews? = null
    private var notification: Notification? = null
    private var notificationManager: NotificationManager? = null
    private val NotificationID = 1111
    private var builder: NotificationCompat.Builder? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    fun notifyMe(view: View?) {
        notificationManager = getSystemService(NOTIFICATION_SERVICE) as NotificationManager
        builder = NotificationCompat.Builder(applicationContext, "ChannelId-111")
        notificationManager!!.cancel(NotificationID)
        remoteView = RemoteViews(packageName, R.layout.notification_layout)
        val switchIntent = Intent(this, MainActivity::class.java)
        val pendingSwitchIntent = PendingIntent.getBroadcast(this, 1212, switchIntent, 0)
        builder!!.setSmallIcon(R.drawable.ic_launcher_background)
        builder!!.setFullScreenIntent(pendingSwitchIntent, true)
        builder!!.priority = Notification.PRIORITY_HIGH
        builder!!.build().flags = Notification.FLAG_NO_CLEAR or Notification.PRIORITY_HIGH
        builder!!.setContent(remoteView)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channelId = "ChannelId"
            val channel = NotificationChannel(channelId, "MyChannelName", NotificationManager.IMPORTANCE_HIGH)
            notificationManager!!.createNotificationChannel(channel)
            builder!!.setChannelId(channelId)
        }
        notification = builder!!.build()
        notificationManager!!.notify(NotificationID, notification)
    }
}