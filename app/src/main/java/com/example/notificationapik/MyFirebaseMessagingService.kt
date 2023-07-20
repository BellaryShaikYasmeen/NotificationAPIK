package com.example.notificationapik

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


const val channelid="yasmeen"
const val channelname="com.example.notificationapik"
class MyFirebaseMessagingService : FirebaseMessagingService() {

    override fun onMessageReceived(message: RemoteMessage) {
        if(message.notification!=null)
        {
            generateNotfication(message.notification!!.title!!, message.notification!!.body!!)
        }
    }

    lateinit var notificationManager: NotificationManager
    fun generateNotfication(title:String,message:String) {
        notificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        val i = Intent(this, MainActivity::class.java)
        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
        val pendingIntent = PendingIntent.getActivity(this, 0, i, PendingIntent.FLAG_ONE_SHOT)
        var builder: NotificationCompat.Builder =
            NotificationCompat.Builder(applicationContext, channelid)
                .setVibrate(longArrayOf(1000, 1000, 1000, 1000))
                .setSmallIcon(R.drawable.baseline_push_pin_24)
                .setAutoCancel(true)
                .setOnlyAlertOnce(true)
                .setContentIntent(pendingIntent)
        builder = builder.setContent(getRemoteView(title, message))
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val notificationChannel =
                NotificationChannel(channelid, channelname, NotificationManager.IMPORTANCE_HIGH)

            notificationManager.createNotificationChannel(notificationChannel)
        }
        notificationManager.notify(1234, builder.build())
    }

    private fun getRemoteView(title: String, message: String): RemoteViews? {
       val remoteViews=RemoteViews("com.example.notificationapik",R.layout.activity_after_notification)
        remoteViews.setTextViewText(R.id.txt_title,title)
        remoteViews.setTextViewText(R.id.txt_content,message)
        remoteViews.setImageViewResource(R.id.imageView,R.drawable.baseline_push_pin_24)
        return remoteViews

    }


}