package pl.dev.kefirx

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.lifecycle.ViewModelProvider
import com.google.gson.Gson
import pl.dev.kefirx.classes.DashboardBestThreeView
import pl.dev.kefirx.classes.ListenersSet
import pl.dev.kefirx.classes.ModalsView
import pl.dev.kefirx.classes.SpinnersSet
import pl.dev.kefirx.databinding.ActivityMainBinding
import pl.dev.kefirx.json.GetJSONString
import pl.dev.kefirx.json.ListOfTopicsJSON
import pl.dev.kefirx.reminder.BootReceiver
import pl.dev.kefirx.viewModel.CSViewModel
import java.util.*


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


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        listOfTopicsObject  = gson.fromJson(getJSONString.getJsonStringFromAssets(applicationContext, LIST_OF_TOPICS_PATH), ListOfTopicsJSON::class.java)

        modalsView = ModalsView(binding, this)
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
            modalsView.hideAllModals()
            setDashboardCurrentInfo()
            listenersSet.setMainActivityListeners(binding, applicationContext, this)
        }

    }

    fun callOnResume(){
        onResume()
    }


    private fun setDashboardCurrentInfo(){
        val theme = viewModel.getUserInfoAsync().theme
        if(theme == AppCompatDelegate.MODE_NIGHT_YES){
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
        }
        val name = viewModel.getUserInfoAsync().name + "!"
        binding.userNameTextView.text = name
        dashboardBestThreeView.setBestOfThreeView(viewModel.getThreeExams())
    }

    fun schedulePushNotifications(lesson: String, topic: String) {

        val title = "Czas na naukę!"
        val message = "$lesson - $topic"

        val alarmManager = getSystemService(ALARM_SERVICE) as AlarmManager
        val alarmPendingIntent by lazy {
            val intent = Intent(applicationContext, BootReceiver::class.java)

            intent.putExtra(BootReceiver.TITLE_EXTRA, title)
            intent.putExtra(BootReceiver.MESSAGE_EXTRA, message)

            PendingIntent.getBroadcast(applicationContext, BootReceiver.NOTIFICATION_ID, intent, PendingIntent.FLAG_IMMUTABLE or PendingIntent.FLAG_UPDATE_CURRENT)
        }

        val timeInMillis = getTimeInMillis()

        alarmManager.setInexactRepeating(
            AlarmManager.RTC_WAKEUP,
            timeInMillis,
            AlarmManager.INTERVAL_DAY,
            alarmPendingIntent
        )

    }

    fun getTimeInMillis(): Long{
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