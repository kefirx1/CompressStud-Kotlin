package pl.dev.kefirx

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import pl.dev.kefirx.classes.Convert
import pl.dev.kefirx.databinding.ActivityStatisticsBinding
import pl.dev.kefirx.viewModel.CSViewModel

class StatisticsActivity : AppCompatActivity() {

    private lateinit var binding: ActivityStatisticsBinding
    private lateinit var viewModel: CSViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        viewModel = ViewModelProvider
            .AndroidViewModelFactory
            .getInstance(application)
            .create(CSViewModel::class.java)

        binding = ActivityStatisticsBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setStats()

        binding.backToDashboardButton.setOnClickListener{
            this.finish()
        }
    }


    private fun setStats(){

        val allTests = viewModel.getAllTestsInfoAsync()
        var studyingTimeCounter = 0.0
        var watchedVideos = 0

        allTests.forEach{
            studyingTimeCounter += it.timeOfLearning
            watchedVideos += it.watchedVideos
        }


        binding.allStudyingTimeText.text = Convert.getTimeStringFromDouble(studyingTimeCounter)
        binding.allWatchedVideosText.text = watchedVideos.toString()

    }
}