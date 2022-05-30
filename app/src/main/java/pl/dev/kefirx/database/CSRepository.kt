package pl.dev.kefirx.database

import android.app.Application
import io.reactivex.rxjava3.core.Observable
import kotlinx.coroutines.*
import pl.dev.kefirx.data.Tests
import pl.dev.kefirx.data.User
import pl.dev.kefirx.database.dao.TestsDao
import pl.dev.kefirx.database.dao.UserDao
import pl.dev.kefirx.json.ytResponse.YoutubeResponseJSON
import pl.dev.kefirx.network.YoutubeAPIService
import pl.dev.kefirx.network.YoutubeRetrofitClient


class CSRepository (
    private val userDao: UserDao,
    private val testsDao: TestsDao,
    private val youtubeAPIService: YoutubeAPIService
    ) {

    private val youtubeRetrofitClient = YoutubeRetrofitClient()
//    private val service = youtubeRetrofitClient.getYouTubeService()

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

    fun deleteAllTests() = CoroutineScope(Dispatchers.IO).launch {
        testsDao.deleteAllTests()
    }

    fun getNewestTestInfoObservable(): Observable<Tests>{
        return testsDao.getNewestExam()
    }

    fun getNewestThreeTestsInfoObservable(): Observable<List<Tests>>{
        return testsDao.getNewestThreeExams()
    }

    fun getUserInfoObservable(): Observable<User>{
        return userDao.getUserInfo()
    }

    fun getAllTestsInfoObservable(): Observable<List<Tests>>{
        return testsDao.getAllTestsInfo()
    }

    fun getTestByIdInfoObservable(id: Int): Observable<Tests>{
        return testsDao.getTestByIdInfo(id = id)
    }

    fun getYouTubeVideosResponseObservable(searchKey: String): Observable<YoutubeResponseJSON> {
        return youtubeAPIService.getResponseAsync(searchKey = searchKey)
    }

}