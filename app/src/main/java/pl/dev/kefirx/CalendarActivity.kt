package pl.dev.kefirx

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.applandeo.materialcalendarview.EventDay
import pl.dev.kefirx.MainActivity.Companion.viewModel
import pl.dev.kefirx.databinding.ActivityCalendarBinding
import java.time.LocalDate
import java.util.*
import kotlin.collections.ArrayList


class CalendarActivity : AppCompatActivity() {

    private lateinit var binding: ActivityCalendarBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCalendarBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.backToDashboardButton.setOnClickListener{
            this.finish()
        }

        setHighLightedDays()




        binding.calendarView.setOnDayClickListener { eventDay ->
            val clickedDayCalendar: Calendar = eventDay.calendar

        }


    }

    private fun setHighLightedDays(){
        val datesToHighlightList: MutableList<Calendar> = ArrayList()
        val calendar = Calendar.getInstance()

        val testsList = viewModel.getAllTestsInfoAsync()

        testsList.forEach{
            val dateOfExamMillis = it.dateOfExam
            val dateOfExamDays = dateOfExamMillis/1000/86400
            val dateOfExam = LocalDate.ofEpochDay(dateOfExamDays)
            calendar.set(dateOfExam.year, dateOfExam.monthValue-1, dateOfExam.dayOfMonth)
            datesToHighlightList.add(calendar.clone() as Calendar)
        }

        binding.calendarView.setHighlightedDays(datesToHighlightList)

    }


}