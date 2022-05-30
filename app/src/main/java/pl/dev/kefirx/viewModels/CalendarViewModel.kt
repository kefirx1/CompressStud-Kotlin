package pl.dev.kefirx.viewModels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.rxjava3.core.Observer
import io.reactivex.rxjava3.disposables.Disposable
import io.reactivex.rxjava3.schedulers.Schedulers
import pl.dev.kefirx.data.Tests
import pl.dev.kefirx.database.CSRepository
import javax.inject.Inject

@HiltViewModel
class CalendarViewModel
@Inject
constructor(
    private val csRepository: CSRepository
): ViewModel(){

    private var testInfo = MutableLiveData<List<Tests>?>()
    val testInfoResult: LiveData<List<Tests>?>
        get() = testInfo

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

    private fun onTestNext(tests: List<Tests>){
        testInfo.postValue(tests)
    }
    private fun onTestError(){
        testInfo.postValue(null)
    }

    fun deleteTest(tests: Tests){
        csRepository.deleteTest(tests)
    }

}