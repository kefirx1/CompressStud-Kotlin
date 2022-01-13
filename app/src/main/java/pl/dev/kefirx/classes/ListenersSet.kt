package pl.dev.kefirx.classes

import android.R
import android.content.Context
import android.content.Intent
import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import pl.dev.kefirx.*
import pl.dev.kefirx.databinding.ActivityMainBinding
import pl.dev.kefirx.room.Tests

class ListenersSet {

    fun setMainActivityListeners(binding: ActivityMainBinding, applicationContext: Context, instance: MainActivity){

        val modalsView = ModalsView(binding, instance)
        val spinnersSet = SpinnersSet()

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
            modalsView.hideAllModals()

            println("Siema")


            binding.addNewTestModal.visibility = View.VISIBLE
            val levelOfEdu = MainActivity.viewModel.getUserInfoAsync().levelOfEdu

            if(levelOfEdu == "Podstawowa"){
                val lessonsList = instance.resources.getStringArray(pl.dev.kefirx.R.array.listOfPrimaryLessons)
                val adapter = ArrayAdapter(applicationContext, R.layout.simple_spinner_item, lessonsList)
                binding.lessonsSpinner.adapter = adapter
            }else if(levelOfEdu == "Średnia"){
                val lessonsList = instance.resources.getStringArray(pl.dev.kefirx.R.array.listOfHighLessons)
                val adapter = ArrayAdapter(applicationContext, R.layout.simple_spinner_item, lessonsList)
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
                modalsView.newTestModalReset()
            }

            binding.timeOfNotificationTimePicker.setIs24HourView(true)


            binding.addNewTestButton.setOnClickListener{

                val lesson = binding.lessonsSpinner.selectedItem.toString()
                val topic = binding.topicSpinner.selectedItem.toString()
                val dateOfExam = instance.getTimeInMillis() //TODO
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

                //TODO
                instance.schedulePushNotifications(lesson, topic)

                MainActivity.viewModel.insertTest(Tests(lesson, topic, dateOfExam, timeOfLearning, watchedVideos, reminder, timeOfRemindH, timeOfRemindM))
                Log.e("TAG", "Insert test")
                Toast.makeText(applicationContext, "Dodano sprawdzian", Toast.LENGTH_SHORT).show()

                modalsView.newTestModalReset()
            }

        }
    }




}