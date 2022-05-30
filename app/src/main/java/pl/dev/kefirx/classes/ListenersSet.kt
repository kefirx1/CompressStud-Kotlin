package pl.dev.kefirx.classes

import android.R.layout
import android.app.AlarmManager
import android.app.Application
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import pl.dev.kefirx.CalendarActivity
import pl.dev.kefirx.MainActivity
import pl.dev.kefirx.SettingsActivity
import pl.dev.kefirx.StatisticsActivity
import pl.dev.kefirx.data.Tests
import pl.dev.kefirx.databinding.ActivityMainBinding
import pl.dev.kefirx.receivers.NotificationReceiver
import pl.dev.kefirx.receivers.NotificationReceiver.Companion.CHANNEL_EXTRA
import pl.dev.kefirx.receivers.NotificationReceiver.Companion.MESSAGE_EXTRA
import pl.dev.kefirx.receivers.NotificationReceiver.Companion.NOTIFICATION_EXTRA
import pl.dev.kefirx.receivers.NotificationReceiver.Companion.TITLE_EXTRA
import pl.dev.kefirx.viewModels.DashboardViewModel
import java.util.*


class ListenersSet (
    private val application: Application,
    private val viewModel: DashboardViewModel
) {

    fun setMainActivityListeners(
        binding: ActivityMainBinding,
        applicationContext: Context,
        instance: MainActivity
    ) {

        val modalsView = ModalsView()
        val spinnersSet = SpinnersSet()

        setCurrentDateTime(binding)

        if (binding.settingsButton.isClickable) {
            binding.settingsButton.setOnClickListener {
                Log.e("TAG", "Go to settings")
                val settingsIntent = Intent(
                    applicationContext, SettingsActivity::class.java
                )
                instance.startActivity(settingsIntent)
            }
        }
        if (binding.statisticsButton.isClickable) {
            binding.statisticsButton.setOnClickListener {
                Log.e("TAG", "Go to statistics")
                val statisticsIntent = Intent(applicationContext, StatisticsActivity::class.java)
                instance.startActivity(statisticsIntent)
            }
        }
        if (binding.calendarButton.isClickable) {
            binding.calendarButton.setOnClickListener {
                Log.e("TAG", "Go to calendar")
                val calendarIntent = Intent(applicationContext, CalendarActivity::class.java)
                instance.startActivity(calendarIntent)
            }
        }

        if (binding.openNewTestModalButton.isClickable) {
            binding.openNewTestModalButton.setOnClickListener {
                modalsView.hideAllModals(binding)

                binding.openNewTestModalButton.isClickable = false
                binding.top3test1.isClickable = false
                binding.top3test2.isClickable = false
                binding.top3test3.isClickable = false
                binding.calendarButton.isClickable = false
                binding.statisticsButton.isClickable = false
                binding.settingsButton.isClickable = false

                binding.addNewTestModal.visibility = View.VISIBLE
                var levelOfEdu = ""

                viewModel.userInfoResult.observe(instance){
                    if(it!=null){
                        levelOfEdu = it.levelOfEdu
                    }
                }

                if (levelOfEdu == "Podstawowa") {
                    val lessonsList =
                        instance.resources.getStringArray(pl.dev.kefirx.R.array.listOfPrimaryLessons)
                    val adapter = ArrayAdapter(instance, layout.simple_spinner_item, lessonsList)
                    binding.lessonsSpinner.adapter = adapter
                } else if (levelOfEdu == "Średnia") {
                    val lessonsList =
                        instance.resources.getStringArray(pl.dev.kefirx.R.array.listOfHighLessons)
                    val adapter = ArrayAdapter(instance, layout.simple_spinner_item, lessonsList)
                    binding.lessonsSpinner.adapter = adapter
                }

                binding.lessonsSpinner.onItemSelectedListener =
                    object : AdapterView.OnItemSelectedListener {
                        override fun onItemSelected(
                            parent: AdapterView<*>?,
                            view: View?,
                            position: Int,
                            id: Long
                        ) {
                            spinnersSet.setMATopicSpinner(binding, instance, levelOfEdu)
                        }

                        override fun onNothingSelected(parent: AdapterView<*>?) {
                        }
                    }

                binding.notificationSpinner.onItemSelectedListener =
                    object : AdapterView.OnItemSelectedListener {
                        override fun onItemSelected(
                            parent: AdapterView<*>?,
                            view: View?,
                            position: Int,
                            id: Long
                        ) {
                            binding.timeOfNotificationTimePicker.isEnabled =
                                binding.notificationSpinner.selectedItem.toString() != "Brak"
                        }

                        override fun onNothingSelected(parent: AdapterView<*>?) {
                        }
                    }

                binding.cancelNewTestButton.setOnClickListener {
                    modalsView.newTestModalReset(binding, instance)
                }

                binding.timeOfNotificationTimePicker.setIs24HourView(true)


                binding.addNewTestButton.setOnClickListener {
                    val dateOfExam = getTimeInMillis(binding)

                    if(dateOfExam > Calendar.getInstance().timeInMillis){
                        val lesson = binding.lessonsSpinner.selectedItem.toString()
                        val topic = binding.topicSpinner.selectedItem.toString()
                        var reminder = 0
                        val timeOfRemindH: String
                        val timeOfRemindM: String
                        if(binding.timeOfNotificationTimePicker.isEnabled){
                            timeOfRemindH = binding.timeOfNotificationTimePicker.hour.toString()
                            timeOfRemindM = binding.timeOfNotificationTimePicker.minute.toString()
                        }else{
                            timeOfRemindH = "-"
                            timeOfRemindM = "-"
                        }
                        val timeOfLearning = 0.0
                        val watchedVideos = 0

                        when (binding.notificationSpinner.selectedItem.toString()) {
                            "Codziennie" -> reminder = 1
                            "Co dwa dni" -> reminder = 2
                            "Dzień przed sprawdzianem" -> reminder = 3
                        }

                        val newTest = Tests(
                            lesson,
                            topic,
                            dateOfExam,
                            timeOfLearning,
                            watchedVideos,
                            reminder,
                            timeOfRemindH,
                            timeOfRemindM
                        )

                        viewModel.insertTest(newTest)
                        Log.e("TAG", "Insert test")

//                        viewModel = ViewModelProvider
//                            .AndroidViewModelFactory
//                            .getInstance(application)
//                            .create(DashboardViewModel::class.java)

                        var notificationID: Int
                        var channelID: String

                        viewModel.setNewestTestInfoObserver()

                        viewModel.newestTestInfoResult.observe(instance){
                            if(it!=null){
                                val exam = it

                                if (reminder != 0) {
                                    notificationID = exam.test_id
                                    channelID = "channel" + (exam.test_id).toString()

                                    instance.createNotificationChannel(channelID)
                                    scheduleNotification(
                                        applicationContext,
                                        instance,
                                        lesson,
                                        topic,
                                        reminder,
                                        dateOfExam,
                                        notificationID,
                                        channelID,
                                        timeOfRemindH,
                                        timeOfRemindM
                                    )
                                }
                            }else{
                                //TODO
                            }

                        }


                        Toast.makeText(applicationContext, "Dodano sprawdzian", Toast.LENGTH_SHORT)
                            .show()

                        modalsView.newTestModalReset(binding, instance)
                    }else{
                        Toast.makeText(applicationContext, "Podaj popawną datę", Toast.LENGTH_SHORT)
                            .show()
                    }

                }

            }
        }
    }

    private fun scheduleNotification(
        applicationContext: Context,
        instance: MainActivity,
        lesson: String,
        topic: String,
        reminder: Int,
        dateOfExam: Long,
        notificationID: Int,
        channelID: String,
        timeOfRemindH: String,
        timeOfRemindM: String
    ) {
        val intent = Intent(instance, NotificationReceiver::class.java)
        val title = "Czas na naukę!"
        val message = "$lesson - $topic"

        intent.putExtra(TITLE_EXTRA, title)
        intent.putExtra(MESSAGE_EXTRA, message)
        intent.putExtra(CHANNEL_EXTRA, channelID)
        intent.putExtra(NOTIFICATION_EXTRA, notificationID)

        val pendingIntent = PendingIntent.getBroadcast(
            applicationContext,
            notificationID,
            intent,
            PendingIntent.FLAG_IMMUTABLE
        )

        val alarmManager = instance.getSystemService(Context.ALARM_SERVICE) as AlarmManager

        val currentDate = Calendar.getInstance()
        val year = currentDate.get(Calendar.YEAR)
        val month = currentDate.get(Calendar.MONTH)
        val day = currentDate.get(Calendar.DAY_OF_MONTH)
        val hour = timeOfRemindH.toInt()
        val minute = timeOfRemindM.toInt()
        val nextDay = Calendar.getInstance()
        nextDay.set(year, month, day, hour, minute, 0)
        val nextDayMillis = nextDay.timeInMillis + 86400000


        when (reminder) {
            1 -> alarmManager.setInexactRepeating(
                AlarmManager.RTC_WAKEUP,
                nextDayMillis,
                AlarmManager.INTERVAL_DAY,
                pendingIntent
            )
            2 -> alarmManager.setInexactRepeating(
                AlarmManager.RTC_WAKEUP,
                nextDayMillis,
                AlarmManager.INTERVAL_DAY * 2,
                pendingIntent
            )
            3 -> alarmManager.setExactAndAllowWhileIdle(
                AlarmManager.RTC_WAKEUP,
                dateOfExam - 86400000,
                pendingIntent
            )
        }

    }

    private fun setCurrentDateTime(binding: ActivityMainBinding) {
        val currentDate = Calendar.getInstance()
        val currentYear = currentDate.get(Calendar.YEAR)
        val currentMonth = currentDate.get(Calendar.MONTH)
        val currentDay = currentDate.get(Calendar.DAY_OF_MONTH)
        val currentHour = currentDate.get(Calendar.HOUR_OF_DAY)
        val currentMinute = currentDate.get(Calendar.MINUTE)

        binding.testDatePicker.updateDate(currentYear, currentMonth, currentDay)
        binding.timeOfNotificationTimePicker.hour = currentHour
        binding.timeOfNotificationTimePicker.minute = currentMinute
    }

    private fun getTimeInMillis(binding: ActivityMainBinding): Long {
        val year = binding.testDatePicker.year
        val month = binding.testDatePicker.month
        val dayOfMonth = binding.testDatePicker.dayOfMonth

        val calendar = Calendar.getInstance()
        calendar.set(year, month, dayOfMonth, 23, 0, 0)

        return calendar.timeInMillis
    }

}