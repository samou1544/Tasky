package com.asma.tasky.feature_management.domain.notification_service

import android.app.NotificationManager
import android.app.PendingIntent
import android.app.TaskStackBuilder
import android.content.Context
import android.content.Intent
import android.net.Uri
import androidx.core.app.NotificationCompat
import com.asma.tasky.R
import com.asma.tasky.feature_management.domain.AgendaItem

class ReminderNotificationService(private val context: Context) {

    private val notificationManager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

    fun showNotification(agendaItem: AgendaItem) {
        val intent = Intent(Intent.ACTION_VIEW, Uri.parse("https://tasky.asma.com/task/${agendaItem.id}"))
        val pendingIntent = TaskStackBuilder.create(context).run {
            addNextIntentWithParentStack(intent)
            getPendingIntent(
                0,
                PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
            )
        }
//
//        activityIntent.putExtra("itemType", agendaItem.javaClass)
//        activityIntent.putExtra("itemId", agendaItem.id)
//        val activityPendingIntent = PendingIntent.getActivity(
//            context,
//            1,
//            activityIntent,
//            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
//        )
        val notification = NotificationCompat.Builder(context, REMINDER_CHANNEL_ID)
            .setSmallIcon(R.drawable.tasky_logo)
            .setContentTitle(agendaItem.title)
            .setContentText(agendaItem.description)
            .setContentIntent(pendingIntent)
            .build()

        notificationManager.notify(1, notification)
    }

    companion object {
        const val REMINDER_CHANNEL_ID = "reminder_channel"
    }
}
