package pl.dev.kefirx

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import dagger.hilt.android.AndroidEntryPoint
import pl.dev.kefirx.databinding.ActivityMainBinding

@AndroidEntryPoint
class RegisterActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.e("TAG", "Register")

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(R.layout.activity_register)
    }
}