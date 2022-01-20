package pl.dev.kefirx

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.applandeo.materialcalendarview.EventDay
import pl.dev.kefirx.MainActivity.Companion.viewModel
import pl.dev.kefirx.databinding.ActivityCalendarBinding
import pl.dev.kefirx.room.Tests
import pl.dev.kefirx.viewModel.CSViewModel
import java.time.LocalDateTime
import java.time.ZoneOffset
import java.util.*
import kotlin.collections.ArrayList


class CalendarActivity : AppCompatActivity() {


    private val events: MutableList<EventDay> = ArrayList()
    private lateinit var binding: ActivityCalendarBinding
    private val examsToViewList: ArrayList<Tests> = ArrayList()

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
            val clickedDayCalendar: Calendar = eventDay.calendar
            var exam = Tests("","",0,0.0,0,0,"", "")
            val calendarDay = clickedDayCalendar.timeInMillis

            if( events.contains(eventDay)){
                binding.examCalendarLayout.visibility = View.VISIBLE

                examsToViewList.forEach{
                    if(it.dateOfExam/1000/60/60/24 == (calendarDay/1000/60/60/24)+1){
                        exam = it
                    }
                    return@forEach
                }

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
            events.add(EventDay(calendar.clone() as Calendar, R.drawable.ic_baseline_book_24))
        }

        binding.calendarView.setEvents(events)
    }


}