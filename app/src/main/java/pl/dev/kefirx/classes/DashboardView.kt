package pl.dev.kefirx.classes

import android.content.Context
import android.content.Intent
import android.util.Log
import android.view.View
import pl.dev.kefirx.MainActivity
import pl.dev.kefirx.StudyingActivity
import pl.dev.kefirx.databinding.ActivityMainBinding
import pl.dev.kefirx.room.Tests
import java.time.Instant
import java.time.ZoneId

class DashboardView(val binding: ActivityMainBinding, private val applicationContext: Context, private val instance: MainActivity){

    private var modalsView = ModalsView(binding, instance)

    fun setBestOfThreeView(listOfThreeTests: List<Tests>){

        binding.learnModalBackButton.setOnClickListener{
            binding.learnModal.visibility = View.GONE
        }

        when(listOfThreeTests.size){
            0 -> setTestsNull()

            1 -> {  setFirstTest(listOfThreeTests) }

            2 -> {  setFirstTest(listOfThreeTests)
                    setSecondTest(listOfThreeTests)}

            3 -> {  setFirstTest(listOfThreeTests)
                    setSecondTest(listOfThreeTests)
                    setThirdTest(listOfThreeTests)}
        }

    }


    private fun setTestsNull(){
        with(binding){
            top3TestFirstDayNumber.text = ""
            top3testSecondDayNumber.text = ""
            top3TestThirdDayNumber.text = ""
            top3TestFirstMonth.text = ""
            top3TestSecondMonth.text = ""
            top3TestThirdMonth.text = ""
            top3TestFirstLessonName.text = ""
            top3TestSecondLessonName.text = ""
            top3TestThirdLessonName.text = ""
        }
    }

    private fun setFirstTest(listOfThreeTests: List<Tests>){

        val dateLong1 = listOfThreeTests[0].dateOfExam
        val  dateLocalDate1  = Instant.ofEpochMilli(dateLong1)
            .atZone(ZoneId.systemDefault())
            .toLocalDate()

        binding.top3testSecondDayNumber.text = dateLocalDate1.dayOfMonth.toString()
        binding.top3TestSecondMonth.text = Convert.monthMap[dateLocalDate1.monthValue.toString()]
        binding.top3TestSecondLessonName.text = listOfThreeTests[0].lesson

        binding.top3test1.setOnClickListener{
            modalsView.hideAllModals()
            binding.learnModal.visibility = View.VISIBLE
            binding.modalLessonName.text = listOfThreeTests[0].lesson.uppercase()
            binding.modalTopicName.text = listOfThreeTests[0].topic.uppercase()
            binding.modalDate.text = StringBuilder(dateLocalDate1.dayOfMonth.toString()
                    + " " + Convert.monthFullMap[(dateLocalDate1.monthValue).toString()].toString().uppercase())
            setDeleteTestButtonListener(listOfThreeTests[0])
            setStartStudyingButtonListener(listOfThreeTests[0])
        }

    }
    private fun setSecondTest(listOfThreeTests: List<Tests>){

        val dateLong2 = listOfThreeTests[1].dateOfExam
        val  dateLocalDate2  = Instant.ofEpochMilli(dateLong2)
            .atZone(ZoneId.systemDefault())
            .toLocalDate()

        binding.top3TestFirstDayNumber.text = dateLocalDate2.dayOfMonth.toString()
        binding.top3TestFirstMonth.text = Convert.monthMap[dateLocalDate2.monthValue.toString()]
        binding.top3TestFirstLessonName.text = listOfThreeTests[1].lesson



        binding.top3test2.setOnClickListener{
            modalsView.hideAllModals()
            binding.learnModal.visibility = View.VISIBLE
            binding.modalLessonName.text = listOfThreeTests[1].lesson.uppercase()
            binding.modalTopicName.text = listOfThreeTests[1].topic.uppercase()
            binding.modalDate.text = StringBuilder(dateLocalDate2.dayOfMonth.toString()
                    + " " + Convert.monthFullMap[(dateLocalDate2.monthValue).toString()].toString().uppercase())
            setDeleteTestButtonListener(listOfThreeTests[1])
            setStartStudyingButtonListener(listOfThreeTests[1])
        }
    }
    private fun setThirdTest(listOfThreeTests: List<Tests>){

        val dateLong3 = listOfThreeTests[2].dateOfExam
        val  dateLocalDate3  = Instant.ofEpochMilli(dateLong3)
            .atZone(ZoneId.systemDefault())
            .toLocalDate()

        binding.top3TestThirdDayNumber.text = dateLocalDate3.dayOfMonth.toString()
        binding.top3TestThirdMonth.text = Convert.monthMap[dateLocalDate3.monthValue.toString()]
        binding.top3TestThirdLessonName.text = listOfThreeTests[2].lesson

        binding.top3test3.setOnClickListener{
            modalsView.hideAllModals()
            binding.learnModal.visibility = View.VISIBLE
            binding.modalLessonName.text = listOfThreeTests[2].lesson.uppercase()
            binding.modalTopicName.text = listOfThreeTests[2].topic.uppercase()
            binding.modalDate.text = StringBuilder(dateLocalDate3.dayOfMonth.toString()
                    + " " + Convert.monthFullMap[(dateLocalDate3.monthValue).toString()].toString().uppercase())
            setDeleteTestButtonListener(listOfThreeTests[2])
            setStartStudyingButtonListener(listOfThreeTests[2])
        }
    }

    private fun setDeleteTestButtonListener(test: Tests){
        binding.deleteTestButton.setOnClickListener{
            MainActivity.viewModel.deleteTest(test)
            Log.e("TAG", "Test deleted")
            binding.top3test1.setOnClickListener(null)
            binding.top3test2.setOnClickListener(null)
            binding.top3test3.setOnClickListener(null)
            setTestsNull()
            instance.callOnResume()
        }
    }

    private fun setStartStudyingButtonListener(test: Tests){
        binding.startStudying.setOnClickListener{
            val studyingIntent = Intent(applicationContext, StudyingActivity::class.java).apply {
                putExtra("testId", test.test_id )
            }
            instance.startActivity(studyingIntent)
        }
    }




}