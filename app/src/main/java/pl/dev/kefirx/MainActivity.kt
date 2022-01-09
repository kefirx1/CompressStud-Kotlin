package pl.dev.kefirx

import android.app.*
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.text.format.DateFormat.getLongDateFormat
import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.google.gson.Gson
import pl.dev.kefirx.databinding.ActivityMainBinding
import pl.dev.kefirx.json.GetJSONString
import pl.dev.kefirx.json.ListOfTopicsJSON
import pl.dev.kefirx.reminder.*
import pl.dev.kefirx.reminder.Notification
import pl.dev.kefirx.room.Tests
import pl.dev.kefirx.viewModel.CSViewModel
import java.text.DateFormat
import java.time.Instant
import java.time.LocalDate
import java.time.ZoneId
import java.util.*
import kotlin.collections.ArrayList

class MainActivity : AppCompatActivity() {

    companion object{
        lateinit var viewModel: CSViewModel
        private const val LIST_OF_TOPICS_PATH = "listOfTopics.json"
        lateinit var listOfTopicsObject: ListOfTopicsJSON
        val gson = Gson()
    }

    private lateinit var binding: ActivityMainBinding
    private var getJSONString = GetJSONString()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
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
            Log.e("TAG", "Start act")
            startActivity(registerIntent)
        }else{
            hideAllModals()
            setDashboardActuallyInfo()
            setListeners()
        }

        println(viewModel.getUserInfoAsync())

    }


    private fun setDashboardActuallyInfo(){
        val name = viewModel.getUserInfoAsync().name + "!"
        binding.userNameTextView.text = name

        checkActuallyDashboardTests()

    }

    private fun checkActuallyDashboardTests(){

        binding.learnModalBackButton.setOnClickListener{
            binding.learnModal.visibility = View.GONE
        }

        val listOfThreeTests = viewModel.getThreeExams()

        fun setTestsNull(){
            with(binding){
                top3TestFirstDayNumber.text = ""
                top3testSecondDayNumber.text = ""
                top3TestThirdDayNumber.text = ""
                top3TestFirstMonth.text = ""
                top3TestSecondMonth.text = ""
                top3TestThirdMonth.text = ""
                top3TestFirstLessonName.text = ""
                top3TestSecondLessonName.text = ""
                top3TestThirdLessonName.text = ""
            }
        }

        fun setDeleteTestButtonListener(test: Tests){
            binding.deleteTestButton.setOnClickListener{
                viewModel.deleteTest(test)
                Log.e("TAG", "Test deleted")
                binding.top3test1.setOnClickListener(null)
                binding.top3test2.setOnClickListener(null)
                binding.top3test3.setOnClickListener(null)
                setTestsNull()
                onResume()
            }
        }

        fun setStartStudyingButtonListener(test: Tests){
            binding.startStudying.setOnClickListener{
                val studyingIntent = Intent(this, StudyingActivity::class.java).apply {
                    putExtra("testId", test.test_id )
                }
                startActivity(studyingIntent)

            }
        }

        //TODO - refactoring of repeating lines

        fun setTestOne(){

            val dateLong1 = listOfThreeTests[0].dateOfExam
            val  dateLocalDate1  = Instant.ofEpochSecond(dateLong1)
                .atZone(ZoneId.systemDefault())
                .toLocalDate()

            binding.top3testSecondDayNumber.text = dateLocalDate1.dayOfMonth.toString()
            binding.top3TestSecondMonth.text = Convert.monthMap[dateLocalDate1.monthValue.toString()]
            binding.top3TestSecondLessonName.text = listOfThreeTests[0].lesson

            binding.top3test1.setOnClickListener{
                hideAllModals()
                binding.learnModal.visibility = View.VISIBLE
                binding.modalLessonName.text = listOfThreeTests[0].lesson.uppercase()
                binding.modalTopicName.text = listOfThreeTests[0].topic.uppercase()
                binding.modalDate.text = StringBuilder(dateLocalDate1.dayOfMonth.toString()
                        + " " + Convert.monthFullMap[(dateLocalDate1.monthValue+1).toString()].toString().uppercase())
                setDeleteTestButtonListener(listOfThreeTests[0])
                setStartStudyingButtonListener(listOfThreeTests[0])
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

            binding.top3testSecondDayNumber.text = dateLocalDate1.dayOfMonth.toString()
            binding.top3TestSecondMonth.text = Convert.monthMap[dateLocalDate1.monthValue.toString()]
            binding.top3TestSecondLessonName.text = listOfThreeTests[0].lesson
            binding.top3TestFirstDayNumber.text = dateLocalDate2.dayOfMonth.toString()
            binding.top3TestFirstMonth.text = Convert.monthMap[dateLocalDate2.monthValue.toString()]
            binding.top3TestFirstLessonName.text = listOfThreeTests[1].lesson

            binding.top3test1.setOnClickListener{
                hideAllModals()
                binding.learnModal.visibility = View.VISIBLE
                binding.modalLessonName.text = listOfThreeTests[0].lesson.uppercase()
                binding.modalTopicName.text = listOfThreeTests[0].topic.uppercase()
                binding.modalDate.text = StringBuilder(dateLocalDate1.dayOfMonth.toString()
                        + " " + Convert.monthFullMap[(dateLocalDate1.monthValue+1).toString()].toString().uppercase())
                setDeleteTestButtonListener(listOfThreeTests[0])
                setStartStudyingButtonListener(listOfThreeTests[0])
            }
            binding.top3test2.setOnClickListener{
                hideAllModals()
                binding.learnModal.visibility = View.VISIBLE
                binding.modalLessonName.text = listOfThreeTests[1].lesson.uppercase()
                binding.modalTopicName.text = listOfThreeTests[1].topic.uppercase()
                binding.modalDate.text = StringBuilder(dateLocalDate2.dayOfMonth.toString()
                        + " " + Convert.monthFullMap[(dateLocalDate2.monthValue+1).toString()].toString().uppercase())
                setDeleteTestButtonListener(listOfThreeTests[1])
                setStartStudyingButtonListener(listOfThreeTests[1])
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

            binding.top3testSecondDayNumber.text = dateLocalDate1.dayOfMonth.toString()
            binding.top3TestSecondMonth.text = Convert.monthMap[dateLocalDate1.monthValue.toString()]
            binding.top3TestSecondLessonName.text = listOfThreeTests[0].lesson
            binding.top3TestFirstDayNumber.text = dateLocalDate2.dayOfMonth.toString()
            binding.top3TestFirstMonth.text = Convert.monthMap[dateLocalDate2.monthValue.toString()]
            binding.top3TestFirstLessonName.text = listOfThreeTests[1].lesson
            binding.top3TestThirdDayNumber.text = dateLocalDate3.dayOfMonth.toString()
            binding.top3TestThirdMonth.text = Convert.monthMap[dateLocalDate3.monthValue.toString()]
            binding.top3TestThirdLessonName.text = listOfThreeTests[2].lesson

            binding.top3test1.setOnClickListener{
                hideAllModals()
                binding.learnModal.visibility = View.VISIBLE
                binding.modalLessonName.text = listOfThreeTests[0].lesson.uppercase()
                binding.modalTopicName.text = listOfThreeTests[0].topic.uppercase()
                binding.modalDate.text = StringBuilder(dateLocalDate1.dayOfMonth.toString()
                        + " " + Convert.monthFullMap[(dateLocalDate1.monthValue+1).toString()].toString().uppercase())
                setDeleteTestButtonListener(listOfThreeTests[0])
                setStartStudyingButtonListener(listOfThreeTests[0])
            }
            binding.top3test2.setOnClickListener{
                hideAllModals()
                binding.learnModal.visibility = View.VISIBLE
                binding.modalLessonName.text = listOfThreeTests[1].lesson.uppercase()
                binding.modalTopicName.text = listOfThreeTests[1].topic.uppercase()
                binding.modalDate.text = StringBuilder(dateLocalDate2.dayOfMonth.toString()
                        + " " + Convert.monthFullMap[(dateLocalDate2.monthValue + 1).toString()].toString().uppercase())
                setDeleteTestButtonListener(listOfThreeTests[1])
                setStartStudyingButtonListener(listOfThreeTests[1])
            }
            binding.top3test3.setOnClickListener{
                hideAllModals()
                binding.learnModal.visibility = View.VISIBLE
                binding.modalLessonName.text = listOfThreeTests[2].lesson.uppercase()
                binding.modalTopicName.text = listOfThreeTests[2].topic.uppercase()
                binding.modalDate.text = StringBuilder(dateLocalDate3.dayOfMonth.toString()
                        + " " + Convert.monthFullMap[(dateLocalDate3.monthValue+1).toString()].toString().uppercase())
                setDeleteTestButtonListener(listOfThreeTests[2])
                setStartStudyingButtonListener(listOfThreeTests[2])
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


    private fun hideAllModals(){
        binding.addNewTestModal.visibility = View.GONE
        binding.learnModal.visibility = View.GONE
    }

    private fun newTestModalReset(){
        binding.addNewTestModal.visibility = View.GONE
        onResume()
    }

    private fun setListeners(){

        binding.settingsButton.setOnClickListener{
            Log.e("TAG", "Go to settings")
            val settingsIntent = Intent(this, SettingsActivity::class.java)
            startActivity(settingsIntent)
        }
        binding.statisticsButton.setOnClickListener{
            Log.e("TAG", "Go to statistics")
            val statisticsIntent = Intent(this, StatisticsActivity::class.java)
            startActivity(statisticsIntent)
        }
        binding.calendarButton.setOnClickListener{
            Log.e("TAG", "Go to calendar")
            val calendarIntent = Intent(this, CalendarActivity::class.java)
            startActivity(calendarIntent)
        }
        binding.openNewTestModalButton.setOnClickListener{
            hideAllModals()


            binding.addNewTestModal.visibility = View.VISIBLE
            val levelOfEdu = viewModel.getUserInfoAsync().levelOfEdu

            if(levelOfEdu == "Podstawowa"){
                val lessonsList = resources.getStringArray(R.array.listOfPrimaryLessons)
                val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, lessonsList)
                binding.lessonsSpinner.adapter = adapter
            }else if(levelOfEdu == "Średnia"){
                val lessonsList = resources.getStringArray(R.array.listOfHighLessons)
                val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, lessonsList)
                binding.lessonsSpinner.adapter = adapter
            }

            binding.lessonsSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
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

            binding.cancelNewTestButton.setOnClickListener{
                newTestModalReset()
            }

            binding.timeOfNotificationTimePicker.setIs24HourView(true)


            createNotificationChannel()
            binding.addNewTestButton.setOnClickListener{




                scheduleNotification()


                val lesson = binding.lessonsSpinner.selectedItem.toString()
                val topic = binding.topicSpinner.selectedItem.toString()
                val dateOfExamLocalDate = LocalDate.of(binding.testDatePicker.year, binding.testDatePicker.month+1, binding.testDatePicker.dayOfMonth)

                //val date = java.time.format.DateTimeFormatter.ISO_INSTANT
              //      .format(java.time.Instant.ofEpochSecond(dateOfExamLocalDate.toEpochDay()*86400L))

                val dateOfExam = dateOfExamLocalDate.toEpochDay()*86400L
                var reminder = 0
                val timeOfRemindH = binding.timeOfNotificationTimePicker.hour.toString()
                val timeOfRemindM = binding.timeOfNotificationTimePicker.minute.toString()
                val timeOfLearning = 0.0
                val watchedVideos = 0







                when(binding.notificationSpinner.selectedItem.toString()){
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

    private fun scheduleNotification(){

        val intent = Intent(applicationContext, Notification::class.java)
        val title = "Tytuł"
        val message = "Wiadomosc"

        intent.putExtra(titleExtra, title)
        intent.putExtra(messageExtra, message)

        val pendingIntent = PendingIntent.getBroadcast(
            applicationContext,
            NOTIFICATION_ID,
            intent,
            PendingIntent.FLAG_IMMUTABLE or PendingIntent.FLAG_UPDATE_CURRENT)

        val alarmManger = getSystemService(Context.ALARM_SERVICE) as AlarmManager

        val time = getTime()
        alarmManger.setExactAndAllowWhileIdle(
            AlarmManager.RTC_WAKEUP,
            time,
            pendingIntent
        )
        showAlert(time, title, message)


    }

    private fun showAlert(time: Long, title: String, message: String) {
        val date = Date(time)
        val dateFormat = android.text.format.DateFormat.getLongDateFormat(applicationContext)
        val timeFormat = android.text.format.DateFormat.getTimeFormat(applicationContext)

        AlertDialog.Builder(this)
            .setTitle("Tytuł tej notyfikacji na s")
            .setMessage(
                "Title: " + title +
                        "\nMessage: " + message +
                        "\nAt: " + dateFormat.format(date) + " " + timeFormat.format(date))
            .setPositiveButton("Okay"){_,_ ->}
            .show()
    }


    private fun getTime(): Long{
        val hour = binding.timeOfNotificationTimePicker.hour
        val minute = binding.timeOfNotificationTimePicker.minute
        val year = binding.testDatePicker.year
        val month = binding.testDatePicker.month
        val dayOfMonth = binding.testDatePicker.dayOfMonth

        val calendar = Calendar.getInstance()
        calendar.set(year, month, dayOfMonth, hour, minute)
        return calendar.timeInMillis
    }

    private fun createNotificationChannel(){
        val name = "Notif Channel"
        val desc = "A description"
        val importance = NotificationManager.IMPORTANCE_DEFAULT
        val channel = NotificationChannel(CHANNEL_ID, name, importance)
        channel.description = desc
        val notificationManager = getSystemService(NOTIFICATION_SERVICE) as NotificationManager
        notificationManager.createNotificationChannel(channel)


    }



}