package pl.dev.kefirx

import android.os.Bundle
import android.view.View
import android.view.View.VISIBLE
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import pl.dev.kefirx.classes.Notification
import pl.dev.kefirx.databinding.ActivitySettingsBinding
import pl.dev.kefirx.room.User
import pl.dev.kefirx.viewModel.CSViewModel

class SettingsActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySettingsBinding
    private lateinit var viewModel: CSViewModel
    private var temp = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        viewModel = ViewModelProvider
            .AndroidViewModelFactory
            .getInstance(application)
            .create(CSViewModel::class.java)

        binding = ActivitySettingsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.backToDashboardButton.setOnClickListener {
            this.finish()
        }

        binding.openNameModal.setOnClickListener {
            setGoneToAllModals()
            binding.nameModal.visibility = VISIBLE

            binding.editNameButton.setOnClickListener {
                val newName: String = binding.newNameEditText.text.toString()
                val user: User = viewModel.getUserInfoAsync()

                if (newName.isNotBlank()) {
                    user.name = newName
                    viewModel.updateUser(user)
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
                val user: User = viewModel.getUserInfoAsync()
                user.levelOfEdu = newLevel
                viewModel.updateUser(user)
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
                    val user: User = viewModel.getUserInfoAsync()
                    user.likeMusic = true
                    viewModel.updateUser(user)
                } else {
                    val user: User = viewModel.getUserInfoAsync()
                    user.likeMusic = false
                    viewModel.updateUser(user)
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

    private fun isLikeMusicTrue() = viewModel.getUserInfoAsync().likeMusic
    private fun isMusicGenresEmpty() = viewModel.getUserInfoAsync().musicGenres == ""

    private fun setMusicModalView(){
        if(isLikeMusicTrue()){
            binding.newMusicSpinner.visibility = VISIBLE
            binding.newFavMusicTextView.visibility = VISIBLE
            binding.newMusicSwitch.isChecked = true
            binding.newMusicButton.setOnClickListener{
                val newFavMusic = binding.newMusicSpinner.selectedItem.toString()
                val likeMusic = binding.newMusicSwitch.isChecked
                val user : User = viewModel.getUserInfoAsync()
                user.musicGenres = newFavMusic
                user.likeMusic = likeMusic
                viewModel.updateUser(user)
                this.finish()
            }
        }else{
            binding.newMusicSpinner.visibility = View.GONE
            binding.newFavMusicTextView.visibility = View.GONE
            binding.newMusicButton.setOnClickListener{
                val likeMusic = binding.newMusicSwitch.isChecked
                val user : User = viewModel.getUserInfoAsync()
                user.musicGenres = ""
                user.likeMusic = likeMusic
                viewModel.updateUser(user)
                this.finish()
            }
        }
    }


    override fun onStop() {
        super.onStop()

        if (!temp) {
            if (!isLikeMusicTrue()) {
                val user: User = viewModel.getUserInfoAsync()
                user.musicGenres = ""
                viewModel.updateUser(user)
            } else {
                if (isMusicGenresEmpty()) {
                    val user: User = viewModel.getUserInfoAsync()
                    user.musicGenres = "Trap"
                    viewModel.updateUser(user)
                }
            }

        }
    }


}