package pl.dev.kefirx.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import pl.dev.kefirx.R
import pl.dev.kefirx.databinding.FragmentRegisterStep1Binding

class FragmentRegisterStep1 : Fragment() {

    private lateinit var binding: FragmentRegisterStep1Binding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        return inflater.inflate(R.layout.fragment_register_step1, container, false)
    }

    override fun onStart() {
        super.onStart()

        binding = FragmentRegisterStep1Binding.inflate(layoutInflater)

        binding.nextStep2Button.setOnClickListener{

            val personName = binding.personNameEditText.text.toString()

            val personalInformation: ArrayList<String> = ArrayList()
            personalInformation.add(personName)

            findNavController().navigate(
                FragmentRegisterStep1Directions.actionFragmentRegisterStep1ToFragmentRegisterStep2().actionId,
                bundleOf("info" to personalInformation))
        }

    }
}