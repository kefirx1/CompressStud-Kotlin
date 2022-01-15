package pl.dev.kefirx.classes

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import pl.dev.kefirx.MainActivity
import pl.dev.kefirx.databinding.ActivityMainBinding
import pl.dev.kefirx.reminder.BootReceiver
import java.util.*

class Notification {


    fun schedulePushNotifications(lesson: String, topic: String, reminder: Int, binding: ActivityMainBinding, applicationContext: Context, instance: MainActivity) {

        val title = "Czas na naukę!"
        val message = "$lesson - $topic"

        val alarmManager = instance.getSystemService(AppCompatActivity.ALARM_SERVICE) as AlarmManager
        val alarmPendingIntent by lazy {
            val intent = Intent(applicationContext, BootReceiver::class.java)

            intent.putExtra(BootReceiver.TITLE_EXTRA, title)
            intent.putExtra(BootReceiver.MESSAGE_EXTRA, message)

            PendingIntent.getBroadcast(applicationContext, BootReceiver.NOTIFICATION_ID, intent, 0)
        }


        val timeInMillis = getTimeInMillis(binding)

//        when(reminder){
//            1 -> alarmManager.setInexactRepeating(
//                AlarmManager.RTC_WAKEUP,
//                timeInMillis,
//                AlarmManager.INTERVAL_DAY,
//                alarmPendingIntent
//            )
//            2 -> alarmManager.setInexactRepeating(
//                AlarmManager.RTC_WAKEUP,
//                timeInMillis,
//                AlarmManager.INTERVAL_DAY*2,
//                alarmPendingIntent
//            )
//            3 -> alarmManager.setAndAllowWhileIdle(
//                AlarmManager.RTC_WAKEUP,
//                timeInMillis-86400000,
//                alarmPendingIntent
//            )
//        }


        val date = Date(timeInMillis)

        println(date)

        println(reminder)
        when(reminder){
            1 -> alarmManager.setAndAllowWhileIdle(
                AlarmManager.RTC_WAKEUP,
                timeInMillis,
                alarmPendingIntent
            )
            2 -> alarmManager.setAndAllowWhileIdle(
                AlarmManager.RTC_WAKEUP,
                timeInMillis,
                alarmPendingIntent
            )
            3 -> alarmManager.setAndAllowWhileIdle(
                AlarmManager.RTC_WAKEUP,
                timeInMillis-86400000,
                alarmPendingIntent
            )
        }

    }

    fun getTimeInMillis(binding: ActivityMainBinding): Long{
        val hour = binding.timeOfNotificationTimePicker.hour
        val minute = binding.timeOfNotificationTimePicker.minute
        val year = binding.testDatePicker.year
        val month = binding.testDatePicker.month
        val dayOfMonth = binding.testDatePicker.dayOfMonth

        val calendar = Calendar.getInstance()
        calendar.set(year, month, dayOfMonth, hour, minute)
        return calendar.timeInMillis
    }


}