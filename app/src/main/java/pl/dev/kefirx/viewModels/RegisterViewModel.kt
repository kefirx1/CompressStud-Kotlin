package pl.dev.kefirx.viewModels

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import pl.dev.kefirx.data.User
import pl.dev.kefirx.database.CSRepository
import javax.inject.Inject

@HiltViewModel
class RegisterViewModel
@Inject
constructor(
    private val csRepository: CSRepository
): ViewModel(){

    fun insertUser(user: User){
        csRepository.insertUser(user)
    }

}