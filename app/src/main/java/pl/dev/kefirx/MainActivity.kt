package pl.dev.kefirx

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import dagger.hilt.android.AndroidEntryPoint
import pl.dev.kefirx.App.Companion.applicationContext
import pl.dev.kefirx.classes.*
import pl.dev.kefirx.databinding.ActivityMainBinding
import pl.dev.kefirx.receivers.NotificationReceiver
import pl.dev.kefirx.viewModels.DashboardViewModel

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    companion object {
        val ai = applicationContext().packageManager
            .getApplicationInfo(applicationContext().packageName, PackageManager.GET_META_DATA)
    }

    private lateinit var binding: ActivityMainBinding
    private lateinit var dashboardBestThreeView: DashboardBestThreeView
    private lateinit var modalsView: ModalsView
    private lateinit var listenersSet: ListenersSet
    private lateinit var spinnersSet: SpinnersSet
    private lateinit var examsExpiration: ExamsExpiration

    private val viewModel: DashboardViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }


    fun createNotificationChannel(channelID: String) {
        val name = "Notif channel"
        val desc = "Desc channel"
        val importance = NotificationManager.IMPORTANCE_DEFAULT
        val channel = NotificationChannel(channelID, name, importance)
        channel.description = desc

        val notificationManager =
            getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        notificationManager.createNotificationChannel(channel)

    }


    override fun onResume() {
        super.onResume()

        if (viewModel.getUserCountAsync() <= 0) {
            Log.e("TAG", "Create user")
            val registerIntent = Intent(this, RegisterActivity::class.java)
            startActivity(registerIntent)
        } else {
            modalsView = ModalsView()
            dashboardBestThreeView = DashboardBestThreeView(
                binding = binding,
                applicationContext = applicationContext,
                instance = this,
                viewModel = viewModel
            )
            listenersSet = ListenersSet(
                viewModel = viewModel
            )
            spinnersSet = SpinnersSet()
            examsExpiration = ExamsExpiration()


            dashboardBestThreeView.resetListeners()
            modalsView.hideAllModals(binding)
            setDashboardCurrentInfo()
            listenersSet.setMainActivityListeners(
                binding = binding,
                applicationContext = applicationContext,
                instance = this
            )

            val receiver = ComponentName(applicationContext, NotificationReceiver::class.java)

            packageManager.setComponentEnabledSetting(
                receiver,
                PackageManager.COMPONENT_ENABLED_STATE_ENABLED,
                PackageManager.DONT_KILL_APP
            )

        }

    }



    private fun setDashboardCurrentInfo() {
        viewModel.setUserInfoObserver()
        viewModel.setNewestThreeTestsInfoObserver()

        viewModel.userInfoResult.observe(this){ userInfo ->
            if(userInfo!=null){
                val name = userInfo.name + "!"

                binding.userNameTextView.text = name
                examsExpiration.checkExamsExpirationDate(
                    applicationContext = applicationContext,
                    instance = this,
                    viewModel = viewModel
                )

                viewModel.newestThreeTestsInfoResult.observe(this){
                    if(it!=null){
                        dashboardBestThreeView.setBestOfThreeView(listOfThreeTests = it)
                    }else{
                        //TODO
                    }
                }

            }else{
                //TODO
            }
        }


    }

}