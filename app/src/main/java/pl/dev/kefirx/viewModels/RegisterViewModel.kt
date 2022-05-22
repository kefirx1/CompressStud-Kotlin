package pl.dev.kefirx.viewModels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import pl.dev.kefirx.database.CSRepository
import pl.dev.kefirx.data.User

class RegisterViewModel(application: Application) : AndroidViewModel(application) {

    private var csRepository: CSRepository = CSRepository(application)

    fun insertUser(user: User){
        csRepository.insertUser(user)
    }

}