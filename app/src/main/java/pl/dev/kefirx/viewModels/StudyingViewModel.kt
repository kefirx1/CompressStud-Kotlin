package pl.dev.kefirx.viewModels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import pl.dev.kefirx.room.CSRepository
import pl.dev.kefirx.room.Tests

class StudyingViewModel(application: Application) : AndroidViewModel(application) {

    private var csRepository: CSRepository = CSRepository(application)

    fun getTestByIdInfoAsync(id: Int): Tests = csRepository.getTestByIdInfoAsync(id)

    fun updateTest(tests: Tests){
        csRepository.updateTest(tests)
    }

}