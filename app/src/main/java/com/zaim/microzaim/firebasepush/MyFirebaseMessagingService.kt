package com.zaim.microzaim.firebasepush

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent

import android.os.Build
import android.widget.RemoteViews
import androidx.core.app.NotificationCompat
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import com.zaim.microzaim.MainActivity
import com.zaim.microzaim.R

const val chanelId = "chanel_id"
const val chanelName = "chanel_Name"

class MyFirebaseMessagingService : FirebaseMessagingService() {

    override fun onMessageReceived(remoteMessage: RemoteMessage) {
        if (remoteMessage.notification != null) {
            try {
                generateNotification(
                    remoteMessage.notification!!.title!!,
                    remoteMessage.notification!!.body!!,
                )
            }catch (_:java.lang.Exception){

            }

        }
    }

    fun generateNotification(title: String, message: String) {
        val intent = Intent(this, MainActivity::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
        val pendingIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_ONE_SHOT)
        var builder = NotificationCompat.Builder(applicationContext, chanelId)
            .setSmallIcon(R.drawable.ic_baseline_notifications_active_24)
            .setAutoCancel(true)
            .setVibrate(longArrayOf(1000, 1000, 1000, 1000))
            .setOnlyAlertOnce(true)
            .setContentIntent(pendingIntent)
        builder = builder.setContent(getRemoveView(title, message))
        val notificationManager =
            getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val notificationChannel =
                NotificationChannel(chanelId, chanelName, NotificationManager.IMPORTANCE_HIGH)
            notificationManager.createNotificationChannel(notificationChannel)
        }
        notificationManager.notify(0, builder.build())
    }

    private fun getRemoveView(title: String, message: String): RemoteViews? {
        val remoteView =
            RemoteViews("com.zaim.microzaim.firebasepush", R.layout.push_notification)
        remoteView.setTextViewText(R.id.tvTitle, title)
        remoteView.setTextViewText(R.id.tvMessage, message)
        remoteView.setImageViewResource(R.id.imLogo, R.drawable.ic_baseline_notifications_active_24)
        return remoteView
    }
}