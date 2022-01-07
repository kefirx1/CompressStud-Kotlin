package pl.dev.kefirx

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_studying.*
import kotlinx.coroutines.delay
import pl.dev.kefirx.room.Tests
import java.lang.Exception

class StudyingActivity : AppCompatActivity() {

    lateinit var testToStudying: Tests

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_studying)

        val idTestToStudying = intent.getIntExtra("testId", -1)
        testToStudying = MainActivity.viewModel.getTestByIdInfoAsync(idTestToStudying)

        setListeners()
        setTestInfo()
    }



    @SuppressLint("SetTextI18n")
    private fun setListeners(){
        studyingPauseButton.setOnClickListener{
            if(studyingPauseButton.tag == "1"){
                studyingPauseButton.text = "START"
                studyingPauseButton.tag = "0"
            }else if (studyingPauseButton.tag == "0"){
                studyingPauseButton.text = "PAUZA"
                studyingPauseButton.tag = "1"
            }
        }

        studyingStopButton.setOnClickListener{

            finish()
        }

    }

    private fun setTestInfo(){
        testLessonNameText.text = testToStudying.lesson
        testTopicNameText.text = testToStudying.topic
        studyingTimeText.text = testToStudying.timeOfLearning.toString()
    }
}