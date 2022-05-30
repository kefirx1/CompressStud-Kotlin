package pl.dev.kefirx

import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.applandeo.materialcalendarview.EventDay
import dagger.hilt.android.AndroidEntryPoint
import pl.dev.kefirx.adapters.ViewPagerAdapter
import pl.dev.kefirx.data.Tests
import pl.dev.kefirx.databinding.ActivityCalendarBinding
import pl.dev.kefirx.viewModels.CalendarViewModel
import java.time.LocalDateTime
import java.time.ZoneOffset
import java.util.*

@AndroidEntryPoint
class CalendarActivity : AppCompatActivity() {


    companion object {
        val examsToViewList: ArrayList<Tests> = ArrayList()
        val examsToEvent: HashMap<EventDay, Tests> = HashMap()
        val events: MutableList<EventDay> = ArrayList()
    }


    private lateinit var binding: ActivityCalendarBinding
    private val viewModel: CalendarViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCalendarBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.backToDashboardButton.setOnClickListener {
            this.finish()
        }
    }


    override fun onResume() {
        super.onResume()

//        resetViewModel()

        setEventsToDays()

        viewModel.setAllTestsInfoObserver()

        binding.calendarView.setOnDayClickListener { eventDay ->

            val examsList: ArrayList<Tests> = ArrayList()

            if (events.contains(eventDay)) {


                binding.examCalendarLayout.visibility = View.VISIBLE
                binding.circleIndicatorCalendar.visibility = View.VISIBLE

                examsToEvent.forEach {
                    if (it.value.dateOfExam / 1000 / 60 / 60 / 24 == (eventDay.calendar.timeInMillis / 1000 / 60 / 60 / 24) + 1) {
                        examsList.add(it.value)
                    }
                }

                binding.examCalendarLayout.adapter =
                    ViewPagerAdapter(examsList, applicationContext, viewModel, this)
                binding.circleIndicatorCalendar.setViewPager(binding.examCalendarLayout)

            } else {
                examsList.clear()
                binding.circleIndicatorCalendar.visibility = View.GONE
                binding.examCalendarLayout.visibility = View.GONE
            }

        }
    }

//    private fun resetViewModel() {
//        viewModel = ViewModelProvider
//            .AndroidViewModelFactory
//            .getInstance(application)
//            .create(CalendarViewModel::class.java)
//    }

    private fun setEventsToDays() {

        val calendar = Calendar.getInstance()

        viewModel.testInfoResult.observe(this){ testsList ->
            if(testsList!=null){
                testsList.forEach {
                    examsToViewList.add(it)
                    val dateOfExamMillis = it.dateOfExam
                    val dateOfExamSec = dateOfExamMillis / 1000
                    val dateOfExam = LocalDateTime.ofEpochSecond(
                        dateOfExamSec, 0,
                        ZoneOffset.UTC
                    )

                    var hour = dateOfExam.hour + 1

                    if (dateOfExam.monthValue == 3 && dateOfExam.dayOfMonth >= 27) {
                        hour++
                    } else if (dateOfExam.monthValue in 4..8) {
                        hour++
                    }

                    calendar.set(
                        dateOfExam.year,
                        dateOfExam.monthValue - 1,
                        dateOfExam.dayOfMonth,
                        hour,
                        dateOfExam.minute,
                        dateOfExam.second
                    )
                    val event = EventDay(calendar.clone() as Calendar, R.drawable.ic_baseline_book_24)
                    events.add(event)
                    examsToEvent[event] = it
                }

                binding.calendarView.setEvents(events)

            }else{
                //TODO
            }
        }

    }


    private fun clearAllCollections() {
        examsToViewList.clear()
        events.clear()
        examsToEvent.clear()
    }

    override fun onDestroy() {
        super.onDestroy()
        clearAllCollections()
    }

}