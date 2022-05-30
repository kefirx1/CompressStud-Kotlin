package pl.dev.kefirx.classes

import android.R.layout
import android.widget.ArrayAdapter
import androidx.lifecycle.Observer
import pl.dev.kefirx.MainActivity
import pl.dev.kefirx.databinding.ActivityMainBinding
import pl.dev.kefirx.viewModels.DashboardViewModel

class SpinnersSet {

    fun setMATopicSpinner(binding: ActivityMainBinding, instance: MainActivity, levelOfEdu: String, viewModel: DashboardViewModel){

        viewModel.setListOfTopics()
        val lesson = binding.lessonsSpinner.selectedItem.toString()
        var topicsList: ArrayList<String> = ArrayList()

        if(viewModel.listOfTopicsResult.value != null){
            when(levelOfEdu){
                "Podstawowa" -> {
                    viewModel.listOfTopicsResult.observe(instance) {
                        it!!.Podstawowa.forEach { list ->
                            if (list[0] == lesson) {
                                topicsList = list as ArrayList<String>
                            }
                        }
                    }
                }
                "Średnia" -> {
                    viewModel.listOfTopicsResult.observe(instance) {
                        it!!.Srednia.forEach { list ->
                            if (list[0] == lesson) {
                                topicsList = list as ArrayList<String>
                            }
                        }
                    }
                }
            }

            val adapter = ArrayAdapter(instance, layout.simple_spinner_item, topicsList.subList(1, topicsList.lastIndex))
            binding.topicSpinner.adapter = adapter

        }else{
            //TODO
        }

    }


}