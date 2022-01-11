package pl.dev.kefirx

import android.annotation.SuppressLint
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.AbstractYouTubePlayerListener
import pl.dev.kefirx.databinding.ActivityStudyingBinding
import pl.dev.kefirx.json.ytResponse.YoutubeResponseJSON
import pl.dev.kefirx.room.Tests
import pl.dev.kefirx.services.TimerService
import pl.dev.kefirx.youTube.YoutubeObject
import pl.dev.kefirx.youTube.YoutubeSampleRespose




class StudyingActivity : AppCompatActivity() {

    private lateinit var testToStudying: Tests
    private lateinit var binding: ActivityStudyingBinding
    private lateinit var serviceIntent: Intent
    private var time = 0.0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityStudyingBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val idTestToStudying = intent.getIntExtra("testId", -1)
        testToStudying = MainActivity.viewModel.getTestByIdInfoAsync(idTestToStudying)

        time = testToStudying.timeOfLearning

        serviceIntent = Intent(applicationContext, TimerService::class.java)
        registerReceiver(updateTime, IntentFilter(TimerService.TIMER_UPDATED))
        setListeners()
        setTestInfo()


//        CoroutineScope(Dispatchers.IO).launch{
//            println(YoutubeRetrofitClient.instance
//                .getResponseAsync("")
//                .await()
//                .body()!!.items[0].id.videoId)
//        }

        val responseObject = YoutubeSampleRespose.getSampleResponse(this)

        loadVideos(responseObject)



    }


    override fun onResume() {
        super.onResume()
        startTimer()
    }

    private fun loadVideos(responseObject: YoutubeResponseJSON){

        val bestOfFiveVideosURL: ArrayList<String> = YoutubeObject.getBestOfFive(responseObject)

        lifecycle.addObserver(binding.youtubePlayer1)
        lifecycle.addObserver(binding.youtubePlayer2)
        lifecycle.addObserver(binding.youtubePlayer3)
        lifecycle.addObserver(binding.youtubePlayer4)
        lifecycle.addObserver(binding.youtubePlayer5)

        binding.youtubePlayer1.addYouTubePlayerListener(object : AbstractYouTubePlayerListener() {
            override fun onReady(youTubePlayer: YouTubePlayer) {
                youTubePlayer.cueVideo(bestOfFiveVideosURL[0], 0f)
            }
        })
        binding.youtubePlayer2.addYouTubePlayerListener(object : AbstractYouTubePlayerListener() {
            override fun onReady(youTubePlayer: YouTubePlayer) {
                youTubePlayer.cueVideo(bestOfFiveVideosURL[1], 0f)
            }
        })
        binding.youtubePlayer3.addYouTubePlayerListener(object : AbstractYouTubePlayerListener() {
            override fun onReady(youTubePlayer: YouTubePlayer) {
                youTubePlayer.cueVideo(bestOfFiveVideosURL[2], 0f)
            }
        })
        binding.youtubePlayer4.addYouTubePlayerListener(object : AbstractYouTubePlayerListener() {
            override fun onReady(youTubePlayer: YouTubePlayer) {
                youTubePlayer.cueVideo(bestOfFiveVideosURL[3], 0f)
            }
        })
        binding.youtubePlayer5.addYouTubePlayerListener(object : AbstractYouTubePlayerListener() {
            override fun onReady(youTubePlayer: YouTubePlayer) {
                youTubePlayer.cueVideo(bestOfFiveVideosURL[4], 0f)
            }
        })
    }

    @SuppressLint("SetTextI18n")
    private fun setListeners(){
        binding.studyingPauseButton.setOnClickListener{
            if(binding.studyingPauseButton.text == "PAUZA"){
                binding.studyingPauseButton.text = "START"
                stopTimer()
            }else if (binding.studyingPauseButton.text == "START"){
                binding.studyingPauseButton.text = "PAUZA"
                startTimer()
            }
        }
        binding.studyingStopButton.setOnClickListener{
            finish()
        }
    }

    private fun setTestInfo(){
        binding.testLessonNameText.text = testToStudying.lesson
        binding.testTopicNameText.text = testToStudying.topic
    }

    private fun startTimer(){
        serviceIntent.putExtra(TimerService.TIME_EXTRA, time)
        startService(serviceIntent)
    }
    private fun stopTimer(){
        stopService(serviceIntent)
        testToStudying.timeOfLearning = time
    }

    private val updateTime: BroadcastReceiver = object : BroadcastReceiver(){
        override fun onReceive(context: Context?, intent: Intent?) {
            time = intent!!.getDoubleExtra(TimerService.TIME_EXTRA, 0.0)
            binding.studyingTimeText.text = Convert.getTimeStringFromDouble(time)
        }
    }


    override fun onPause() {
        super.onPause()
        stopService(serviceIntent)
        testToStudying.timeOfLearning = time
        MainActivity.viewModel.updateTest(testToStudying)
    }
}