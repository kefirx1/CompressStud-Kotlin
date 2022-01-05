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
import java.time.Instant
import java.time.LocalDate
import java.time.ZoneId

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
            hideAllModals()
            setDashboardActuallyInfo()
            setListeners()
            viewModel.getThreeExams().forEach{ println(it)}

        }
    }


    private fun setDashboardActuallyInfo(){
        val name = viewModel.getUserInfoAsync().name + "!"
        userNameTextView.text = name

        checkActuallyDashboardTests()

    }

    private fun checkActuallyDashboardTests(){

        learnModalBackButton.setOnClickListener{
            learnModal.visibility = View.GONE
        }

        val listOfThreeTests = viewModel.getThreeExams()

        fun setTestsNull(){
            top3test__first__dayNumber.text = ""
            top3test__second__dayNumber.text = ""
            top3test__third__dayNumber.text = ""
            top3test__first__month.text = ""
            top3test__second__month.text = ""
            top3test__third__month.text = ""
            top3test__first__lessonName.text = ""
            top3test__second__lessonName.text = ""
            top3test__third__lessonName.text = ""
        }

        //TODO - refactoring of repeating lines

        fun setTestOne(){

            val dateLong1 = listOfThreeTests[0].dateOfExam
            val  dateLocalDate1  = Instant.ofEpochSecond(dateLong1)
                .atZone(ZoneId.systemDefault())
                .toLocalDate()

            top3test__second__dayNumber.text = dateLocalDate1.dayOfMonth.toString()
            top3test__second__month.text = Convert.monthMap[dateLocalDate1.monthValue.toString()]
            top3test__second__lessonName.text = listOfThreeTests[0].lesson

            top3test1.setOnClickListener{
                hideAllModals()
                learnModal.visibility = View.VISIBLE
                modalLessonName.text = listOfThreeTests[0].lesson.uppercase()
                modalTopicName.text = listOfThreeTests[0].topic.uppercase()
                modalDate.text = StringBuilder(dateLocalDate1.dayOfMonth.toString()
                        + " " + Convert.monthFullMap[(dateLocalDate1.monthValue+1).toString()].toString().uppercase())
            }

        }

        fun setTestsTwo(){

            val dateLong1 = listOfThreeTests[0].dateOfExam
            val  dateLocalDate1  = Instant.ofEpochSecond(dateLong1)
                .atZone(ZoneId.systemDefault())
                .toLocalDate()
            val dateLong2 = listOfThreeTests[1].dateOfExam
            val  dateLocalDate2  = Instant.ofEpochSecond(dateLong2)
                .atZone(ZoneId.systemDefault())
                .toLocalDate()

            top3test__second__dayNumber.text = dateLocalDate1.dayOfMonth.toString()
            top3test__second__month.text = Convert.monthMap[dateLocalDate1.monthValue.toString()]
            top3test__second__lessonName.text = listOfThreeTests[0].lesson
            top3test__first__dayNumber.text = dateLocalDate2.dayOfMonth.toString()
            top3test__first__month.text = Convert.monthMap[dateLocalDate2.monthValue.toString()]
            top3test__first__lessonName.text = listOfThreeTests[1].lesson

            top3test1.setOnClickListener{
                hideAllModals()
                learnModal.visibility = View.VISIBLE
                modalLessonName.text = listOfThreeTests[0].lesson.uppercase()
                modalTopicName.text = listOfThreeTests[0].topic.uppercase()
                modalDate.text = StringBuilder(dateLocalDate1.dayOfMonth.toString()
                        + " " + Convert.monthFullMap[(dateLocalDate1.monthValue+1).toString()].toString().uppercase())
            }
            top3test2.setOnClickListener{
                hideAllModals()
                learnModal.visibility = View.VISIBLE
                modalLessonName.text = listOfThreeTests[1].lesson.uppercase()
                modalTopicName.text = listOfThreeTests[1].topic.uppercase()
                modalDate.text = StringBuilder(dateLocalDate2.dayOfMonth.toString()
                        + " " + Convert.monthFullMap[(dateLocalDate2.monthValue+1).toString()].toString().uppercase())
            }

        }

        fun setTestsThree(){

            val dateLong1 = listOfThreeTests[0].dateOfExam
            val  dateLocalDate1  = Instant.ofEpochSecond(dateLong1)
                .atZone(ZoneId.systemDefault())
                .toLocalDate()
            val dateLong2 = listOfThreeTests[1].dateOfExam
            val  dateLocalDate2  = Instant.ofEpochSecond(dateLong2)
                .atZone(ZoneId.systemDefault())
                .toLocalDate()
            val dateLong3 = listOfThreeTests[2].dateOfExam
            val  dateLocalDate3  = Instant.ofEpochSecond(dateLong3)
                .atZone(ZoneId.systemDefault())
                .toLocalDate()

            top3test__second__dayNumber.text = dateLocalDate1.dayOfMonth.toString()
            top3test__second__month.text = Convert.monthMap[dateLocalDate1.monthValue.toString()]
            top3test__second__lessonName.text = listOfThreeTests[0].lesson
            top3test__first__dayNumber.text = dateLocalDate2.dayOfMonth.toString()
            top3test__first__month.text = Convert.monthMap[dateLocalDate2.monthValue.toString()]
            top3test__first__lessonName.text = listOfThreeTests[1].lesson
            top3test__third__dayNumber.text = dateLocalDate3.dayOfMonth.toString()
            top3test__third__month.text = Convert.monthMap[dateLocalDate3.monthValue.toString()]
            top3test__third__lessonName.text = listOfThreeTests[2].lesson

            top3test1.setOnClickListener{
                hideAllModals()
                learnModal.visibility = View.VISIBLE
                modalLessonName.text = listOfThreeTests[0].lesson.uppercase()
                modalTopicName.text = listOfThreeTests[0].topic.uppercase()
                modalDate.text = StringBuilder(dateLocalDate1.dayOfMonth.toString()
                        + " " + Convert.monthFullMap[(dateLocalDate1.monthValue+1).toString()].toString().uppercase())
            }
            top3test2.setOnClickListener{
                hideAllModals()
                learnModal.visibility = View.VISIBLE
                modalLessonName.text = listOfThreeTests[1].lesson.uppercase()
                modalTopicName.text = listOfThreeTests[1].topic.uppercase()
                modalDate.text = StringBuilder(dateLocalDate2.dayOfMonth.toString()
                        + " " + Convert.monthFullMap[(dateLocalDate2.monthValue + 1).toString()].toString().uppercase())
            }
            top3test3.setOnClickListener{
                hideAllModals()
                learnModal.visibility = View.VISIBLE
                modalLessonName.text = listOfThreeTests[2].lesson.uppercase()
                modalTopicName.text = listOfThreeTests[2].topic.uppercase()
                modalDate.text = StringBuilder(dateLocalDate3.dayOfMonth.toString()
                        + " " + Convert.monthFullMap[(dateLocalDate3.monthValue+1).toString()].toString().uppercase())
            }

        }

        when(listOfThreeTests.size){
            0 -> setTestsNull()

            1 -> setTestOne()

            2 -> setTestsTwo()

            3 -> setTestsThree()
        }

    }

    private fun setTopicSpinner(levelOfEdu: String){
        val lesson = lessonsSpinner.selectedItem.toString()
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
        topicSpinner.adapter = adapter
    }

    private fun hideAllModals(){
        addNewTestModal.visibility = View.GONE
        learnModal.visibility = View.GONE
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
            hideAllModals()
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