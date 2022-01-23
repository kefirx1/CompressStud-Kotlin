package pl.dev.kefirx.reminder

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.app.TaskStackBuilder
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import androidx.core.app.NotificationCompat
import pl.dev.kefirx.MainActivity
import pl.dev.kefirx.R


class NotificationReceiver: BroadcastReceiver() {

    companion object {
        const val TITLE_EXTRA = "titleExtra"
        const val MESSAGE_EXTRA = "messageExtra"
        const val CHANNEL_EXTRA = "channelExtra"
        const val NOTIFICATION_EXTRA = "notificationExtra"
        const val DEFAULT_TITLE = "Czas na naukę!"
        const val DEFAULT_MESSAGE = "Sprawdź aplikację!"
        const val DEFAULT_NOTIFICATION_ID = 0
        const val DEFAULT_CHANNEL_ID = "channel"
    }

    override fun onReceive(context: Context, intent: Intent) {

        val notificationID = intent.getIntExtra(NOTIFICATION_EXTRA, 0)
        val channelID = intent.getStringExtra(CHANNEL_EXTRA).toString()

        if (channelID != "null") {
            val resultIntent = Intent(context, MainActivity::class.java)

            val resultPendingIntent: PendingIntent? = TaskStackBuilder.create(context).run {
                addNextIntentWithParentStack(resultIntent)
                getPendingIntent(notificationID, PendingIntent.FLAG_IMMUTABLE)
            }

            val notification = NotificationCompat.Builder(context, channelID)
                .setSmallIcon(R.drawable.ic_baseline_menu_book_24)
                .setContentTitle(intent.getStringExtra(TITLE_EXTRA))
                .setContentText(intent.getStringExtra(MESSAGE_EXTRA))
                .setContentIntent(resultPendingIntent)
                .build()

            val manager =
                context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            manager.notify(notificationID, notification)
        } else {

            val name = "Notif channel"
            val desc = "Desc channel"
            val importance = NotificationManager.IMPORTANCE_DEFAULT
            val channel = NotificationChannel(DEFAULT_CHANNEL_ID, name, importance)
            channel.description = desc

            val resultIntent = Intent(context, MainActivity::class.java)

            val resultPendingIntent: PendingIntent? = TaskStackBuilder.create(context).run {
                addNextIntentWithParentStack(resultIntent)
                getPendingIntent(DEFAULT_NOTIFICATION_ID, PendingIntent.FLAG_IMMUTABLE)
            }

            val notification = NotificationCompat.Builder(context, DEFAULT_CHANNEL_ID)
                .setSmallIcon(R.drawable.ic_baseline_menu_book_24)
                .setContentTitle(DEFAULT_TITLE)
                .setContentText(DEFAULT_MESSAGE)
                .setContentIntent(resultPendingIntent)
                .build()

            val manager =
                context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            manager.createNotificationChannel(channel)
            manager.notify(DEFAULT_NOTIFICATION_ID, notification)
        }
    }
}