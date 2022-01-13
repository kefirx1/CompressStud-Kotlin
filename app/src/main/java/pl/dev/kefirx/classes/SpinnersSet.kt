package pl.dev.kefirx.classes

import android.R
import android.widget.ArrayAdapter
import pl.dev.kefirx.MainActivity
import pl.dev.kefirx.MainActivity.Companion.listOfTopicsObject
import pl.dev.kefirx.databinding.ActivityMainBinding

class SpinnersSet {

    fun setMATopicSpinner(binding: ActivityMainBinding, instance: MainActivity, levelOfEdu: String){

        val lesson = binding.lessonsSpinner.selectedItem.toString()

        var topicsList: ArrayList<String> = ArrayList()

        if(levelOfEdu == "Podstawowa") {
            listOfTopicsObject.Podstawowa.forEach{
                if(it[0] == lesson) {
                    topicsList = it as ArrayList<String>
                }
            }
        }else if(levelOfEdu == "Średnia"){
            listOfTopicsObject.Srednia.forEach{
                if(it[0] == lesson) {
                    topicsList = it as ArrayList<String>
                }
            }
        }
        val adapter = ArrayAdapter(instance, R.layout.simple_spinner_item, topicsList.subList(1, topicsList.lastIndex))
        binding.topicSpinner.adapter = adapter

        println(binding.topicSpinner.selectedItem)

    }


}