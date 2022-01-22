package pl.dev.kefirx.room

import android.app.Application
import kotlinx.coroutines.*


class CSRepository (application: Application) {

    private var userDao: UserDao
    private var testsDao: TestsDao

    init{
        val database = CSDatabase
            .getInstance(application.applicationContext)
        userDao = database!!.userDao()
        testsDao = database.testsDao()
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
    fun getUserCountAsync(): Int = userDao.getUserCount()

    fun getUserInfoAsync(): Deferred<User> =
        CoroutineScope(Dispatchers.IO).async {
            userDao.getUserInfo()
        }
    fun deleteAllUserInfo() = CoroutineScope(Dispatchers.IO).launch {
        userDao.deleteAllUserInfo()
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
    fun getTestByIdInfoAsync(id: Int):  Tests = testsDao.getTestByIdInfo(id)

    fun deleteAllTests() = CoroutineScope(Dispatchers.IO).launch {
        testsDao.deleteAllTests()
    }
    fun getThreeExams(): List<Tests> = testsDao.getThreeExams()

    fun getNewestExamAsync():  Deferred<Tests> =
        CoroutineScope(Dispatchers.IO).async {
            testsDao.getNewestExam()
        }
}