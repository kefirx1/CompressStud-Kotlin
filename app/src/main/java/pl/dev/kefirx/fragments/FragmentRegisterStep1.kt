package pl.dev.kefirx.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import kotlinx.android.synthetic.main.fragment_register_start.*
import kotlinx.android.synthetic.main.fragment_register_step1.*
import pl.dev.kefirx.R

class FragmentRegisterStep1 : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        return inflater.inflate(R.layout.fragment_register_step1, container, false)
    }

    override fun onStart() {
        super.onStart()



        nextStep2Button.setOnClickListener{

            val personName = personNameEditText.text.toString()

            println(personName)

            val personalInformation: ArrayList<String> = ArrayList()
            personalInformation.add(personName)

            println(personalInformation)

            findNavController().navigate(
                FragmentRegisterStep1Directions.actionFragmentRegisterStep1ToFragmentRegisterStep2().actionId,
                bundleOf("info" to personalInformation))
        }

    }
}