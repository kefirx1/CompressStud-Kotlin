package pl.dev.kefirx

import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintSet.GONE
import androidx.core.view.isVisible
import kotlinx.android.synthetic.main.activity_settings.*
import kotlinx.android.synthetic.main.fragment_register_step2.*
import pl.dev.kefirx.databinding.ActivitySettingsBinding
import pl.dev.kefirx.room.User

class SettingsActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySettingsBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_settings)
        binding = ActivitySettingsBinding.inflate(layoutInflater)

        backToDashboardButton.setOnClickListener{
            this.finish()
        }

        openNameModal.setOnClickListener{
            setGoneToAllModals()
            nameModal.visibility = VISIBLE

            editNameButton.setOnClickListener{
                val newName: String = newNameEditText.text.toString()
                val user : User = MainActivity.viewModel.getUserInfoAsync()
                user.name = newName
                MainActivity.viewModel.updateUser(user)
                this.finish()
            }

        }

        openLevelModal.setOnClickListener{
            setGoneToAllModals()
            levelModal.visibility = VISIBLE

            newLevelButton.setOnClickListener{
                val newLevel = newLevelSpinner.selectedItem.toString()
                val user : User = MainActivity.viewModel.getUserInfoAsync()
                user.levelOfEdu = newLevel
                MainActivity.viewModel.updateUser(user)
                this.finish()
            }
        }

        openMusicModal.setOnClickListener{
            setGoneToAllModals()
            musicModal.visibility = VISIBLE

            newMusicButton.setOnClickListener{
                val newFavMusic = newMusicSpinner.selectedItem.toString()
                val user : User = MainActivity.viewModel.getUserInfoAsync()
                user.musicGenres = newFavMusic
                MainActivity.viewModel.updateUser(user)
                this.finish()
            }
        }

        openThemeModal.setOnClickListener{
            setGoneToAllModals()
            themeModal.visibility = VISIBLE

            themeButton.setOnClickListener{
                this.finish()
            }
        }

        openWarningModal.setOnClickListener{
            setGoneToAllModals()
            wipeDataModal.visibility = VISIBLE

            wipeDataButton.setOnClickListener{
                MainActivity.viewModel.deleteAllTests()
                MainActivity.viewModel.deleteAllUserInfo()
                this.finish()
            }
        }


        settingsLayout.setOnClickListener{
            setGoneToAllModals()
        }


    }

    private fun setGoneToAllModals(){
        wipeDataModal.visibility = View.GONE
        nameModal.visibility = View.GONE
        levelModal.visibility = View.GONE
        musicModal.visibility = View.GONE
        themeModal.visibility = View.GONE
    }

}