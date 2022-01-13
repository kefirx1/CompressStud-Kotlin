package pl.dev.kefirx.fragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import pl.dev.kefirx.databinding.FragmentRegisterStartBinding


class FragmentRegisterStart : Fragment() {

    private lateinit var binding: FragmentRegisterStartBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = FragmentRegisterStartBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onStart() {
        super.onStart()


        Log.e("TAG", "start")


        binding.startRegisterButton.setOnClickListener{
            findNavController().navigate(
                FragmentRegisterStartDirections.actionFragmentRegisterStartToFragmentRegisterStep1())
        }


    }


}