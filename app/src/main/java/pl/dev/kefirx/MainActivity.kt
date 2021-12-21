package pl.dev.kefirx

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import pl.dev.kefirx.databinding.ActivityMainBinding
import pl.dev.kefirx.viewModel.UserViewModel

class MainActivity : AppCompatActivity() {

    companion object{
        lateinit var viewModel: UserViewModel
    }

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        binding = ActivityMainBinding.inflate(layoutInflater)

        viewModel = ViewModelProvider
            .AndroidViewModelFactory
            .getInstance(application)
            .create(UserViewModel::class.java)


        val registerIntent = Intent(this, RegisterActivity::class.java).apply{}
        startActivity(registerIntent)




    }
}