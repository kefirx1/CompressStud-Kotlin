package pl.dev.kefirx.viewModels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import io.reactivex.rxjava3.core.Observer
import io.reactivex.rxjava3.disposables.Disposable
import io.reactivex.rxjava3.schedulers.Schedulers
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.runBlocking
import pl.dev.kefirx.data.Tests
import pl.dev.kefirx.data.User
import pl.dev.kefirx.database.CSRepository

class DashboardViewModel(application: Application) : AndroidViewModel(application) {

    private var csRepository: CSRepository = CSRepository(application)

    private var userInfo = MutableLiveData<User?>()
    val userInfoResult: LiveData<User?>
        get() = userInfo
    private var testInfo = MutableLiveData<List<Tests>?>()
    val testInfoResult: LiveData<List<Tests>?>
        get() = testInfo
    private var newestThreeTestsInfo = MutableLiveData<List<Tests>?>()
    val newestThreeTestsInfoResult: LiveData<List<Tests>?>
        get() = newestThreeTestsInfo
    private var newestTestInfo = MutableLiveData<Tests?>()
    val newestTestInfoResult: LiveData<Tests?>
        get() = newestTestInfo

    fun setUserInfoObserver(){
        csRepository.getUserInfoObservable()
            .subscribeOn(Schedulers.io())
            .subscribe(object : Observer<User> {
                override fun onSubscribe(d: Disposable) {
                }

                override fun onNext(t: User) {
                    onUserInfoNext(user = t)
                }

                override fun onError(e: Throwable) {
                    onUserInfoError()
                }

                override fun onComplete() {
                }


            })
    }

    fun setAllTestsInfoObserver(){
        csRepository.getAllTestsInfoObservable()
            .subscribeOn(Schedulers.io())
            .subscribe(object : Observer<List<Tests>> {
                override fun onSubscribe(d: Disposable) {
                }

                override fun onNext(t: List<Tests>) {
                    onTestNext(tests = t)
                }

                override fun onError(e: Throwable) {
                    onTestError()
                }

                override fun onComplete() {
                }

            })
    }

    fun setNewestThreeTestsInfoObserver(){
        csRepository.getNewestThreeTestsInfoObservable()
            .subscribeOn(Schedulers.io())
            .subscribe(object : Observer<List<Tests>> {
                override fun onSubscribe(d: Disposable) {
                }

                override fun onNext(t: List<Tests>) {
                    onNewestThreeTestsNext(tests = t)
                }

                override fun onError(e: Throwable) {
                    onNewestThreeTestsError()
                }

                override fun onComplete() {
                }

            })
    }

    fun setNewestTestInfoObserver(){
        csRepository.getNewestTestInfoObservable()
            .subscribeOn(Schedulers.io())
            .subscribe(object : Observer<Tests> {
                override fun onSubscribe(d: Disposable) {
                }

                override fun onNext(t: Tests) {
                    onNewestTestNext(tests = t)
                }

                override fun onError(e: Throwable) {
                    onNewestTestError()
                }

                override fun onComplete() {
                }

            })
    }

    fun getUserCountAsync(): Int = csRepository.getUserCountAsync()


    fun deleteTest(tests: Tests){
        csRepository.deleteTest(tests)
    }
    fun insertTest(tests: Tests){
        csRepository.insertTest(tests)
    }

    private fun onUserInfoNext(user: User){
        userInfo.postValue(user)
    }
    private fun onUserInfoError(){
        userInfo.postValue(null)
    }

    private fun onTestNext(tests: List<Tests>){
        testInfo.postValue(tests)
    }
    private fun onTestError(){
        testInfo.postValue(null)
    }

    private fun onNewestThreeTestsNext(tests: List<Tests>){
       newestThreeTestsInfo.postValue(tests)
    }
    private fun onNewestThreeTestsError(){
        newestThreeTestsInfo.postValue(null)
    }

    private fun onNewestTestNext(tests: Tests){
        newestTestInfo.postValue(tests)
    }
    private fun onNewestTestError(){
        newestTestInfo.postValue(null)
    }

}