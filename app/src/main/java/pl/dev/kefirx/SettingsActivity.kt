package pl.dev.kefirx

import android.os.Bundle
import android.view.View
import android.view.View.VISIBLE
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import pl.dev.kefirx.classes.Notification
import pl.dev.kefirx.data.User
import pl.dev.kefirx.databinding.ActivitySettingsBinding
import pl.dev.kefirx.viewModels.SettingsViewModel

class SettingsActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySettingsBinding
    private lateinit var viewModel: SettingsViewModel
    private lateinit var userInfo: User
    private var temp = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        viewModel = ViewModelProvider
            .AndroidViewModelFactory
            .getInstance(application)
            .create(SettingsViewModel::class.java)

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
                Notification().cancelAllNotification(applicationContext)
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