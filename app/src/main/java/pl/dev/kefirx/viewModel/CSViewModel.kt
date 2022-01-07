package pl.dev.kefirx.viewModel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import pl.dev.kefirx.room.User
import pl.dev.kefirx.room.CSRepository
import pl.dev.kefirx.room.Tests
import kotlin.properties.Delegates

class CSViewModel(application: Application) : AndroidViewModel(application) {


    private var CSRepository: CSRepository = CSRepository(application)
    var id: Int = 0

    private fun idSetter(id: Int){
        this.id = id
    }

    private var userInfo: Deferred<User> =
        CSRepository.getUserInfoAsync()
    fun insertUser(user:User){
        CSRepository.insertUser(user)
    }
    fun updateUser(user:User){
        CSRepository.updateUser(user)
    }
    fun deleteUser(user:User){
        CSRepository.deleteUser(user)
    }

    fun getUserCountAsync(): Int = CSRepository.getUserCountAsync()

    fun getUserInfoAsync(): User = runBlocking {
        userInfo.await()
    }
    fun deleteAllUserInfo(){
        CSRepository.deleteAllUserInfo()
    }


    private var allTestsInfo: Deferred<List<Tests>> =
        CSRepository.getAllTestsInfoAsync()

    fun insertTest(tests: Tests){
        CSRepository.insertTest(tests)
    }
    fun updateTest(tests: Tests){
        CSRepository.updateTest(tests)
    }
    fun deleteTest(tests: Tests){
        CSRepository.deleteTest(tests)
    }
    fun getAllTestsInfoAsync(): List<Tests> = runBlocking {
        allTestsInfo.await()
    }
    fun getTestByIdInfoAsync(id: Int): Tests = CSRepository.getTestByIdInfoAsync(id)

    fun deleteAllTests(){
        CSRepository.deleteAllTests()
    }
    fun getThreeExams(): List<Tests> = CSRepository.getThreeExams()




}