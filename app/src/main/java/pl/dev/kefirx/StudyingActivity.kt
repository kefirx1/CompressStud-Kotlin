package pl.dev.kefirx

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.PlayerConstants
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.AbstractYouTubePlayerListener
import pl.dev.kefirx.classes.Convert
import pl.dev.kefirx.data.Tests
import pl.dev.kefirx.databinding.ActivityStudyingBinding
import pl.dev.kefirx.network.YoutubeObject
import pl.dev.kefirx.services.TimerService
import pl.dev.kefirx.viewModels.StudyingViewModel


class StudyingActivity : AppCompatActivity() {

    private lateinit var testToStudying: Tests
    private lateinit var binding: ActivityStudyingBinding
    private lateinit var serviceIntent: Intent
    private lateinit var viewModel: StudyingViewModel

    private var pauseButtonStartText = "START"
    private var pauseButtonPauseText = "PAUZA"
    private var time = 0.0
    private var watchedVideos = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        viewModel = ViewModelProvider
            .AndroidViewModelFactory
            .getInstance(application)
            .create(StudyingViewModel::class.java)

        binding = ActivityStudyingBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val idTestToStudying = intent.getIntExtra("testId", -1)

        viewModel.setTestByIdInfoObserver(id = idTestToStudying)

        viewModel.testInfoResult.observe(this) {
            if (it != null) {
                testToStudying = it
                time = testToStudying.timeOfLearning
                watchedVideos = testToStudying.watchedVideos

                setListeners()
                setTestInfo()

                viewModel.setYouTubeVideosResponseObserver(
                    searchKey = getSearchKey(testToStudying)
                )

                loadVideosFromAPI(
                    testToStudying.lesson
                )

                startTimer()

            } else {
                //TODO
            }
        }
        serviceIntent = Intent(applicationContext, TimerService::class.java)
        registerReceiver(updateTime, IntentFilter(TimerService.TIMER_UPDATED))

    }

    override fun onPause() {
        super.onPause()
        testToStudying.timeOfLearning = time
        testToStudying.watchedVideos = watchedVideos
        viewModel.updateTest(testToStudying)
    }

    override fun onStop() {
        super.onStop()
        stopTimer()
        binding.studyingPauseButton.text = pauseButtonStartText
    }

    private fun getSearchKey(testToStudying: Tests) = testToStudying.lesson + testToStudying.topic

    private fun loadVideosFromAPI(lesson: String) {

        viewModel.dataFromAPIResult.observe(this) {

            if (it != null) {
                val bestOfFiveVideosURL: ArrayList<String> =
                    YoutubeObject.getBestOfFive(it, lesson)

                setUpYoutubePlayers(bestOfFiveVideosURL = bestOfFiveVideosURL)
            } else {
                //TODO
            }

        }

    }

    private fun setUpYoutubePlayers(bestOfFiveVideosURL: ArrayList<String>) {
        lifecycle.addObserver(binding.youtubePlayer1)
        lifecycle.addObserver(binding.youtubePlayer2)
        lifecycle.addObserver(binding.youtubePlayer3)
        lifecycle.addObserver(binding.youtubePlayer4)
        lifecycle.addObserver(binding.youtubePlayer5)

        binding.youtubePlayer1.addYouTubePlayerListener(object : AbstractYouTubePlayerListener() {
            override fun onReady(youTubePlayer: YouTubePlayer) {
                youTubePlayer.cueVideo(bestOfFiveVideosURL[0], 0f)
            }

            override fun onStateChange(
                youTubePlayer: YouTubePlayer,
                state: PlayerConstants.PlayerState
            ) {
                super.onStateChange(youTubePlayer, state)
                if (state.name == "ENDED") {
                    watchedVideos++
                }
            }

        })
        binding.youtubePlayer2.addYouTubePlayerListener(object : AbstractYouTubePlayerListener() {
            override fun onReady(youTubePlayer: YouTubePlayer) {
                youTubePlayer.cueVideo(bestOfFiveVideosURL[1], 0f)
            }

            override fun onStateChange(
                youTubePlayer: YouTubePlayer,
                state: PlayerConstants.PlayerState
            ) {
                super.onStateChange(youTubePlayer, state)
                if (state.name == "ENDED") {
                    watchedVideos++
                }
            }

        })
        binding.youtubePlayer3.addYouTubePlayerListener(object : AbstractYouTubePlayerListener() {
            override fun onReady(youTubePlayer: YouTubePlayer) {
                youTubePlayer.cueVideo(bestOfFiveVideosURL[2], 0f)
            }

            override fun onStateChange(
                youTubePlayer: YouTubePlayer,
                state: PlayerConstants.PlayerState
            ) {
                super.onStateChange(youTubePlayer, state)
                if (state.name == "ENDED") {
                    watchedVideos++
                }
            }

        })
        binding.youtubePlayer4.addYouTubePlayerListener(object : AbstractYouTubePlayerListener() {
            override fun onReady(youTubePlayer: YouTubePlayer) {
                youTubePlayer.cueVideo(bestOfFiveVideosURL[3], 0f)
            }

            override fun onStateChange(
                youTubePlayer: YouTubePlayer,
                state: PlayerConstants.PlayerState
            ) {
                super.onStateChange(youTubePlayer, state)
                if (state.name == "ENDED") {
                    watchedVideos++
                }
            }

        })
        binding.youtubePlayer5.addYouTubePlayerListener(object : AbstractYouTubePlayerListener() {
            override fun onReady(youTubePlayer: YouTubePlayer) {
                youTubePlayer.cueVideo(bestOfFiveVideosURL[4], 0f)
            }

            override fun onStateChange(
                youTubePlayer: YouTubePlayer,
                state: PlayerConstants.PlayerState
            ) {
                super.onStateChange(youTubePlayer, state)
                if (state.name == "ENDED") {
                    watchedVideos++
                }
            }

        })
    }

    private fun setListeners() {
        binding.studyingPauseButton.setOnClickListener {
            if (binding.studyingPauseButton.text == pauseButtonPauseText) {
                binding.studyingPauseButton.text = pauseButtonStartText
                stopTimer()
            } else if (binding.studyingPauseButton.text == pauseButtonStartText) {
                binding.studyingPauseButton.text = pauseButtonPauseText
                startTimer()
            }
        }
        binding.studyingStopButton.setOnClickListener {
            finish()
        }
    }

    private fun setTestInfo() {
        binding.testLessonNameText.text = testToStudying.lesson
        binding.testTopicNameText.text = testToStudying.topic
    }

    private fun startTimer() {
        serviceIntent.putExtra(TimerService.TIME_EXTRA, time)
        startService(serviceIntent)
    }

    private fun stopTimer() {
        stopService(serviceIntent)
        testToStudying.timeOfLearning = time
    }

    private val updateTime: BroadcastReceiver = object : BroadcastReceiver() {
        override fun onReceive(context: Context?, intent: Intent?) {
            time = intent!!.getDoubleExtra(TimerService.TIME_EXTRA, 0.0)
            binding.studyingTimeText.text = Convert.getTimeStringFromDouble(time)
        }
    }

}