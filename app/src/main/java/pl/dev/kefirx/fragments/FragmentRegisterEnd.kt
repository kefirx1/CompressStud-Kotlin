package pl.dev.kefirx.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import pl.dev.kefirx.data.User
import pl.dev.kefirx.databinding.FragmentRegisterEndBinding
import pl.dev.kefirx.viewModels.RegisterViewModel


class FragmentRegisterEnd : Fragment() {

    private lateinit var binding: FragmentRegisterEndBinding
    private val viewModel: RegisterViewModel by activityViewModels()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = FragmentRegisterEndBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onStart() {
        super.onStart()

        val personalInformation: ArrayList<String> = arguments?.getStringArrayList("info") as ArrayList<String>

        val name = personalInformation[0]
        val levelOfEdu = personalInformation[1]
        val likeMusic = personalInformation[2].toBoolean()
        val musicGenres = personalInformation[3]
        val user  = User(name, levelOfEdu, likeMusic, musicGenres)


        viewModel.insertUser(user)

        binding.endRegisterButton.setOnClickListener{
            this.activity?.finish()
        }
    }



}