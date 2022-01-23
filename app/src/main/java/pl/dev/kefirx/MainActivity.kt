package pl.dev.kefirx

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.google.gson.Gson
import pl.dev.kefirx.classes.*
import pl.dev.kefirx.databinding.ActivityMainBinding
import pl.dev.kefirx.json.GetJSONString
import pl.dev.kefirx.json.ListOfTopicsJSON
import pl.dev.kefirx.reminder.NotificationReceiver
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
    private lateinit var examsExpiration: ExamsExpiration
    private var getJSONString = GetJSONString()
    private val gson = Gson()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        listOfTopicsObject  = gson.fromJson(getJSONString.getJsonStringFromAssets(applicationContext, LIST_OF_TOPICS_PATH), ListOfTopicsJSON::class.java)
    }


    fun createNotificationChannel(channelID: String) {
        val name = "Notif channel"
        val desc = "Desc channel"
        val importance = NotificationManager.IMPORTANCE_DEFAULT
        val channel= NotificationChannel(channelID, name, importance)
        channel.description = desc

        val notificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        notificationManager.createNotificationChannel(channel)

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
            startActivity(registerIntent)
        }else{
            modalsView = ModalsView()
            dashboardBestThreeView = DashboardBestThreeView(binding, applicationContext, this)
            listenersSet = ListenersSet(application)
            spinnersSet = SpinnersSet()
            examsExpiration = ExamsExpiration()


            dashboardBestThreeView.resetListeners()
            modalsView.hideAllModals(binding)
            setDashboardCurrentInfo()
            listenersSet.setMainActivityListeners(binding, applicationContext, this)

            val receiver = ComponentName(applicationContext, NotificationReceiver::class.java)

            packageManager.setComponentEnabledSetting(
                receiver,
                PackageManager.COMPONENT_ENABLED_STATE_ENABLED,
                PackageManager.DONT_KILL_APP
            )

        }

    }

    fun callOnResume(){
        onResume()
    }


    private fun setDashboardCurrentInfo(){
        val name = viewModel.getUserInfoAsync().name + "!"
        binding.userNameTextView.text = name
        examsExpiration.checkExamsExpirationDate(applicationContext, this)
        dashboardBestThreeView.setBestOfThreeView(viewModel.getThreeExams())
    }

}