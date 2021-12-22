package pl.dev.kefirx.viewModel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.runBlocking
import pl.dev.kefirx.room.User
import pl.dev.kefirx.room.CSRepository
import pl.dev.kefirx.room.Tests
import kotlin.properties.Delegates

class CSViewModel(application: Application) : AndroidViewModel(application) {


    private var CSRepository: CSRepository = CSRepository(application)
    var id: Int = 0

    fun idSetter(id: Int){
        this.id = id
    }

    private var userCount: Deferred<Int> =
        CSRepository.getUserCountAsync()
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
    fun getUserCountAsync(): Int = runBlocking {
        userCount.await()
    }
    fun getUserInfoAsync(): User = runBlocking {
        userInfo.await()
    }


    private var allTestsInfo: Deferred<List<Tests>> =
        CSRepository.getAllTestsInfoAsync()
    private var testByIdInfo: Deferred<Tests> =
        CSRepository.getTestByIdInfoAsync(id)
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
    fun getTestByIdInfoAsync(id: Int): Tests = runBlocking {
        idSetter(id)
        testByIdInfo.await()
    }




}