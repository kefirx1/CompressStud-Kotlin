package pl.dev.kefirx

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.applandeo.materialcalendarview.EventDay
import pl.dev.kefirx.databinding.ActivityCalendarBinding
import pl.dev.kefirx.room.Tests
import pl.dev.kefirx.viewModel.CSViewModel
import java.time.LocalDateTime
import java.time.ZoneOffset
import java.util.*
import kotlin.collections.ArrayList
import kotlin.collections.HashMap


class CalendarActivity : AppCompatActivity() {


    private val events: MutableList<EventDay> = ArrayList()
    private lateinit var binding: ActivityCalendarBinding
    private val examsToViewList: ArrayList<Tests> = ArrayList()
    private val examsToEvent: HashMap<EventDay, Tests> = HashMap()
    private lateinit var viewModel: CSViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCalendarBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.backToDashboardButton.setOnClickListener{
            this.finish()
        }
    }

    override fun onResume() {
        super.onResume()

        viewModel = ViewModelProvider
            .AndroidViewModelFactory
            .getInstance(application)
            .create(CSViewModel::class.java)

        setEventsToDays()

        binding.calendarView.setOnDayClickListener { eventDay ->
            val exam: Tests

            if( events.contains(eventDay)){
                binding.examCalendarLayout.visibility = View.VISIBLE

                exam = examsToEvent[eventDay]!!

                setExamDetails(exam)
                setStartStudyingButtonListener(exam)
                setDeleteTestButtonListener(exam)

            }else{
                binding.examCalendarLayout.visibility = View.GONE
            }

        }
    }

    private fun setStartStudyingButtonListener(test: Tests){
        binding.startStudyingCalendarButton.setOnClickListener{
            val studyingIntent = Intent(applicationContext, StudyingActivity::class.java).apply {
                putExtra("testId", test.test_id )
            }
            startActivity(studyingIntent)
        }
    }

    private fun setDeleteTestButtonListener(test: Tests){
        binding.deleteTestCalendarButton.setOnClickListener{
            viewModel.deleteTest(test)
            Log.e("TAG", "Test deleted")
            examsToViewList.clear()
            events.clear()
            examsToEvent.clear()
            binding.examCalendarLayout.visibility = View.GONE
            onResume()
        }
    }

    private fun setExamDetails(test: Tests){
        binding.calendarTopicName.text = test.topic
        binding.calendarLessonName.text = test.lesson
    }

    private fun setEventsToDays(){
        val calendar = Calendar.getInstance()
        val testsList = viewModel.getAllTestsInfoAsync()

        testsList.forEach{
            examsToViewList.add(it)
            val dateOfExamMillis = it.dateOfExam
            val dateOfExamSec = dateOfExamMillis/1000
            val dateOfExam = LocalDateTime.ofEpochSecond(dateOfExamSec, 0,
                ZoneOffset.UTC
            )

            var hour = dateOfExam.hour+1

            if(dateOfExam.monthValue==3 && dateOfExam.dayOfMonth>=27){
                hour++
            }else if(dateOfExam.monthValue in 4..8){
                hour++
            }

            calendar.set(dateOfExam.year, dateOfExam.monthValue-1, dateOfExam.dayOfMonth, hour, dateOfExam.minute, dateOfExam.second)
            val event = EventDay(calendar.clone() as Calendar, R.drawable.ic_baseline_book_24)
            events.add(event)
            examsToEvent[event] = it
        }

        binding.calendarView.setEvents(events)
    }


}