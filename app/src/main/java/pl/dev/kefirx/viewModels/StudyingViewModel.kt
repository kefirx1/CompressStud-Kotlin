package pl.dev.kefirx.viewModels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import io.reactivex.rxjava3.core.Observer
import io.reactivex.rxjava3.disposables.Disposable
import io.reactivex.rxjava3.schedulers.Schedulers
import pl.dev.kefirx.database.CSRepository
import pl.dev.kefirx.data.Tests
import pl.dev.kefirx.json.ytResponse.YoutubeResponseJSON

class StudyingViewModel(application: Application) : AndroidViewModel(application) {

    private var csRepository: CSRepository = CSRepository(application)

    private var dataFromAPI = MutableLiveData<YoutubeResponseJSON?>()
    val dataFromAPIResult: LiveData<YoutubeResponseJSON?>
        get() = dataFromAPI
    private var testInfo = MutableLiveData<Tests?>()
    val testInfoResult: LiveData<Tests?>
        get() = testInfo

    fun updateTest(tests: Tests){
        csRepository.updateTest(tests)
    }

    fun setTestByIdInfoObserver(id: Int) {
        csRepository.getTestByIdInfoObservable(id = id)
            .subscribeOn(Schedulers.io())
            .subscribe(object : Observer<Tests>{
                override fun onSubscribe(d: Disposable) {
                }

                override fun onNext(t: Tests) {
                    onTestNext(tests = t)
                }

                override fun onError(e: Throwable) {
                    onTestError()
                }

                override fun onComplete() {
                }

            })
    }

    fun setYouTubeVideosResponseObserver(searchKey: String) {
        csRepository.getYouTubeVideosResponseObservable(searchKey = searchKey)
            .subscribeOn(Schedulers.io())
            .subscribe(object : Observer<YoutubeResponseJSON> {
                override fun onSubscribe(d: Disposable) {
                }

                override fun onNext(t: YoutubeResponseJSON) {
                    onResponseNext(youtubeResponseJSON = t)
                }

                override fun onError(e: Throwable) {
                    onResponseError()
                }

                override fun onComplete() {
                }

            })
    }

    private fun onResponseNext(youtubeResponseJSON: YoutubeResponseJSON){
        dataFromAPI.postValue(youtubeResponseJSON)
    }
    private fun onResponseError(){
        dataFromAPI.postValue(null)
    }
    private fun onTestNext(tests: Tests){
        testInfo.postValue(tests)
    }
    private fun onTestError(){
        testInfo.postValue(null)
    }
}
