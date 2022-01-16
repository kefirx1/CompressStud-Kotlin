package pl.dev.kefirx.reminder

import android.annotation.SuppressLint
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.core.app.NotificationCompat
import pl.dev.kefirx.R

class BootReceiver : BroadcastReceiver() {

    companion object{
        const val NOTIFICATION_ID = 1
        const val CHANNEL_ID = "CHANNEL1"
        const val MESSAGE_EXTRA = "messageExtra"
        const val TITLE_EXTRA = "titleExtra"
    }

    lateinit var notificationManager: NotificationManager

    @SuppressLint("UnsafeProtectedBroadcastReceiver")
    override fun onReceive(context: Context, intent: Intent) {
       // if (intent.action == "android.intent.action.BOOT_COMPLETED") {

        println("receive")
            schedulePushNotifications(context, intent)
        //}else{
          //  schedulePushNotifications(context, intent)
        //}
    }

    private fun schedulePushNotifications(context: Context, intent: Intent){

        val notification = NotificationCompat.Builder(context, CHANNEL_ID)
            .setContentTitle(intent.getStringExtra(TITLE_EXTRA))
            .setContentText(intent.getStringExtra(MESSAGE_EXTRA))
            .setSmallIcon(R.drawable.ic_launcher_foreground)
            .build()


        notificationManager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        createNotificationChannel()
        notificationManager.notify(NOTIFICATION_ID, notification)

    }

    private fun createNotificationChannel() {
        val importance = NotificationManager.IMPORTANCE_HIGH
        val channel = NotificationChannel(CHANNEL_ID, "Name", importance).apply {
            description = "Opis"
        }
        notificationManager.createNotificationChannel(channel)


    }

}