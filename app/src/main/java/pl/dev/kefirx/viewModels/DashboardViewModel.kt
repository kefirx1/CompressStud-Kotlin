package pl.dev.kefirx.viewModels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.runBlocking
import pl.dev.kefirx.database.CSRepository
import pl.dev.kefirx.data.Tests
import pl.dev.kefirx.data.User

class DashboardViewModel(application: Application) : AndroidViewModel(application) {

    private var csRepository: CSRepository = CSRepository(application)

    private var userInfo: Deferred<User> =
        csRepository.getUserInfoAsync()
    private var allTestsInfo: Deferred<List<Tests>> =
        csRepository.getAllTestsInfoAsync()
    private var newestExam: Deferred<Tests> =
        csRepository.getNewestExamAsync()
    fun getThreeExams(): List<Tests> = csRepository.getThreeExams()

    fun getUserCountAsync(): Int = csRepository.getUserCountAsync()

    fun getUserInfoAsync(): User = runBlocking {
        userInfo.await()
    }
    fun deleteTest(tests: Tests){
        csRepository.deleteTest(tests)
    }
    fun getAllTestsInfoAsync(): List<Tests> = runBlocking {
        allTestsInfo.await()
    }
    fun getNewestExamAsync(): Tests = runBlocking {
        newestExam.await()
    }
    fun insertTest(tests: Tests){
        csRepository.insertTest(tests)
    }
}