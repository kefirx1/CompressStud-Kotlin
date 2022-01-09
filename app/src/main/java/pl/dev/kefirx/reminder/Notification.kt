package pl.dev.kefirx.reminder


import android.app.NotificationManager
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import androidx.core.app.NotificationCompat
import pl.dev.kefirx.R


const val NOTIFICATION_ID = 1
const val CHANNEL_ID = "CHANNEL1"
const val messageExtra = "messageExtra"
const val titleExtra = "titleExtra"

class Notification: BroadcastReceiver() {


    override fun onReceive(context: Context, intent: Intent) {

        val notification = NotificationCompat.Builder(context, CHANNEL_ID)
            .setContentTitle(intent.getStringExtra(titleExtra))
            .setContentText(intent.getStringExtra(messageExtra))
            .setSmallIcon(R.drawable.ic_launcher_foreground)
            .build()


        val manager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        manager.notify(NOTIFICATION_ID, notification)


    }


}