package pl.dev.kefirx.viewModels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.rxjava3.core.Observer
import io.reactivex.rxjava3.disposables.Disposable
import io.reactivex.rxjava3.schedulers.Schedulers
import kotlinx.coroutines.launch
import pl.dev.kefirx.data.Tests
import pl.dev.kefirx.database.CSRepository
import pl.dev.kefirx.json.ListOfTopicsJSON
import pl.dev.kefirx.json.ytResponse.YoutubeResponseJSON
import pl.dev.kefirx.json.ytResponse.recommendedChannels.RecommendedChannelsIDJSON
import javax.inject.Inject

@HiltViewModel
class StudyingViewModel
@Inject
constructor(
    private val csRepository: CSRepository
): ViewModel(){

    private var dataFromAPI = MutableLiveData<YoutubeResponseJSON?>()
    val dataFromAPIResult: LiveData<YoutubeResponseJSON?>
        get() = dataFromAPI
    private var testInfo = MutableLiveData<Tests?>()
    val testInfoResult: LiveData<Tests?>
        get() = testInfo
    private var listOfChannels = MutableLiveData<RecommendedChannelsIDJSON?>()
    val listOfChannelsResult: LiveData<RecommendedChannelsIDJSON?>
        get() = listOfChannels

    fun updateTest(tests: Tests){
        csRepository.updateTest(tests)
    }

    fun setListOfRecommendedChannels(){
        viewModelScope.launch{
            listOfChannels.postValue(csRepository.getListOfRecommendedChannels())
        }
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
                    println(t)
                    onResponseNext(youtubeResponseJSON = t)
                }

                override fun onError(e: Throwable) {
                    println(e)
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
