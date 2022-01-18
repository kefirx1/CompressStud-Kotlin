package pl.dev.kefirx.reminder

import android.app.NotificationManager
import android.app.PendingIntent
import android.app.TaskStackBuilder
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.widget.Toast
import androidx.core.app.NotificationCompat
import androidx.lifecycle.ViewModelProvider
import pl.dev.kefirx.MainActivity
import pl.dev.kefirx.MainActivity.Companion.viewModel
import pl.dev.kefirx.R
import pl.dev.kefirx.classes.ListenersSet
import pl.dev.kefirx.viewModel.CSViewModel
import java.util.*


class NotificationReceiver: BroadcastReceiver() {

    companion object{
        const val titleExtra = "titleExtra"
        const val messageExtra = "messageExtra"
        const val DEFAULT_TITLE = "Czas na naukę!"
        var notificationID = 1
        var channelID = "channel1"
    }

    override fun onReceive(context: Context, intent: Intent) {
        if (intent.action == "android.intent.action.BOOT_COMPLETED") {



//            Toast.makeText(context, "Boot", Toast.LENGTH_SHORT).show()
//
//
//            ListenersSet.restartViewModel(MainActivity())
//
//            val currentDate = Calendar.getInstance().timeInMillis/10000
//
//            println(currentDate)
//
//            val tests = viewModel.getAllTestsInfoAsync()
//
//            var message = ":D"
//
//            tests.forEach{
//
//                println(it)
//
//                if(currentDate == it.dateOfExam/10000){
//                    it.dateOfExam
//
//                    channelID = "channel" + (it.test_id).toString()
//                    notificationID = it.test_id
//
//                    val lesson = it.lesson
//                    val topic = it.topic
//
//                    message = "$lesson - $topic"
//
//                }
//            }

            val resultIntent = Intent(context, MainActivity::class.java)

            val resultPendingIntent: PendingIntent? = TaskStackBuilder.create(context).run {
                addNextIntentWithParentStack(resultIntent)
                getPendingIntent(notificationID, PendingIntent.FLAG_IMMUTABLE)
            }

            val notification = NotificationCompat.Builder(context, channelID)
                .setSmallIcon(R.drawable.ic_baseline_menu_book_24)
                .setContentTitle(DEFAULT_TITLE)
                .setContentText(DEFAULT_TITLE)
                .setContentIntent(resultPendingIntent)
                .build()

            val manager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            manager.notify(notificationID, notification)

        }else{

            println(intent.action.toString())

            val tests = viewModel.getAllTestsInfoAsync()
            val currentDate = Calendar.getInstance().timeInMillis/10000

            tests.forEach{

                if(currentDate == it.dateOfExam/10000){

                    channelID = "channel" + (it.test_id).toString()
                    notificationID = it.test_id

                }
            }

            val resultIntent = Intent(context, MainActivity::class.java)

            val resultPendingIntent: PendingIntent? = TaskStackBuilder.create(context).run {
                addNextIntentWithParentStack(resultIntent)
                getPendingIntent(notificationID, PendingIntent.FLAG_IMMUTABLE)
            }

            val notification = NotificationCompat.Builder(context, channelID)
                .setSmallIcon(R.drawable.ic_baseline_menu_book_24)
                .setContentTitle(intent.getStringExtra(titleExtra))
                .setContentText(intent.getStringExtra(messageExtra))
                .setContentIntent(resultPendingIntent)
                .build()

            val manager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            manager.notify(notificationID, notification)
        }

    }


}