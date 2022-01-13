package pl.dev.kefirx.classes

import android.view.View
import pl.dev.kefirx.MainActivity
import pl.dev.kefirx.databinding.ActivityMainBinding

class ModalsView(val binding: ActivityMainBinding, private val instance: MainActivity){

    fun hideAllModals(){
        binding.addNewTestModal.visibility = View.GONE
        binding.learnModal.visibility = View.GONE
    }

    fun newTestModalReset(){
        binding.addNewTestModal.visibility = View.GONE
        instance.callOnResume()
    }

}