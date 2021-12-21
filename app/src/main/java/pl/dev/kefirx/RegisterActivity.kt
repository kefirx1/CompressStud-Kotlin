package pl.dev.kefirx

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import pl.dev.kefirx.databinding.ActivityMainBinding
import pl.dev.kefirx.fragments.FragmentRegisterStart

class RegisterActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)
        binding = ActivityMainBinding.inflate(layoutInflater)
    }
}