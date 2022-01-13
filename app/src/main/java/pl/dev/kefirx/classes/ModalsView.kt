package pl.dev.kefirx.classes

import android.view.View
import pl.dev.kefirx.MainActivity
import pl.dev.kefirx.databinding.ActivityMainBinding

class ModalsView{

    fun hideAllModals(binding: ActivityMainBinding){
        binding.addNewTestModal.visibility = View.INVISIBLE
        binding.learnModal.visibility = View.INVISIBLE
    }

    fun newTestModalReset(binding: ActivityMainBinding, instance: MainActivity){
        binding.addNewTestModal.visibility = View.INVISIBLE
        instance.callOnResume()
    }

}