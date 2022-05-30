package pl.dev.kefirx.viewModels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.scopes.ViewModelScoped
import io.reactivex.rxjava3.core.Observer
import io.reactivex.rxjava3.disposables.Disposable
import io.reactivex.rxjava3.schedulers.Schedulers
import kotlinx.coroutines.launch
import pl.dev.kefirx.data.Tests
import pl.dev.kefirx.data.User
import pl.dev.kefirx.database.CSRepository
import pl.dev.kefirx.json.ListOfTopicsJSON
import javax.inject.Inject

@HiltViewModel
class DashboardViewModel
@Inject
constructor(
    private val csRepository: CSRepository
): ViewModel(){

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

    private var listOfTopics = MutableLiveData<ListOfTopicsJSON?>()
    val listOfTopicsResult: LiveData<ListOfTopicsJSON?>
        get() = listOfTopics

    fun setListOfTopics(){
        viewModelScope.launch{
            listOfTopics.postValue(csRepository.getListOfTopics())
        }
    }

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