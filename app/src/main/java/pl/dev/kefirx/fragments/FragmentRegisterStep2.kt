package pl.dev.kefirx.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import pl.dev.kefirx.R
import pl.dev.kefirx.databinding.FragmentRegisterStep2Binding

class FragmentRegisterStep2 : Fragment() {

    private lateinit var binding: FragmentRegisterStep2Binding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        return inflater.inflate(R.layout.fragment_register_step2, container, false)
    }

    override fun onStart() {
        super.onStart()

        binding = FragmentRegisterStep2Binding.inflate(layoutInflater)

        binding.nextStep3Button.setOnClickListener{

            val levelOfEdu = binding.levelSpinner.selectedItem.toString()

            val personalInformation: ArrayList<String> = arguments?.getStringArrayList("info") as ArrayList<String>
            personalInformation.add(levelOfEdu)

            findNavController().navigate(
                FragmentRegisterStep2Directions.actionFragmentRegisterStep2ToFragmentRegisterStep3().actionId,
                bundleOf("info" to personalInformation))
        }

    }
}