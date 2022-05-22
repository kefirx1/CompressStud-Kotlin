package pl.dev.kefirx.classes

import android.content.Context
import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import pl.dev.kefirx.CalendarActivity
import pl.dev.kefirx.R
import pl.dev.kefirx.StudyingActivity
import pl.dev.kefirx.room.Tests
import pl.dev.kefirx.viewModels.CalendarViewModel

class ViewPagerAdapter(private val examsList: ArrayList<Tests>, private val applicationContext: Context, val viewModel: CalendarViewModel, private val instance: CalendarActivity): RecyclerView.Adapter<ViewPagerAdapter.Pager2ViewHandler>() {

    inner class Pager2ViewHandler(itemView: View): RecyclerView.ViewHolder(itemView) {
        val lessonTextView: TextView = itemView.findViewById(R.id.calendarLessonName)
        val topicTextView: TextView = itemView.findViewById(R.id.calendarTopicName)
        val startButton: Button = itemView.findViewById(R.id.startStudyingCalendarButton)
        val deleteButton: Button = itemView.findViewById(R.id.deleteTestCalendarButton)

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Pager2ViewHandler {
        return Pager2ViewHandler(LayoutInflater.from(parent.context).inflate(R.layout.exam_info_page, parent, false))
    }

    override fun onBindViewHolder(holder: Pager2ViewHandler, position: Int) {
        setStartStudyingButtonListener(holder, position)
        setDeleteTestButtonListener(holder, position)
        setExamDetails(holder, position)
    }

    override fun getItemCount(): Int {
        return examsList.size
    }


    private fun setStartStudyingButtonListener(holder: Pager2ViewHandler, position: Int){
        holder.startButton.setOnClickListener{

            if (DeviceInfo.isDeviceOnline(applicationContext)) {
                val studyingIntent =
                    Intent(applicationContext, StudyingActivity::class.java).apply {
                        putExtra("testId", examsList[position].test_id)
                    }
                studyingIntent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
                applicationContext.startActivity(studyingIntent)
            }else{
                Toast.makeText(applicationContext, "Brak internetu", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun setDeleteTestButtonListener(holder: Pager2ViewHandler, position: Int){
        holder.deleteButton.setOnClickListener{
            viewModel.deleteTest(examsList[position])
            Notification().cancelNotificationByID(examsList[position].test_id, applicationContext)
            Log.e("TAG", "Test deleted")
            instance.finish()
        }
    }

    private fun setExamDetails(holder: Pager2ViewHandler, position: Int){
        holder.topicTextView.text = examsList[position].topic
        holder.lessonTextView.text = examsList[position].lesson
    }


}