package pl.dev.kefirx

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import pl.dev.kefirx.databinding.ActivityCalendarBinding

class CalendarActivity : AppCompatActivity() {

    private lateinit var binding: ActivityCalendarBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCalendarBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.backToDashboardButton.setOnClickListener{
            this.finish()
        }

    }


}