package pl.dev.kefirx

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.lifecycle.ViewModelProvider
import pl.dev.kefirx.databinding.ActivityMainBinding
import pl.dev.kefirx.viewModel.CSViewModel

class RegisterActivity : AppCompatActivity() {

    companion object{
        lateinit var viewModel: CSViewModel
    }

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.e("TAG", "Register")

        viewModel = ViewModelProvider
            .AndroidViewModelFactory
            .getInstance(application)
            .create(CSViewModel::class.java)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(R.layout.activity_register)
    }
}