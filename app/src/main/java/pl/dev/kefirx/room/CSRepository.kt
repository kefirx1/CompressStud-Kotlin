package pl.dev.kefirx.room

import android.app.Application
import kotlinx.coroutines.*


class CSRepository (application: Application) {

    private lateinit var userDao: UserDao
    private lateinit var testsDao: TestsDao

    init{
        val database = CSDatabase
            .getInstance(application.applicationContext)
        userDao = database!!.userDao()
        testsDao = database!!.testsDao()
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
    fun getUserInfoAsync(): Deferred<User> =
        CoroutineScope(Dispatchers.IO).async {
            userDao.getUserInfo()
        }


    fun insertTest(tests: Tests) = CoroutineScope(Dispatchers.IO).launch {
        testsDao.insert(tests)
    }
    fun updateTest(tests: Tests) = CoroutineScope(Dispatchers.IO).launch {
        testsDao.update(tests)
    }
    fun deleteTest(tests: Tests) = CoroutineScope(Dispatchers.IO).launch {
        testsDao.delete(tests)
    }
    fun getAllTestsInfoAsync():  Deferred<List<Tests>> =
        CoroutineScope(Dispatchers.IO).async {
            testsDao.getAllTestsInfo()
        }
    fun getTestByIdInfoAsync(id: Int):  Deferred<Tests> =
        CoroutineScope(Dispatchers.IO).async {
            testsDao.getTestByIdInfo(id)
        }
}