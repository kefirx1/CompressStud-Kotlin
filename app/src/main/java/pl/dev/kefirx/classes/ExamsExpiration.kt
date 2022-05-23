package pl.dev.kefirx.classes

import android.content.Context
import pl.dev.kefirx.MainActivity
import pl.dev.kefirx.MainActivity.Companion.viewModel
import java.util.*

class ExamsExpiration {

    private val notification = Notification()

    fun checkExamsExpirationDate(applicationContext: Context, instance: MainActivity){

        viewModel.setAllTestsInfoObserver()
        viewModel.testInfoResult.observe(instance){ testsList ->
            if(testsList!=null){
                testsList.forEach{
                    val dateOfExam = it.dateOfExam

                    if(dateOfExam<getNextDayDateMillis()){
                        notification.cancelNotificationByID(it.test_id, applicationContext)
                        viewModel.deleteTest(it)

                        instance.callOnResume()
                    }

                }
            }else{
                //TODO
            }
        }

    }


    private fun getNextDayDateMillis(): Long{
        val currentDayDate = Calendar.getInstance()
        val year = currentDayDate.get(Calendar.YEAR)
        val month = currentDayDate.get(Calendar.MONTH)
        val day = currentDayDate.get(Calendar.DAY_OF_MONTH)
        val currentDay = Calendar.getInstance()
        currentDay.set(year, month, day, 0, 0, 0)
        return currentDay.timeInMillis
    }

}