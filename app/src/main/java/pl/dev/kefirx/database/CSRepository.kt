package pl.dev.kefirx.database

import com.google.gson.Gson
import io.reactivex.rxjava3.core.Observable
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import pl.dev.kefirx.App.Companion.applicationContext
import pl.dev.kefirx.data.Tests
import pl.dev.kefirx.data.User
import pl.dev.kefirx.database.dao.TestsDao
import pl.dev.kefirx.database.dao.UserDao
import pl.dev.kefirx.json.GetJSONString
import pl.dev.kefirx.json.ListOfTopicsJSON
import pl.dev.kefirx.json.ytResponse.YoutubeResponseJSON
import pl.dev.kefirx.json.ytResponse.recommendedChannels.RecommendedChannelsIDJSON
import pl.dev.kefirx.network.YoutubeAPIService

const val LIST_OF_TOPICS_PATH = "listOfTopics.json"
const val LIST_OF_RECOMMENDED_CHANNELS_PATH = "listOfRecommendedChannels.json"

class CSRepository (
    private val userDao: UserDao,
    private val testsDao: TestsDao,
    private val youtubeAPIService: YoutubeAPIService
    ) {

    private val gson = Gson()
    private var getJSONString = GetJSONString()

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

    fun getListOfTopics(): ListOfTopicsJSON {
        return gson.fromJson(
            getJSONString.getJsonStringFromAssets(
                applicationContext(),
                LIST_OF_TOPICS_PATH
            ), ListOfTopicsJSON::class.java)
    }
    fun getListOfRecommendedChannels(): RecommendedChannelsIDJSON {
        return gson.fromJson(
            getJSONString.getJsonStringFromAssets(
                applicationContext(),
                LIST_OF_RECOMMENDED_CHANNELS_PATH
            ), RecommendedChannelsIDJSON::class.java
        )
    }

}