package pl.dev.kefirx

import android.os.Bundle
import android.util.Log
import android.view.View.VISIBLE
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_settings.*
import pl.dev.kefirx.databinding.ActivitySettingsBinding

class SettingsActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySettingsBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_settings)
        binding = ActivitySettingsBinding.inflate(layoutInflater)

        backToDashboardButton.setOnClickListener{
            this.finish()
        }

        openWarningModal.setOnClickListener{
            wipeDataModal.visibility = VISIBLE

            wipeDataButton.setOnClickListener{
                MainActivity.viewModel.deleteAllTests()
                MainActivity.viewModel.deleteAllUserInfo()
                this.finish()
            }
        }


    }

}