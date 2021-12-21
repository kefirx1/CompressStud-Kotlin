package pl.dev.kefirx.room

import android.app.Application
import androidx.lifecycle.LiveData
import kotlinx.coroutines.*

class UserRepository (application: Application) {

    private lateinit var userDao: UserDao

    init{
        val database = UserDatabase
            .getInstance(application.applicationContext)
        userDao = database!!.userDao()
    }

    fun insertUser(user: User) = CoroutineScope(Dispatchers.IO).launch {
        userDao.insert(user)
    }
    fun updateUser(user: User) = CoroutineScope(Dispatchers.IO).launch {
        userDao.update(user)
    }
    fun deleteUser(user: User) = CoroutineScope(Dispatchers.IO).launch {
        userDao.delete(user)
    }
    fun getUserCountAsync():  Deferred<Int> =
        CoroutineScope(Dispatchers.IO).async {
            userDao.getUserCount()
        }

}