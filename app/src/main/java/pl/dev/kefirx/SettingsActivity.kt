package pl.dev.kefirx

import android.os.Bundle
import android.view.View
import android.view.View.VISIBLE
import androidx.appcompat.app.AppCompatActivity
import pl.dev.kefirx.databinding.ActivitySettingsBinding
import pl.dev.kefirx.room.User

class SettingsActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySettingsBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySettingsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.backToDashboardButton.setOnClickListener{
            this.finish()
        }

        binding.openNameModal.setOnClickListener{
            setGoneToAllModals()
            binding.nameModal.visibility = VISIBLE

            binding.editNameButton.setOnClickListener{
                val newName: String = binding.newNameEditText.text.toString()
                val user : User = MainActivity.viewModel.getUserInfoAsync()
                user.name = newName
                MainActivity.viewModel.updateUser(user)
                this.finish()
            }

        }

        binding.openLevelModal.setOnClickListener{
            setGoneToAllModals()
            binding.levelModal.visibility = VISIBLE

            binding.newLevelButton.setOnClickListener{
                val newLevel = binding.newLevelSpinner.selectedItem.toString()
                val user : User = MainActivity.viewModel.getUserInfoAsync()
                user.levelOfEdu = newLevel
                MainActivity.viewModel.updateUser(user)
                this.finish()
            }
        }

        binding.openMusicModal.setOnClickListener{
            setGoneToAllModals()
            binding.musicModal.visibility = VISIBLE

            setMusicModalView()

            binding.newMusicSwitch.setOnClickListener{
                val isChecked = binding.newMusicSwitch.isChecked
                if(isChecked){
                    val user : User = MainActivity.viewModel.getUserInfoAsync()
                    user.likeMusic = true
                    MainActivity.viewModel.updateUser(user)
                }else{
                    val user : User = MainActivity.viewModel.getUserInfoAsync()
                    user.likeMusic = false
                    MainActivity.viewModel.updateUser(user)
                }
                setMusicModalView()
            }

        }

        binding.openThemeModal.setOnClickListener{
            setGoneToAllModals()
            binding.themeModal.visibility = VISIBLE

            binding.themeButton.setOnClickListener{
                this.finish()
            }
        }

        binding.openWarningModal.setOnClickListener{
            setGoneToAllModals()
            binding.wipeDataModal.visibility = VISIBLE

            binding.wipeDataButton.setOnClickListener{
                MainActivity.viewModel.deleteAllTests()
                MainActivity.viewModel.deleteAllUserInfo()
                this.finish()
            }
        }


        binding.settingsLayout.setOnClickListener{
            setGoneToAllModals()
        }


    }

    private fun setGoneToAllModals(){
        binding.wipeDataModal.visibility = View.GONE
        binding.nameModal.visibility = View.GONE
        binding.levelModal.visibility = View.GONE
        binding.musicModal.visibility = View.GONE
        binding.themeModal.visibility = View.GONE
    }

    private fun isLikeMusicTrue() = MainActivity.viewModel.getUserInfoAsync().likeMusic
    private fun isMusicGenresEmpty() = MainActivity.viewModel.getUserInfoAsync().musicGenres == ""

    private fun setMusicModalView(){
        if(isLikeMusicTrue()){
            binding.newMusicSpinner.visibility = View.VISIBLE
            binding.newFavMusicTextView.visibility = View.VISIBLE
            binding.newMusicSwitch.isChecked = true
            binding.newMusicButton.setOnClickListener{
                val newFavMusic = binding.newMusicSpinner.selectedItem.toString()
                val likeMusic = binding.newMusicSwitch.isChecked
                val user : User = MainActivity.viewModel.getUserInfoAsync()
                user.musicGenres = newFavMusic
                user.likeMusic = likeMusic
                MainActivity.viewModel.updateUser(user)
                this.finish()
            }
        }else{
            binding.newMusicSpinner.visibility = View.GONE
            binding.newFavMusicTextView.visibility = View.GONE
            binding.newMusicButton.setOnClickListener{
                val likeMusic = binding.newMusicSwitch.isChecked
                val user : User = MainActivity.viewModel.getUserInfoAsync()
                user.musicGenres = ""
                user.likeMusic = likeMusic
                MainActivity.viewModel.updateUser(user)
                this.finish()
            }
        }
    }

    override fun onStop() {
        super.onStop()

        if(!isLikeMusicTrue()){
            val user : User = MainActivity.viewModel.getUserInfoAsync()
            user.musicGenres = ""
            MainActivity.viewModel.updateUser(user)
        }else{
            if(isMusicGenresEmpty()){
                val user : User = MainActivity.viewModel.getUserInfoAsync()
                user.musicGenres = "Trap"
                MainActivity.viewModel.updateUser(user)
            }
        }

    }


}