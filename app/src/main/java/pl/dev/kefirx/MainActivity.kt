package pl.dev.kefirx

import android.annotation.SuppressLint
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.ClipDescription
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.NotificationCompat
import androidx.lifecycle.ViewModelProvider
import androidx.room.util.TableInfo
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.messaging.FirebaseMessaging
import com.google.gson.Gson
import pl.dev.kefirx.classes.DashboardBestThreeView
import pl.dev.kefirx.classes.ListenersSet
import pl.dev.kefirx.classes.ModalsView
import pl.dev.kefirx.classes.SpinnersSet
import pl.dev.kefirx.databinding.ActivityMainBinding
import pl.dev.kefirx.json.GetJSONString
import pl.dev.kefirx.json.ListOfTopicsJSON
import pl.dev.kefirx.viewModel.CSViewModel


open class MainActivity : AppCompatActivity() {

    companion object{
        lateinit var viewModel: CSViewModel
        const val LIST_OF_TOPICS_PATH = "listOfTopics.json"
        lateinit var listOfTopicsObject: ListOfTopicsJSON
    }

    protected lateinit var binding: ActivityMainBinding
    private lateinit var dashboardBestThreeView: DashboardBestThreeView
    private lateinit var modalsView: ModalsView
    private lateinit var listenersSet: ListenersSet
    private lateinit var spinnersSet: SpinnersSet
    private var getJSONString = GetJSONString()
    private val gson = Gson()

    private val channelID = "1"
    private var notificationManager: NotificationManager? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        listOfTopicsObject  = gson.fromJson(getJSONString.getJsonStringFromAssets(applicationContext, LIST_OF_TOPICS_PATH), ListOfTopicsJSON::class.java)

        modalsView = ModalsView()
        dashboardBestThreeView = DashboardBestThreeView(binding, applicationContext, this)
        listenersSet = ListenersSet()
        spinnersSet = SpinnersSet()
    }

    override fun onResume() {
        super.onResume()

        viewModel = ViewModelProvider
            .AndroidViewModelFactory
            .getInstance(application)
            .create(CSViewModel::class.java)

        if(viewModel.getUserCountAsync() <= 0) {
            Log.e("TAG", "Create user")
            val registerIntent = Intent(this, RegisterActivity::class.java)
            Log.e("TAG", "Start act")
            startActivity(registerIntent)
        }else{
            modalsView.hideAllModals(binding)
            setDashboardCurrentInfo()
            listenersSet.setMainActivityListeners(binding, applicationContext, this)
        }





    }

    fun callOnResume(){
        onResume()
    }


    private fun setDashboardCurrentInfo(){
        val name = viewModel.getUserInfoAsync().name + "!"
        binding.userNameTextView.text = name
        dashboardBestThreeView.setBestOfThreeView(viewModel.getThreeExams())

//        notificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
//
//        createNotificationChannel(channelID, "DemoChannel", "this is a demo")
//
//        displayNotification()
    }

    @SuppressLint("UnspecifiedImmutableFlag")
    private fun displayNotification(){
        val notificationId = 45
        val tapResultIntent = Intent(this, CalendarActivity::class.java)
        val pendingIntent = PendingIntent.getActivity(
            this,
            0,
            tapResultIntent,
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )



        val notification = NotificationCompat.Builder(applicationContext, channelID)
            .setContentTitle("Demo title")
            .setContentText("Demo notifi")
            .setSmallIcon(R.drawable.ic_baseline_menu_book_24)
            .setAutoCancel(true)
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
            .setContentIntent(pendingIntent)
            .build()
        notificationManager?.notify(notificationId, notification)

    }

    private fun createNotificationChannel(id: String, name: String, channelDescription: String){
        if(Build.VERSION.SDK_INT>= Build.VERSION_CODES.O){
            val importance = NotificationManager.IMPORTANCE_DEFAULT
            val channel = NotificationChannel(id, name, importance).apply {
                description = channelDescription
            }
            notificationManager?.createNotificationChannel(channel)


        }
    }

}