package pl.dev.kefirx.viewModel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.runBlocking
import pl.dev.kefirx.room.User
import pl.dev.kefirx.room.UserRepository

class UserViewModel(application: Application) : AndroidViewModel(application) {

    private var userRepository: UserRepository = UserRepository(application)

    private var userCount: Deferred<Int> =
        userRepository.getUserCountAsync()

    fun insertUser(user:User){
        userRepository.insertUser(user)
    }

    fun updateUser(user:User){
        userRepository.updateUser(user)
    }

    fun deleteUser(user:User){
        userRepository.deleteUser(user)
    }

    fun getUserCountAsync(): Int = runBlocking {
        userCount.await()
    }


}