package pl.dev.kefirx.fragments

import android.app.Activity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import pl.dev.kefirx.MainActivity
import pl.dev.kefirx.R
import pl.dev.kefirx.databinding.FragmentRegisterEndBinding
import pl.dev.kefirx.room.User

class FragmentRegisterEnd : Fragment() {

    private lateinit var binding: FragmentRegisterEndBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        return inflater.inflate(R.layout.fragment_register_end, container, false)
    }

    override fun onStart() {
        super.onStart()
        binding = FragmentRegisterEndBinding.inflate(layoutInflater)

        val personalInformation: ArrayList<String> = arguments?.getStringArrayList("info") as ArrayList<String>

        val name = personalInformation[0]
        val levelOfEdu = personalInformation[1]
        val likeMusic = personalInformation[2]
        val musicGenres = personalInformation[3]
        val user  = User(name, levelOfEdu, likeMusic, musicGenres)

        MainActivity.viewModel.insertUser(user)

        binding.endRegisterButton.setOnClickListener{
            this.activity?.finish()
        }
    }



}