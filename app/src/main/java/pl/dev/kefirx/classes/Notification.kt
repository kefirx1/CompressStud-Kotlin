package pl.dev.kefirx.classes

import android.app.Activity
import android.app.AlarmManager
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import pl.dev.kefirx.MainActivity.Companion.viewModel
import pl.dev.kefirx.SettingsActivity
import pl.dev.kefirx.receivers.NotificationReceiver

class Notification {

    fun cancelNotificationByID(notificationId: Int, applicationContext: Context){
        val manager = applicationContext.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        val intent = Intent(applicationContext, NotificationReceiver::class.java)
        val pendingIntent = PendingIntent.getBroadcast(
            applicationContext,
            notificationId,
            intent,
            PendingIntent.FLAG_IMMUTABLE
        )
        val alarmManager = applicationContext.getSystemService(Context.ALARM_SERVICE) as AlarmManager

        manager.cancel(notificationId)
        alarmManager.cancel(pendingIntent)
    }

    fun cancelAllNotification(applicationContext: Context, instance: SettingsActivity){
        val manager = applicationContext.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        manager.cancelAll()

        viewModel.setAllTestsInfoObserver()

        viewModel.testInfoResult.observe(instance){ tests ->
            if(tests!=null){
                tests.forEach{
                    val notificationId = it.test_id

                    val intent = Intent(applicationContext, NotificationReceiver::class.java)
                    val pendingIntent = PendingIntent.getBroadcast(
                        applicationContext,
                        notificationId,
                        intent,
                        PendingIntent.FLAG_IMMUTABLE
                    )
                    val alarmManager = applicationContext.getSystemService(Context.ALARM_SERVICE) as AlarmManager
                    alarmManager.cancel(pendingIntent)
                }
            }else{
                //TODO
            }

        }


    }

}
