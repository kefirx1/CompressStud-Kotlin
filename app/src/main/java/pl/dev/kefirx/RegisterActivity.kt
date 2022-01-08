package pl.dev.kefirx

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import pl.dev.kefirx.databinding.ActivityMainBinding

class RegisterActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.e("TAG", "Register")
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(R.layout.activity_register)
    }
}