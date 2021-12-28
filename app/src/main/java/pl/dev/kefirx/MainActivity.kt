package pl.dev.kefirx

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import kotlinx.android.synthetic.main.activity_main.*
import pl.dev.kefirx.databinding.ActivityMainBinding
import pl.dev.kefirx.room.CSDatabase
import pl.dev.kefirx.room.User
import pl.dev.kefirx.viewModel.CSViewModel

class MainActivity : AppCompatActivity() {

    companion object{
        lateinit var viewModel: CSViewModel

    }

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        binding = ActivityMainBinding.inflate(layoutInflater)
    }

    override fun onStart() {
        super.onStart()

        println(123456789)


        createNewInstance()
        if(viewModel.getUserCountAsync() <= 0) {
            Log.e("TAG", "Create user")
            val registerIntent = Intent(this, RegisterActivity::class.java).apply {}
            startActivity(registerIntent)
        }
        CSDatabase.deleteInstanceOfDatabase()

        println("Tutaj to")

        setDashboardActuallyInfo()

        //TODO - Change into binding
        settingsButton.setOnClickListener{
            Log.e("TAG", "Go to settings")
            val settingsIntent = Intent(this, SettingsActivity::class.java).apply {}
            startActivity(settingsIntent)
        }
        statisticsButton.setOnClickListener{
            Log.e("TAG", "Go to statistics")
            val statisticsIntent = Intent(this, StatisticsActivity::class.java).apply {}
            startActivity(statisticsIntent)
        }

    }



    private fun setDashboardActuallyInfo(){
        createNewInstance()
        val name = viewModel.getUserInfoAsync().name + "!"
        userNameTextView.text = name
        CSDatabase.deleteInstanceOfDatabase()
    }

    fun createNewInstance(){
        viewModel = ViewModelProvider
            .AndroidViewModelFactory
            .getInstance(application)
            .create(CSViewModel::class.java)
    }


}