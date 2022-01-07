package pl.dev.kefirx.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import pl.dev.kefirx.R
import pl.dev.kefirx.databinding.FragmentRegisterStartBinding


class FragmentRegisterStart : Fragment() {

    private lateinit var binding: FragmentRegisterStartBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        return inflater.inflate(R.layout.fragment_register_start, container, false)
    }

    override fun onStart() {
        super.onStart()

        binding = FragmentRegisterStartBinding.inflate(layoutInflater)

        binding.startRegisterButton.setOnClickListener{
            findNavController().navigate(
                FragmentRegisterStartDirections.actionFragmentRegisterStartToFragmentRegisterStep1())
        }


    }


}