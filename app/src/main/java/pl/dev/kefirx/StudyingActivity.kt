package pl.dev.kefirx

import android.annotation.SuppressLint
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_studying.*
import pl.dev.kefirx.databinding.ActivityStudyingBinding
import pl.dev.kefirx.room.Tests
import pl.dev.kefirx.services.TimerService
import kotlin.math.roundToInt

class StudyingActivity : AppCompatActivity() {

    private lateinit var testToStudying: Tests
    private lateinit var binding: ActivityStudyingBinding
    private lateinit var serviceIntent: Intent
    private var time = 0.0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityStudyingBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val idTestToStudying = intent.getIntExtra("testId", -1)
        testToStudying = MainActivity.viewModel.getTestByIdInfoAsync(idTestToStudying)

        time = testToStudying.timeOfLearning

        serviceIntent = Intent(applicationContext, TimerService::class.java)
        registerReceiver(updateTime, IntentFilter(TimerService.TIMER_UPDATED))
        setListeners()
        setTestInfo()
    }

    override fun onResume() {
        super.onResume()
        startTimer()
    }

    @SuppressLint("SetTextI18n")
    private fun setListeners(){
        studyingPauseButton.setOnClickListener{
            if(studyingPauseButton.text == "PAUZA"){
                studyingPauseButton.text = "START"
                stopTimer()
            }else if (studyingPauseButton.text == "START"){
                studyingPauseButton.text = "PAUZA"
                startTimer()
            }
        }
        studyingStopButton.setOnClickListener{
            finish()
        }
    }

    private fun setTestInfo(){
        testLessonNameText.text = testToStudying.lesson
        testTopicNameText.text = testToStudying.topic
    }

    private fun startTimer(){
        serviceIntent.putExtra(TimerService.TIME_EXTRA, time)
        startService(serviceIntent)
    }
    private fun stopTimer(){
        stopService(serviceIntent)
        testToStudying.timeOfLearning = time
    }

    private val updateTime: BroadcastReceiver = object : BroadcastReceiver(){
        override fun onReceive(context: Context?, intent: Intent?) {
            time = intent!!.getDoubleExtra(TimerService.TIME_EXTRA, 0.0)
            binding.studyingTimeText.text = Convert.getTimeStringFromDouble(time)
        }
    }


    override fun onPause() {
        super.onPause()
        stopService(serviceIntent)
        testToStudying.timeOfLearning = time
        MainActivity.viewModel.updateTest(testToStudying)
    }
}