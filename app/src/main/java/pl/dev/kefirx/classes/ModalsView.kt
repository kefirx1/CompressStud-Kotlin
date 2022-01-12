package pl.dev.kefirx.classes

import android.view.View
import pl.dev.kefirx.MainActivity

class ModalsView: MainActivity() {

    fun hideAllModals(){
        binding.addNewTestModal.visibility = View.GONE
        binding.learnModal.visibility = View.GONE
    }

    fun newTestModalReset(){
        binding.addNewTestModal.visibility = View.GONE
        onResume()
    }

}