package pl.dev.kefirx.classes

import android.R.layout
import android.app.AlarmManager
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
import pl.dev.kefirx.MainActivity.Companion.viewModel
import pl.dev.kefirx.SettingsActivity
import pl.dev.kefirx.StatisticsActivity
import pl.dev.kefirx.databinding.ActivityMainBinding
import pl.dev.kefirx.reminder.NotificationReceiver
import pl.dev.kefirx.reminder.NotificationReceiver.Companion.channelID
import pl.dev.kefirx.reminder.NotificationReceiver.Companion.messageExtra
import pl.dev.kefirx.reminder.NotificationReceiver.Companion.notificationID
import pl.dev.kefirx.reminder.NotificationReceiver.Companion.titleExtra
import pl.dev.kefirx.room.Tests
import pl.dev.kefirx.viewModel.CSViewModel
import java.util.*

class ListenersSet {

    fun setMainActivityListeners(binding: ActivityMainBinding, applicationContext: Context, instance: MainActivity){

        val modalsView = ModalsView()
        val spinnersSet = SpinnersSet()
        val notification = Notification()

        setCurrentDateTime(binding)

        binding.settingsButton.setOnClickListener{
            Log.e("TAG", "Go to settings")
            val settingsIntent = Intent(applicationContext, SettingsActivity::class.java)
            instance.startActivity(settingsIntent)
        }
        binding.statisticsButton.setOnClickListener{
            Log.e("TAG", "Go to statistics")
            val statisticsIntent = Intent(applicationContext, StatisticsActivity::class.java)
            instance.startActivity(statisticsIntent)
        }
        binding.calendarButton.setOnClickListener{
            Log.e("TAG", "Go to calendar")
            val calendarIntent = Intent(applicationContext, CalendarActivity::class.java)
            instance.startActivity(calendarIntent)
        }
        binding.openNewTestModalButton.setOnClickListener{
            modalsView.hideAllModals(binding)

            binding.addNewTestModal.visibility = View.VISIBLE
            val levelOfEdu = viewModel.getUserInfoAsync().levelOfEdu

            if(levelOfEdu == "Podstawowa"){
                val lessonsList = instance.resources.getStringArray(pl.dev.kefirx.R.array.listOfPrimaryLessons)
                val adapter = ArrayAdapter(instance, layout.simple_spinner_item, lessonsList)
                binding.lessonsSpinner.adapter = adapter
            }else if(levelOfEdu == "Średnia"){
                val lessonsList = instance.resources.getStringArray(pl.dev.kefirx.R.array.listOfHighLessons)
                val adapter = ArrayAdapter(instance, layout.simple_spinner_item, lessonsList)
                binding.lessonsSpinner.adapter = adapter
            }

            binding.lessonsSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
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

            binding.cancelNewTestButton.setOnClickListener{
                modalsView.newTestModalReset(binding, instance)
            }

            binding.timeOfNotificationTimePicker.setIs24HourView(true)


            binding.addNewTestButton.setOnClickListener{

                val lesson = binding.lessonsSpinner.selectedItem.toString()
                val topic = binding.topicSpinner.selectedItem.toString()
                val dateOfExam = notification.getTimeInMillis(binding)
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

                val newTest = Tests(lesson, topic, dateOfExam, timeOfLearning, watchedVideos, reminder, timeOfRemindH, timeOfRemindM)

                viewModel.insertTest(newTest)
                Log.e("TAG", "Insert test")

                println(newTest.dateOfExam)

//                restartViewModel(instance)

                if(reminder!=0){

                    instance.createNotificationChannel()
                    scheduleNotification(applicationContext,instance, lesson, topic, reminder, dateOfExam)
                }

                Toast.makeText(applicationContext, "Dodano sprawdzian", Toast.LENGTH_SHORT).show()

                modalsView.newTestModalReset(binding, instance)
            }

        }
    }

    private fun scheduleNotification(applicationContext: Context, instance: MainActivity, lesson: String, topic: String, reminder: Int, dateOfExam: Long) {
        val intent = Intent(applicationContext, NotificationReceiver::class.java)
        val title = "Czas na naukę!"
        val message = "$lesson - $topic"

        intent.putExtra(titleExtra, title)
        intent.putExtra(messageExtra, message)

        val pendingIntent = PendingIntent.getBroadcast(
            applicationContext,
            notificationID,
            intent,
            PendingIntent.FLAG_IMMUTABLE
        )

        val alarmManager = instance.getSystemService(Context.ALARM_SERVICE) as AlarmManager

        when(reminder){
            1 -> alarmManager.setExactAndAllowWhileIdle(
                AlarmManager.RTC_WAKEUP,
                dateOfExam,
//                AlarmManager.INTERVAL_DAY,
                pendingIntent
            )
            2 -> alarmManager.setInexactRepeating(
                AlarmManager.RTC_WAKEUP,
                dateOfExam,
                AlarmManager.INTERVAL_DAY*2,
               pendingIntent
            )
            3 -> alarmManager.setExactAndAllowWhileIdle(
                AlarmManager.RTC_WAKEUP,
                dateOfExam-86400000,
                pendingIntent
            )
        }

    }

    companion object{
        fun restartViewModel(instance: MainActivity){
            viewModel = ViewModelProvider
                .AndroidViewModelFactory
                .getInstance(instance.application)
                .create(CSViewModel::class.java)
        }
    }



    private fun setCurrentDateTime(binding: ActivityMainBinding){
        val currentDate = Calendar.getInstance()
        val currentYear = currentDate.get(Calendar.YEAR)
        val currentMonth = currentDate.get(Calendar.MONTH)
        val currentDay = currentDate.get(Calendar.DAY_OF_MONTH)
        val currentHour = currentDate.get(Calendar.HOUR_OF_DAY)
        val currentMinute = currentDate.get(Calendar.MINUTE)

        binding.testDatePicker.updateDate(currentYear,currentMonth, currentDay)
        binding.timeOfNotificationTimePicker.hour = currentHour
        binding.timeOfNotificationTimePicker.minute = currentMinute
    }




}