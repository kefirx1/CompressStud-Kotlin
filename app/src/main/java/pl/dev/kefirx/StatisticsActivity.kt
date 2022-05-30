package pl.dev.kefirx

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import dagger.hilt.android.AndroidEntryPoint
import pl.dev.kefirx.classes.Convert
import pl.dev.kefirx.databinding.ActivityStatisticsBinding
import pl.dev.kefirx.viewModels.StatisticsViewModel

@AndroidEntryPoint
class StatisticsActivity : AppCompatActivity() {

    private lateinit var binding: ActivityStatisticsBinding
    private val viewModel: StatisticsViewModel by viewModels()

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

        viewModel.setAllTestsInfoObserver()

        var studyingTimeCounter = 0.0
        var watchedVideos = 0

        viewModel.testInfoResult.observe(this){ testsList ->

            testsList?.forEach {
                studyingTimeCounter += it.timeOfLearning
                watchedVideos += it.watchedVideos
            }
            binding.allStudyingTimeText.text =
                Convert.getTimeStringFromDouble(studyingTimeCounter)
            binding.allWatchedVideosText.text = watchedVideos.toString()
        }

    }
}