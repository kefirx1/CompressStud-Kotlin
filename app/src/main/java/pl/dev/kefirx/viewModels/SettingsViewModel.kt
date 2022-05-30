package pl.dev.kefirx.viewModels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.rxjava3.core.Observer
import io.reactivex.rxjava3.disposables.Disposable
import io.reactivex.rxjava3.schedulers.Schedulers
import pl.dev.kefirx.data.Tests
import pl.dev.kefirx.data.User
import pl.dev.kefirx.database.CSRepository
import javax.inject.Inject

@HiltViewModel
class SettingsViewModel
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

    fun updateUser(user: User){
        csRepository.updateUser(user)
    }

    fun deleteAllUserInfo(){
        csRepository.deleteAllUserInfo()
    }

    fun deleteAllTests(){
        csRepository.deleteAllTests()
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


}