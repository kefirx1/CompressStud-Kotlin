package pl.dev.kefirx

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.google.gson.Gson
import kotlinx.android.synthetic.main.activity_main.*
import pl.dev.kefirx.databinding.ActivityMainBinding
import pl.dev.kefirx.json.GetJSONString
import pl.dev.kefirx.json.ListOfTopicsJSON
import pl.dev.kefirx.room.Tests
import pl.dev.kefirx.viewModel.CSViewModel
import java.sql.Timestamp
import java.time.Instant
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.*
import kotlin.collections.ArrayList

class MainActivity : AppCompatActivity() {

    companion object{
        lateinit var viewModel: CSViewModel
        private const val LIST_OF_TOPICS_PATH = "listOfTopics.json"
        lateinit var listOfTopicsObject: ListOfTopicsJSON
    }

    private lateinit var binding: ActivityMainBinding
    private var getJSONString = GetJSONString()
    private val gson = Gson()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        binding = ActivityMainBinding.inflate(layoutInflater)
        listOfTopicsObject  = gson.fromJson(getJSONString.getJsonStringFromAssets(applicationContext, LIST_OF_TOPICS_PATH), ListOfTopicsJSON::class.java)
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

            setDashboardActuallyInfo()
            setListeners()
            viewModel.getThreeExams().forEach(){ println(it)}

        }
    }


    private fun setDashboardActuallyInfo(){
        val name = viewModel.getUserInfoAsync().name + "!"
        userNameTextView.text = name
    }

    private fun setTopicSpinner(levelOfEdu: String){
        val lesson = lessonsSpinner.selectedItem.toString()
        var topicsList: ArrayList<String> = ArrayList()

        if(levelOfEdu == "Podstawowa") {
            listOfTopicsObject.Podstawowa.forEach(){
                if(it[0] == lesson) {
                    topicsList = it as ArrayList<String>
                }
            }
        }else if(levelOfEdu == "Średnia"){
            listOfTopicsObject.Srednia.forEach(){
                if(it[0] == lesson) {
                    topicsList = it as ArrayList<String>
                }
            }
        }
        topicsList.remove(lesson)
        val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, topicsList)
        topicSpinner.adapter = adapter
    }

    private fun newTestModalReset(){
        addNewTestModal.visibility = View.GONE
        onResume()
    }

    private fun setListeners(){
        //TODO - Change into binding
        settingsButton.setOnClickListener{
            Log.e("TAG", "Go to settings")
            val settingsIntent = Intent(this, SettingsActivity::class.java)
            startActivity(settingsIntent)
        }
        statisticsButton.setOnClickListener{
            Log.e("TAG", "Go to statistics")
            val statisticsIntent = Intent(this, StatisticsActivity::class.java)
            startActivity(statisticsIntent)
        }
        calendarButton.setOnClickListener{
            Log.e("TAG", "Go to calendar")
            val calendarIntent = Intent(this, CalendarActivity::class.java)
            startActivity(calendarIntent)
        }
        openNewTestModalButton.setOnClickListener{
            addNewTestModal.visibility = View.VISIBLE
            val levelOfEdu = viewModel.getUserInfoAsync().levelOfEdu

            if(levelOfEdu == "Podstawowa"){
                val lessonsList = resources.getStringArray(R.array.listOfPrimaryLessons)
                val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, lessonsList)
                lessonsSpinner.adapter = adapter
            }else if(levelOfEdu == "Średnia"){
                val lessonsList = resources.getStringArray(R.array.listOfHighLessons)
                val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, lessonsList)
                lessonsSpinner.adapter = adapter
            }

            lessonsSpinner?.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
                override fun onItemSelected(
                    parent: AdapterView<*>?,
                    view: View?,
                    position: Int,
                    id: Long
                ) {
                    setTopicSpinner(levelOfEdu)
                }
                override fun onNothingSelected(parent: AdapterView<*>?) {
                }
            }

            cancelNewTestButton.setOnClickListener{
                newTestModalReset()
            }

            timeOfNotificationTimePicker.setIs24HourView(true)


            addNewTestButton.setOnClickListener{
                val lesson = lessonsSpinner.selectedItem.toString()
                val topic = topicSpinner.selectedItem.toString()
                val dateOfExamLocalDate = LocalDate.of(testDatePicker.year, testDatePicker.month+1, testDatePicker.dayOfMonth)

                //val date = java.time.format.DateTimeFormatter.ISO_INSTANT
              //      .format(java.time.Instant.ofEpochSecond(dateOfExamLocalDate.toEpochDay()*86400L))

                val dateOfExam = dateOfExamLocalDate.toEpochDay()*86400L
                var reminder = 0
                val timeOfRemindH = timeOfNotificationTimePicker.hour.toString()
                val timeOfRemindM = timeOfNotificationTimePicker.minute.toString()
                val timeOfLearning = 0
                val watchedVideos = 0

                when(notificationSpinner.selectedItem.toString()){
                    "Codziennie" -> reminder = 1
                    "Co dwa dni" -> reminder = 2
                    "Dzień przed sprawdzianem" -> reminder = 3
                }

                viewModel.insertTest(Tests(lesson, topic, dateOfExam, timeOfLearning, watchedVideos, reminder, timeOfRemindH, timeOfRemindM))
                Log.e("TAG", "Insert test")
                Toast.makeText(this, "Dodano sprawdzian", Toast.LENGTH_SHORT).show()
                newTestModalReset()
            }

        }
    }



}