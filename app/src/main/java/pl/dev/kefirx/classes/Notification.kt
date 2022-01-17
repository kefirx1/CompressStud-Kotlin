package pl.dev.kefirx.classes

import android.app.AlarmManager
import android.content.Context
import pl.dev.kefirx.MainActivity
import pl.dev.kefirx.databinding.ActivityMainBinding
import java.util.*

class Notification {




    fun schedulePushNotifications(lesson: String, topic: String, reminder: Int, dateOfExam: Long, binding: ActivityMainBinding, applicationContext: Context, instance: MainActivity) {



//        val alarmPendingIntent by lazy {
//            val intent = Intent(applicationContext, BootReceiver::class.java)
//
//            intent.putExtra(BootReceiver.TITLE_EXTRA, title)
//            intent.putExtra(BootReceiver.MESSAGE_EXTRA, message)
//
//            PendingIntent.getActivity(applicationContext, BootReceiver.NOTIFICATION_ID, intent, PendingIntent.FLAG_IMMUTABLE)
//        }


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
    }

    fun getTimeInMillis(binding: ActivityMainBinding): Long{
        val hour = binding.timeOfNotificationTimePicker.hour
        val minute = binding.timeOfNotificationTimePicker.minute
        val year = binding.testDatePicker.year
        val month = binding.testDatePicker.month
        val dayOfMonth = binding.testDatePicker.dayOfMonth

        val calendar = Calendar.getInstance()
        calendar.set(year, month, dayOfMonth, hour, minute, 0)
        return calendar.timeInMillis
    }


}