package pl.dev.kefirx.viewModel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.runBlocking
import pl.dev.kefirx.room.CSRepository
import pl.dev.kefirx.room.Tests
import pl.dev.kefirx.room.User

class CSViewModel(application: Application) : AndroidViewModel(application) {

    private var cSRepository: CSRepository = CSRepository(application)

    private var userInfo: Deferred<User> =
        cSRepository.getUserInfoAsync()
    fun insertUser(user:User){
        cSRepository.insertUser(user)
    }
    fun updateUser(user:User){
        cSRepository.updateUser(user)
    }
    fun deleteUser(user:User){
        cSRepository.deleteUser(user)
    }
    fun getUserCountAsync(): Int = cSRepository.getUserCountAsync()

    fun getUserInfoAsync(): User = runBlocking {
        userInfo.await()
    }
    fun deleteAllUserInfo(){
        cSRepository.deleteAllUserInfo()
    }


    private var allTestsInfo: Deferred<List<Tests>> =
        cSRepository.getAllTestsInfoAsync()

    fun insertTest(tests: Tests){
        cSRepository.insertTest(tests)
    }
    fun updateTest(tests: Tests){
        cSRepository.updateTest(tests)
    }
    fun deleteTest(tests: Tests){
        cSRepository.deleteTest(tests)
    }
    fun getAllTestsInfoAsync(): List<Tests> = runBlocking {
        allTestsInfo.await()
    }
    fun getTestByIdInfoAsync(id: Int): Tests = cSRepository.getTestByIdInfoAsync(id)

    fun deleteAllTests(){
        cSRepository.deleteAllTests()
    }
    fun getThreeExams(): List<Tests> = cSRepository.getThreeExams()




}