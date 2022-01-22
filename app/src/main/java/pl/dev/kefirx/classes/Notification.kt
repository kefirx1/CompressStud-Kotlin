package pl.dev.kefirx.classes

import android.app.NotificationManager
import android.content.Context

class Notification {

    fun cancelNotificationByID(notificationId: Int, applicationContext: Context){
        println("Delete - $notificationId")
        val manager = applicationContext.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        manager.cancel(notificationId)
    }

    fun cancelAllNotification(applicationContext: Context){
        val manager = applicationContext.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        manager.cancelAll()
    }

}
