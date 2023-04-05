package com.example.kite.constants

/*

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.core.content.ContextCompat.getSystemService
import com.example.kite.MainActivity
import com.example.kite.R




class NotificationManager(context: Context) {

    private lateinit var notificationChannel: NotificationChannel
    private lateinit var notificationManager: NotificationManager
    private lateinit var notificationBuilder: Notification.Builder
    private val channelId = "ChannelId"



    private fun makeSimpleNotification() {
        getNotificationChannel()

        notificationBuilder = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            Notification.Builder(this, channelId)
                .setContentTitle(getString(R.string.your_title))
                .setContentText(getString(R.string.your_body))
                .setSmallIcon(R.mipmap.app_icon)
                .setContentIntent(setPendingIntent(this))
        } else {
            Notification.Builder(this)
                .setContentTitle(getString(R.string.your_title))
                .setContentText(getString(R.string.your_body))
                .setSmallIcon(R.mipmap.app_icon)
                .setContentIntent(setPendingIntent(this))
        }
        notificationManager.notify(1, notificationBuilder.build())
    }
    */
/*
        * ABOUT NOTIFICATION CHANNEL
          Channels were introduced in Oreo, and allow users to select which notifications their applications
          can show them. As application developers we should our notifications based on their topic. If
          an application uses only one channel for all of its notifications,the user would not be able
          to select which notifications they want to see and if they blocked one channel,
          they would no longer get notifications from the application.
          *
          *//*

    private fun getNotificationChannel() {
        notificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            notificationChannel = NotificationChannel(
                channelId,
                getString(R.string.description_of_your_channel),
                NotificationManager.IMPORTANCE_HIGH
            ).also {
                it.enableLights(true)
                it.enableVibration(true)
            }
            notificationManager.createNotificationChannel(notificationChannel)
        }

        */
/*
       * ABOUT PENDING INTENT
       A PendingIntent is a token that you give to a foreign application
       which allows the foreign application to use your application's permissions
       to execute a predefined piece of code
       *
       *//*

        fun setPendingIntent(context: Context): PendingIntent {
            val intent = Intent(context, MainActivity::class.java)
            return PendingIntent.getActivity(
                context,
                0,
                intent,
                PendingIntent.FLAG_IMMUTABLE
            )
        }
    }
}
*/
