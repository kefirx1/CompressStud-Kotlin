package pl.dev.kefirx

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.lifecycle.ViewModelProvider
import pl.dev.kefirx.databinding.ActivityMainBinding
import pl.dev.kefirx.viewModels.RegisterViewModel

class RegisterActivity : AppCompatActivity() {

    companion object{
        lateinit var viewModel: RegisterViewModel
    }

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.e("TAG", "Register")

        viewModel = ViewModelProvider
            .AndroidViewModelFactory
            .getInstance(application)
            .create(RegisterViewModel::class.java)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(R.layout.activity_register)
    }
}