package pl.dev.kefirx

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.ArrayAdapter
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.google.gson.Gson
import pl.dev.kefirx.classes.DashboardView
import pl.dev.kefirx.classes.Listeners
import pl.dev.kefirx.classes.ModalsView
import pl.dev.kefirx.databinding.ActivityMainBinding
import pl.dev.kefirx.json.GetJSONString
import pl.dev.kefirx.json.ListOfTopicsJSON
import pl.dev.kefirx.reminder.BootReceiver
import pl.dev.kefirx.viewModel.CSViewModel
import java.util.*
import kotlin.collections.ArrayList


open class MainActivity : AppCompatActivity() {

    companion object{
        lateinit var viewModel: CSViewModel
        private const val LIST_OF_TOPICS_PATH = "listOfTopics.json"
    }

    protected lateinit var binding: ActivityMainBinding
    private lateinit var listOfTopicsObject: ListOfTopicsJSON
    private lateinit var dashboardView: DashboardView
    private lateinit var modalsView: ModalsView
    private lateinit var listeners: Listeners
    private var getJSONString = GetJSONString()
    private val gson = Gson()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        listOfTopicsObject  = gson.fromJson(getJSONString.getJsonStringFromAssets(applicationContext, LIST_OF_TOPICS_PATH), ListOfTopicsJSON::class.java)

        modalsView = ModalsView(binding, this)
        dashboardView = DashboardView(binding, applicationContext, this)
        listeners = Listeners()

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
            setDashboardActuallyInfo()
            listeners.setMainActivityListeners(binding, applicationContext, this)
        }

    }

    fun callOnResume(){
        onResume()
    }

    private fun setDashboardActuallyInfo(){
        val name = viewModel.getUserInfoAsync().name + "!"
        binding.userNameTextView.text = name

        dashboardView.setBestOfThreeView(viewModel.getThreeExams())
    }


    fun setTopicSpinner(levelOfEdu: String){
        val lesson = binding.lessonsSpinner.selectedItem.toString()
        var topicsList: ArrayList<String> = ArrayList()

        if(levelOfEdu == "Podstawowa") {
            listOfTopicsObject.Podstawowa.forEach{
                if(it[0] == lesson) {
                    topicsList = it as ArrayList<String>
                }
            }
        }else if(levelOfEdu == "Średnia"){
            listOfTopicsObject.Srednia.forEach{
                if(it[0] == lesson) {
                    topicsList = it as ArrayList<String>
                }
            }
        }
        topicsList.remove(lesson)
        val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, topicsList)
        binding.topicSpinner.adapter = adapter
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