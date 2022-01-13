package pl.dev.kefirx

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import pl.dev.kefirx.MainActivity.Companion.viewModel
import pl.dev.kefirx.classes.Convert
import pl.dev.kefirx.databinding.ActivityStatisticsBinding

class StatisticsActivity : AppCompatActivity() {

    private lateinit var binding: ActivityStatisticsBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
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