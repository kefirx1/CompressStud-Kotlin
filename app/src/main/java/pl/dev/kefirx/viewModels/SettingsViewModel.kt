package pl.dev.kefirx.viewModels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.runBlocking
import pl.dev.kefirx.room.CSRepository
import pl.dev.kefirx.room.User

class SettingsViewModel(application: Application) : AndroidViewModel(application) {

    private var csRepository: CSRepository = CSRepository(application)

    private var userInfo: Deferred<User> =
        csRepository.getUserInfoAsync()

    fun updateUser(user:User){
        csRepository.updateUser(user)
    }

    fun deleteAllUserInfo(){
        csRepository.deleteAllUserInfo()
    }

    fun deleteAllTests(){
        csRepository.deleteAllTests()
    }

    fun getUserInfoAsync(): User = runBlocking {
        userInfo.await()
    }
}