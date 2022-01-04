package pl.dev.kefirx

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.google.gson.Gson
import kotlinx.android.synthetic.main.activity_main.*
import pl.dev.kefirx.databinding.ActivityMainBinding
import pl.dev.kefirx.json.GetJSONString
import pl.dev.kefirx.json.ListOfTopicsJSON
import pl.dev.kefirx.viewModel.CSViewModel

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
                    TODO("Not yet implemented")
                }

            }




            cancelNewTestButton.setOnClickListener{
                addNewTestModal.visibility = View.GONE
            }

            timeOfNotificationTimePicker.setIs24HourView(true)


            addNewTestButton.setOnClickListener{
                //val lesson = lessonsSpinner.selectedItem.toString()
                //val topic = topicSpinner.selectedItem.toString()
                val testDate = testDatePicker.dayOfMonth.toString() + "/" + (testDatePicker.month+1).toString() + "/" + testDatePicker.year.toString()
                val reminder = notificationSpinner.selectedItem.toString()
                val remindersH = timeOfNotificationTimePicker.hour.toString()
                val remindersM = timeOfNotificationTimePicker.minute.toString()

            }



        }
    }



}