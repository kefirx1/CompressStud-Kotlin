package pl.dev.kefirx.viewModels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import io.reactivex.rxjava3.core.Observer
import io.reactivex.rxjava3.disposables.Disposable
import io.reactivex.rxjava3.schedulers.Schedulers
import pl.dev.kefirx.data.User
import pl.dev.kefirx.database.CSRepository

class SettingsViewModel(application: Application) : AndroidViewModel(application) {

    private var csRepository: CSRepository = CSRepository(application)

    private var userInfo = MutableLiveData<User?>()
    val userInfoResult: LiveData<User?>
        get() = userInfo

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

    private fun onUserInfoNext(user: User){
        userInfo.postValue(user)
    }
    private fun onUserInfoError(){
        userInfo.postValue(null)
    }

}