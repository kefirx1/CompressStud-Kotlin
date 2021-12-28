package pl.dev.kefirx.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import kotlinx.android.synthetic.main.fragment_register_end.*
import pl.dev.kefirx.MainActivity
import pl.dev.kefirx.R
import pl.dev.kefirx.room.User
import pl.dev.kefirx.viewModel.CSViewModel

class FragmentRegisterEnd : Fragment() {


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        return inflater.inflate(R.layout.fragment_register_end, container, false)
    }

    override fun onStart() {
        super.onStart()

        val personalInformation: ArrayList<String> = arguments?.getStringArrayList("info") as ArrayList<String>

        val name = personalInformation[0]
        val levelOfEdu = personalInformation[1]
        val likeMusic = personalInformation[2]
        val musicGenres = personalInformation[3]
        val user  = User(name, levelOfEdu, likeMusic, musicGenres)

        MainActivity.viewModel.insertUser(user)

        println(MainActivity.viewModel.getUserCountAsync())

        endRegisterButton.setOnClickListener{
            this.activity?.finish()
        }
    }



}