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
        binding.openNewTestModalButton.isClickable = true
        binding.top3test1.isClickable = true
        binding.top3test2.isClickable = true
        binding.top3test3.isClickable = true
        binding.calendarButton.isClickable = true
        binding.statisticsButton.isClickable = true
        binding.settingsButton.isClickable = true
        instance.callOnResume()
    }

}