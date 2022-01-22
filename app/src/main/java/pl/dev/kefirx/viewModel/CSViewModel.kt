package pl.dev.kefirx.viewModel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.runBlocking
import pl.dev.kefirx.room.CSRepository
import pl.dev.kefirx.room.Tests
import pl.dev.kefirx.room.User

class CSViewModel(application: Application) : AndroidViewModel(application) {

    private var csRepository: CSRepository = CSRepository(application)

    private var userInfo: Deferred<User> =
        csRepository.getUserInfoAsync()
    fun insertUser(user:User){
        csRepository.insertUser(user)
    }
    fun updateUser(user:User){
        csRepository.updateUser(user)
    }
    fun deleteUser(user:User){
        csRepository.deleteUser(user)
    }
    fun getUserCountAsync(): Int = csRepository.getUserCountAsync()

    fun getUserInfoAsync(): User = runBlocking {
        userInfo.await()
    }
    fun deleteAllUserInfo(){
        csRepository.deleteAllUserInfo()
    }


    private var allTestsInfo: Deferred<List<Tests>> =
        csRepository.getAllTestsInfoAsync()

    private var newestExam: Deferred<Tests> =
        csRepository.getNewestExamAsync()

    fun insertTest(tests: Tests){
        csRepository.insertTest(tests)
    }
    fun updateTest(tests: Tests){
        csRepository.updateTest(tests)
    }
    fun deleteTest(tests: Tests){
        csRepository.deleteTest(tests)
    }
    fun getAllTestsInfoAsync(): List<Tests> = runBlocking {
        allTestsInfo.await()
    }
    fun getTestByIdInfoAsync(id: Int): Tests = csRepository.getTestByIdInfoAsync(id)

    fun deleteAllTests(){
        csRepository.deleteAllTests()
    }
    fun getThreeExams(): List<Tests> = csRepository.getThreeExams()

    fun getNewestExamAsync(): Tests = runBlocking {
        newestExam.await()
    }



}