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

            binding.newMusicButton.setOnClickListener{
                val newFavMusic = binding.newMusicSpinner.selectedItem.toString()
                val user : User = MainActivity.viewModel.getUserInfoAsync()
                user.musicGenres = newFavMusic
                MainActivity.viewModel.updateUser(user)
                this.finish()
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

}