package pl.dev.kefirx

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.google.gson.Gson
import pl.dev.kefirx.classes.DashboardBestThreeView
import pl.dev.kefirx.classes.ListenersSet
import pl.dev.kefirx.classes.ModalsView
import pl.dev.kefirx.classes.SpinnersSet
import pl.dev.kefirx.databinding.ActivityMainBinding
import pl.dev.kefirx.json.GetJSONString
import pl.dev.kefirx.json.ListOfTopicsJSON
import pl.dev.kefirx.viewModel.CSViewModel


open class MainActivity : AppCompatActivity() {

    companion object{
        lateinit var viewModel: CSViewModel
        const val LIST_OF_TOPICS_PATH = "listOfTopics.json"
        lateinit var listOfTopicsObject: ListOfTopicsJSON
    }

    protected lateinit var binding: ActivityMainBinding
    private lateinit var dashboardBestThreeView: DashboardBestThreeView
    private lateinit var modalsView: ModalsView
    private lateinit var listenersSet: ListenersSet
    private lateinit var spinnersSet: SpinnersSet
    private var getJSONString = GetJSONString()
    private val gson = Gson()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        listOfTopicsObject  = gson.fromJson(getJSONString.getJsonStringFromAssets(applicationContext, LIST_OF_TOPICS_PATH), ListOfTopicsJSON::class.java)

        modalsView = ModalsView()
        dashboardBestThreeView = DashboardBestThreeView(binding, applicationContext, this)
        listenersSet = ListenersSet()
        spinnersSet = SpinnersSet()
    }

    override fun onResume() {
        super.onResume()

        viewModel = ViewModelProvider
            .AndroidViewModelFactory
            .getInstance(application)
            .create(CSViewModel::class.java)

        if(viewModel.getUserCountAsync() <= 0) {
            Log.e("TAG", "Create user")
            val registerIntent = Intent(this, RegisterActivity::class.java)
            Log.e("TAG", "Start act")
            startActivity(registerIntent)
        }else{
            modalsView.hideAllModals(binding)
            setDashboardCurrentInfo()
            listenersSet.setMainActivityListeners(binding, applicationContext, this)
        }

    }

    fun callOnResume(){
        onResume()
    }


    private fun setDashboardCurrentInfo(){
        val name = viewModel.getUserInfoAsync().name + "!"
        binding.userNameTextView.text = name
        dashboardBestThreeView.setBestOfThreeView(viewModel.getThreeExams())
    }


}