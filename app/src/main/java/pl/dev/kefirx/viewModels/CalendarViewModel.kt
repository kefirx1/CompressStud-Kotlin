package pl.dev.kefirx.viewModels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.runBlocking
import pl.dev.kefirx.room.CSRepository
import pl.dev.kefirx.room.Tests

class CalendarViewModel(application: Application) : AndroidViewModel(application) {

    private var csRepository: CSRepository = CSRepository(application)

    private var allTestsInfo: Deferred<List<Tests>> =
        csRepository.getAllTestsInfoAsync()

    fun getAllTestsInfoAsync(): List<Tests> = runBlocking {
        allTestsInfo.await()
    }

    fun deleteTest(tests: Tests){
        csRepository.deleteTest(tests)
    }

}