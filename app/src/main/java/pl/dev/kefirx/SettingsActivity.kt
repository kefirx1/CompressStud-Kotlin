package pl.dev.kefirx

import android.os.Bundle
import android.view.View
import android.view.View.VISIBLE
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import dagger.hilt.android.AndroidEntryPoint
import pl.dev.kefirx.classes.Notification
import pl.dev.kefirx.data.User
import pl.dev.kefirx.databinding.ActivitySettingsBinding
import pl.dev.kefirx.viewModels.SettingsViewModel

@AndroidEntryPoint
class SettingsActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySettingsBinding
    private lateinit var userInfo: User
    private val viewModel: SettingsViewModel by viewModels()
    private var temp = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivitySettingsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModel.setUserInfoObserver()

        binding.backToDashboardButton.setOnClickListener {
            this.finish()
        }

        viewModel.userInfoResult.observe(this){
            if(it!=null){
                userInfo = it

                setListeners()

            }else{
                //TODO
            }
        }

    }

    private fun setListeners(){
        binding.openNameModal.setOnClickListener {
            setGoneToAllModals()
            binding.nameModal.visibility = VISIBLE

            binding.editNameButton.setOnClickListener {
                val newName: String = binding.newNameEditText.text.toString()

                if (newName.isNotBlank()) {
                    userInfo.name = newName
                    viewModel.updateUser(userInfo)
                    this.finish()
                } else {
                    Toast.makeText(this, "Podaj poprawne imię", Toast.LENGTH_SHORT).show()
                }

            }

        }

        binding.openLevelModal.setOnClickListener {
            setGoneToAllModals()
            binding.levelModal.visibility = VISIBLE

            binding.newLevelButton.setOnClickListener {
                val newLevel = binding.newLevelSpinner.selectedItem.toString()
                userInfo.levelOfEdu = newLevel
                viewModel.updateUser(userInfo)
                this.finish()
            }
        }

        binding.openMusicModal.setOnClickListener {
            setGoneToAllModals()
            binding.musicModal.visibility = VISIBLE

            setMusicModalView()

            binding.newMusicSwitch.setOnClickListener {
                val isChecked = binding.newMusicSwitch.isChecked
                if (isChecked) {
                    userInfo.likeMusic = true
                    viewModel.updateUser(userInfo)
                } else {
                    userInfo.likeMusic = false
                    viewModel.updateUser(userInfo)
                }
                setMusicModalView()
            }

        }

        binding.openWarningModal.setOnClickListener {
            setGoneToAllModals()
            binding.wipeDataModal.visibility = VISIBLE

            binding.wipeDataButton.setOnClickListener {
                viewModel.deleteAllTests()
                viewModel.deleteAllUserInfo()
                Notification().cancelAllNotification(
                    applicationContext = applicationContext,
                    instance = this,
                    viewModel = viewModel)
                temp = true
                this.finish()
            }
        }


        binding.settingsLayout.setOnClickListener {
            setGoneToAllModals()
        }
    }


    private fun setGoneToAllModals(){
        binding.wipeDataModal.visibility = View.GONE
        binding.nameModal.visibility = View.GONE
        binding.levelModal.visibility = View.GONE
        binding.musicModal.visibility = View.GONE
    }

    private fun isLikeMusicTrue() = userInfo.likeMusic
    private fun isMusicGenresEmpty() = userInfo.musicGenres == ""

    private fun setMusicModalView(){
        if(isLikeMusicTrue()){
            binding.newMusicSpinner.visibility = VISIBLE
            binding.newFavMusicTextView.visibility = VISIBLE
            binding.newMusicSwitch.isChecked = true
            binding.newMusicButton.setOnClickListener{
                val newFavMusic = binding.newMusicSpinner.selectedItem.toString()
                val likeMusic = binding.newMusicSwitch.isChecked
                userInfo.musicGenres = newFavMusic
                userInfo.likeMusic = likeMusic
                viewModel.updateUser(userInfo)
                this.finish()
            }
        }else{
            binding.newMusicSpinner.visibility = View.GONE
            binding.newFavMusicTextView.visibility = View.GONE
            binding.newMusicButton.setOnClickListener{
                val likeMusic = binding.newMusicSwitch.isChecked
                userInfo.musicGenres = ""
                userInfo.likeMusic = likeMusic
                viewModel.updateUser(userInfo)
                this.finish()
            }
        }
    }


    override fun onStop() {
        super.onStop()

        if (!temp) {
            if (!isLikeMusicTrue()) {
                userInfo.musicGenres = ""
                viewModel.updateUser(userInfo)
            } else {
                if (isMusicGenresEmpty()) {
                    userInfo.musicGenres = "Trap"
                    viewModel.updateUser(userInfo)
                }
            }

        }
    }


}