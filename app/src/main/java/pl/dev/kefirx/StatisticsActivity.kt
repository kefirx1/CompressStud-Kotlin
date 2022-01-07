package pl.dev.kefirx

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import kotlinx.android.synthetic.main.activity_settings.*
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

        val allTests = MainActivity.viewModel.getAllTestsInfoAsync()
        var studyingTimeCounter = 0.0

        allTests.forEach{
            studyingTimeCounter += it.timeOfLearning
        }

        binding.allStudyingTimeText.text = Convert.getTimeStringFromDouble(studyingTimeCounter)
    }
}